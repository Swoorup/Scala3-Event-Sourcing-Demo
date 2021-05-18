// DUs
enum Shape: 
  case Rectangle(width: Float, height: Float)
  case Circle(radius: Float)


def getArea(shape: Shape) = shape match 
  case Shape.Rectangle(width, height) => width * height
  case Shape.Circle(radius) => ???


// Records
case class Point(x: Float, y: Float, z: Float)

// Parameterised enums
enum Color(val rgb: Int):
  case Red   extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue  extends Color(0x0000FF)

val c = Color.Red 
c.rgb

