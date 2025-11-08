@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import kotlin.time.Clock
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.rule.AbsoluteRule
import site.geniyz.ofp.rule.ByHourRule
import java.time.LocalDateTime
import java.util.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalTime::class)
class RulesTest : AutoCloseKoinTest() {
    @Test
    fun `Absolute должен всегда срабатывать в true`() {
        assert(AbsoluteRule().execute())
    }

    @Test
    fun `ByHour отрабатывает корректно текущее время`() {
        val currentHour = LocalDateTime.ofInstant(Clock.System.now().toJavaInstant(), TimeZone.getDefault().toZoneId() ).hour

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
