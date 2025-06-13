package site.geniyz.ofp.repo

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object Links : UUIDTable() {
    val code: Column<String> = varchar("code", length = 128).uniqueIndex()
}

object Rules : UUIDTable() {
    val linkId = reference("link_id", Links.id)
    val code: Column<String> = varchar("code", length = 128)
    val href: Column<String> = varchar("href", length = 128)

    val index = uniqueIndex(linkId, code)
}

object Params : UUIDTable() {
    val ruleId = reference("rule_id", Rules.id)
    val key: Column<String> = varchar("key", length = 128)
    val value: Column<String> = varchar("val", length = 4000)

    val index = uniqueIndex(ruleId, key, value)
}
