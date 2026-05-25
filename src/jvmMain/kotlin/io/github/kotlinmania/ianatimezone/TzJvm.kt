// port-lint: source tz_linux.rs (upstream Rust has no JVM target; this leaf
//                                 picks the std::fs port that mirrors what
//                                 cfg-based dispatch would select on a UNIX
//                                 host. Windows JVM is handled by the
//                                 mingwMain TzWindows path on the native
//                                 side; a JVM-on-Windows port needs
//                                 windows-kotlin with WinRT Calendar
//                                 bindings, which isn't yet published.)
package io.github.kotlinmania.ianatimezone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzPosixFs.getTimezoneInner()
}
