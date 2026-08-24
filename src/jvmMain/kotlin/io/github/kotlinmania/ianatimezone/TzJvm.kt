// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

import java.time.ZoneId

internal object TzJvm {
    fun getTimezoneInner(): Result<String> = try {
        val id = ZoneId.systemDefault().id
        if (id.isNotBlank()) {
            Result.success(id)
        } else {
            Result.failure(GetTimezoneError.OsError.toBridge())
        }
    } catch (e: Exception) {
        Result.failure(GetTimezoneError.IoError(e.message ?: e.toString()).toBridge())
    }
}

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzJvm.getTimezoneInner()
}
