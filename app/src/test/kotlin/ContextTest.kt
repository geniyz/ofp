@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import org.junit.Test
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.rule.RuleAPI
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

class ContextTest{
    @Test
    fun `get-set`() {

        val x = Context()

        x["a"] = "b"
        assertEquals("b", x["a"])

        x["a"] = "a"
        assertEquals("a", x["a"])

        x["b.c"] = "d"
        assertEquals(mapOf("c" to "d"), x["b"])
        assertEquals("d", x["b.c"])

        assertEquals("a → a; b → {c=d}", "$x")

        assertEquals(null, x[""])

        x[""] = "e"
        assertEquals(null, x[""])

    }

}
