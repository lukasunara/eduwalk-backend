package hr.eduwalk

import hr.eduwalk.data.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import hr.eduwalk.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    configureKoin()
    configureSerialization()
    configureRouting()
}
