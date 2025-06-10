package site.geniyz.ofp.url

import site.geniyz.ofp.rule.RuleAPI

/**
 * «Умная ссылка»
 */
interface IUrl {
    /**
     * код ссылки, собственно как-бы сама ссылка
     */
    val code: String

    /**
     * Набор «шаблонов правил»
     * при каких условиях куда она ведёт
     */
    var rules: List<RuleAPI>
}
