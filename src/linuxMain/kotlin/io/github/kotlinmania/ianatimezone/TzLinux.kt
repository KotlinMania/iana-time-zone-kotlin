// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import platform.posix.readlink

internal object TzLinux {
    private val PREFIXES = listOf(
        "/usr/share/zoneinfo/",
        "../usr/share/zoneinfo/",
        "/etc/zoneinfo/",
        "../etc/zoneinfo/",
    )

    fun getTimezoneInner(): Result<String> = etcLocaltime()
        .recoverCatching { etcTimezone().getOrThrow() }
        .recoverCatching { etcConfigSystem().getOrThrow() }

    @OptIn(ExperimentalForeignApi::class)
    private fun etcLocaltime(): Result<String> {
        val s = memScoped {
            val buf = allocArray<ByteVar>(4096)
            val len = readlink("/etc/localtime", buf, 4096u)
            if (len <= 0) return Result.failure(GetTimezoneError.IoError("readlink(/etc/localtime) failed").toBridge())
            buf.toKString().take((len as Number).toInt())
        }

        for (prefix in PREFIXES) {
            if (s.startsWith(prefix)) {
                return Result.success(s.substring(prefix.length))
            }
        }
        return Result.failure(GetTimezoneError.FailedParsingString.toBridge())
    }

    private fun etcTimezone(): Result<String> {
        val contents = readFileToString("/etc/timezone")
            ?: return Result.failure(GetTimezoneError.IoError("read(/etc/timezone) failed").toBridge())
        return Result.success(contents.trimEnd())
    }

    private fun etcConfigSystem(): Result<String> {
        val contents = readFileToString("/etc/config/system")
            ?: return Result.failure(GetTimezoneError.IoError("read(/etc/config/system) failed").toBridge())
        return parseOpenWrtSystemConfig(contents)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun readFileToString(path: String): String? {
        val file = fopen(path, "r") ?: return null
        return try {
            val sb = StringBuilder()
            memScoped {
                val buf = allocArray<ByteVar>(1024)
                while (fgets(buf, 1024, file) != null) {
                    sb.append(buf.toKString())
                }
            }
            sb.toString()
        } finally {
            fclose(file)
        }
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzLinux.getTimezoneInner()
}
