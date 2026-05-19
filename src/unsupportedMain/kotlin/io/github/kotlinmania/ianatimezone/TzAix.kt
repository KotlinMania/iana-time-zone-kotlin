// port-lint: source tz_aix.rs
package io.github.kotlinmania.ianatimezone

internal object TzAix {
    fun getTimezoneInner(): Result<String> =
        environmentVariable("TZ") ?: Result.failure(GetTimezoneError.OsError)
}

