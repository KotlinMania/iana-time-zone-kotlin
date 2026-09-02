// port-lint: source tz_netbsd.rs
package io.github.kotlinmania.ianatimezone

internal object TzNetbsd {
    // see https://www.cyberciti.biz/faq/openbsd-time-zone-howto/

    // This is a backport of the Linux implementation.
    // NetBSDs is less than thorough how the softlink should be set up.

    private val PREFIXES = listOf(
        "/usr/share/zoneinfo/", // absolute path
        "../usr/share/zoneinfo/", // relative path
    )

    fun getTimezoneInner(linkReader: (String) -> String? = { null }): Result<String> {
        val s = linkReader("/etc/localtime")
            ?: return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
        return parseLocaltimeLink(s)
    }

    fun parseLocaltimeLink(path: String): Result<String> {
        for (prefix in PREFIXES) {
            if (path.startsWith(prefix)) {
                return Result.success(path.substring(prefix.length))
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
    }
}
