package EventSourcingScala3Demo
package projections

import domain.{DistrictEvent, ZoneEvent}

/** Event can only be of type ZoneEvent.ZoneCreated or DistrictEvent.DistrictCreated Note that the inner type is one of the cases of the
  * parent alzebraic data type
  */
type CreateEvent = ZoneEvent.ZoneCreated | DistrictEvent.DistrictCreated

type DeleteEvent = ZoneEvent.ZoneDeleted | DistrictEvent.DistrictDeleted

/** Event can be of all combinations of DistrictEvent type or all combinations of ZoneEvent
  */
type AllEvent = DistrictEvent | ZoneEvent
