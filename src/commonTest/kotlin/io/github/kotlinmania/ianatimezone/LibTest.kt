// port-lint: tests lib.rs
package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Smoke test that [getTimezone] resolves a non-empty IANA-style name on
 * every host where the test runner executes. Each Kotlin Multiplatform
 * target wires its own [Platform] actual:
 *
 *  - macOS / iOS / tvOS / watchOS -> CoreFoundation CFTimeZoneCopySystem
 *  - Linux                        -> /etc/localtime symlink / /etc/timezone / OpenWRT
 *  - MinGW (Windows)              -> Windows.Globalization.Calendar.GetTimeZone
 *  - Android (JVM)                -> Bionic persist.sys.timezone
 *  - Android Native               -> Bionic via cinterop
 *  - JVM                          -> java.util.TimeZone.getDefault().id
 *  - JS / WasmJS                  -> Intl.DateTimeFormat().resolvedOptions().timeZone
 *  - WasmWASI                     -> WASI preview1 environGet TZ with Etc/UTC fallback
 *
 * The test asserts the call returns [TimezoneResult.Ok] with a non-blank
 * string. It does not pin a specific zone name because the host runner's
 * configured zone varies.
 */
class GetTimezoneTest {

    @Test
    fun getCurrent() {
        when (val result = getTimezone()) {
            is TimezoneResult.Ok ->
                assertTrue(result.name.isNotBlank(), "timezone name should not be blank, got '${result.name}'")
            is TimezoneResult.Failure ->
                fail("getTimezone() should succeed on host runtimes; got ${result.error.displayMessage}")
        }
    }

    @Test
    fun ioErrorCarriesItsMessage() {
        val err: GetTimezoneError = GetTimezoneError.IoError("boom")
        assertTrue(err.displayMessage.contains("boom"))
    }

    @Test
    fun failedParsingStringHasStableMessage() {
        val err: GetTimezoneError = GetTimezoneError.FailedParsingString
        assertTrue(err.displayMessage.contains("FailedParsingString"))
    }

    @Test
    fun osErrorHasStableMessage() {
        val err: GetTimezoneError = GetTimezoneError.OsError
        assertTrue(err.displayMessage.contains("OsError"))
    }
}
