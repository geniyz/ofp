package site.geniyz.ofp.rule

import kotlin.time.Clock
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject
import java.time.LocalDateTime
import java.util.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

/**
 * Правило на основе времени
 * в зависимости от того, сколько сейчас часов
 */
@OptIn(ExperimentalTime::class)
class ByHourRule(
    override val context: Context = Context.NONE,
    override val params: UObject,
) : IRule {

    override fun execute(): Boolean {
        val currentHour = LocalDateTime.ofInstant(Clock.System.now().toJavaInstant(), TimeZone.getDefault().toZoneId() ).hour
        (params["hour"] as String).split(",", ";").forEach {
            if((it.trim()) == "$currentHour") return true
        }
        return false
    }

}
