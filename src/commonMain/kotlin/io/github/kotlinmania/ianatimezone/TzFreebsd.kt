// port-lint: source tz_freebsd.rs
package io.github.kotlinmania.ianatimezone

internal object TzFreebsd {
    // see https://gitlab.gnome.org/GNOME/evolution-data-server/-/issues/19
    fun getTimezoneInner(fileReader: (String) -> String? = { null }): Result<String> {
        val contents = fileReader("/var/db/zoneinfo")
            ?: return Result.failure(GetTimezoneError.OsError.toBridge())
        // Trim to the correct length without allocating.
        return Result.success(contents.trimEnd())
    }
}
