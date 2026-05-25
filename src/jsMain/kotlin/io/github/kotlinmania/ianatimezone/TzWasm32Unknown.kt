// port-lint: source tz_wasm32_unknown.rs
package io.github.kotlinmania.ianatimezone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzWasm32Unknown.getTimezoneInner()
}

internal object TzWasm32Unknown {
    fun getTimezoneInner(): Result<String> {
        val tz = jsIntlDateTimeFormatTimeZone()
        return tz?.let { Result.success(it) } ?: Result.failure(GetTimezoneError.OsError.toBridge())
    }
}

/**
 * Returns the `timeZone` field of `Intl.DateTimeFormat().resolvedOptions()`.
 *
 * Mirror of upstream Rust's
 * `js_sys::Intl::DateTimeFormat::new(&Array::new(), &Object::new())
 *      .resolved_options()` + `Reflect::get(&intl, "timeZone")`. The Kotlin/JS
 * compiler inlines this into a single property read against the standard
 * ECMA-402 Intl namespace, which is present in every modern browser and in
 * Node.js >=14.
 */
private fun jsIntlDateTimeFormatTimeZone(): String? =
    js("(new Intl.DateTimeFormat()).resolvedOptions().timeZone") as String?
