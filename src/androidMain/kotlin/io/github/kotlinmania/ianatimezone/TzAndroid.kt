// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.androidsystemproperties.AndroidSystemProperties
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName
import java.util.TimeZone as JavaTimeZone

internal object TzAndroid {
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        val fromBionic = getProperties()?.getFromCString(key)
        if (!fromBionic.isNullOrBlank()) {
            return Result.success(fromBionic)
        }
        val fromJvm = JavaTimeZone.getDefault().id
        return if (fromJvm.isNullOrBlank()) {
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
