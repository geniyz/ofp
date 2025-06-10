package site.geniyz.ofp

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import site.geniyz.ofp.service.*
import site.geniyz.ofp.di.appModule
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.cntxt.ContextAdapter
import site.geniyz.ofp.core.UObject
import site.geniyz.ofp.di.Loader
import site.geniyz.ofp.repo.IRepository
import site.geniyz.ofp.rule.IRule
import site.geniyz.ofp.url.Link
import java.io.File
import kotlin.jvm.Throws

fun Application.main() {
    install(Koin) { // При старте приложения произвести инициализацию IoC
        slf4jLogger()
        modules(appModule) // произвести первичное дефолтное связывание (см. appModule.kt)

        modules( buildList { // подгрузить из каталога с плагинами/дополнениями/библиотеками:
            Loader.load( "libs", IRule::class.java ) { clazz, constructor ->
                add( module {
                    factory( named(clazz.canonicalName) ) // имя/код правила — это полное наименование Класса
                    { (c: Context, p: UObject) -> constructor!!.call(c, p) as IRule } // вызвать конструктор и передать ему необходимые значения, и привести это всё к IRule
                }) }

            Loader.load( "libs", IRepository::class.java ) { clazz, constructor -> add( module { single { constructor!!.call() as IRepository } }) }
            Loader.load( "libs", ILinksService::class.java ) { clazz, constructor -> add( module { single { constructor!!.call() as ILinksService } }) }
        })
    }

    val service    by inject<ILinksService>() // получить из IoC реализацию ILinksService
    val content404 by inject<Content>(named("content404"))

    routing {
        /**
         * переход по «умной ссылке»
         */
        get("/{linkCode}") {
            try {
                service.get(
                    context  = ContextAdapter(call), // сформировать и передать «контекст проверок»
                    linkCode = call.parameters["linkCode"]!!, // собственно «ссылка»
                )?.let { // если сервис вернул результат — значит есть подходящая цель для перехода
                    call.respondRedirect(it) // осуществить переход
                } ?: call.respondText("$content404", status = HttpStatusCode.NotFound) // если ни чего не найдено — отдать NotFound
            }catch (t: Throwable){
                log.error("""${t.localizedMessage} {} {}""", call.parameters["linkCode"], t)
                call.respondText(t.stackTraceToString(), status = HttpStatusCode.InternalServerError)
            }
        }

        /**
         * добавление «умной ссылки»
         */
        post("/") {
            try {
                val link = Link(service.save(ContextAdapter(call), call.receiveText()))
                call.respondText(json.encodeToString(link), status = HttpStatusCode.Created)
            }catch (t: Throwable){
                log.error("""${t.localizedMessage} {} {}""", call.receiveText(), t)
                call.respondText(t.stackTraceToString(), status = HttpStatusCode.InternalServerError)
            }
        }

        staticResources("/", "static")
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)
