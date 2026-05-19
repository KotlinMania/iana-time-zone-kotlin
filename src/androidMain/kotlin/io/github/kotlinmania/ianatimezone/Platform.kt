// port-lint: ignore
// Android actual bridge for upstream platform dispatch.
package io.github.kotlinmania.ianatimezone

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzAndroid.getTimezoneInner()
}
