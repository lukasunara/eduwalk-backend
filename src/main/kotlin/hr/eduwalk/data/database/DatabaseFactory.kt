package hr.eduwalk.data.database

import hr.eduwalk.data.database.table.LocationScoreTable
import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.database.table.QuestionTable
import hr.eduwalk.data.database.table.UsersTable
import hr.eduwalk.data.database.table.WalkScoreTable
import hr.eduwalk.data.database.table.WalkTable
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

    private val defaultWalks = listOf(
        Walk(
            id = "default1",
            title = "Zadar - Poluotok",
            description = "Nauči o ljepotama i bogatoj povijesti grada Zadra",
            creatorId = null,
        ),
        Walk(
            id = "default2",
            title = "Zagrebačke fontane",
            description = "Otkrij zanimljive činjenice o znamenitostima Trnskog naselja u Zagrebu",
            creatorId = null,
        ),
    )

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
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun createDefaultWalks() = defaultWalks.forEach { updateOrCreateWalk(walk = it) }

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
                }
            )
        }
    }
}
