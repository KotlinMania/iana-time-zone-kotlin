// port-lint: source tz_android.rs
package io.github.kotlinmania.ianatimezone

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv

internal actual object Platform {
    @OptIn(ExperimentalForeignApi::class)
    actual fun getTimezoneInner(): Result<String> {
        val tz = getenv("TZ")?.toKString()
        if (!tz.isNullOrBlank()) {
            return Result.success(tz)
        }
        return Result.failure(GetTimezoneError.OsError.toBridge())
    }
}
