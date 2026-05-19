// port-lint: source tz_illumos.rs
package io.github.kotlinmania.ianatimezone

internal object TzIllumos {
    fun getTimezoneInner(): Result<String> {
        val lines = readSystemLines("/etc/default/init").getOrElse { return Result.failure(it.toGetTimezoneError()) }
        for (line in lines) {
            if (line.startsWith("TZ=")) {
                return Result.success(line.trimEnd().removePrefix("TZ="))
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString)
    }
}

