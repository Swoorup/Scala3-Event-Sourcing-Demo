/** Higher Kinded Type Demo
 **/

/**
 * Worksheet file in scala is conceptually similar to fsx scripts and Jupyter notebooks
 * It can inline stdout and intermediate variable values in the editor.
 * 
 * It can also reuse current project files as an import.
 */
import scala.collection.mutable.ListBuffer
import scala.reflect.Typeable
import cats.*
import cats.implicits.*
import EventSourcingScala3Demo.domain.*
import EventSourcingScala3Demo.projections.*
import EventSourcingScala3Demo.infrastructure.*
import cats.data.EitherT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global


case class Customer(name: String, height: Int)

def validateName[F[_]: Monad] (name: String): F[Either[String, String]] =  {
  if name.isEmpty then Left( "Name can't be empty")
  else Right(name)
}.pure

def validateHeight (height: Int): Either[String, Int] =
  if height > 0 then Right(height)
  else Left("Everything has a height")

def validateCustomerForm[F[_]: Monad] (name: String, height: Int): F[Either[String, Customer]] =
  (for 
    validName <- EitherT(validateName(name))
    validHeight <- EitherT.fromEither(validateHeight(height))
  yield
    Customer(validName, validHeight)).value

// Id is a Higher-Kinded Type alias, i.e type Id[A] = A
val idForm = validateCustomerForm[Id]("John", 121)
// or anything that obeys the Monad property
val futureForm = validateCustomerForm[Future]("Sam", 9)
