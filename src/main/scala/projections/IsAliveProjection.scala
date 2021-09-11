/** Check whether a particular entity exists or not Features used: Union Types Higher-Kinded types
  */
package EventSourcingScala3Demo
package projections

import domain.*
import cats.implicits.*
import scala.concurrent.{ExecutionContext, Future}

/** A simple projection that keeps track of whether an entity is active or not
  */
object IsAliveProjection {
  def projection(using ExecutionContext): Projection[Map[AggregateId, Boolean], CreateEvent | DeleteEvent, Future] =
    new:
      def fromSnapshot = Map.empty.pure
      def zero         = Map.empty
      def applyState(state: State, event: EventEnvelope[Event]): Future[State] =
        event.payload match
          case DistrictEvent.DistrictDeleted() | ZoneEvent.ZoneDeleted() =>
            Future(state + ((event.id, false)))
          case DistrictEvent.DistrictCreated(name, city) =>
            Future(state + ((event.id, true)))
          case ZoneEvent.ZoneCreated(name, city) =>
            Future(state + ((event.id, true)))
}
