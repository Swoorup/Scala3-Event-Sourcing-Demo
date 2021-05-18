package EventSourcingScala3Demo

import cats.*
import cats.implicits.*
import scala.concurrent.Future

/**
 * For all types that satisfies the property of being `Foldable`, 
 * Add extension methods defined as follows
 * i.e Option[T], List[T], Array[T], Seq[T]
 */
extension [F[_]: Foldable, T](xs: F[T])
  def foreachFuture(f: T => Future[Unit]): Future[Unit] = 
    xs.foldLeft(Future.unit) { (last, element) => 
      for 
        _ <- last
        result <- f(element)
      yield result
    }