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
  def foreachM[M[_]: Monad](f: T => M[Unit]): M[Unit] = 
    xs.foldLeft(().pure) { (last, element) => 
      for 
        _ <- last
        result <- f(element)
      yield result
    }