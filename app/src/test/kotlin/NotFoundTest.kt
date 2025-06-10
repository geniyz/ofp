@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.*
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import site.geniyz.ofp.service.Content
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

class NotFoundTest : AutoCloseKoinTest() {
    @Test
    fun `запрос «-» возвращает ответ с кодом 404 и соответствующим содержимым`() = testApplication {
        application { main() }
        val response = client.get("-")
        val content404: Content by inject(named("content404"))
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("$content404", response.bodyAsText())
    }
}
