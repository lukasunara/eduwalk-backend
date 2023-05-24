package hr.eduwalk.data.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object WalkTable : Table() {

    val id = varchar(name = "id", length = 8)
    val title = varchar(name = "title", length = 100)
    val description = text(name = "description").nullable()
    val creatorId = varchar(name = "creator_id", length = 50)
        .index(isUnique = false)
        .references(
            ref = UsersTable.username,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
        .nullable()

    override val primaryKey = PrimaryKey(firstColumn = id)
}
