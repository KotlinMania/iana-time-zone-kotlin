// port-lint: source tz_windows.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezone.WindowsBindings.Windows.Globalization.Calendar

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzWindows.getTimezoneInner()
}

internal object TzWindows {
    fun from(orig: WindowsCore.Error): GetTimezoneError =
        GetTimezoneError.IoError(orig.message ?: orig.toString())

    fun getTimezoneInner(): Result<String> {
        val cal = Calendar.new().getOrElse { return Result.failure(from(WindowsCore.Error(it.message ?: it.toString())).toBridge()) }
        val tzHstring = cal.getTimeZone().getOrElse { return Result.failure(from(WindowsCore.Error(it.message ?: it.toString())).toBridge()) }
        return Result.success(tzHstring.toString())
    }
}
