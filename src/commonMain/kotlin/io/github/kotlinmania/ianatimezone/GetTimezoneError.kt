package io.github.kotlinmania.ianatimezone

/** Error types. */
sealed class GetTimezoneError {
    /** Failed to parse the platform-specific timezone string. */
    data object FailedParsingString : GetTimezoneError()

    /** Wrapped I/O error. */
    data class IoError(
        val message: String,
    ) : GetTimezoneError()

    /** Platform-specific error from the operating system. */
    data object OsError : GetTimezoneError()

    /** Returns the underlying cause of the error, if any. */
    fun source(): String? =
        when (this) {
            FailedParsingString -> null
            is IoError -> message
            OsError -> null
        }

    /** Formats the error as a stable string. */
    fun fmt(): String = displayMessage

    companion object {
        /** Creates a [GetTimezoneError] from an error message or exception. */
        fun from(err: Throwable): GetTimezoneError = err.toGetTimezoneError()

        /** Creates a [GetTimezoneError.IoError] from a string message. */
        fun from(message: String): GetTimezoneError = IoError(message)
    }

    /** Stable string form for diagnostics. */
    val displayMessage: String
        get() =
            when (this) {
                FailedParsingString -> "GetTimezoneError.FailedParsingString"
                is IoError -> message
                OsError -> "OsError"
            }
}

/**
 * Converts any platform-side [Throwable] into the public [GetTimezoneError]
 * surface. [GetTimezoneError] instances pass through unchanged; anything else
 * becomes an [GetTimezoneError.IoError] carrying the throwable's message.
 */
internal fun Throwable.toGetTimezoneError(): GetTimezoneError =
    when (this) {
        is GetTimezoneErrorBridge -> error
        else -> GetTimezoneError.IoError(message ?: toString())
    }

/**
 * Internal-only `Throwable` adapter used by the platform actuals to flow a
 * [GetTimezoneError] through the `kotlin.Result<String>` channel without
 * leaking `Throwable` into the public Swift Export surface.
 *
 * Stays `internal`, so the Swift Export bridge does not generate code for it.
 */
internal class GetTimezoneErrorBridge(
    val error: GetTimezoneError,
) : Throwable(error.displayMessage)

/** Lift a [GetTimezoneError] into the internal `Result<String>` failure channel. */
internal fun GetTimezoneError.toBridge(): Throwable = GetTimezoneErrorBridge(this)
