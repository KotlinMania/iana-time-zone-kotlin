// port-lint: source tz_wasm32_emscripten.rs
package io.github.kotlinmania.ianatimezone

internal object TzWasm32Emscripten {
    fun getTimezoneInner(): Result<String> {
        val script = "Intl.DateTimeFormat().resolvedOptions().timeZone"
        return emscriptenRunScriptString(script)
            ?.let { Result.success(it) }
            ?: Result.failure(GetTimezoneError.OsError)
    }
}

