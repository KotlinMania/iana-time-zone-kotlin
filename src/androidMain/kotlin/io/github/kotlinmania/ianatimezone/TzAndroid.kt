// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName
import java.util.TimeZone as JavaTimeZone

internal object TzAndroid {
    /**
     * Resolves the current zone the same way the upstream Rust crate does on
     * Android: reads the `persist.sys.timezone` system property through the
     * Bionic ABI exposed by `android-system-properties-kotlin`.
     *
     * The Android KMP `androidMain` source set compiles for the Android JVM
     * runtime AND runs through `testAndroidHostTest` against a plain host
     * JVM that has no `android.os.SystemProperties` class on its classpath.
     * On the host JVM the reflective lookup returns `null` — and the value
     * we want is sitting right next to us in `java.util.TimeZone.getDefault()`,
     * which Android itself populates from the same `persist.sys.timezone`
     * property. So when the Bionic-backed lookup fails, fall back to the
     * standard JVM API. The two paths agree on real Android and the JVM
     * fallback also produces a meaningful answer on host test runners.
     */
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        val fromBionic = getProperties()?.getFromCString(key)
        if (fromBionic != null) {
            return Result.success(fromBionic)
        }
        val fromJvm = JavaTimeZone.getDefault().id
        return if (fromJvm.isNullOrEmpty()) {
            Result.failure(GetTimezoneError.OsError.toBridge())
        } else {
            Result.success(fromJvm)
        }
    }

    private var properties: AndroidSystemProperties? = null

    private fun getProperties(): AndroidSystemProperties? {
        if (properties == null) {
            properties = AndroidSystemProperties.new()
        }
        return properties
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroid.getTimezoneInner()
}
