// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.io.IOException
import io.github.kotlinmania.io.buffered
import io.github.kotlinmania.io.files.Path
import io.github.kotlinmania.io.files.SystemFileSystem
import io.github.kotlinmania.io.readString

/**
 * commonMain port of upstream Rust `tz_linux.rs`. The Rust file imports
 * only `std::fs::{read_link, read_to_string}` — nothing platform-specific
 * beyond the filesystem. The Kotlin equivalent of `std::fs` is `km-io`'s
 * [SystemFileSystem], which works on every Kotlin Multiplatform target
 * that has a filesystem (Linux/macOS/iOS/tvOS/watchOS/Android/MinGW/JVM).
 * So this single commonMain port replaces what would otherwise be
 * duplicated per-target `actual` implementations.
 *
 * Targets without a real filesystem (`js`, `wasmJs`, `wasmWasi` for the
 * Intl path) handle resolution through their own actuals; targets that
 * have a platform-specific primary path (`apple` via CoreFoundation,
 * `android*` via Bionic property, `mingw`/`jvm-windows` via WinRT or the
 * registry) layer that on top and fall through to [TzPosixFs] when their
 * primary path returns nothing — matching what the Rust crate does at
 * compile time via `cfg`-based path dispatch.
 */
internal object TzPosixFs {
    private val PREFIXES = listOf(
        "/usr/share/zoneinfo/",
        "../usr/share/zoneinfo/",
        "/etc/zoneinfo/",
        "../etc/zoneinfo/",
        // Apple lays /etc/localtime out under /var/db/timezone/zoneinfo.
        // Rust on Apple targets compiles tz_darwin.rs (CoreFoundation),
        // so this prefix is absent upstream; we include it here so the
        // same symlink-strip works on every UNIX-y target that has
        // /etc/localtime.
        "/var/db/timezone/zoneinfo/",
    )

    /**
     * Mirror of upstream `get_timezone_inner`: try `/etc/localtime`,
     * then `/etc/timezone`, then the OpenWRT config, in upstream order.
     */
    fun getTimezoneInner(): Result<String> = etcLocaltime()
        .recoverCatching { etcTimezone().getOrThrow() }
        .recoverCatching { etcConfigSystem().getOrThrow() }

    /**
     * Port of upstream `etc_localtime`.
     *
     * `std::fs::read_link("/etc/localtime")` returns the immediate symlink
     * target (relative or absolute). km-io has no one-level `read_link`
     * primitive; it has [SystemFileSystem.resolve] which canonicalises the
     * full chain and returns an absolute [Path]. The IANA name extraction
     * is the same either way — strip any of the well-known zoneinfo prefixes
     * from the resulting string and what's left is the zone.
     */
    private fun etcLocaltime(): Result<String> = try {
        val resolved = SystemFileSystem.resolve(Path("/etc/localtime")).toString()
        for (prefix in PREFIXES) {
            if (resolved.startsWith(prefix)) {
                return Result.success(resolved.substring(prefix.length))
            }
        }
        Result.failure(GetTimezoneError.FailedParsingString.toBridge())
    } catch (e: IOException) {
        Result.failure(GetTimezoneError.IoError("resolve(/etc/localtime): ${e.message ?: e}").toBridge())
    }

    /** Port of upstream `etc_timezone`. */
    private fun etcTimezone(): Result<String> = try {
        val contents = SystemFileSystem.source(Path("/etc/timezone")).buffered().use { it.readString() }
        Result.success(contents.trimEnd())
    } catch (e: IOException) {
        Result.failure(GetTimezoneError.IoError("source(/etc/timezone): ${e.message ?: e}").toBridge())
    }

    /** Port of upstream `openwrt::etc_config_system`. */
    private fun etcConfigSystem(): Result<String> = try {
        val contents = SystemFileSystem.source(Path("/etc/config/system")).buffered().use { it.readString() }
        val lines = contents.lineSequence().toList()
        var inSystemSection = false
        var timezone: String? = null

        for (line in lines) {
            val iter = IterWords(line)
            val keyword = iter.next().getOrElse {
                return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
            } ?: continue
            if (keyword == "config") {
                inSystemSection = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                } == "system" && iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                } == null
            } else if (inSystemSection && keyword == "option") {
                val key = iter.next().getOrElse {
                    return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                }
                if (key == "zonename") {
                    val zonename = iter.next().getOrElse {
                        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                    }
                    if (zonename != null && iter.next().getOrElse {
                            return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                        } == null
                    ) {
                        return Result.success(zonename)
                    }
                } else if (key == "timezone") {
                    val value = iter.next().getOrElse {
                        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                    }
                    if (value != null && iter.next().getOrElse {
                            return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
                        } == null
                    ) {
                        timezone = value
                    }
                }
            }
        }

        timezone?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())
    } catch (e: IOException) {
        Result.failure(GetTimezoneError.IoError("source(/etc/config/system): ${e.message ?: e}").toBridge())
    }
}
