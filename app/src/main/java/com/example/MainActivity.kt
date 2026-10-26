package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.data.repository.VayoraData
import com.example.ui.components.VayoraBottomNavigation
import com.example.ui.components.VayoraTopHeader
import com.example.ui.screens.AbuDhabiLocationDialog
import com.example.ui.screens.CartCheckoutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.StoreDetailScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.VayoraScreen
import com.example.ui.viewmodel.VayoraViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: VayoraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.toastNotice) {
                    uiState.toastNotice?.let { notice ->
                        snackbarHostState.showSnackbar(notice)
                        viewModel.clearToastNotice()
                    }
                }

                // Global BackHandler for nested sub-screens
                BackHandler(enabled = uiState.currentScreen !is VayoraScreen.Home) {
                    when (uiState.currentScreen) {
                        is VayoraScreen.StoreDetail -> viewModel.navigateTo(VayoraScreen.Home)
                        is VayoraScreen.Checkout -> viewModel.navigateTo(VayoraScreen.Cart)
                        is VayoraScreen.OrderTracking -> viewModel.navigateTo(VayoraScreen.OrderHistory)
                        else -> viewModel.navigateTo(VayoraScreen.Home)
                    }
                }

                val currentRoute = when (uiState.currentScreen) {
                    is VayoraScreen.Home, is VayoraScreen.StoreDetail -> "home"
                    is VayoraScreen.Cart, is VayoraScreen.Checkout -> "cart"
                    is VayoraScreen.OrderTracking, is VayoraScreen.OrderHistory -> "orders"
                    is VayoraScreen.Profile -> "profile"
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        if (uiState.currentScreen is VayoraScreen.Home) {
                            VayoraTopHeader(
                                selectedZone = uiState.selectedArea,
                                cartItemCount = uiState.cartItems.sumOf { it.quantity },
                                onLocationClick = { viewModel.showLocationDialog(true) },
                                onCartClick = { viewModel.navigateTo(VayoraScreen.Cart) }
                            )
                        }
                    },
                    bottomBar = {
                        VayoraBottomNavigation(
                            currentScreen = currentRoute,
                            cartCount = uiState.cartItems.sumOf { it.quantity },
                            onNavigate = { route ->
                                when (route) {
                                    "home" -> viewModel.navigateTo(VayoraScreen.Home)
                                    "cart" -> viewModel.navigateTo(VayoraScreen.Cart)
                                    "orders" -> viewModel.navigateTo(VayoraScreen.OrderHistory)
                                    "profile" -> viewModel.navigateTo(VayoraScreen.Profile)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (val screen = uiState.currentScreen) {
                            is VayoraScreen.Home -> {
                                HomeScreen(
                                    stores = uiState.stores,
                                    selectedCategory = uiState.selectedCategory,
                                    searchQuery = uiState.searchQuery,
                                    onCategorySelect = { viewModel.selectCategory(it) },
                                    onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                                    onStoreClick = { viewModel.openStore(it) },
                                    onLocationBannerClick = { viewModel.showLocationDialog(true) }
                                )
                            }

                            is VayoraScreen.StoreDetail -> {
                                val store = uiState.selectedStore
                                    ?: uiState.stores.find { it.id == screen.storeId }
                                    ?: uiState.stores.first()

                                StoreDetailScreen(
                                    store = store,
                                    cartItemCount = uiState.cartItems.sumOf { it.quantity },
                                    onBack = { viewModel.navigateTo(VayoraScreen.Home) },
                                    onAddToCart = { prod, st -> viewModel.addToCart(prod, st) },
                                    onViewCart = { viewModel.navigateTo(VayoraScreen.Cart) }
                                )
                            }

                            is VayoraScreen.Cart, is VayoraScreen.Checkout -> {
                                CartCheckoutScreen(
                                    cartItems = uiState.cartItems,
                                    deliveryAddress = uiState.selectedDeliveryAddress,
                                    selectedZone = uiState.selectedArea,
                                    onUpdateQuantity = { id, delta -> viewModel.updateCartItemQuantity(id, delta) },
                                    onClearCart = { viewModel.clearCart() },
                                    onChangeAddressClick = { viewModel.showLocationDialog(true) },
                                    onPlaceOrder = { method -> viewModel.placeOrder(method) },
                                    onBrowseStores = { viewModel.navigateTo(VayoraScreen.Home) }
                                )
                            }

                            is VayoraScreen.OrderTracking -> {
                                val order = uiState.selectedOrder
                                    ?: uiState.orders.find { it.id == screen.orderId }
                                    ?: uiState.orders.firstOrNull()

                                if (order != null) {
                                    OrderTrackingScreen(
                                        order = order,
                                        onBack = { viewModel.navigateTo(VayoraScreen.OrderHistory) },
                                        onAdvanceStatus = { id -> viewModel.advanceOrderStatus(id) }
                                    )
                                } else {
                                    OrderHistoryScreen(
                                        orders = uiState.orders,
                                        onSelectOrder = { viewModel.selectOrderForTracking(it) },
                                        onBrowseStores = { viewModel.navigateTo(VayoraScreen.Home) }
                                    )
                                }
                            }

                            is VayoraScreen.OrderHistory -> {
                                OrderHistoryScreen(
                                    orders = uiState.orders,
                                    onSelectOrder = { viewModel.selectOrderForTracking(it) },
                                    onBrowseStores = { viewModel.navigateTo(VayoraScreen.Home) }
                                )
                            }

                            is VayoraScreen.Profile -> {
                                ProfileScreen(
                                    userProfile = uiState.userProfile,
                                    savedAddresses = uiState.savedAddresses,
                                    selectedAddressId = uiState.selectedDeliveryAddress.id,
                                    onSelectAddress = { viewModel.selectDeliveryAddress(it) },
                                    onManageLocationsClick = { viewModel.showLocationDialog(true) }
                                )
                            }
                        }

                        // Abu Dhabi Location & Geofence Dialog
                        if (uiState.isLocationDialogVisible) {
                            AbuDhabiLocationDialog(
                                currentZone = uiState.selectedArea,
                                savedAddresses = uiState.savedAddresses,
                                rejectionNotice = uiState.addressRejectionNotice,
                                onSelectZone = { viewModel.selectAbuDhabiZone(it) },
                                onSelectAddress = { viewModel.selectDeliveryAddress(it) },
                                onAttemptUnsupportedEmirate = { viewModel.attemptSelectUnsupportedEmirate(it) },
                                onAddNewAddress = { label, emirate, zone, bld, str, apt, inst ->
                                    viewModel.addDeliveryAddress(label, emirate, zone, bld, str, apt, inst)
                                },
                                onDismiss = {
                                    viewModel.clearAddressRejectionNotice()
                                    viewModel.showLocationDialog(false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
