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

    /*
     * The system property named 'persist.sys.timezone' contains the name of the
     * current timezone.
     *
     * The name of the current timezone is taken from the TZ environment
     * variable, if defined. Otherwise, the system property named
     * 'persist.sys.timezone' is checked instead.
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

internal val knownTimezoneNames: List<String> = listOf(
    "Africa/Abidjan",
    "Africa/Accra",
    "Africa/Addis_Ababa",
    "Africa/Algiers",
    "Africa/Asmara",
    "Africa/Asmera",
    "Africa/Bamako",
    "Africa/Bangui",
    "Africa/Banjul",
    "Africa/Bissau",
    "Africa/Blantyre",
    "Africa/Brazzaville",
    "Africa/Bujumbura",
    "Africa/Cairo",
    "Africa/Casablanca",
    "Africa/Conakry",
    "Africa/Dakar",
    "Africa/Dar_es_Salaam",
    "Africa/Djibouti",
    "Africa/Douala",
    "Africa/El_Aaiun",
    "Africa/Freetown",
    "Africa/Gaborone",
    "Africa/Harare",
    "Africa/Johannesburg",
    "Africa/Juba",
    "Africa/Kampala",
    "Africa/Khartoum",
    "Africa/Kigali",
    "Africa/Libreville",
    "Africa/Lome",
    "Africa/Luanda",
    "Africa/Lusaka",
    "Africa/Malabo",
    "Africa/Maseru",
    "Africa/Mbabane",
    "Africa/Mogadishu",
    "Africa/Monrovia",
    "Africa/Nairobi",
    "Africa/Ndjamena",
    "Africa/Niamey",
    "Africa/Nouakchott",
    "Africa/Ouagadougou",
    "Africa/Porto-Novo",
    "Africa/Sao_Tome",
    "Africa/Timbuktu",
    "Africa/Tripoli",
    "Africa/Tunis",
    "Africa/Windhoek",
    "America/Anguilla",
    "America/Antigua",
    "America/Argentina/ComodRivadavia",
    "America/Aruba",
    "America/Asuncion",
    "America/Atka",
    "America/Barbados",
    "America/Belize",
    "America/Bogota",
    "America/Buenos_Aires",
    "America/Caracas",
    "America/Catamarca",
    "America/Cayenne",
    "America/Cayman",
    "America/Coral_Harbour",
    "America/Cordoba",
    "America/Costa_Rica",
    "America/Curacao",
    "America/Dominica",
    "America/El_Salvador",
    "America/Ensenada",
    "America/Fort_Wayne",
    "America/Godthab",
    "America/Grand_Turk",
    "America/Grenada",
    "America/Guadeloupe",
    "America/Guatemala",
    "America/Guyana",
    "America/Havana",
    "America/Indianapolis",
    "America/Jamaica",
    "America/Jujuy",
    "America/Knox_IN",
    "America/Kralendijk",
    "America/La_Paz",
    "America/Lima",
    "America/Louisville",
    "America/Lower_Princes",
    "America/Managua",
    "America/Marigot",
    "America/Martinique",
    "America/Mendoza",
    "America/Miquelon",
    "America/Montevideo",
    "America/Montreal",
    "America/Montserrat",
    "America/Nassau",
    "America/Nipigon",
    "America/Pangnirtung",
    "America/Paramaribo",
    "America/Port-au-Prince",
    "America/Port_of_Spain",
    "America/Porto_Acre",
    "America/Rainy_River",
    "America/Rosario",
    "America/Santa_Isabel",
    "America/Santo_Domingo",
    "America/Shiprock",
    "America/St_Barthelemy",
    "America/St_Kitts",
    "America/St_Lucia",
    "America/St_Thomas",
    "America/St_Vincent",
    "America/Tegucigalpa",
    "America/Thunder_Bay",
    "America/Tortola",
    "America/Virgin",
    "America/Yellowknife",
    "Antarctica/South_Pole",
    "Arctic/Longyearbyen",
    "Asia/Aden",
    "Asia/Amman",
    "Asia/Ashgabat",
    "Asia/Ashkhabad",
    "Asia/Baghdad",
    "Asia/Bahrain",
    "Asia/Baku",
    "Asia/Beirut",
    "Asia/Bishkek",
    "Asia/Brunei",
    "Asia/Calcutta",
    "Asia/Choibalsan",
    "Asia/Chongqing",
    "Asia/Chungking",
    "Asia/Colombo",
    "Asia/Dacca",
    "Asia/Damascus",
    "Asia/Dhaka",
    "Asia/Dili",
    "Asia/Dushanbe",
    "Asia/Harbin",
    "Asia/Hong_Kong",
    "Asia/Istanbul",
    "Asia/Jerusalem",
    "Asia/Kabul",
    "Asia/Karachi",
    "Asia/Kashgar",
    "Asia/Kathmandu",
    "Asia/Katmandu",
    "Asia/Kolkata",
    "Asia/Kuwait",
    "Asia/Macao",
    "Asia/Macau",
    "Asia/Manila",
    "Asia/Muscat",
    "Asia/Phnom_Penh",
    "Asia/Pyongyang",
    "Asia/Qatar",
    "Asia/Rangoon",
    "Asia/Saigon",
    "Asia/Seoul",
    "Asia/Taipei",
    "Asia/Tbilisi",
    "Asia/Tehran",
    "Asia/Tel_Aviv",
    "Asia/Thimbu",
    "Asia/Thimphu",
    "Asia/Ujung_Pandang",
    "Asia/Ulan_Bator",
    "Asia/Vientiane",
    "Asia/Yangon",
    "Asia/Yerevan",
    "Atlantic/Bermuda",
    "Atlantic/Cape_Verde",
    "Atlantic/Faeroe",
    "Atlantic/Faroe",
    "Atlantic/Jan_Mayen",
    "Atlantic/Reykjavik",
    "Atlantic/South_Georgia",
    "Atlantic/St_Helena",
    "Atlantic/Stanley",
    "Etc/UTC",
    "Europe/Amsterdam",
    "Europe/Berlin",
    "Europe/London",
    "Europe/Madrid",
    "Europe/Moscow",
    "Europe/Paris",
    "America/Chicago",
    "America/Denver",
    "America/Los_Angeles",
    "America/New_York",
    "Asia/Tokyo",
    "Pacific/Auckland",
    "UTC",
)

internal fun testAndroidTimezonePropertyNameIsValidCString() {
    check(AndroidTimezonePropertyNameValidator.isValid(FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME))
    val invalidPropertyName = FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME + byteArrayOf(0)
    check(!AndroidTimezonePropertyNameValidator.isValid(invalidPropertyName))
}

internal fun testAndroidTimezonePropertyNameGetter() {
    val key = FfiUtils.androidTimezonePropertyName()
    check(key.contentEquals(FfiUtils.ANDROID_TIMEZONE_PROPERTY_NAME))
    key.decodeToString()
}

internal fun testTznameBufferFitsAllIanaNames() {
    val buf = FfiUtils.Buffer.tznameBuf()
    val maxLen = buf.size
    val failedTzNames = knownTimezoneNames.filter { it.length >= maxLen }
    check(failedTzNames.isEmpty()) {
        "One or more timezone names exceed the buffer length of $maxLen. Max length of found timezone: ${failedTzNames.maxOf { it.length }}\n$failedTzNames"
    }
}

internal fun testTznameBufferCorrectSize() {
    check(FfiUtils.Buffer.MAX_LEN == 64)
    check(FfiUtils.Buffer.tznameBuf().size == FfiUtils.Buffer.MAX_LEN)
}
