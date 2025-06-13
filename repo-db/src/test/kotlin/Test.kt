@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.di.appModule
import site.geniyz.ofp.repo.DBRepo
import site.geniyz.ofp.repo.IRepository
import site.geniyz.ofp.rule.RuleAPI
import site.geniyz.ofp.url.Link
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DBRepoTest: AutoCloseKoinTest() {

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Добавляем правило и проверяем, что оно действительно сохраняется и доступно`() {
        startKoin {
            modules(appModule)
            // modules( module { singleOf(::FilesRepo) { bind<IRepository>() } } )
        }

        val testUrl1 = Uuid.random().toString()

        System.setProperty("SMARTLINKS_CONNECTSTRING", "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1")
        System.setProperty("SMARTLINKS_USER", "gnz")
        System.setProperty("SMARTLINKS_PASSWORD", testUrl1)

        val repo: IRepository = DBRepo() // by inject<IRepository>()

        val link = Link(code = "test-path",
            listOf(
                RuleAPI(
                    code = "site.geniyz.ofp.rule.AbsouteRule",
                    href = testUrl1,
                    params = mapOf("hour" to "11"),
                )
            )
        )
        repo.addUrl(link)

        val resp = repo.findUrl(link.code)

        assertEquals(link, resp)

    }
}