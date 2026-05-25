// port-lint: source tz_haiku.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezonehaiku.getTimezone as haikuGetTimezone

internal object TzHaiku {
    fun getTimezoneInner(): Result<String> =
        haikuGetTimezone()?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError)
}

