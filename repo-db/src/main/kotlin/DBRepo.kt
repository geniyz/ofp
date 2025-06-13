package site.geniyz.ofp.repo

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import site.geniyz.ofp.rule.RuleAPI
import site.geniyz.ofp.url.IUrl
import site.geniyz.ofp.url.Link

/**
 * Хранилище в СУБД
 */
class DBRepo : IRepository {

    init {
        Database.connect(connectString, user = user, password = password)
        transaction {
            SchemaUtils.create(Links, Rules, Params)
        }

        println("""
            СВОЁ     ХРАНИЛИЩЕ
            В РЕЛЯЦИОННОЙ СУБД
            → $connectString
        """)
    }

    override fun findUrl(code: String): IUrl = transaction {
        val l = Links.selectAll().where {
            Links.code eq code
        }.singleOrNull()

        if(l==null){
            Link.NONE
        }else {
            Link(
                code = l[Links.code],
                rules = Rules.selectAll().where {
                    Rules.linkId eq l[Links.id]
                }.map { r ->
                    RuleAPI(
                        code = r[Rules.code],
                        href = r[Rules.href],
                        params = Params.selectAll().where {
                            Params.ruleId eq r[Rules.id]
                        }.associate { p ->
                            p[Params.key] to p[Params.value]
                        }
                    )
                }
            )
        }
    }

    override fun addUrl(newUrl: IUrl):Unit = transaction {
        val lnkId = Links.insertAndGetId { it[code] = newUrl.code }.value
        newUrl.rules.map { r ->
            val rlId = Rules.insertAndGetId {
                it[linkId] = lnkId
                it[code] = r.code
                it[href] = r.href
            }
            r.params.map { p ->
                Params.insert {
                    it[ruleId] = rlId
                    it[key] = p.key
                    it[value] = p.value
                }
            }
        }
    }

    companion object {
        val connectString = (System.getProperty("SMARTLINKS_CONNECTSTRING") ?: System.getenv("SMARTLINKS_CONNECTSTRING") ?: "jdbc:h2:mem:inMemDB;DB_CLOSE_DELAY=-1")
        val user          = (System.getProperty("SMARTLINKS_USER") ?: System.getenv("SMARTLINKS_USER") ?: "")
        val password      = (System.getProperty("SMARTLINKS_USER") ?: System.getenv("SMARTLINKS_PASSWORD") ?: "")
    }
}
