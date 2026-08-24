// port-lint: source lib.rs
package io.github.kotlinmania.ianatimezone

/**
 * Gets the IANA time zone for the current system.
 *
 * This small utility module provides the [getTimezone] function.
 *
 * The resulting [TimezoneResult] is either a [TimezoneResult.Ok] wrapping the
 * IANA zone name or a [TimezoneResult.Failure] wrapping a [GetTimezoneError]
 * describing what went wrong.
 */

/** Error types. */
sealed class GetTimezoneError {
    /** Failed to parse the platform-specific timezone string. */
    data object FailedParsingString : GetTimezoneError()

    /** Wrapped I/O error. */
    data class IoError(val message: String) : GetTimezoneError()

    /** Platform-specific error from the operating system. */
    data object OsError : GetTimezoneError()

    /** Returns the underlying cause of the error, if any. */
    fun source(): String? = when (this) {
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
        get() = when (this) {
            FailedParsingString -> "GetTimezoneError.FailedParsingString"
            is IoError -> message
            OsError -> "OsError"
        }
}

/** Outcome of [getTimezone]. */
sealed class TimezoneResult {
    /** The current IANA zone name. */
    data class Ok(val name: String) : TimezoneResult()

    /** Resolution failed; [error] describes why. */
    data class Failure(val error: GetTimezoneError) : TimezoneResult()
}

/**
 * Gets the current IANA time zone as a [TimezoneResult].
 *
 * See the module-level documentation for a usage example and more details
 * about this function.
 */
fun getTimezone(): TimezoneResult {
    val inner = Platform.getTimezoneInner()
    return inner.fold(
        onSuccess = { TimezoneResult.Ok(it) },
        onFailure = { TimezoneResult.Failure(it.toGetTimezoneError()) },
    )
}

/**
 * Converts any platform-side [Throwable] into the public [GetTimezoneError]
 * surface. [GetTimezoneError] instances pass through unchanged; anything else
 * becomes an [GetTimezoneError.IoError] carrying the throwable's message.
 */
internal fun Throwable.toGetTimezoneError(): GetTimezoneError = when (this) {
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
internal class GetTimezoneErrorBridge(val error: GetTimezoneError) : Throwable(error.displayMessage)

/** Lift a [GetTimezoneError] into the internal `Result<String>` failure channel. */
internal fun GetTimezoneError.toBridge(): Throwable = GetTimezoneErrorBridge(this)
