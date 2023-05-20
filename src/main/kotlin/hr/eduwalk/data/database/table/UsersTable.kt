package hr.eduwalk.data.database.table

import hr.eduwalk.data.model.UserRole
import org.jetbrains.exposed.sql.Table

object UsersTable : Table() {

    val username = varchar(name = "username", length = 50)
    val role = enumeration<UserRole>(name = "role").default(defaultValue = UserRole.STUDENT)

    override val primaryKey = PrimaryKey(firstColumn = username)
}
