// port-lint: source tz_darwin.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.MAX_LEN
import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.tznameBuf
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.interpretObjCPointer
import kotlinx.cinterop.rawValue
import kotlinx.cinterop.toKString
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringGetCStringPtr
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTimeZoneCopySystem
import platform.CoreFoundation.CFTimeZoneGetName
import platform.CoreFoundation.CFTimeZoneRef
import platform.CoreFoundation.CFTimeZoneResetSystem
import platform.CoreFoundation.kCFStringEncodingUTF8

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

@OptIn(ExperimentalForeignApi::class)
internal class SystemTimeZone private constructor(private val ref: CFTimeZoneRef) {
    fun release() {
        CFRelease(ref)
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
        val string = CFTimeZoneGetName(ref) ?: return null
        return StringRef.new(string, this)
    }

    companion object {
        fun new(): SystemTimeZone? {
            CFTimeZoneResetSystem()
            val value = CFTimeZoneCopySystem() ?: return null
            return SystemTimeZone(value)
        }
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal class StringRef<T> private constructor(
    private val string: CFStringRef,
    private val parent: T,
) {
    fun asUtf8(): String? {
        val ptr = CFStringGetCStringPtr(string, kCFStringEncodingUTF8) ?: return null
        return ptr.toKString()
    }

    fun toUtf8(buf: ByteArray): String? {
        val nsStr = interpretObjCPointer<platform.Foundation.NSString>(string.rawValue)
        return nsStr.toString()
    }

    companion object {
        fun <T> new(string: CFStringRef, parent: T): StringRef<T> = StringRef(string, parent)
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzDarwin.getTimezoneInner()
}
