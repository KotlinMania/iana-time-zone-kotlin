// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzLinux.getTimezoneInner()
}

internal object TzLinux {
    fun getTimezoneInner(): Result<String> =
        etcLocaltime()
            .recoverCatching { etcTimezone().getOrThrow() }
            .recoverCatching { OpenWrt.etcConfigSystem().getOrThrow() }

    private fun etcTimezone(): Result<String> {
        val contents = readSystemText("/etc/timezone").getOrElse { return Result.failure(it.toGetTimezoneError().toBridge()) }
        return Result.success(contents.trimEnd())
    }

    private fun etcLocaltime(): Result<String> {
        /*
         * The /etc/localtime file configures the system-wide timezone of the
         * local system that is used by applications for presentation to the
         * user. It should be an absolute or relative symbolic link pointing to
         * /usr/share/zoneinfo/, followed by a timezone identifier such as
         * "Europe/Berlin" or "Etc/UTC". The resulting link should lead to the
         * corresponding binary timezone data for the configured timezone.
         */

        /*
         * Systemd does not canonicalize the link, but only checks if it is
         * prefixed by "/usr/share/zoneinfo/" or "../usr/share/zoneinfo/". So
         * we do the same.
         */
        val prefixes = listOf(
            "/usr/share/zoneinfo/",
            "../usr/share/zoneinfo/",
            "/etc/zoneinfo/",
            "../etc/zoneinfo/",
        )
        var link = readSystemLink("/etc/localtime").getOrElse { return Result.failure(it.toGetTimezoneError().toBridge()) }
        for (prefix in prefixes) {
            if (link.startsWith(prefix)) {
                link = link.removePrefix(prefix)
                return Result.success(link)
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
    }

    private object OpenWrt {
        fun etcConfigSystem(): Result<String> {
            val lines = readSystemLines("/etc/config/system").getOrElse { return Result.failure(it.toGetTimezoneError().toBridge()) }
            var inSystemSection = false
            var timezone: String? = null

            for (line in lines) {
                val iter = IterWords(line)
                val keyword = iter.next().getOrElse { return Result.failure(it) } ?: continue
                if (keyword == "config") {
                    inSystemSection = iter.next().getOrElse { return Result.failure(it) } == "system" &&
                        iter.next().getOrElse { return Result.failure(it) } == null
                } else if (inSystemSection && keyword == "option") {
                    val key = iter.next().getOrElse { return Result.failure(it) }
                    if (key == "zonename") {
                        val zonename = iter.next().getOrElse { return Result.failure(it) }
                        if (zonename != null && iter.next().getOrElse { return Result.failure(it) } == null) {
                            return Result.success(zonename)
                        }
                    } else if (key == "timezone") {
                        val value = iter.next().getOrElse { return Result.failure(it) }
                        if (value != null && iter.next().getOrElse { return Result.failure(it) } == null) {
                            timezone = value
                        }
                    }
                }
            }

            return timezone?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())
        }
    }
}
internal data object BrokenQuote : Throwable()

/** Iterates over all words in an OpenWRT config line. */
internal class IterWords(private var line: String) : Iterator<Result<String?>> {
    private var done = false

    override fun hasNext(): Boolean = !done

    override fun next(): Result<String?> {
        val result = readWord(line)
        return result.fold(
            onSuccess = { item ->
                if (item == null) {
                    line = ""
                    done = true
                    Result.success(null)
                } else {
                    line = item.second
                    Result.success(item.first)
                }
            },
            onFailure = { err ->
                line = ""
                done = true
                Result.failure(err)
            },
        )
    }
}

/**
 * Reads the next word in an OpenWRT config line. Strips any surrounding
 * quotation marks.
 *
 * Returns a pair of the word and remaining line if found, `null` if the line
 * is exhausted, or [BrokenQuote] if the line could not be parsed.
 */
internal fun readWord(source: String): Result<Pair<String, String>?> {
    val s = source.trimStart()
    return if (s.isEmpty() || s.startsWith('#')) {
        Result.success(null)
    } else if (s.startsWith('\'')) {
        val index = s.indexOf('\'', startIndex = 1)
        if (index >= 0) {
            Result.success(s.substring(1, index) to s.substring(index + 1))
        } else {
            Result.failure(BrokenQuote)
        }
    } else if (s.startsWith('"')) {
        val index = s.indexOf('"', startIndex = 1)
        if (index >= 0) {
            Result.success(s.substring(1, index) to s.substring(index + 1))
        } else {
            Result.failure(BrokenQuote)
        }
    } else {
        val index = s.indexOfFirst { it.isWhitespace() }
        if (index >= 0) {
            Result.success(s.substring(0, index) to s.substring(index))
        } else {
            Result.success(s to "")
        }
    }
}
