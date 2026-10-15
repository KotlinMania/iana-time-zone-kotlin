// port-lint: source tz_darwin.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.corefoundationsys.CFStringRef
import io.github.kotlinmania.corefoundationsys.CFTimeZoneRef
import io.github.kotlinmania.corefoundationsys.base.CFRange
import io.github.kotlinmania.corefoundationsys.cfRelease
import io.github.kotlinmania.corefoundationsys.cfStringGetBytes
import io.github.kotlinmania.corefoundationsys.cfStringGetCStringPtr
import io.github.kotlinmania.corefoundationsys.cfStringGetLength
import io.github.kotlinmania.corefoundationsys.cfTimeZoneCopySystem
import io.github.kotlinmania.corefoundationsys.cfTimeZoneGetName
import io.github.kotlinmania.corefoundationsys.cfTimeZoneResetSystem
import io.github.kotlinmania.corefoundationsys.string.kCFStringEncodingUTF8
import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.MAX_LEN
import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.tznameBuf

internal object TzDarwin {
    fun getTimezoneInner(): Result<String> =
        getTimezone()?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())

    private fun getTimezone(): String? {
        val buf = tznameBuf()
        val tz = SystemTimeZone.new() ?: return null
        try {
            val name = tz.name() ?: return null
            val stringName = name.asUtf8() ?: name.toUtf8(buf) ?: return null
            return if (stringName.isEmpty() || stringName.length > MAX_LEN) {
                null
            } else {
                stringName
            }
        } finally {
            tz.release()
        }
    }
}

internal class SystemTimeZone private constructor(
    private val ref: CFTimeZoneRef,
) {
    fun release() {
        cfRelease(ref)
    }

    /**
     * Creates a new [SystemTimeZone] by querying the current Darwin system
     * timezone.
     *
     * This function implicitly calls `CFTimeZoneResetSystem` to invalidate the
     * cached timezone and ensure we always retrieve the current system
     * timezone.
     *
     * Due to CoreFoundation internal caching mechanism, subsequent calls to
     * `CFTimeZoneCopySystem` do not reflect system timezone changes made while
     * the process is running. Thus, we explicitly call `CFTimeZoneResetSystem`
     * first to invalidate the cached value and ensure we always retrieve the
     * current system timezone.
     */
    fun name(): StringRef<SystemTimeZone>? {
        val string = cfTimeZoneGetName(ref) ?: return null
        return StringRef.new(string, this)
    }

    companion object {
        fun new(): SystemTimeZone? {
            cfTimeZoneResetSystem()
            val value = cfTimeZoneCopySystem() ?: return null
            return SystemTimeZone(value)
        }
    }
}

internal class StringRef<T> private constructor(
    private val string: CFStringRef,
    private val parent: T,
) {
    fun asUtf8(): String? = cfStringGetCStringPtr(string, kCFStringEncodingUTF8)

    fun toUtf8(buf: ByteArray): String? {
        val length = cfStringGetLength(string)
        val range = CFRange(location = 0, length = length)
        val convertedBytes =
            cfStringGetBytes(
                string,
                range,
                kCFStringEncodingUTF8,
                0u,
                false,
                buf,
                buf.size,
            )
        if (convertedBytes != length) {
            return null
        }
        return buf.decodeToString(endIndex = convertedBytes)
    }

    companion object {
        fun <T> new(string: CFStringRef, parent: T): StringRef<T> = StringRef(string, parent)
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzDarwin.getTimezoneInner()
}
