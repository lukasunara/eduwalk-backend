package hr.eduwalk.data.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ScoreTable : Table() {

    val userId = varchar(name = "user_id", length = 50)
        .index(isUnique = false)
        .references(
            ref = UsersTable.username,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val questionId = integer(name = "question_id")
        .index(isUnique = false)
        .references(
            ref = QuestionTable.id,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )
    val isCorrect = bool(name = "is_correct")

    override val primaryKey = PrimaryKey(userId, questionId)
}
