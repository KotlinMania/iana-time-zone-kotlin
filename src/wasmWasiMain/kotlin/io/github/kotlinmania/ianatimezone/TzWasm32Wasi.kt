// port-lint: source tz_wasm32_wasi.rs
@file:OptIn(kotlin.wasm.unsafe.UnsafeWasmMemoryApi::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.ianatimezone

import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.Pointer
import kotlin.wasm.unsafe.withScopedMemoryAllocator

internal actual object Platform {
    actual fun getTimezoneInner(): Result<String> = TzWasm32Wasi.getTimezoneInner()
}

internal object TzWasm32Wasi {
    /**
     * Reads `TZ` from the WASI environment, falling back to `Etc/UTC` when it
     * is unset.
     */
    fun getTimezoneInner(): Result<String> =
        Result.success(wasiEnvironmentVariable("TZ") ?: "Etc/UTC")
}

// ----- WASI preview1 environment ABI --------------

@WasmImport("wasi_snapshot_preview1", "environ_sizes_get")
private external fun wasiEnvironSizesGet(outCount: Int, outBufSize: Int): Int

@WasmImport("wasi_snapshot_preview1", "environ_get")
private external fun wasiEnvironGet(environPtr: Int, environBufPtr: Int): Int

/**
 * Decodes the WASI environment table and returns the value associated with
 * [name], or `null` if the variable is unset.
 *
 * The two-call shape (sizes get followed by environ get) is mandated by
 * the WASI preview1 ABI: the first call reports the count of entries and the
 * total byte size of the packed key-value buffer, the second writes the
 * pointer table and the buffer into linear memory at addresses the caller
 * allocated.
 */
private fun wasiEnvironmentVariable(name: String): String? {
    val target = "$name="
    return withScopedMemoryAllocator { allocator ->
        val countPtr = allocator.allocate(Int.SIZE_BYTES)
        val bufSizePtr = allocator.allocate(Int.SIZE_BYTES)
        if (wasiEnvironSizesGet(countPtr.address.toInt(), bufSizePtr.address.toInt()) != 0) return@withScopedMemoryAllocator null
        val count = countPtr.loadInt()
        val bufSize = bufSizePtr.loadInt()
        if (count == 0 || bufSize == 0) return@withScopedMemoryAllocator null

        val environPtr = allocator.allocate(count * Int.SIZE_BYTES)
        val environBufPtr = allocator.allocate(bufSize)
        if (wasiEnvironGet(environPtr.address.toInt(), environBufPtr.address.toInt()) != 0) return@withScopedMemoryAllocator null

        for (i in 0 until count) {
            val entryAddress = (environPtr + i * Int.SIZE_BYTES).loadInt()
            val entryStart = Pointer(entryAddress.toUInt())
            val length = nulTerminatedLength(entryStart, bufSize)
            val bytes = ByteArray(length)
            for (j in 0 until length) bytes[j] = (entryStart + j).loadByte()
            val entry = bytes.decodeToString()
            if (entry.startsWith(target)) {
                return@withScopedMemoryAllocator entry.substring(target.length)
            }
        }
        null
    }
}

private fun nulTerminatedLength(start: Pointer, maxLength: Int): Int {
    var n = 0
    while (n < maxLength && (start + n).loadByte() != 0.toByte()) {
        n += 1
    }
    return n
}
