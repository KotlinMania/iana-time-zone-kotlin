// port-lint: source tz_ohos.rs
package io.github.kotlinmania.ianatimezone

/**
 * OpenHarmony timezone resolution via Time Service.
 */
internal object TzOhos {
    enum class TimeServiceErrCode(val code: Int) {
        TimeserviceErrOk(0),
        TimeserviceErrInternalError(13000001),
        TimeserviceErrInvalidParameter(13000002),
    }

    fun fromBytesUntilNul(bytes: ByteArray): String? {
        val nulPos = bytes.indexOf(0.toByte())
        if (nulPos < 0) return null
        return bytes.decodeToString(0, nulPos)
    }

    fun getTimezoneInner(serviceRunner: (ByteArray) -> TimeServiceErrCode = { TimeServiceErrCode.TimeserviceErrInternalError }): Result<String> {
        val timeZone = FfiUtils.Buffer.tznameBuf()
        val ret = serviceRunner(timeZone)
        if (ret != TimeServiceErrCode.TimeserviceErrOk) {
            return Result.failure(GetTimezoneError.OsError.toBridge())
        }
        val str = fromBytesUntilNul(timeZone)
        return str?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}
