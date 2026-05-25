// port-lint: source lib.rs (JVM-specific implementation; upstream Rust crate
//                          has no analogue because Rust on the JVM is N/A.)
package io.github.kotlinmania.ianatimezone

import java.util.TimeZone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzJvm.getTimezoneInner()
}

internal object TzJvm {
    /**
     * Returns the JVM default time zone's IANA ID.
     *
     * `TimeZone.getDefault()` reads `user.timezone`, falling back to a
     * platform-specific lookup (`/etc/localtime` on Linux, the registry on
     * Windows, CoreFoundation on macOS, `getprop persist.sys.timezone` on
     * Android). The returned [TimeZone.id] is the same string an
     * `iana-time-zone` consumer would see by calling the matching
     * platform-specific upstream module directly.
     */
    fun getTimezoneInner(): Result<String> {
        val id = TimeZone.getDefault().id
        return if (id.isNullOrEmpty()) {
            Result.failure(GetTimezoneError.OsError.toBridge())
        } else {
            Result.success(id)
        }
    }
}
