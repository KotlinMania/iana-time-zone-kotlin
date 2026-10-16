package io.github.kotlinmania.ianatimezone

/** Outcome of [getTimezone]. */
sealed class TimezoneResult {
    /** The resolved time zone name, or null if retrieval failed. */
    open val name: String? get() = null

    /** The error describing retrieval failure, or null on success. */
    open val error: GetTimezoneError? get() = null

    /** True if the time zone was successfully retrieved. */
    val isSuccess: Boolean get() = this is Ok

    /** True if time zone retrieval failed. */
    val isFailure: Boolean get() = this is Failure

    /** Returns the resolved time zone name, or null on failure. */
    fun getOrNull(): String? = name

    /** The current IANA zone name. */
    data class Ok(
        override val name: String,
    ) : TimezoneResult()

    /** Resolution failed; [error] describes why. */
    data class Failure(
        override val error: GetTimezoneError,
    ) : TimezoneResult()
}
