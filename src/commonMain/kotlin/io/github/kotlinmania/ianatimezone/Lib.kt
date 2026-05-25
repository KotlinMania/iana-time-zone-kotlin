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

/** Error types — mirror of upstream Rust `enum GetTimezoneError`. */
sealed class GetTimezoneError {
    /** Failed to parse the platform-specific timezone string. */
    data object FailedParsingString : GetTimezoneError()

    /**
     * Wrapped I/O error.
     *
     * The upstream Rust crate wraps `std::io::Error` here. Kotlin
     * Multiplatform has no single `IoError` type that exists on every
     * configured target (`java.io.IOException` is JVM-only; POSIX errno on
     * native; `DOMException` on JS; WASI errno on Wasm-WASI), so this
     * stores the message string that the platform-specific actuals
     * generate. Consumers can pattern-match on prefixes like
     * `"open(/etc/timezone) failed:"` if they need to discriminate; the
     * upstream Rust API does the same by reading
     * `std::io::Error::to_string()`.
     */
    data class IoError(val message: String) : GetTimezoneError()

    /** Platform-specific error from the operating system. */
    data object OsError : GetTimezoneError()

    /** Stable string form for diagnostics; mirrors the Rust `Display` impl. */
    val displayMessage: String
        get() = when (this) {
            FailedParsingString -> "GetTimezoneError.FailedParsingString"
            is IoError -> message
            OsError -> "OsError"
        }
}

/**
 * Outcome of [getTimezone]. A sealed Outcome rather than `kotlin.Result<String>`
 * because the auto-generated Swift Export bridge pulls in the
 * `Throwable.getStackTrace() -> Array` bridge for any public `Result` /
 * `Throwable` surface, which then fails the workspace-wide
 * `allWarningsAsErrors=true` gate with `Unchecked cast 'Any?' to 'Array<Any?>'`
 * in the generated `KotlinStdlib.kt`. See workspace AGENTS.md §4 hazard class
 * "Stdlib" for the full background.
 */
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
