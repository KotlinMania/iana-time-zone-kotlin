// port-lint: source tz_ohos.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.MAX_LEN
import io.github.kotlinmania.ianatimezone.FfiUtils.Buffer.tznameBuf

/**
 * OpenHarmony does not have `/etc/localtime`; it uses its Time Service to get
 * the time-zone information.
 */

internal enum class TimeServiceErrCode(val code: Int) {
    TIMESERVICE_ERR_OK(0),
    TIMESERVICE_ERR_INTERNAL_ERROR(13_000_001),
    TIMESERVICE_ERR_INVALID_PARAMETER(13_000_002),
}

internal object TzOhos {
    /** Changes to `fromBytesUntilNul` can use a platform intrinsic once the baseline supports one. */
    private fun fromBytesUntilNul(bytes: ByteArray): String? {
        val nulPos = bytes.indexOf(0)
        if (nulPos < 0) {
            return null
        }
        return bytes.decodeToString(endIndex = nulPos)
    }

    fun getTimezoneInner(): Result<String> {
        val timeZone = tznameBuf()
        val ret = ohTimeServiceGetTimeZone(timeZone, MAX_LEN.toUInt() - 1u)
        if (ret != TimeServiceErrCode.TIMESERVICE_ERR_OK) {
            return Result.failure(GetTimezoneError.OsError)
        }
        return fromBytesUntilNul(timeZone)
            ?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError)
    }
}

