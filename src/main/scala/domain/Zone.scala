package EventSourcingScala3Demo
package domain

/**
* Enums are same as Discriminated Unions in F#. 
* https://dotty.epfl.ch/docs/reference/enums/enums.html
*/
enum ZoneEvent:
  case ZoneCreated(name: String, city: String)
  case ZoneGeometryUpdated(name: String)
  case ZoneDeleted()
