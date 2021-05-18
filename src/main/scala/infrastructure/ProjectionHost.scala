package EventSourcingScala3Demo
package infrastructure

import domain.*
import cats.*
import cats.implicits.*
import scala.concurrent.Future
import scala.collection.mutable.ListBuffer
import scala.reflect.Typeable

/**
 * Container object to mutate state from a single owner
 */
case class StateMutator[S, E, F[_]: Monad](
  var inner: S, // var means mutable variable
  projection: Projection[S, E, F]
) extends ReadView[S]{

  def update(event: EventEnvelope[E]): F[Unit] = 
    for newState <- projection.applyState(inner, event)
    yield inner = newState

  def state: S = inner
}

class ProjectionHost[F[_]: Monad]: 
  var projectionStates: ListBuffer[StateMutator[?, ?, F]] = ListBuffer()

  /**
   * Add projection giving a ReadView[S] (viewer object) back to inspect the state
   */
  def addProjection[S, E: Typeable](projection: Projection[S, E, F]): ReadView[S] = 
    val wrappedState = StateMutator(projection.zero, projection)
    projectionStates += wrappedState
    wrappedState

  /**
   * Run all the events previously added with the specified events
   */
  def runAll[E](events: List[EventEnvelope[E]]): F[Unit] = 
    val psList = projectionStates.toList

    // load snapshots
    psList.foreachM { ps => 
      for snapshot <- ps.projection.fromSnapshot
      yield ps.inner = snapshot
    }

    // start projecting
    events.foreachM { event => 
      psList.foreachM { ps => 
        import ps.projection.given

        event.payload match 
          case consumableEvent: ps.projection.Event => 
            ps.update(event.asInstanceOf[EventEnvelope[ps.projection.Event]])
          case _ => 
            { println(s"Skipping event: ${event.payload.getClass.getSimpleName} for state: ${ps.state.getClass.getCanonicalName}") }.pure
      }
    }