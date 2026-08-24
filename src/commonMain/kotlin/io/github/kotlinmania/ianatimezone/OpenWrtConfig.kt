// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

/** Returned by [readWord] when an opening quote has no matching close. */
internal data object BrokenQuote : Throwable()

/**
 * Iterates over all words in an OpenWRT config line.
 */
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
 * Returns a pair of the word and the remaining line if found, `null` if
 * the line is exhausted, or [BrokenQuote] if the line could not be
 * parsed.
 */
internal fun readWord(source: String): Result<Pair<String, String>?> {
    val s = source.trimStart()
    return when {
        s.isEmpty() || s.startsWith('#') -> Result.success(null)
        s.startsWith('\'') -> {
            val index = s.indexOf('\'', startIndex = 1)
            if (index >= 0) {
                Result.success(s.substring(1, index) to s.substring(index + 1))
            } else {
                Result.failure(BrokenQuote)
            }
        }
        s.startsWith('"') -> {
            val index = s.indexOf('"', startIndex = 1)
            if (index >= 0) {
                Result.success(s.substring(1, index) to s.substring(index + 1))
            } else {
                Result.failure(BrokenQuote)
            }
        }
        else -> {
            val index = s.indexOfFirst { it.isWhitespace() }
            if (index >= 0) {
                Result.success(s.substring(0, index) to s.substring(index + 1))
            } else {
                Result.success(s to "")
            }
        }
    }
}

internal fun parseOpenWrtSystemConfig(contents: String): Result<String> {
    val lines = contents.lineSequence()
    var inSystemSection = false
    var timezone: String? = null

    for (line in lines) {
        if (line.isEmpty()) continue
        val iter = IterWords(line)
        val keyword = iter.next().getOrElse {
            return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
        } ?: continue

        if (keyword == "config") {
            val section = iter.next().getOrElse {
                return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
            }
            val tail = iter.next().getOrElse {
                return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
            }
            inSystemSection = section == "system" && tail == null
        } else if (inSystemSection && keyword == "option") {
            val key = iter.next().getOrElse {
                return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
            }
            if (key == "zonename") {
                val zonename = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                }
                val tail = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                }
                if (zonename != null && tail == null) {
                    return Result.success(zonename)
                }
            } else if (key == "timezone") {
                val value = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                }
                val tail = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                }
                if (value != null && tail == null) {
                    timezone = value
                }
            }
        }
    }

    return timezone?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())
}
