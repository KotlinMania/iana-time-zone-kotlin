// port-lint: source ffi_utils.rs
package io.github.kotlinmania.ianatimezone

/** Cross platform FFI helpers. */
internal object FfiUtils {
    /** A buffer to store the timezone name when calling the C API. */
    object Buffer {
        /** The longest name in the IANA time zone database is 32 ASCII characters long. */
        const val MAX_LEN: Int = 64

        /**
         * Returns a buffer to store the timezone name.
         *
         * The buffer is used to store the timezone name when calling the C API.
         */
        fun tznameBuf(): ByteArray = ByteArray(MAX_LEN)
    }

    /**
     * The system property named persist.sys.timezone contains the name of the
     * current timezone.
     */
    val ANDROID_TIMEZONE_PROPERTY_NAME: ByteArray = "persist.sys.timezone\u0000".encodeToByteArray()

    /**
     * Returns bytes to access the timezone from an Android system properties
     * environment.
     */
    fun androidTimezonePropertyName(): ByteArray {
        check(AndroidTimezonePropertyNameValidator.isValid(ANDROID_TIMEZONE_PROPERTY_NAME))
        return ANDROID_TIMEZONE_PROPERTY_NAME
    }
}

internal object AndroidTimezonePropertyNameValidator {
    fun isValid(bytes: ByteArray): Boolean =
        bytes.isNotEmpty() && bytes.last() == 0.toByte() && bytes.dropLast(1).none { it == 0.toByte() }
}
