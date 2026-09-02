// port-lint: source tz_wasm32_emscripten.rs
package io.github.kotlinmania.ianatimezone

internal object TzWasm32Emscripten {
    const val SCRIPT: String = "Intl.DateTimeFormat().resolvedOptions().timeZone"

    fun getTimezoneInner(scriptRunner: (String) -> String? = { null }): Result<String> {
        val tz = scriptRunner(SCRIPT)
            ?: return Result.failure(GetTimezoneError.OsError.toBridge())
        return Result.success(tz)
    }
}
