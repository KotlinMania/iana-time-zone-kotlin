// port-lint: source tz_haiku.rs
package io.github.kotlinmania.ianatimezone

internal object TzHaiku {
    fun getTimezoneInner(): Result<String> =
        haikuTimezone()?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError)
}

