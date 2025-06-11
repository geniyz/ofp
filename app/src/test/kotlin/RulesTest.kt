@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.rule.AbsoluteRule
import site.geniyz.ofp.rule.ByHourRule
import kotlin.uuid.ExperimentalUuidApi

class RulesTest : AutoCloseKoinTest() {
    @Test
    fun `Absolute должен всегда срабатывать в true`() {
        assert(AbsoluteRule().execute())
    }

    @Test
    fun `ByHour отрабатывает корректно текущее время`() {
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour

        assert(ByHourRule(
            params = Context("hour" to "${currentHour-1};${currentHour};${currentHour+1}")
        ).execute())
    }

    @Test
    fun `ByHour отрабатывает корректно нереальное время (не падает, а возвращает false)`() {
        assert(!ByHourRule(
            params = Context("hour" to "178")
        ).execute())
    }

}
