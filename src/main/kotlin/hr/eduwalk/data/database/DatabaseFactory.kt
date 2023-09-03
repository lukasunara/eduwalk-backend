package hr.eduwalk.data.database

import hr.eduwalk.data.database.table.LocationScoreTable
import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.database.table.QuestionTable
import hr.eduwalk.data.database.table.UsersTable
import hr.eduwalk.data.database.table.WalkScoreTable
import hr.eduwalk.data.database.table.WalkTable
import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.Walk
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object DatabaseFactory {

    fun init() {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcURL = "jdbc:sqlite:./build/EduWalk"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(
                UsersTable,
                WalkTable,
                LocationTable,
                QuestionTable,
                WalkScoreTable,
                LocationScoreTable,
            )
            createDefaultWalks()
            createDefaultLocations()
            createDefaultQuestions()
//            createDefaultWalkScores()
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun createDefaultWalks() = DefaultData.defaultWalks.forEach { updateOrCreateWalk(walk = it) }

    private fun updateOrCreateWalk(walk: Walk) {
        val existingWalk = WalkTable.select { WalkTable.id eq walk.id }.singleOrNull()
        if (existingWalk == null) {
            WalkTable.insert {
                it[id] = walk.id
                it[title] = walk.title
                it[description] = walk.description
                it[creatorId] = walk.creatorId
            }
        } else {
            WalkTable.update(
                where = { WalkTable.id eq walk.id },
                body = {
                    it[title] = walk.title
                    it[description] = walk.description
                },
            )
        }
    }

    private fun createDefaultLocations() =
        DefaultData.defaultLocations.forEach { updateOrCreateLocation(location = it) }

    private fun updateOrCreateLocation(location: Location) {
        location.id ?: return

        val existingLocation = LocationTable.select { LocationTable.id eq location.id }.singleOrNull()
        if (existingLocation == null) {
            LocationTable.insert {
                it[id] = location.id
                it[latitude] = location.latitude
                it[longitude] = location.longitude
                it[title] = location.title
                it[description] = location.description
                it[imageBase64] = location.imageBase64
                it[thresholdDistance] = location.thresholdDistance
                it[walkId] = location.walkId
            }
        } else {
            LocationTable.update(
                where = { LocationTable.id eq location.id },
                body = {
                    it[latitude] = location.latitude
                    it[longitude] = location.longitude
                    it[title] = location.title
                    it[description] = location.description
                    it[imageBase64] = location.imageBase64
                    it[thresholdDistance] = location.thresholdDistance
                },
            )
        }
    }

    private fun createDefaultQuestions() =
        DefaultData.defaultQuestions.forEach { updateOrCreateQuestion(question = it) }

    private fun updateOrCreateQuestion(question: Question) {
        val existingQuestion = QuestionTable.select { QuestionTable.id eq question.id }.singleOrNull()

        if (existingQuestion == null) {
            QuestionTable.insert {
                it[questionText] = question.questionText
                it[answer0] = question.answers[0]
                it[answer1] = question.answers[1]
                it[answer2] = question.answers.getOrNull(2)
                it[answer3] = question.answers.getOrNull(3)
                it[answer4] = question.answers.getOrNull(4)
                it[correctAnswer] = question.correctAnswer
                it[locationId] = question.locationId
            }
        } else {
            QuestionTable.update(
                where = { QuestionTable.id eq question.id },
                body = {
                    it[questionText] = question.questionText
                    it[answer0] = question.answers[0]
                    it[answer1] = question.answers[1]
                    it[answer2] = question.answers.getOrNull(2)
                    it[answer3] = question.answers.getOrNull(3)
                    it[answer4] = question.answers.getOrNull(4)
                    it[correctAnswer] = question.correctAnswer
                }
            )
        }
    }

//    private fun createDefaultWalkScores() =
//        DefaultData.defaultWalkScores.forEach { updateOrCreateWalkScore(walkScore = it) }
//
//    private fun updateOrCreateWalkScore(walkScore: WalkScore) {
//        val existingWalkScore = WalkScoreTable.select {
//            (WalkScoreTable.userId eq walkScore.userId) and (WalkScoreTable.walkId eq walkScore.walkId)
//        }.singleOrNull()
//
//        if (existingWalkScore == null) {
//            WalkScoreTable.insert {
//                it[walkId] = walkScore.walkId
//                it[userId] = walkScore.userId
//                it[score] = walkScore.score
//            }
//        } else {
//            WalkScoreTable.update(
//                where = {
//                    (WalkScoreTable.userId eq walkScore.userId) and (WalkScoreTable.walkId eq walkScore.walkId)
//                },
//                body = { it[score] = walkScore.score }
//            )
//        }
//    }
}
