// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        val fromBionic = AndroidSystemProperties.new().getFromCString(key)
        if (fromBionic != null) {
            return Result.success(fromBionic)
        }
        // androidNativeMain compiles for Android NDK; if `__system_property_find`
        // somehow returns nothing on a real device (very unusual — only
        // happens in an emulator/test sandbox), defer to the same std::fs
        // path Rust would pick under cfg dispatch.
        return TzPosixFs.getTimezoneInner()
    }
}
