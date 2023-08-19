package hr.eduwalk.data.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object WalkScoreTable : Table() {

    val userId = varchar(name = "user_id", length = 50)
        .index(isUnique = false)
        .references(
            ref = UsersTable.username,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val walkId = varchar(name = "walk_id", length = 8)
        .index(isUnique = false)
        .references(
            ref = WalkTable.id,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val score = integer(name = "score").nullable().default(defaultValue = null)

    override val primaryKey = PrimaryKey(userId, walkId)
}
