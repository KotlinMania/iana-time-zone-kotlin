// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import android.icu.util.TimeZone
import io.github.kotlinmania.ianatimezone.FfiUtils.androidTimezonePropertyName

internal object TzAndroid {
    fun getTimezoneInner(): Result<String> {
        val key = androidTimezonePropertyName()
        return getProperties()
            ?.getFromCString(key)
            ?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError)
    }

    private var properties: AndroidSystemProperties? = null

    private fun getProperties(): AndroidSystemProperties? {
        if (properties == null) {
            properties = AndroidSystemProperties.new()
        }
        return properties
    }
}

internal class AndroidSystemProperties private constructor() {
    fun getFromCString(key: ByteArray): String? = androidSystemProperty(key)

    companion object {
        fun new(): AndroidSystemProperties = AndroidSystemProperties()
    }
}

private fun androidSystemProperty(key: ByteArray): String? {
    val nulIndex = key.indexOf(0.toByte())
    val name = key.decodeToString(endIndex = if (nulIndex >= 0) nulIndex else key.size)
    return when (name) {
        "persist.sys.timezone" -> TimeZone.getDefault().id.takeIf { it.isNotEmpty() }
        else -> null
    }
}
