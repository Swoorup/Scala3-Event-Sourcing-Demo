package EventSourcingScala3Demo
package infrastructure

import domain.*
import cats.*
import cats.implicits.*
import scala.concurrent.Future
import scala.collection.mutable.ListBuffer
import scala.reflect.Typeable

/** Container object to mutate state from a single owner
  */
case class EventReactor[State, Event, F[_]: Monad](
  var inner: State, // var means mutable variable
  projection: Projection[State, Event, F]
) extends ReadView[State] {

  def update(event: EventEnvelope[Event]): F[Unit] =
    for newState <- projection.applyState(inner, event)
    yield inner = newState

  def state: State = inner
}

class ProjectionHost[F[_]: Monad] {
  var projectionStates: ListBuffer[EventReactor[?, ?, F]] = ListBuffer()

  /** Add projection giving a ReadView[S] (viewer object) back to inspect the state
    */
  def addProjection[State, Event: Typeable](projection: Projection[State, Event, F]): ReadView[State] = {
    val wrappedState = EventReactor(projection.zero, projection)
    projectionStates += wrappedState
    wrappedState
  }

  /** Run all the events previously added with the specified events
    */
  def runAll[E](events: List[EventEnvelope[E]]): F[Unit] = {
    val psList = projectionStates.toList

    // load snapshots
    psList.traverse { ps =>
      for snapshot <- ps.projection.fromSnapshot
      yield ps.inner = snapshot
    }

    // start projecting
    events.traverse { event =>
      psList.traverse { ps =>
        import ps.projection.given

        event.payload match
          case consumableEvent: ps.projection.Event =>
            ps.update(event.asInstanceOf[EventEnvelope[ps.projection.Event]])
          case _ =>
            println(s"Skipping event: ${event.payload.getClass.getSimpleName} for state: ${ps.state.getClass.getCanonicalName}").pure
      }
    }.void
  }
}
