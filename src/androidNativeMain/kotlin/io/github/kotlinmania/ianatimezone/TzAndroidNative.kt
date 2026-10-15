// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroidNative.getTimezoneInner()
}

internal object TzAndroidNative {
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        return getProperties()
            ?.getFromCString(key)
            ?.takeIf { it.isNotBlank() }
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
