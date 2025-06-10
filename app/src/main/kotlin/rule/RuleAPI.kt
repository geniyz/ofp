package site.geniyz.ofp.rule

import kotlinx.serialization.Serializable

@Serializable
data class RuleAPI(
    val code: String = "",
    val href: String = "",
    val params: Map<String, String> = emptyMap(),
)
