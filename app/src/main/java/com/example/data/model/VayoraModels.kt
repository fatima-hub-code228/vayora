package com.example.data.model

enum class StoreCategory(val displayName: String, val iconName: String) {
    ALL("All Stores", "Storefront"),
    ROASTERIES_NUTS("Roasteries & Nuts", "LocalCafe"),
    RESTAURANTS("Restaurants", "Restaurant"),
    GROCERY("Grocery & Market", "LocalGroceryStore"),
    SWEETS_CAFES("Sweets & Cafes", "BakeryDining")
}

data class Store(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val category: StoreCategory,
    val address: String,
    val zoneId: String,
    val zoneName: String,
    val emirate: String = "Abu Dhabi",
    val phone: String,
    val rating: Double,
    val reviewCount: Int,
    val deliveryMinutes: Int,
    val deliveryFeeAed: Double,
    val minOrderAed: Double,
    val isApproved: Boolean, // Municipal / ADAFSA Food Safety Approval
    val approvalAuthority: String = "Abu Dhabi Agriculture & Food Safety Authority (ADAFSA)",
    val approvalNumber: String,
    val bannerTag: String,
    val description: String
)

data class Product(
    val id: String,
    val storeId: String,
    val nameEn: String,
    val nameAr: String,
    val description: String,
    val priceAed: Double,
    val weightOrPortion: String,
    val category: String,
    val isAvailable: Boolean = true
)

data class CartItem(
    val product: Product,
    val storeId: String,
    val storeName: String,
    val quantity: Int,
    val specialNotes: String = ""
)

enum class OrderStatus(val title: String, val description: String) {
    CONFIRMED("Order Confirmed", "Sent to store in Abu Dhabi"),
    PREPARING("Preparing Items", "Kitchen/Roastery is packaging your order"),
    ON_THE_WAY("Out for Delivery", "Abu Dhabi courier is on the road"),
    DELIVERED("Delivered", "Delivered to your Abu Dhabi address")
}

data class DeliveryAddress(
    val id: String,
    val label: String, // "Home", "Office", "Apartment"
    val emirate: String = "Abu Dhabi",
    val zoneName: String,
    val buildingName: String,
    val street: String,
    val apartmentOrVilla: String,
    val instructions: String = "",
    val isVerifiedAbuDhabi: Boolean = true
)

data class Order(
    val id: String,
    val storeId: String,
    val storeName: String,
    val items: List<CartItem>,
    val subtotalAed: Double,
    val deliveryFeeAed: Double,
    val totalAed: Double,
    val deliveryAddress: DeliveryAddress,
    val status: OrderStatus,
    val paymentMethod: String,
    val createdAtFormatted: String,
    val estimatedArrival: String
)

data class UserProfile(
    val name: String,
    val phone: String,
    val email: String,
    val defaultAddressId: String
)
