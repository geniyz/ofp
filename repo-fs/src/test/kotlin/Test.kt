@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.di.appModule
import site.geniyz.ofp.repo.FilesRepo
import site.geniyz.ofp.repo.IRepository
import site.geniyz.ofp.rule.RuleAPI
import site.geniyz.ofp.url.Link
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FilesRepoTest: AutoCloseKoinTest() {

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Добавляем правило и проверяем, что оно действительно сохраняется и доступно`() {
        startKoin {
            modules(appModule)
            // modules( module { singleOf(::FilesRepo) { bind<IRepository>() } } )
        }

        val testUrl1 = Uuid.random().toString()

        val dir = "/tmp/store-for-smart-links"
        System.setProperty("SMARTLINKS_PATH", dir)

        val repo: IRepository = FilesRepo() // by inject<IRepository>()

        val link = Link(code = "testByHour",
            listOf(
                RuleAPI(
                    code = "site.geniyz.ofp.rule.ByHourRule",
                    href = testUrl1,
                    params = mapOf("hour" to "11"),
                )
            )
        )
        repo.addUrl(link)

        val resp = repo.findUrl(link.code)

        assertEquals(link, resp)

        assert( Path(dir, link.code).exists() )

        assertEquals( json.encodeToString(link), Path(dir, link.code).readText() )

    }
}