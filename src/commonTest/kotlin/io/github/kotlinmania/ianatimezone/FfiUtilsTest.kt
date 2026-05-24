// port-lint: source ffi_utils.rs
package io.github.kotlinmania.ianatimezone

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FfiUtilsTest {
    @Test
    fun testAndroidTimezonePropertyNameIsValidCString() {
        assertTrue(AndroidTimezonePropertyNameValidator.isValid(FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME))

        val invalidPropertyName = FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME + byteArrayOf(0)
        assertFalse(AndroidTimezonePropertyNameValidator.isValid(invalidPropertyName))
    }

    @Test
    fun testAndroidTimezonePropertyNameGetter() {
        val key = FfiUtils.androidTimezonePropertyName()
        assertContentEquals(FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME, key)
        assertEquals("persist.sys.timezone\u0000", key.decodeToString())
    }

    @Test
    fun testTznameBufferCorrectSize() {
        assertEquals(
            64,
            FfiUtils.Buffer.MAX_LEN,
            "Buffer length changed unexpectedly, ensure consistency with documented limit.",
        )
        assertEquals(
            FfiUtils.Buffer.MAX_LEN,
            FfiUtils.Buffer.tznameBuf().size,
            "Buffer length changed unexpectedly, ensure consistency with documented limit.",
        )
    }
}
