// port-lint: source platform.rs
package io.github.kotlinmania.ianatimezone

internal fun getTimezoneInner(): Result<String> = Platform.getTimezoneInner()

internal expect object Platform {
    fun getTimezoneInner(): Result<String>
}

