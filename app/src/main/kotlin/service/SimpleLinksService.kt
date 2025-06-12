package site.geniyz.ofp.service

import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.url.*
import site.geniyz.ofp.rule.*
import site.geniyz.ofp.repo.*

import org.koin.core.component.*
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import site.geniyz.ofp.json

/**
 * Простой сервис работы с ссылками
 * без изысков
 */
class SimpleLinksService : ILinksService {

    override var repo: IRepository = get() // получить реализацию из IoC

    override fun save(
        context: Context,
        urlData: String,
    ):IUrl {
        val link: IUrl = json.decodeFromString<Link>(urlData) // получить объект из пришедшего JSON
        repo.addUrl(link) // добавить этот объект в хранилище
        return repo.findUrl(link.code)!!
    }

    override fun get(
        context: Context,
        linkCode: String,
    ) : String? {
        repo.findUrl(linkCode).rules
            .map {
                // получить из IoC класс, отвечающий за проверку
                // передать ему в конструктор пришедший контекст и параметры указанные в правиле
                // val test by inject<IRule>(named(it.code)){ parametersOf(context, Context(it.params)) }
                val test = get<IRule>(named(it.code)){ parametersOf(context, Context(it.params)) }
                // вызвать обработку этого правила
                // если true — значит это, что и требуется
                if( test.execute() ) return it.href
            }
        return null // нет ни чего подходящего
    }
}
