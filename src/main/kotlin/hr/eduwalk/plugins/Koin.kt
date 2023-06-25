package hr.eduwalk.plugins

import hr.eduwalk.data.dao.LocationDaoImpl
import hr.eduwalk.data.dao.QuestionDaoImpl
import hr.eduwalk.data.dao.UserDaoImpl
import hr.eduwalk.data.dao.WalkDaoImpl
import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.usecase.location.DeleteLocation
import hr.eduwalk.domain.usecase.location.GetWalkLocations
import hr.eduwalk.domain.usecase.location.UpdateOrInsertLocation
import hr.eduwalk.domain.usecase.question.DeleteQuestion
import hr.eduwalk.domain.usecase.question.GetLocationQuestions
import hr.eduwalk.domain.usecase.question.UpdateOrInsertQuestion
import hr.eduwalk.domain.usecase.user.DeleteUser
import hr.eduwalk.domain.usecase.user.GetOrInsertUser
import hr.eduwalk.domain.usecase.user.GetUser
import hr.eduwalk.domain.usecase.walk.DeleteWalk
import hr.eduwalk.domain.usecase.walk.GetWalkById
import hr.eduwalk.domain.usecase.walk.InsertWalk
import hr.eduwalk.domain.usecase.walk.UpdateWalk
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(userModule, walkModule, locationModule, questionModule)
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
