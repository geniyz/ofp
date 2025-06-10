package site.geniyz.ofp.repo

import kotlinx.io.files.SystemTemporaryDirectory
import site.geniyz.ofp.json
import site.geniyz.ofp.url.IUrl
import site.geniyz.ofp.url.Link
import kotlin.io.path.Path
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/**
 * Хранилище на файловой системе
 */
class FilesRepo : IRepository {

    init {
        Path(root).toFile().mkdirs()

        println("""
            СВОЁ      ХРАНИЛИЩЕ
            НА ФАЙЛОВОЙ СИСТЕМЕ
            → $root
        """)
    }

    override fun findUrl(code: String): IUrl? {
            val f = Path(root, code)
            if (f.notExists()) return null // error("Ссылка «$code» не найдена")

            val data = f.readText()
            if (data.isEmpty()) error("Ссылка «$code» плохая (")

            return json.decodeFromString<Link>(data)
    }

    override fun addUrl(newUrl: IUrl) {
        Path(root, newUrl.code).writeText( json.encodeToString( Link(newUrl) ))
    }

    companion object {
        val root = System.getProperty("SMARTLINKS_PATH") ?: System.getenv("SMARTLINKS_PATH") ?: SystemTemporaryDirectory.toString()
    }
}
