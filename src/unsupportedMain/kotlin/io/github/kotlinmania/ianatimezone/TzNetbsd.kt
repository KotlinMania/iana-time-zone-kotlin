// port-lint: source tz_netbsd.rs
package io.github.kotlinmania.ianatimezone

internal object TzNetbsd {
    fun getTimezoneInner(): Result<String> {
        val prefixes = listOf(
            "/usr/share/zoneinfo/",
            "../usr/share/zoneinfo/",
        )
        var link = readSystemLink("/etc/localtime").getOrElse { return Result.failure(it.toGetTimezoneError()) }
        for (prefix in prefixes) {
            if (link.startsWith(prefix)) {
                link = link.removePrefix(prefix)
                return Result.success(link)
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString)
    }
}

