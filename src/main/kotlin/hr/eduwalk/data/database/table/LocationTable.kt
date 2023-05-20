package hr.eduwalk.data.database.table

import hr.eduwalk.data.model.Location
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object LocationTable : Table() {

    val id = integer(name = "id").autoIncrement()
    val latitude = varchar(name = "latitude", length = 20)
    val longitude = varchar(name = "longitude", length = 20)
    val title = varchar(name = "title", length = 200)
    val description = text(name = "description").nullable()
    val imageBase64 = text(name = "image_base64").nullable()
    val thresholdDistance = integer(name = "threshold_distance").default(Location.DEFAULT_THRESHOLD_DISTANCE)
    val walkId = varchar(name = "walk_id", length = 8)
        .index(isUnique = false)
        .references(
            ref = WalkTable.id,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )

    override val primaryKey = PrimaryKey(firstColumn = id)
}
