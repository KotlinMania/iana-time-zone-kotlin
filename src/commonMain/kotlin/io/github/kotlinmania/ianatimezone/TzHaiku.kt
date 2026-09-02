// port-lint: source tz_haiku.rs
package io.github.kotlinmania.ianatimezone

internal object TzHaiku {
    fun getTimezoneInner(haikuProvider: () -> String? = { null }): Result<String> {
        val tz = haikuProvider()
        return tz?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}
