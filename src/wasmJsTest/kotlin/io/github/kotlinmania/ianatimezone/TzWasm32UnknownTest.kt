// port-lint: tests tz_wasm32_unknown.rs
package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertTrue

class TzWasm32UnknownTest {
    @Test
    fun pass() {
        val tz = TzWasm32Unknown.getTimezoneInner().getOrThrow()
        assertTrue(tz.isNotBlank(), "tz should not be blank")
        println("tz=$tz")
    }
}
