@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.di.appModule
import site.geniyz.ofp.repo.IRepository
import site.geniyz.ofp.repo.MemoRepo
import site.geniyz.ofp.rule.ByHourRule
import site.geniyz.ofp.rule.RuleAPI
import site.geniyz.ofp.url.Link
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MemoRepoTest: AutoCloseKoinTest() {

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Добавляем ByHour и проверяем, что оно срабатывает`() {
        startKoin { modules(appModule) }

        val testUrl1 = Uuid.random().toString()

        val repo: IRepository = MemoRepo()

        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        val link = Link(code = "testByHour",
            listOf(
                RuleAPI(
                    code = ByHourRule::class.qualifiedName!!, // "site.geniyz.ofp.rule.ByHourRule",
                    href = testUrl1,
                    params = mapOf("hour" to "${currentHour-1};${currentHour};${currentHour+1}" ),
                )
            )
        )
        repo.addUrl(link)

        val resp = repo.findUrl(link.code)
        assertEquals(link, resp)

        val exception = assertThrows<Throwable> { repo.addUrl(link) }
        assertEquals("Адрес «testByHour» уже занят", exception.message)
    }
}
