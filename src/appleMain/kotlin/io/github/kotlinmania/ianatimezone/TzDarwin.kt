// port-lint: source tz_darwin.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.MAX_LEN
import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.tznameBuf

internal object TzDarwin {
    fun getTimezoneInner(): Result<String> =
        getTimezone()?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError)

    private fun getTimezone(): String? {
        val buf = tznameBuf()
        val tz = SystemTimeZone.new() ?: return null
        val name = tz.name() ?: return null

        val stringName = name.asUtf8() ?: name.toUtf8(buf) ?: return null
        return if (stringName.isEmpty() || stringName.length > MAX_LEN) {
            null
        } else {
            stringName
        }
    }
}

internal class SystemTimeZone private constructor(private val ref: CFTimeZoneRef) {
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
     * Due to CoreFoundation's internal caching mechanism, subsequent calls to
     * `CFTimeZoneCopySystem` do not reflect system timezone changes made while
     * the process is running. Thus, we explicitly call `CFTimeZoneResetSystem`
     * first to invalidate the cached value and ensure we always retrieve the
     * current system timezone.
     */
    fun name(): StringRef<SystemTimeZone>? {
        val string = cfTimeZoneGetName(ref)
        return string?.let { StringRef.new(it, this) }
    }

    companion object {
        fun new(): SystemTimeZone? {
            cfTimeZoneResetSystem()
            val value = cfTimeZoneCopySystem()
            return value?.let { SystemTimeZone(it) }
        }
    }
}

internal class StringRef<T> private constructor(
    private val string: CFStringRef,
    private val parent: T,
) {
    fun asUtf8(): String? = cfStringGetCStringPtr(string, CF_STRING_ENCODING_UTF8)

    fun toUtf8(buf: ByteArray): String? {
        val length = cfStringGetLength(string)
        val range = CFRange(location = 0, length = length)
        val convertedBytes = cfStringGetBytes(
            string,
            range,
            CF_STRING_ENCODING_UTF8,
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

internal data class CFRange(val location: Int, val length: Int)
internal class CFTimeZoneRef
internal class CFStringRef

internal const val CF_STRING_ENCODING_UTF8: UInt = 0x08000100u
