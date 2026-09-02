package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlatformParsingTest {
    @Test
    fun testTzIllumosParsing() {
        val sampleInit = """
            TZ=America/New_York
            CMASK=022
        """.trimIndent()
        val res = TzIllumos.parseInitConfig(sampleInit)
        assertTrue(res.isSuccess)
        assertEquals("America/New_York", res.getOrNull())

        val invalid = "CMASK=022\n"
        val invalidRes = TzIllumos.parseInitConfig(invalid)
        assertTrue(invalidRes.isFailure)
    }

    @Test
    fun testTzNetbsdParsing() {
        val absLink = "/usr/share/zoneinfo/Asia/Tokyo"
        val resAbs = TzNetbsd.parseLocaltimeLink(absLink)
        assertTrue(resAbs.isSuccess)
        assertEquals("Asia/Tokyo", resAbs.getOrNull())

        val relLink = "../usr/share/zoneinfo/Europe/London"
        val resRel = TzNetbsd.parseLocaltimeLink(relLink)
        assertTrue(resRel.isSuccess)
        assertEquals("Europe/London", resRel.getOrNull())

        val invalidLink = "/var/db/localtime"
        val resInvalid = TzNetbsd.parseLocaltimeLink(invalidLink)
        assertTrue(resInvalid.isFailure)
    }

    @Test
    fun testTzFreebsdParsing() {
        val res = TzFreebsd.getTimezoneInner { "Europe/Paris\n" }
        assertTrue(res.isSuccess)
        assertEquals("Europe/Paris", res.getOrNull())
    }

    @Test
    fun testTzOhosFromBytesUntilNul() {
        val bytes = "Asia/Shanghai\u0000extra".encodeToByteArray()
        val parsed = TzOhos.fromBytesUntilNul(bytes)
        assertEquals("Asia/Shanghai", parsed)

        val empty = byteArrayOf(0)
        assertEquals("", TzOhos.fromBytesUntilNul(empty))
    }

    @Test
    fun testTzWasm32Emscripten() {
        val res = TzWasm32Emscripten.getTimezoneInner { "UTC" }
        assertTrue(res.isSuccess)
        assertEquals("UTC", res.getOrNull())
    }

    @Test
    fun testTzAix() {
        val res = TzAix.getTimezoneInner { "America/Chicago" }
        assertTrue(res.isSuccess)
        assertEquals("America/Chicago", res.getOrNull())
    }

    @Test
    fun testTzHaiku() {
        val res = TzHaiku.getTimezoneInner { "Pacific/Auckland" }
        assertTrue(res.isSuccess)
        assertEquals("Pacific/Auckland", res.getOrNull())
    }
}
