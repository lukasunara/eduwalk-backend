package hr.eduwalk.data.database

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcURL = "jdbc:sqlite:./EduWalk"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {

        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}