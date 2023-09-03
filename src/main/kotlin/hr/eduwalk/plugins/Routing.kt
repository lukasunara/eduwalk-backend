package hr.eduwalk.plugins

import hr.eduwalk.domain.usecase.join.GetLocationsWithScores
import hr.eduwalk.domain.usecase.join.GetWalksWithScores
import hr.eduwalk.domain.usecase.location.DeleteLocation
import hr.eduwalk.domain.usecase.location.GetLocation
import hr.eduwalk.domain.usecase.location.GetWalkLocations
import hr.eduwalk.domain.usecase.location.UpdateOrInsertLocation
import hr.eduwalk.domain.usecase.locationscore.GetLocationScoreForUser
import hr.eduwalk.domain.usecase.locationscore.UpdateOrInsertLocationScore
import hr.eduwalk.domain.usecase.question.DeleteQuestion
import hr.eduwalk.domain.usecase.question.GetLocationQuestions
import hr.eduwalk.domain.usecase.question.UpdateOrInsertQuestion
import hr.eduwalk.domain.usecase.user.DeleteUser
import hr.eduwalk.domain.usecase.user.GetOrInsertUser
import hr.eduwalk.domain.usecase.user.GetUser
import hr.eduwalk.domain.usecase.walk.DeleteWalk
import hr.eduwalk.domain.usecase.walk.GetDefaultWalks
import hr.eduwalk.domain.usecase.walk.GetWalkById
import hr.eduwalk.domain.usecase.walk.InsertWalk
import hr.eduwalk.domain.usecase.walk.UpdateWalk
import hr.eduwalk.domain.usecase.walkscore.GetUserParticipatedWalkIds
import hr.eduwalk.domain.usecase.walkscore.GetWalkScoreForUser
import hr.eduwalk.domain.usecase.walkscore.GetWalkScoreTop5
import hr.eduwalk.domain.usecase.walkscore.UpdateOrInsertWalkScore
import hr.eduwalk.routes.joinRoutes
import hr.eduwalk.routes.locationRoutes
import hr.eduwalk.routes.locationScoreRoutes
import hr.eduwalk.routes.questionRoutes
import hr.eduwalk.routes.userRoutes
import hr.eduwalk.routes.walkRoutes
import hr.eduwalk.routes.walkScoreRoutes
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
        val getDefaultWalks by inject<GetDefaultWalks>()
        walkRoutes(
            insertWalk = insertWalk,
            updateWalk = updateWalk,
            deleteWalk = deleteWalk,
            getWalkById = getWalkById,
            getDefaultWalks = getDefaultWalks,
        )

        val deleteLocation by inject<DeleteLocation>()
        val getWalkLocations by inject<GetWalkLocations>()
        val updateOrInsertLocation by inject<UpdateOrInsertLocation>()
        locationRoutes(
            updateOrInsertLocation = updateOrInsertLocation,
            deleteLocation = deleteLocation,
            getWalkLocations = getWalkLocations,
        )

        val deleteQuestion by inject<DeleteQuestion>()
        val getLocationQuestions by inject<GetLocationQuestions>()
        val updateOrInsertQuestion by inject<UpdateOrInsertQuestion>()
        questionRoutes(
            deleteQuestion = deleteQuestion,
            getLocationQuestions = getLocationQuestions,
            updateOrInsertQuestion = updateOrInsertQuestion,
        )

        val getWalkScoreTop5 by inject<GetWalkScoreTop5>()
        val getWalkScoreForUser by inject<GetWalkScoreForUser>()
        val updateOrInsertWalkScore by inject<UpdateOrInsertWalkScore>()
        val getUserParticipatedWalkIds by inject<GetUserParticipatedWalkIds>()
        walkScoreRoutes(
            getWalkScoreTop5 = getWalkScoreTop5,
            getWalkScoreForUser = getWalkScoreForUser,
            updateOrInsertWalkScore = updateOrInsertWalkScore,
            getUserParticipatedWalkIds = getUserParticipatedWalkIds,
        )

        val getLocation by inject<GetLocation>()
        val getLocationScoreForUser by inject<GetLocationScoreForUser>()
        val updateOrInsertLocationScore by inject<UpdateOrInsertLocationScore>()
        locationScoreRoutes(
            getLocationScoreForUser = getLocationScoreForUser,
            updateOrInsertLocationScore = updateOrInsertLocationScore,
            updateOrInsertWalkScore = updateOrInsertWalkScore,
            getLocation = getLocation,
            getWalkScoreForUser = getWalkScoreForUser,
        )

        val getLocationsWithScores by inject<GetLocationsWithScores>()
        val getWalksWithScores by inject<GetWalksWithScores>()
        joinRoutes(
            getLocationsWithScores = getLocationsWithScores,
            getWalksWithScores = getWalksWithScores,
            getWalkLocations = getWalkLocations,
        )
    }
}
