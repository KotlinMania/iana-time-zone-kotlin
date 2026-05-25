// port-lint: source tz_wasm32_unknown.rs
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.ianatimezone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzWasm32Unknown.getTimezoneInner()
}

internal object TzWasm32Unknown {
    fun getTimezoneInner(): Result<String> {
        val tz = jsIntlDateTimeFormatTimeZone()
        return tz?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}

/**
 * Returns the `timeZone` field of `Intl.DateTimeFormat().resolvedOptions()`.
 *
 * For Kotlin/Wasm-JS the `js(...)` body must be wrapped in `{ … }` because
 * the compiler emits `(args) => BODY` and a bare expression there is not
 * valid JavaScript at statement position. The runtime semantics are
 * identical to the jsMain sibling.
 */
private fun jsIntlDateTimeFormatTimeZone(): String? = js(
    "{ return (new Intl.DateTimeFormat()).resolvedOptions().timeZone; }",
)
