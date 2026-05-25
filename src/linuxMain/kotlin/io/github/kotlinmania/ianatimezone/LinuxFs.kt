// port-lint: source tz_linux.rs (std::fs::{read_to_string, read_link} + io::BufReader::read_line)
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.experimental.ExperimentalNativeApi::class)

package io.github.kotlinmania.ianatimezone

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned
import platform.posix.O_RDONLY
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.close
import platform.posix.errno
import platform.posix.lseek
import platform.posix.open
import platform.posix.read
import platform.posix.readlink
import platform.posix.strerror

private class PosixIoError(override val message: String) : Throwable()

private fun posixErrno(verb: String, path: String): PosixIoError {
    val e = errno
    val msg = strerror(e)?.toKString() ?: "errno=$e"
    return PosixIoError("$verb($path) failed: $msg")
}

/** Read the full text of a regular file. */
internal fun readSystemText(path: String): Result<String> {
    val fd = open(path, O_RDONLY)
    if (fd < 0) return Result.failure(posixErrno("open", path))
    try {
        val end = lseek(fd, 0, SEEK_END)
        if (end < 0L) return Result.failure(posixErrno("lseek", path))
        if (lseek(fd, 0, SEEK_SET) < 0L) return Result.failure(posixErrno("lseek", path))
        val size = end.toInt()
        val bytes = ByteArray(size)
        bytes.usePinned { pinned ->
            var total = 0
            while (total < size) {
                val remaining = size - total
                val n = read(fd, pinned.addressOf(total), remaining.convert()).toInt()
                if (n < 0) return Result.failure(posixErrno("read", path))
                if (n == 0) break
                total += n
            }
            if (total < size) return Result.success(bytes.decodeToString(endIndex = total))
        }
        return Result.success(bytes.decodeToString())
    } finally {
        close(fd)
    }
}

/** Read the target of a symbolic link. */
internal fun readSystemLink(path: String): Result<String> {
    val cap = 4096
    val bytes = ByteArray(cap)
    val n = bytes.usePinned { pinned ->
        readlink(path, pinned.addressOf(0), cap.convert()).toInt()
    }
    if (n < 0) return Result.failure(posixErrno("readlink", path))
    if (n >= cap) return Result.failure(PosixIoError("readlink($path): result truncated"))
    return Result.success(bytes.decodeToString(endIndex = n))
}

/** Read a file as a list of lines, stripping the trailing newline split-artifact. */
internal fun readSystemLines(path: String): Result<List<String>> =
    readSystemText(path).map { it.lineSequence().toList() }
