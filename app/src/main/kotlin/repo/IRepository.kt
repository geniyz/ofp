package site.geniyz.ofp.repo

import site.geniyz.ofp.url.IUrl

/**
 * «Хранилище ссылок»
 */
interface IRepository {
    /**
     * найти ссылку по коду
     */
    fun findUrl(code : String): IUrl?

    /**
     * добавить ссылку
     */
    fun addUrl(newUrl : IUrl)
}
