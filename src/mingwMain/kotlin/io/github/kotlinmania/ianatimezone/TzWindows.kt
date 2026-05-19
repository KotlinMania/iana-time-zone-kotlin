// port-lint: source tz_windows.rs
package io.github.kotlinmania.ianatimezone

import io.github.kotlinmania.ianatimezone.WindowsBindings.Windows.Globalization.Calendar

internal object TzWindows {
    fun getTimezoneInner(): Result<String> {
        val cal = Calendar.new().getOrElse { return Result.failure(GetTimezoneError.IoError(it)) }
        val tzHstring = cal.getTimeZone().getOrElse { return Result.failure(GetTimezoneError.IoError(it)) }
        return Result.success(tzHstring.toString())
    }
}

