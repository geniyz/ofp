package site.geniyz.ofp.url

import kotlinx.serialization.Serializable
import site.geniyz.ofp.rule.RuleAPI

@Serializable
data class Link(
    override val code: String,
    override var rules: List<RuleAPI>
): IUrl {
    constructor(o: IUrl): this( code = o.code, rules = o.rules )
}
