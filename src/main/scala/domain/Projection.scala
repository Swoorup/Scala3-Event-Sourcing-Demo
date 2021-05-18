package EventSourcingScala3Demo
package domain

import cats.Monad
import scala.reflect.Typeable

/**
 * Here F[_] is a higher-kinded type. i.e generic of type constructor
 * F as an implementation could be any of the follows:
 *  - IO[T]
 *  - Future[T]
 *  - Future[Either[String, T]]
 */
trait Projection[S, E, F[_]: Monad](using te: Typeable[E]): 
  type State = S
  type Event = E
  given Typeable[Event] = te

  def fromSnapshot: F[State]
  def zero: State
  def applyState(state: State, event: EventEnvelope[Event]): F[State]

trait ReadView[State]: 
  def state: State
