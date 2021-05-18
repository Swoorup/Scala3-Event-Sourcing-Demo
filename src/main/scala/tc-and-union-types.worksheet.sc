/** Demonstrating use of Type Class and Union Types with generics
 **/

// Type class and union type example
import scala.language.implicitConversions
import scala.reflect.Typeable

// function which takes Float or generic type T and returns T
// There are 2 constraints in this function.
// 1. T: Typeable, which is the same as `(using Typeable[T])` - to allow runtime type testing of T
// 2. Conversion[Float, T] - to allow implicit conversions of Float to T
def mapToT[T: Typeable](using Conversion[Float, T])(event: Float | T): T = 
  event match 
    case se: Float => se // implicit conversion to T using the type class
    case t: T => t

// Type class implementation for Conversion[Float, T] which is implicitly passed as long it is in scope.
// Other files can import this type class using `import FileX.given`
given Conversion[Float, Int] = v => v.toInt

// Conversion[Float, Int] type class is passed implicitly as long as an instance is in scope
mapToT(121)