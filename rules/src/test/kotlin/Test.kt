@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.*
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject
import site.geniyz.ofp.rule.ByLangRule
import site.geniyz.ofp.rule.IRule
import site.geniyz.ofp.url.Link
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

class ByLangRuleTest : AutoCloseKoinTest() {
    @Test
    fun `Добавить и проверить`() = testApplication {

        application {
            main()

            loadKoinModules( module {
                factory( named(ByLangRule::class.qualifiedName!!) ){
                        (c: Context, p: UObject) -> ByLangRule(c, p) as IRule
                }
            })

            routing {
                get("/ru") { call.respondText("Русский") }
                get("/uk") { call.respondText("Не русский") }
            }
        }

        val data = """
                {
                    code: "by-language",
                    rules: [
                        { href: "/ru", code: "${ByLangRule::class.qualifiedName}", params: {"Language": "ru-RU"} },
                        { href: "/uk", code: "${ByLangRule::class.qualifiedName}", params: {"Language": "en-UK"} },
                    ]
                }
            """.trimIndent()
        val respAdd = client.post("/"){ setBody(data) }
        assertEquals(HttpStatusCode.Created, respAdd.status)
        assertEquals(json.decodeFromString<Link>(data), json.decodeFromString<Link>(respAdd.bodyAsText()))

        val respRu = client.get("/by-language"){
            header("Accept-Language", "ru-RU")
        }
        assertEquals("Русский", respRu.bodyAsText())

        val respDe = client.get("/by-language"){
            header("Accept-Language", "de-DE")
        }
        assertEquals("NOTFOUND", respDe.bodyAsText())
        assertEquals(404, respDe.status.value)

    }
}
