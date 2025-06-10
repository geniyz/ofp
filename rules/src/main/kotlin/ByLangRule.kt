package site.geniyz.ofp.rule

import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject

/**
 * Правило проверки языка
 *
 */
class ByLangRule(
    override val context: Context = Context.NONE,
    override val params: UObject,
) : IRule {
    override fun execute(): Boolean {
        val byContext = (context["Accept-Language"] as List<String>).first().split(",").first()
        val byParams = params["Language"] as String

        return byParams == byContext
    }
}
