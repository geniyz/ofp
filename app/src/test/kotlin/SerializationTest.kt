@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import site.geniyz.ofp.rule.RuleAPI
import site.geniyz.ofp.url.Link
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

class SerializationTest : AutoCloseKoinTest() {
    @Test
    fun `RuleAPI  serialization`() {
        assertEquals(
            RuleAPI(code = "test", href = "geniyz.site", params = mapOf("a" to "b", "c" to "d")),
            json.decodeFromString<RuleAPI>(
                """
                {
                    "code": "test",
                    "href": "geniyz.site",
                    "params": {
                        "a": "b",
                        "c": "d"
                    }
                }
            """.trimIndent())
        )
    }

    @Test
    fun `Link serialization`() {
        val lnk1 = Link(
            code = "link",
            rules = listOf (RuleAPI(
                code = "test",
                href = "geniyz.site",
                params = mapOf("a" to "b", "c" to "d")
            )),
        )

        assertEquals(
            lnk1,
            json.decodeFromString<Link>(
                """
               {
                   "code": "link",
                   "rules": [
                       {
                           "code": "test",
                           "href": "geniyz.site",
                           "params": {
                                "a": "b",
                                "c": "d"
                           }
                       }
                   ]
               }
            """.trimIndent())
        )
    }

}
