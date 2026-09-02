// port-lint: source tz_wasm32_unknown.rs
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
 * Returns the timezone property of Intl.DateTimeFormat resolved options.
 *
 * Interops directly with the standard ECMA-402 Intl namespace, which is
 * present in every modern browser and in Node.js >=14.
 */
private fun jsIntlDateTimeFormatTimeZone(): String? =
    js("(new Intl.DateTimeFormat()).resolvedOptions().timeZone") as String?
