package EventSourcingScala3Demo
package domain

enum DistrictEvent:
  case DistrictCreated(name: String, city: String)
  case DistrictGeometryUpdated(name: String)
  case DistrictDeleted()
