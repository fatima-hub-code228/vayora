package com.example.data.model

/**
 * Abu Dhabi Service Area and Delivery Geofence definitions.
 * VAYORA is strictly restricted to Abu Dhabi, UAE.
 */
data class AbuDhabiZone(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val district: String,
    val deliveryFeeAed: Double,
    val estimatedMinutes: Int,
    val isServiceable: Boolean = true
)

object AbuDhabiServiceRegistry {
    const val SUPPORTED_EMIRATE = "Abu Dhabi"
    const val COUNTRY = "United Arab Emirates"

    // Genuine Abu Dhabi Service Zones
    val SERVICE_ZONES: List<AbuDhabiZone> = listOf(
        AbuDhabiZone(
            id = "ad_al_danah",
            nameEn = "Al Danah / Downtown Abu Dhabi",
            nameAr = "الدانة / وسط المدينة",
            district = "Central Capital",
            deliveryFeeAed = 5.0,
            estimatedMinutes = 25,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_zahiyah",
            nameEn = "Al Zahiyah (Tourist Club Area)",
            nameAr = "الزاهية / النادي السياحي",
            district = "Downtown",
            deliveryFeeAed = 5.0,
            estimatedMinutes = 25,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_khalidiya",
            nameEn = "Al Khalidiya & Corniche",
            nameAr = "الخالدية والكورنيش",
            district = "Corniche Sector",
            deliveryFeeAed = 6.0,
            estimatedMinutes = 30,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_reem",
            nameEn = "Al Reem Island",
            nameAr = "جزيرة الريم",
            district = "Island Sector",
            deliveryFeeAed = 7.0,
            estimatedMinutes = 30,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_maryah",
            nameEn = "Al Maryah Island (Financial District)",
            nameAr = "جزيرة المارية",
            district = "Island Sector",
            deliveryFeeAed = 7.0,
            estimatedMinutes = 25,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_bateen",
            nameEn = "Al Bateen & Marina",
            nameAr = "البطين والمارينا",
            district = "West Capital",
            deliveryFeeAed = 7.0,
            estimatedMinutes = 35,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_nahyan",
            nameEn = "Al Nahyan & Al Wahda",
            nameAr = "النهيان والوحدة",
            district = "Central Capital",
            deliveryFeeAed = 6.0,
            estimatedMinutes = 25,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_al_muroor",
            nameEn = "Al Muroor & Al Hadbat",
            nameAr = "المرور والهضبات",
            district = "Central South",
            deliveryFeeAed = 6.0,
            estimatedMinutes = 30,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_khalifa_city",
            nameEn = "Khalifa City A",
            nameAr = "مدينة خليفة أ",
            district = "Suburban Capital",
            deliveryFeeAed = 10.0,
            estimatedMinutes = 40,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_yas_island",
            nameEn = "Yas Island",
            nameAr = "جزيرة ياس",
            district = "Entertainment Sector",
            deliveryFeeAed = 10.0,
            estimatedMinutes = 35,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_saadiyat",
            nameEn = "Saadiyat Island (Cultural District)",
            nameAr = "جزيرة السعديات",
            district = "Cultural Sector",
            deliveryFeeAed = 9.0,
            estimatedMinutes = 35,
            isServiceable = true
        ),
        AbuDhabiZone(
            id = "ad_mbz_city",
            nameEn = "Mohamed Bin Zayed City (MBZ)",
            nameAr = "مدينة محمد بن زايد",
            district = "Mainland South",
            deliveryFeeAed = 11.0,
            estimatedMinutes = 45,
            isServiceable = true
        )
    )

    // Unserviceable UAE Emirates (explicitly rejected)
    val UNSERVICEABLE_EMIRATES = listOf(
        "Dubai",
        "Sharjah",
        "Ajman",
        "Ras Al Khaimah",
        "Fujairah",
        "Umm Al Quwain"
    )

    sealed class AddressValidationResult {
        data class Valid(val zone: AbuDhabiZone) : AddressValidationResult()
        data class UnsupportedEmirate(val attemptedEmirate: String) : AddressValidationResult()
        data class OutOfServiceArea(val areaName: String) : AddressValidationResult()
        data class IncompleteAddress(val missingFields: String) : AddressValidationResult()
    }

    /**
     * Validates an address strictly against Abu Dhabi coverage.
     */
    fun validateDeliveryAddress(
        emirate: String,
        areaName: String,
        streetOrBuilding: String
    ): AddressValidationResult {
        if (streetOrBuilding.isBlank()) {
            return AddressValidationResult.IncompleteAddress("Building / Street name is required")
        }

        val cleanEmirate = emirate.trim()
        if (!cleanEmirate.equals(SUPPORTED_EMIRATE, ignoreCase = true)) {
            return AddressValidationResult.UnsupportedEmirate(cleanEmirate)
        }

        val matchedZone = SERVICE_ZONES.find { zone ->
            zone.nameEn.contains(areaName, ignoreCase = true) ||
                    zone.id.equals(areaName, ignoreCase = true) ||
                    areaName.contains(zone.nameEn, ignoreCase = true)
        }

        return if (matchedZone != null) {
            if (matchedZone.isServiceable) {
                AddressValidationResult.Valid(matchedZone)
            } else {
                AddressValidationResult.OutOfServiceArea(areaName)
            }
        } else {
            // Check if user entered an unserviceable remote region (e.g. Liwa, Western Region)
            AddressValidationResult.OutOfServiceArea(areaName)
        }
    }
}
