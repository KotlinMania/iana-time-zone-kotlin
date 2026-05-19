// port-lint: source tz_freebsd.rs
package io.github.kotlinmania.ianatimezone

internal object TzFreebsd {
    fun getTimezoneInner(): Result<String> {
        val contents = readSystemText("/var/db/zoneinfo").getOrElse { return Result.failure(it.toGetTimezoneError()) }
        return Result.success(contents.trimEnd())
    }
}

