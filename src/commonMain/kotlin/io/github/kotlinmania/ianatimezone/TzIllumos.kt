// port-lint: source tz_illumos.rs
package io.github.kotlinmania.ianatimezone

internal object TzIllumos {
    // https://illumos.org/man/5/TIMEZONE
    // https://docs.oracle.com/cd/E23824_01/html/821-1473/uc-timezone-4.html

    fun getTimezoneInner(fileReader: (String) -> String? = { null }): Result<String> {
        val contents = fileReader("/etc/default/init")
            ?: return Result.failure(GetTimezoneError.OsError.toBridge())
        return parseInitConfig(contents)
    }

    fun parseInitConfig(contents: String): Result<String> {
        for (rawLine in contents.lineSequence()) {
            val line = rawLine.trimEnd()
            if (line.startsWith("TZ=")) {
                val tz = line.substring(3)
                return Result.success(tz)
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
    }
}
