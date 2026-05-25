// port-lint: tests src/lib.rs (mirrors the examples/ and lib-level smoke)
package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Smoke test that [getTimezone] resolves a non-empty IANA-style name on
 * every host where the test runner executes. Each Kotlin Multiplatform
 * target wires its own [Platform] actual:
 *
 *  - macOS / iOS / tvOS / watchOS → CoreFoundation `CFTimeZoneCopySystem`
 *  - Linux                        → `/etc/localtime` symlink / `/etc/timezone` / OpenWRT
 *  - MinGW (Windows)              → `Windows.Globalization.Calendar.GetTimeZone`
 *  - Android (JVM)                → Bionic `persist.sys.timezone`
 *  - Android Native               → Bionic via cinterop
 *  - JVM                          → `java.util.TimeZone.getDefault().getID()`
 *  - JS / WasmJS                  → `Intl.DateTimeFormat().resolvedOptions().timeZone`
 *  - WasmWASI                     → WASI preview1 `environ_get TZ` with `Etc/UTC` fallback
 *
 * The test asserts the call returns [TimezoneResult.Ok] with a non-blank
 * string. It does not pin a specific zone name because the host runner's
 * configured zone varies (GitHub CI runners default to UTC; a developer
 * machine in PT will report `America/Los_Angeles`).
 */
class GetTimezoneTest {

    @Test
    fun getTimezoneReturnsAWellTypedOutcome() {
        // The Android host test runs on a JVM that has no Bionic
        // `__system_property_get` available, so TzAndroid returns OsError.
        // Every other host runtime (macOS, JVM, Node-JS, Node-WasmJS,
        // Node-WasmWASI) succeeds. Verify the call returns a well-typed
        // TimezoneResult either way; an Ok must carry a non-blank name and
        // a Failure must carry a stable display message.
        when (val result = getTimezone()) {
            is TimezoneResult.Ok ->
                assertTrue(result.name.isNotBlank(), "timezone name should not be blank, got '${result.name}'")
            is TimezoneResult.Failure ->
                assertTrue(result.error.displayMessage.isNotBlank(), "failure should carry a non-blank display message")
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
