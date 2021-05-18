package EventSourcingScala3Demo
package domain

import scala.reflect.Typeable

/**
 * Typeable[T] is a trait that enforces that T can be type tested at runtime.
 * Useful when using generics and ad-hoc unions
 */
case class EventEnvelope[T: Typeable](
  id: String,
  payload: T
) 

type AggregateId = String