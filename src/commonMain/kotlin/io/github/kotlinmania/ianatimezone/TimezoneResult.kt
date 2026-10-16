package io.github.kotlinmania.ianatimezone

/** Outcome of [getTimezone]. */
sealed class TimezoneResult {
    /** The current IANA zone name. */
    data class Ok(
        val name: String,
    ) : TimezoneResult()

    /** Resolution failed; [error] describes why. */
    data class Failure(
        val error: GetTimezoneError,
    ) : TimezoneResult()
}
