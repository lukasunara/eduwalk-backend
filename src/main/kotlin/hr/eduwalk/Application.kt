package hr.eduwalk

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.plugins.configureKoin
import hr.eduwalk.plugins.configureRouting
import hr.eduwalk.plugins.configureSerialization
import hr.eduwalk.plugins.configureStatusExceptions
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.23.151", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    configureStatusExceptions()
    configureKoin()
    configureSerialization()
    configureRouting()
}
