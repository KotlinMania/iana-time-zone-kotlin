// port-lint: source platform.rs
package io.github.kotlinmania.ianatimezone

internal object Platform {
    fun getTimezoneInner(): Result<String> = Result.failure(GetTimezoneError.OsError)
}

