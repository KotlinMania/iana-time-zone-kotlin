// port-lint: source tz_linux.rs (openwrt module IterWords + read_word)
package io.github.kotlinmania.ianatimezone

/**
 * Pure-Kotlin OpenWRT `/etc/config/system` lexer, hoisted out of the
 * Linux-Native source set so the JVM port of `tz_linux.rs` can reuse it
 * verbatim. Upstream Rust keeps this inside `mod openwrt { … }` because
 * Rust's path-dispatch already picked the file at compile time; in
 * Kotlin Multiplatform the same lexer needs to live in `commonMain` so
 * `linuxMain` (POSIX cinterop) and `jvmMain` (Java NIO) can share it.
 */

/** Returned by [readWord] when an opening quote has no matching close. */
internal data object BrokenQuote : Throwable()

/**
 * Iterates over all words in an OpenWRT config line. Mirrors the
 * `IterWords<'a>` struct + `Iterator` impl from upstream Rust.
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
 * parsed. Faithful translation of the upstream Rust `read_word`.
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
                Result.success(s.substring(0, index) to s.substring(index))
            } else {
                Result.success(s to "")
            }
        }
    }
}
