package hr.eduwalk.data.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object LocationScoreTable : Table() {

    val userId = varchar(name = "user_id", length = 50)
        .index(isUnique = false)
        .references(
            ref = UsersTable.username,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val locationId = long(name = "location_id")
        .index(isUnique = false)
        .references(
            ref = LocationTable.id,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val score = integer(name = "score").nullable().default(defaultValue = null)

    override val primaryKey = PrimaryKey(userId, locationId)
}
