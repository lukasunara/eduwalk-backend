package hr.eduwalk.plugins

import hr.eduwalk.data.dao.UserDaoImpl
import hr.eduwalk.data.dao.WalkDaoImpl
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.usecase.GetOrInsertUser
import hr.eduwalk.domain.usecase.GetUser
import hr.eduwalk.domain.usecase.GetWalkById
import hr.eduwalk.domain.usecase.InsertWalk
import hr.eduwalk.domain.usecase.UpdateWalk
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(userModule, walkModule)
    }
}

val userModule = module {
    single<IUserDao> { UserDaoImpl() }

    single { GetUser(get()) }
    single { GetOrInsertUser(get()) }
}

val walkModule = module {
    single<IWalkDao> { WalkDaoImpl() }

    single { InsertWalk(get()) }
    single { UpdateWalk(get()) }
    single { GetWalkById(get()) }
}
