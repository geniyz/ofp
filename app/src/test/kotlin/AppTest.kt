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
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.rule.ByHourRule
import site.geniyz.ofp.url.Link
import kotlin.test.assertEquals

class AppTest : AutoCloseKoinTest() {
    @Test
    fun `Добавить и проверить`() = testApplication {
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
}
