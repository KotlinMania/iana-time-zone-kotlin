// port-lint: source platform.rs
package io.github.kotlinmania.ianatimezone

internal expect object Platform {
    fun getTimezoneInner(): Result<String>
}
