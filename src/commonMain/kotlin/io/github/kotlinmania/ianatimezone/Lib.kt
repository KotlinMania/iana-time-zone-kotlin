// port-lint: source lib.rs
package io.github.kotlinmania.ianatimezone

/**
 * Gets the IANA time zone for the current system.
 *
 * This small utility crate provides the [getTimezone] function.
 *
 * The resulting string can be parsed to a time-zone database value by callers
 * that have a time-zone database implementation in scope.
 */

/** Error types. */
sealed class GetTimezoneError : Throwable() {
    /** Failed to parse. */
    data object FailedParsingString : GetTimezoneError()

    /** Wrapped IO error. */
    data class IoError(val causeValue: Throwable) : GetTimezoneError()

    /** Platform-specific error from the operating system. */
    data object OsError : GetTimezoneError()

    override val message: String
        get() = when (this) {
            FailedParsingString -> "GetTimezoneError.FailedParsingString"
            is IoError -> causeValue.message ?: causeValue.toString()
            OsError -> "OsError"
        }

    override fun toString(): String = message
}

/**
 * Gets the current IANA time zone as a string.
 *
 * See the module-level documentation for a usage example and more details
 * about this function.
 */
fun getTimezone(): Result<String> = Platform.getTimezoneInner()

internal fun Throwable.toGetTimezoneError(): GetTimezoneError = GetTimezoneError.IoError(this)

