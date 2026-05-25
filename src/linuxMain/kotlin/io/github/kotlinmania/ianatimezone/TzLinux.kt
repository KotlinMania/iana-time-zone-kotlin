// port-lint: source tz_linux.rs
package io.github.kotlinmania.ianatimezone

/**
 * Upstream `tz_linux.rs` imports only `std::fs::{read_link, read_to_string}`.
 * The Kotlin equivalent of `std::fs` is `km-io`'s `SystemFileSystem`, which
 * lives in commonMain and works on every native target with a real
 * filesystem. The actual port therefore lives in
 * [commonMain `TzPosixFs`][TzPosixFs] and this file is the leaf `actual`
 * for the Linux/Native source set.
 */
internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzPosixFs.getTimezoneInner()
}
