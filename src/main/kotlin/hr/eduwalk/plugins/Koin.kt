package hr.eduwalk.plugins

import hr.eduwalk.data.dao.JoinDaoImpl
import hr.eduwalk.data.dao.LocationDaoImpl
import hr.eduwalk.data.dao.LocationScoreDaoImpl
import hr.eduwalk.data.dao.QuestionDaoImpl
import hr.eduwalk.data.dao.UserDaoImpl
import hr.eduwalk.data.dao.WalkDaoImpl
import hr.eduwalk.data.dao.WalkScoreDaoImpl
import hr.eduwalk.domain.interfaces.IJoinDao
import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.interfaces.ILocationScoreDao
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.usecase.join.GetLocationsWithScores
import hr.eduwalk.domain.usecase.location.DeleteLocation
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
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            userModule,
            walkModule,
            locationModule,
            questionModule,
            walkScoreModule,
            locationScoreModule,
            joinModule,
        )
    }
}

val userModule = module {
    single<IUserDao> { UserDaoImpl() }

    single { GetUser(get()) }
    single { DeleteUser(get()) }
    single { GetOrInsertUser(get()) }
}

val walkModule = module {
    single<IWalkDao> { WalkDaoImpl() }

    single { InsertWalk(get()) }
    single { UpdateWalk(get()) }
    single { DeleteWalk(get()) }
    single { GetWalkById(get()) }
}

val locationModule = module {
    single<ILocationDao> { LocationDaoImpl() }

    single { DeleteLocation(get()) }
    single { GetWalkLocations(get()) }
    single { UpdateOrInsertLocation(get()) }
}

val questionModule = module {
    single<IQuestionDao> { QuestionDaoImpl() }

    single { DeleteQuestion(get()) }
    single { GetLocationQuestions(get()) }
    single { UpdateOrInsertQuestion(get()) }
}

val walkScoreModule = module {
    single<IWalkScoreDao> { WalkScoreDaoImpl() }

    single { GetDefaultWalks(get()) }
    single { GetWalkScoreTop5(get()) }
    single { GetWalkScoreForUser(get()) }
    single { UpdateOrInsertWalkScore(get()) }
    single { GetUserParticipatedWalkIds(get()) }
}

val locationScoreModule = module {
    single<ILocationScoreDao> { LocationScoreDaoImpl() }

    single { GetLocationScoreForUser(get()) }
    single { UpdateOrInsertLocationScore(get()) }
}

val joinModule = module {
    single<IJoinDao> { JoinDaoImpl() }

    single { GetLocationsWithScores(get()) }
}
