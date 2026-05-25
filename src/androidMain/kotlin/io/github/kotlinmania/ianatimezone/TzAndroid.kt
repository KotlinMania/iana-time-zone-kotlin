// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName

internal object TzAndroid {
    /**
     * Faithful port of upstream `tz_android::get_timezone_inner`:
     * `android_system_properties::AndroidSystemProperties::new()` then
     * `properties.get_from_cstr(key)`. Kotlin uses
     * `android-system-properties-kotlin`, the workspace port of the same
     * Rust crate.
     *
     * `testAndroidHostTest` runs this `androidMain` code on a plain host
     * JVM that has no `android.os.SystemProperties` class. The
     * `android-system-properties-kotlin` reflective lookup returns
     * `null` there. The Rust crate's `cfg(target_os = "android")`
     * dispatch would have selected `tz_linux.rs` (or another `std::fs`
     * port) when not on real Android; we mirror that by falling through
     * to [TzPosixFs] — the same `std::fs` port — so the host test
     * runs against real `/etc/localtime` on Linux/macOS CI runners.
     */
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        val fromBionic = properties.getFromCString(key)
        if (fromBionic != null) {
            return Result.success(fromBionic)
        }
        return TzPosixFs.getTimezoneInner()
    }

    private val properties: AndroidSystemProperties by lazy { AndroidSystemProperties.new() }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroid.getTimezoneInner()
}
