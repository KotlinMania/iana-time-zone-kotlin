// port-lint: source tz_haiku.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezonehaiku.getTimezone as haikuGetTimezone

internal object TzHaiku {
    fun getTimezoneInner(haikuProvider: () -> String? = { haikuGetTimezone() }): Result<String> {
        val tz = haikuProvider()
        return tz?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}
