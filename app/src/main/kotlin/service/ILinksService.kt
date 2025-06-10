package site.geniyz.ofp.service

import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.repo.*
import org.koin.core.component.*
import site.geniyz.ofp.url.IUrl

/**
 * сервис работы с ссылками
 */
interface ILinksService : KoinComponent {
    /**
     * хранилище ссылок
     */
    var repo: IRepository

    /**
     * сохранить «умную  ссылку»
     * @param context «контекст» в котором будут проверяться правила
     * @param urlData текстовое представление «умной ссылки»
     */
    fun save(
        context: Context,
        urlData: String,
    ): IUrl

    /**
     * получить значение ссылки
     * @param context «контекст» в котором будут проверяться правила
     * @param linkCode «код» ссылки
     *
     * @return null — ни чего не подходит, в противном случае — сообтветствующее значение цели ссылки
     */
    fun get(
        context: Context,
        linkCode: String,
    ) : String?
}
