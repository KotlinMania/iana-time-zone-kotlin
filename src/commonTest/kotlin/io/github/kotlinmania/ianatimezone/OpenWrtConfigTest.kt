// port-lint: tests tz_linux.rs
package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OpenWrtConfigTest {
    @Test
    fun testReadWord() {
        assertEquals(
            "option" to "timezone 'CST-8'\n",
            readWord("       option timezone 'CST-8'\n").getOrThrow(),
        )
        assertEquals(
            "timezone" to "'CST-8'\n",
            readWord("timezone 'CST-8'\n").getOrThrow(),
        )
        assertEquals(
            "CST-8" to "\n",
            readWord("'CST-8'\n").getOrThrow(),
        )
        assertNull(
            readWord("\n").getOrThrow(),
        )
        assertEquals(
            "time 'Zone'" to "",
            readWord("\"time 'Zone'\"").getOrThrow(),
        )
        val err = readWord("'CST-8").exceptionOrNull()
        assertTrue(err is BrokenQuote)
    }
}
