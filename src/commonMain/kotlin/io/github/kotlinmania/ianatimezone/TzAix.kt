// port-lint: source tz_aix.rs
package io.github.kotlinmania.ianatimezone

internal object TzAix {
    fun getTimezoneInner(envReader: (String) -> String? = { null }): Result<String> {
        val tz = envReader("TZ")
        return tz?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}
