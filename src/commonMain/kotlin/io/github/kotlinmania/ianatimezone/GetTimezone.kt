package io.github.kotlinmania.ianatimezone

/**
 * Gets the current IANA time zone as a [TimezoneResult].
 *
 * See the package documentation for a usage example and more details
 * about this function.
 */
fun getTimezone(): TimezoneResult {
    val inner = Platform.getTimezoneInner()
    return inner.fold(
        onSuccess = { TimezoneResult.Ok(it) },
        onFailure = { TimezoneResult.Failure(it.toGetTimezoneError()) },
    )
}
