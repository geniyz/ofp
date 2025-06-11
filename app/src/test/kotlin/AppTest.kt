package site.geniyz.ofp

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import site.geniyz.ofp.rule.ByHourRule
import site.geniyz.ofp.service.Content
import site.geniyz.ofp.url.Link
import kotlin.getValue
import kotlin.test.assertEquals

class AppTest : AutoCloseKoinTest() {

    @Test
    fun `запрос «-» возвращает ответ с кодом 404 и соответствующим содержимым`() = testApplication {
        application { main() }
        val response = client.get("-")
        val content404: Content by inject(named("content404"))
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("$content404", response.bodyAsText())
    }

    @Test
    fun `Добавить ссылку с нормальным правилом и проверить его обработку`() = testApplication {
        val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour

        application {
            main()

            routing {
                get("/no") { call.respondText("НЕ") }
                get("/ok") { call.respondText("$currentHour") }
            }
        }

        val data = """
                {
                    code: "by-hour",
                    rules: [
                        { code: "${ByHourRule::class.qualifiedName}", href: "/no", params: {"hour": 33 } },
                        { code: "${ByHourRule::class.qualifiedName}", href: "/ok", params: {"hour": "${currentHour-1};${currentHour};${currentHour+1}"} },
                    ]
                }
            """.trimIndent()

        val respAdd = client.post("/"){ setBody(data) }

        assertEquals(HttpStatusCode.Created, respAdd.status)
        assertEquals( json.decodeFromString<Link>(data), json.decodeFromString<Link>(respAdd.bodyAsText()))

        val resp1 = client.get("/by-hour")
        assertEquals("$currentHour", resp1.bodyAsText())
    }

    @Test
    fun `Добавить ссылку с отсутствующим правилом и проверить его обработку`() = testApplication {
        application { main() }
        val data = """
                {
                    code: "test500",
                    rules: [
                        { code: "какое-то отсутствующее правило", href: "/" },
                    ]
                }
            """.trimIndent()
        val respAdd = client.post("/"){ setBody(data) }

        assertEquals(HttpStatusCode.Created, respAdd.status)

        val resp1 = client.get("/test500")
        assertEquals(HttpStatusCode.InternalServerError, resp1.status)
    }

    @Test
    fun `Попытка добавить ссылку невалидным json`() = testApplication {
        application { main() }
        val data = """
                {
                    code: "test500",
                    бла-бла-бла
                }
            """.trimIndent()
        val respAdd = client.post("/"){ setBody(data) }

        assertEquals(HttpStatusCode.InternalServerError, respAdd.status)
    }

}
