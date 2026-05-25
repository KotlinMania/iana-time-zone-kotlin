// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroidNative.getTimezoneInner()
}

internal object TzAndroidNative {
    /**
     * Reads `persist.sys.timezone` through the Bionic
     * `__system_property_find` / `__system_property_read_callback` ABI
     * exposed by the `android-system-properties-kotlin` sibling's
     * `androidNativeMain` cinterop. Mirrors the upstream Rust
     * `tz_android.rs` for the NDK case where there is no JVM bridge.
     */
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        return getProperties()
            ?.getFromCString(key)
            ?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }

    private var properties: AndroidSystemProperties? = null

    private fun getProperties(): AndroidSystemProperties? {
        if (properties == null) {
            properties = AndroidSystemProperties.new()
        }
        return properties
    }
}
