package hr.eduwalk.plugins

import hr.eduwalk.domain.usecase.DeleteUser
import hr.eduwalk.domain.usecase.DeleteWalk
import hr.eduwalk.domain.usecase.GetOrInsertUser
import hr.eduwalk.domain.usecase.GetUser
import hr.eduwalk.domain.usecase.GetWalkById
import hr.eduwalk.domain.usecase.InsertWalk
import hr.eduwalk.domain.usecase.UpdateWalk
import hr.eduwalk.routes.userRoutes
import hr.eduwalk.routes.walkRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        val getUser by inject<GetUser>()
        val deleteUser by inject<DeleteUser>()
        val getOrInsertUser by inject<GetOrInsertUser>()
        userRoutes(getUser = getUser, deleteUser = deleteUser, getOrInsertUser = getOrInsertUser)

        val insertWalk by inject<InsertWalk>()
        val updateWalk by inject<UpdateWalk>()
        val deleteWalk by inject<DeleteWalk>()
        val getWalkById by inject<GetWalkById>()
        walkRoutes(insertWalk = insertWalk, updateWalk = updateWalk, deleteWalk = deleteWalk, getWalkById = getWalkById)
    }
}
