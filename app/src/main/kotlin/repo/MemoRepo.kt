package site.geniyz.ofp.repo

import site.geniyz.ofp.url.IUrl
import site.geniyz.ofp.url.Link

/**
 * Хранилище в памяти
 */
class MemoRepo : IRepository {
    init {
        println("""
            ХРАНИЛИЩЕ ДЕФОЛОТНОЕ
            ПРОСТОЕ   В   ПАМЯТИ
        """)
    }

    private val urls = mutableMapOf<String, IUrl>()

    override fun findUrl(code: String)= urls.getOrDefault(code, Link.NONE)

    override fun addUrl(newUrl: IUrl) {
        if( urls.contains(newUrl.code) ) error("Адрес «${newUrl.code}» уже занят")
        urls[newUrl.code] = newUrl
    }
}
