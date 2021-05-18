package EventSourcingScala3Demo

import domain.*
import projections.*
import infrastructure.*
import cats.*
import cats.implicits.*
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration.*
import projections.*
import scala.util.{Success, Failure}

given ExecutionContext = scala.concurrent.ExecutionContext.global

type AllEvent = DistrictEvent | ZoneEvent 

val dbEvents: List[EventEnvelope[AllEvent]] = List(
  EventEnvelope("district-9",       DistrictEvent.DistrictCreated("district-9", "johansberg")),
  EventEnvelope("district-9",       DistrictEvent.DistrictGeometryUpdated("POLYGON()")),
  EventEnvelope("district-9",       DistrictEvent.DistrictDeleted()),
  EventEnvelope("alien-zone",       ZoneEvent.ZoneCreated("Alien Zone", "johansberg")),
)

/** Useless projection doing nothing */
val uselessProjectionConsumingAllEvents: Projection[Unit, AllEvent, Future] = new: 
  def fromSnapshot = Future { () }
  def zero = ()
  def applyState(state: State, event: EventEnvelope[Event]): Future[Unit] = 
    println(s"Useless Projection Got Event: ${event.payload.getClass.getSimpleName}").pure

@main def hello: Unit = 
  val host = new ProjectionHost[Future]()

  // Create the projection and get a view
  val doesExistView = host.addProjection(IsAliveProjection.projection)

  val uselessProjectionView = host.addProjection(uselessProjectionConsumingAllEvents)
  
  Await.result(host.runAll(dbEvents), 3.seconds)

  // Print the final state
  println(doesExistView.state)
  println(uselessProjectionView.state)