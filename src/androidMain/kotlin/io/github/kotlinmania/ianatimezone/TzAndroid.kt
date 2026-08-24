// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import java.util.TimeZone

internal object TzAndroid {
    fun getTimezoneInner(): Result<String> {
        val fromSystemProperty = getSystemProperty("persist.sys.timezone")
        if (!fromSystemProperty.isNullOrBlank()) {
            return Result.success(fromSystemProperty)
        }
        val id = TimeZone.getDefault().id
        if (!id.isNullOrBlank()) {
            return Result.success(id)
        }
        return Result.failure(GetTimezoneError.OsError.toBridge())
    }

    private fun getSystemProperty(key: String): String? = try {
        val clazz = Class.forName("android.os.SystemProperties")
        val getMethod = clazz.getMethod("get", String::class.java)
        val result = getMethod.invoke(null, key) as? String
        result?.takeIf { it.isNotEmpty() }
    } catch (_: Throwable) {
        null
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroid.getTimezoneInner()
}
