package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.AbuDhabiServiceRegistry
import com.example.data.model.AbuDhabiZone
import com.example.data.model.CartItem
import com.example.data.model.DeliveryAddress
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Product
import com.example.data.model.Store
import com.example.data.model.StoreCategory
import com.example.data.model.UserProfile
import com.example.data.repository.VayoraData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class VayoraScreen {
    object Home : VayoraScreen()
    data class StoreDetail(val storeId: String) : VayoraScreen()
    object Cart : VayoraScreen()
    object Checkout : VayoraScreen()
    data class OrderTracking(val orderId: String) : VayoraScreen()
    object OrderHistory : VayoraScreen()
    object Profile : VayoraScreen()
}

data class VayoraUiState(
    val currentScreen: VayoraScreen = VayoraScreen.Home,
    val selectedArea: AbuDhabiZone = AbuDhabiServiceRegistry.SERVICE_ZONES[3], // Al Reem Island
    val selectedCategory: StoreCategory = StoreCategory.ALL,
    val searchQuery: String = "",
    val stores: List<Store> = VayoraData.ABU_DHABI_STORES,
    val selectedStore: Store? = null,
    val cartItems: List<CartItem> = emptyList(),
    val savedAddresses: List<DeliveryAddress> = VayoraData.DEFAULT_SAVED_ADDRESSES,
    val selectedDeliveryAddress: DeliveryAddress = VayoraData.DEFAULT_SAVED_ADDRESSES[0],
    val orders: List<Order> = VayoraData.INITIAL_ORDERS,
    val selectedOrder: Order? = VayoraData.INITIAL_ORDERS.firstOrNull(),
    val userProfile: UserProfile = VayoraData.INITIAL_USER,
    val isLocationDialogVisible: Boolean = false,
    val addressRejectionNotice: String? = null,
    val toastNotice: String? = null
)

class VayoraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VayoraUiState())
    val uiState: StateFlow<VayoraUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: VayoraScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun openStore(store: Store) {
        _uiState.update {
            it.copy(
                selectedStore = store,
                currentScreen = VayoraScreen.StoreDetail(store.id)
            )
        }
    }

    fun selectCategory(category: StoreCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun showLocationDialog(show: Boolean) {
        _uiState.update { it.copy(isLocationDialogVisible = show) }
    }

    fun clearAddressRejectionNotice() {
        _uiState.update { it.copy(addressRejectionNotice = null) }
    }

    fun clearToastNotice() {
        _uiState.update { it.copy(toastNotice = null) }
    }

    /**
     * Updates active delivery zone. Validates that zone is in Abu Dhabi.
     */
    fun selectAbuDhabiZone(zone: AbuDhabiZone) {
        if (!zone.isServiceable) {
            _uiState.update {
                it.copy(
                    addressRejectionNotice = "Area '${zone.nameEn}' is currently outside active VAYORA courier coverage in Abu Dhabi."
                )
            }
            return
        }

        // Update zone and default selected address if matched
        _uiState.update { state ->
            val matchingAddress = state.savedAddresses.find { it.zoneName.contains(zone.nameEn, ignoreCase = true) }
                ?: state.selectedDeliveryAddress.copy(zoneName = zone.nameEn)

            state.copy(
                selectedArea = zone,
                selectedDeliveryAddress = matchingAddress,
                isLocationDialogVisible = false,
                addressRejectionNotice = null,
                toastNotice = "Location set to ${zone.nameEn}, Abu Dhabi"
            )
        }
    }

    /**
     * Rejection handler when customer tries selecting an unsupported emirate (e.g. Dubai, Sharjah)
     */
    fun attemptSelectUnsupportedEmirate(emirateName: String) {
        _uiState.update {
            it.copy(
                addressRejectionNotice = "Unsupported Service Area: VAYORA operates exclusively in Abu Dhabi, UAE. $emirateName is not supported.",
                isLocationDialogVisible = true
            )
        }
    }

    /**
     * Adds a new customer delivery address with strict Abu Dhabi validation.
     */
    fun addDeliveryAddress(
        label: String,
        emirate: String,
        zoneName: String,
        building: String,
        street: String,
        apartment: String,
        instructions: String
    ): Boolean {
        val validation = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = emirate,
            areaName = zoneName,
            streetOrBuilding = building
        )

        when (validation) {
            is AbuDhabiServiceRegistry.AddressValidationResult.Valid -> {
                val newAddress = DeliveryAddress(
                    id = "addr_${System.currentTimeMillis()}",
                    label = label.ifBlank { "Delivery Address" },
                    emirate = AbuDhabiServiceRegistry.SUPPORTED_EMIRATE,
                    zoneName = validation.zone.nameEn,
                    buildingName = building,
                    street = street.ifBlank { validation.zone.nameEn },
                    apartmentOrVilla = apartment,
                    instructions = instructions,
                    isVerifiedAbuDhabi = true
                )

                _uiState.update { state ->
                    val updatedList = state.savedAddresses + newAddress
                    state.copy(
                        savedAddresses = updatedList,
                        selectedDeliveryAddress = newAddress,
                        selectedArea = validation.zone,
                        addressRejectionNotice = null,
                        toastNotice = "Abu Dhabi delivery address saved"
                    )
                }
                return true
            }

            is AbuDhabiServiceRegistry.AddressValidationResult.UnsupportedEmirate -> {
                _uiState.update {
                    it.copy(
                        addressRejectionNotice = "Service Area Restriction: VAYORA serves Abu Dhabi only. Addresses in '${validation.attemptedEmirate}' cannot be delivered to."
                    )
                }
                return false
            }

            is AbuDhabiServiceRegistry.AddressValidationResult.OutOfServiceArea -> {
                _uiState.update {
                    it.copy(
                        addressRejectionNotice = "Outside Coverage: '${validation.areaName}' is currently not within VAYORA's active Abu Dhabi service zones."
                    )
                }
                return false
            }

            is AbuDhabiServiceRegistry.AddressValidationResult.IncompleteAddress -> {
                _uiState.update {
                    it.copy(
                        addressRejectionNotice = "Incomplete Address: ${validation.missingFields}"
                    )
                }
                return false
            }
        }
    }

    fun selectDeliveryAddress(address: DeliveryAddress) {
        if (!address.emirate.equals("Abu Dhabi", ignoreCase = true)) {
            _uiState.update {
                it.copy(
                    addressRejectionNotice = "This address is outside Abu Dhabi and cannot be selected."
                )
            }
            return
        }

        val matchingZone = AbuDhabiServiceRegistry.SERVICE_ZONES.find {
            it.nameEn.contains(address.zoneName, ignoreCase = true)
        } ?: _uiState.value.selectedArea

        _uiState.update {
            it.copy(
                selectedDeliveryAddress = address,
                selectedArea = matchingZone,
                addressRejectionNotice = null
            )
        }
    }

    /**
     * Adds an item to the cart, enforcing business approval rules!
     */
    fun addToCart(product: Product, store: Store): Boolean {
        // Enforce business approval rules: do not allow ordering from unapproved businesses!
        if (!store.isApproved) {
            _uiState.update {
                it.copy(
                    addressRejectionNotice = "Ordering Disabled: '${store.nameEn}' is awaiting municipal food safety license approval. Orders cannot be placed at this time."
                )
            }
            return false
        }

        _uiState.update { state ->
            // If cart contains items from another store, prompt or replace
            val isSameStore = state.cartItems.isEmpty() || state.cartItems.first().storeId == store.id
            val currentItems = if (isSameStore) state.cartItems else emptyList()

            val existingItemIndex = currentItems.indexOfFirst { it.product.id == product.id }
            val updatedItems = if (existingItemIndex != -1) {
                currentItems.mapIndexed { index, item ->
                    if (index == existingItemIndex) item.copy(quantity = item.quantity + 1) else item
                }
            } else {
                currentItems + CartItem(
                    product = product,
                    storeId = store.id,
                    storeName = store.nameEn,
                    quantity = 1
                )
            }

            state.copy(
                cartItems = updatedItems,
                toastNotice = "${product.nameEn} added to cart"
            )
        }
        return true
    }

    fun updateCartItemQuantity(productId: String, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull { item ->
                if (item.product.id == productId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else {
                    item
                }
            }
            state.copy(cartItems = updated)
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    /**
     * Validates and places order for Abu Dhabi delivery address.
     */
    fun placeOrder(paymentMethod: String): Boolean {
        val state = _uiState.value
        if (state.cartItems.isEmpty()) return false

        // Rule: if an address rejection is active or unaddressed, reject checkout
        if (state.addressRejectionNotice != null) {
            return false
        }

        val storeId = state.cartItems.first().storeId
        val store = VayoraData.ABU_DHABI_STORES.find { it.id == storeId }

        // Rule: unapproved business cannot receive orders
        if (store != null && !store.isApproved) {
            _uiState.update {
                it.copy(
                    addressRejectionNotice = "Cannot place order: '${store.nameEn}' is not currently approved for delivery by Abu Dhabi food authorities."
                )
            }
            return false
        }

        // Rule: delivery address must be strictly within Abu Dhabi service area
        val address = state.selectedDeliveryAddress
        if (!address.emirate.equals("Abu Dhabi", ignoreCase = true)) {
            _uiState.update {
                it.copy(
                    addressRejectionNotice = "Delivery Rejected: Address must be located in Abu Dhabi, UAE. Deliveries to ${address.emirate} are strictly unavailable."
                )
            }
            return false
        }

        val subtotal = state.cartItems.sumOf { it.product.priceAed * it.quantity }
        val deliveryFee = state.selectedArea.deliveryFeeAed
        val total = subtotal + deliveryFee

        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val orderId = "ORD-AD-${(1000..9999).random()}"

        val newOrder = Order(
            id = orderId,
            storeId = storeId,
            storeName = state.cartItems.first().storeName,
            items = state.cartItems,
            subtotalAed = subtotal,
            deliveryFeeAed = deliveryFee,
            totalAed = total,
            deliveryAddress = address,
            status = OrderStatus.CONFIRMED,
            paymentMethod = paymentMethod,
            createdAtFormatted = "Today, ${timeFormat.format(Date())}",
            estimatedArrival = "${state.selectedArea.estimatedMinutes} mins (${state.selectedArea.nameEn})"
        )

        _uiState.update {
            it.copy(
                orders = listOf(newOrder) + it.orders,
                selectedOrder = newOrder,
                cartItems = emptyList(),
                currentScreen = VayoraScreen.OrderTracking(orderId),
                toastNotice = "Order #$orderId placed successfully in Abu Dhabi!"
            )
        }
        return true
    }

    /**
     * Simulates advancement in order status for tracking testing
     */
    fun advanceOrderStatus(orderId: String) {
        _uiState.update { state ->
            val updatedOrders = state.orders.map { order ->
                if (order.id == orderId) {
                    val nextStatus = when (order.status) {
                        OrderStatus.CONFIRMED -> OrderStatus.PREPARING
                        OrderStatus.PREPARING -> OrderStatus.ON_THE_WAY
                        OrderStatus.ON_THE_WAY -> OrderStatus.DELIVERED
                        OrderStatus.DELIVERED -> OrderStatus.DELIVERED
                    }
                    order.copy(status = nextStatus)
                } else {
                    order
                }
            }
            val updatedSelectedOrder = updatedOrders.find { it.id == orderId }
            state.copy(
                orders = updatedOrders,
                selectedOrder = updatedSelectedOrder
            )
        }
    }

    fun selectOrderForTracking(order: Order) {
        _uiState.update {
            it.copy(
                selectedOrder = order,
                currentScreen = VayoraScreen.OrderTracking(order.id)
            )
        }
    }
}
