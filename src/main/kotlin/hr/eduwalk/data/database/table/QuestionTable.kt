package hr.eduwalk.data.database.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object QuestionTable : Table() {

    val id = long(name = "id").autoIncrement()
    val questionText = text(name = "question_text")
    val answer0 = text(name = "answer0")
    val answer1 = text(name = "answer1")
    val answer2 = text(name = "answer2").nullable()
    val answer3 = text(name = "answer3").nullable()
    val answer4 = text(name = "answer4").nullable()
    val correctAnswer = text(name = "correct_answer")
    val locationId = long(name = "location_id")
        .index(isUnique = false)
        .references(
            ref = LocationTable.id,
            onDelete = ReferenceOption.CASCADE,
            onUpdate = ReferenceOption.CASCADE,
        )

    override val primaryKey = PrimaryKey(firstColumn = id)
}
