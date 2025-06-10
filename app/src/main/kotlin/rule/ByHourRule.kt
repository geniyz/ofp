package site.geniyz.ofp.rule

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject

/**
 * Правило на основе времени
 * в зависимости от того, сколько сейчас часов
 */
class ByHourRule(
    override val context: Context = Context.NONE,
    override val params: UObject,
) : IRule {

    override fun execute(): Boolean {
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        (params["hour"] as String).split(",", ";").forEach {
            if((it.trim()) == "$currentHour") return true
        }
        return false
    }

}
