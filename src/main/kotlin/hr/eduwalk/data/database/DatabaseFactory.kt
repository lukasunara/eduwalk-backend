package hr.eduwalk.data.database

import hr.eduwalk.data.database.table.LocationScoreTable
import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.database.table.QuestionTable
import hr.eduwalk.data.database.table.UsersTable
import hr.eduwalk.data.database.table.WalkScoreTable
import hr.eduwalk.data.database.table.WalkTable
import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.Walk
import hr.eduwalk.data.model.WalkScore
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteAll
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
            createDefaultUsers()
            createDefaultWalks()
            createDefaultLocations()
            createDefaultQuestions()
//            deleteLocationScores()
            createDefaultLocationScores()
            createDefaultWalkScores()
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun createDefaultUsers() = DefaultData.defaultUsers.forEach(::updateOrCreateUser)

    private fun updateOrCreateUser(user: User) {
        val existingUser = UsersTable.select { UsersTable.username eq user.username }.singleOrNull()
        if (existingUser == null) {
            UsersTable.insert {
                it[username] = user.username
                it[role] = user.role
            }
        } else {
            UsersTable.update(
                where = { UsersTable.username eq user.username },
                body = { it[role] = user.role },
            )
        }
    }

    private fun createDefaultWalks() = DefaultData.defaultWalks.forEach(::updateOrCreateWalk)

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

    private fun createDefaultLocations() = DefaultData.defaultLocations.forEach(::updateOrCreateLocation)

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
                    it[walkId] = location.walkId
                },
            )
        }
    }

    private fun createDefaultQuestions() =
        DefaultData.defaultQuestions.forEach(::updateOrCreateQuestion)

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
                    it[locationId] = question.locationId
                }
            )
        }
    }

    private fun createDefaultLocationScores() = DefaultData.defaultLocationScores.forEach(::updateOrCreateLocationScore)

    private fun updateOrCreateLocationScore(locationScore: LocationScore) {
        val existingLocationScore = LocationScoreTable.select {
            (LocationScoreTable.userId eq locationScore.userId) and (LocationScoreTable.locationId eq locationScore.locationId)
        }.singleOrNull()

        if (existingLocationScore == null) {
            LocationScoreTable.insert {
                it[locationId] = locationScore.locationId
                it[userId] = locationScore.userId
                it[score] = locationScore.score
            }
        } else {
            LocationScoreTable.update(
                where = {
                    (LocationScoreTable.userId eq locationScore.userId) and (LocationScoreTable.locationId eq locationScore.locationId)
                },
                body = { it[score] = locationScore.score }
            )
        }
    }

    private fun deleteLocationScores() {
        LocationScoreTable.deleteAll()
        WalkScoreTable.deleteAll()
    }

    private fun createDefaultWalkScores() = DefaultData.defaultWalkScores.forEach(::updateOrCreateWalkScore)

    private fun updateOrCreateWalkScore(walkScore: WalkScore) {
        val existingWalkScore = WalkScoreTable.select {
            (WalkScoreTable.userId eq walkScore.userId) and (WalkScoreTable.walkId eq walkScore.walkId)
        }.singleOrNull()

        if (existingWalkScore == null) {
            WalkScoreTable.insert {
                it[walkId] = walkScore.walkId
                it[userId] = walkScore.userId
                it[score] = walkScore.score
            }
        } else {
            WalkScoreTable.update(
                where = {
                    (WalkScoreTable.userId eq walkScore.userId) and (WalkScoreTable.walkId eq walkScore.walkId)
                },
                body = { it[score] = walkScore.score }
            )
        }
    }
}
