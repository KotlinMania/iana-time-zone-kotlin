// port-lint: source tz_wasm32_unknown.rs
package io.github.kotlinmania.ianatimezone

internal object TzWasm32Unknown {
    fun getTimezoneInner(): Result<String> {
        val intl = jsIntlDateTimeFormatResolvedOptions()
        return intl["timeZone"]?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError)
    }

    fun pass() {
        val tz = getTimezoneInner().getOrThrow()
        consoleLog("tz=$tz")
    }
}

