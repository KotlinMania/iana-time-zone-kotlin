// port-lint: source tz_wasm32_wasi.rs
package io.github.kotlinmania.ianatimezone

internal object TzWasm32Wasi {
    fun getTimezoneInner(): Result<String> = environmentVariable("TZ") ?: Result.success("Etc/UTC")
}

