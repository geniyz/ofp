package site.geniyz.ofp.rule

import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject

/**
 * Абсолютное правило
 * всегда выполняется
 * просто безусловно возвращает всегда true
 */
class AbsoluteRule(
    override val context: Context = Context.NONE,
    override val params: UObject = context,
) : IRule {
    override fun execute() = true
}
