package hr.eduwalk.data.database

import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.database.table.QuestionTable
import hr.eduwalk.data.database.table.WalkScoreTable
import hr.eduwalk.data.database.table.UsersTable
import hr.eduwalk.data.database.table.WalkTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

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
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}