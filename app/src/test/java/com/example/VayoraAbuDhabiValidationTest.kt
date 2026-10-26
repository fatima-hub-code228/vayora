package com.example

import androidx.compose.ui.graphics.Color
import com.example.data.model.AbuDhabiServiceRegistry
import com.example.data.model.AbuDhabiZone
import com.example.data.model.DeliveryAddress
import com.example.data.model.StoreCategory
import com.example.data.repository.VayoraData
import com.example.ui.theme.DarkText
import com.example.ui.theme.DeepGolden
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftCreamBackground
import com.example.ui.viewmodel.VayoraViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class VayoraAbuDhabiValidationTest {

    private lateinit var viewModel: VayoraViewModel

    @Before
    fun setup() {
        viewModel = VayoraViewModel()
    }

    @Test
    fun `test app configured exclusively for Abu Dhabi`() {
        assertEquals("Abu Dhabi", AbuDhabiServiceRegistry.SUPPORTED_EMIRATE)
        // Verify all configured stores are in Abu Dhabi
        VayoraData.ABU_DHABI_STORES.forEach { store ->
            assertEquals("Store ${store.nameEn} must be in Abu Dhabi", "Abu Dhabi", store.emirate)
        }
    }

    @Test
    fun `test validation rejects addresses in other UAE Emirates like Dubai or Sharjah`() {
        val dubaiResult = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = "Dubai",
            areaName = "Downtown Dubai",
            streetOrBuilding = "Burj Khalifa Tower"
        )
        assertTrue(
            "Dubai address must be rejected as UnsupportedEmirate",
            dubaiResult is AbuDhabiServiceRegistry.AddressValidationResult.UnsupportedEmirate
        )

        val sharjahResult = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = "Sharjah",
            areaName = "Al Majaz",
            streetOrBuilding = "Corniche Tower"
        )
        assertTrue(
            "Sharjah address must be rejected",
            sharjahResult is AbuDhabiServiceRegistry.AddressValidationResult.UnsupportedEmirate
        )
    }

    @Test
    fun `test validation accepts genuine Abu Dhabi service zones`() {
        val validReem = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = "Abu Dhabi",
            areaName = "Al Reem Island",
            streetOrBuilding = "Sky Tower, Shams Abu Dhabi"
        )
        assertTrue("Al Reem Island must be valid", validReem is AbuDhabiServiceRegistry.AddressValidationResult.Valid)

        val validDanah = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = "Abu Dhabi",
            areaName = "Al Danah / Downtown Abu Dhabi",
            streetOrBuilding = "Sheikh Zayed St Building 12"
        )
        assertTrue("Al Danah must be valid", validDanah is AbuDhabiServiceRegistry.AddressValidationResult.Valid)
    }

    @Test
    fun `test validation rejects out-of-service area in Abu Dhabi`() {
        val remoteResult = AbuDhabiServiceRegistry.validateDeliveryAddress(
            emirate = "Abu Dhabi",
            areaName = "Liwa Oasis Desert Outpost",
            streetOrBuilding = "Camp 4"
        )
        assertTrue(
            "Remote unserviceable area must be rejected",
            remoteResult is AbuDhabiServiceRegistry.AddressValidationResult.OutOfServiceArea
        )
    }

    @Test
    fun `test adding delivery address in Dubai is rejected`() {
        val added = viewModel.addDeliveryAddress(
            label = "Dubai Office",
            emirate = "Dubai",
            zoneName = "Business Bay",
            building = "The Prism",
            street = "Marasi Dr",
            apartment = "301",
            instructions = ""
        )
        assertFalse("Address addition in Dubai must fail", added)
        assertNotNull("Rejection notice must be set", viewModel.uiState.value.addressRejectionNotice)
        assertTrue("Rejection notice mentions Dubai", viewModel.uiState.value.addressRejectionNotice!!.contains("Dubai"))
    }

    @Test
    fun `test business approval rule prevents ordering from unapproved stores`() {
        val unapprovedStore = VayoraData.ABU_DHABI_STORES.first { !it.isApproved }
        val product = VayoraData.PRODUCTS.first { it.storeId == unapprovedStore.id }

        // Attempting to add product from unapproved merchant must return false
        val addSuccess = viewModel.addToCart(product, unapprovedStore)
        assertFalse("Ordering from unapproved store must be rejected", addSuccess)
        assertNotNull("Rejection notice should be set in UI state", viewModel.uiState.value.addressRejectionNotice)
        assertTrue("Cart must remain empty", viewModel.uiState.value.cartItems.isEmpty())
    }

    @Test
    fun `test checkout rejects unsupported out-of-area delivery addresses`() {
        val approvedStore = VayoraData.ABU_DHABI_STORES.first { it.isApproved }
        val product = VayoraData.PRODUCTS.first { it.storeId == approvedStore.id }

        // Add valid item to cart
        val added = viewModel.addToCart(product, approvedStore)
        assertTrue("Should add approved item to cart", added)
        assertEquals(1, viewModel.uiState.value.cartItems.size)

        // Attempt to select an unsupported address outside Abu Dhabi
        val invalidAddress = DeliveryAddress(
            id = "dubai_addr",
            label = "Dubai Office",
            emirate = "Dubai",
            zoneName = "Business Bay",
            buildingName = "The Prism",
            street = "Marasi Dr",
            apartmentOrVilla = "Office 301",
            isVerifiedAbuDhabi = false
        )

        viewModel.selectDeliveryAddress(invalidAddress)
        assertNotNull("Rejection notice must be set when selecting invalid address", viewModel.uiState.value.addressRejectionNotice)

        val orderSuccess = viewModel.placeOrder("Apple Pay / Credit Card")
        assertFalse("Checkout must fail when out-of-area address rejection is active", orderSuccess)
    }

    @Test
    fun `test cart and order placement succeeds for valid Abu Dhabi address`() {
        val approvedStore = VayoraData.ABU_DHABI_STORES.first { it.isApproved }
        val product = VayoraData.PRODUCTS.first { it.storeId == approvedStore.id }

        viewModel.addToCart(product, approvedStore)

        // Select Abu Dhabi Reem Island address
        val validAddress = VayoraData.DEFAULT_SAVED_ADDRESSES[0]
        viewModel.selectDeliveryAddress(validAddress)

        val initialOrdersCount = viewModel.uiState.value.orders.size
        val orderPlaced = viewModel.placeOrder("Apple Pay / Credit Card")
        assertTrue("Order should place successfully in Abu Dhabi", orderPlaced)
        assertEquals(initialOrdersCount + 1, viewModel.uiState.value.orders.size)
        assertTrue("Cart should be cleared after order placement", viewModel.uiState.value.cartItems.isEmpty())
    }

    @Test
    fun `test yellow and golden theme color values match specifications`() {
        assertEquals("Primary yellow must be #FACC15", Color(0xFFFACC15), PrimaryYellow)
        assertEquals("Deep golden must be #B8860B", Color(0xFFB8860B), DeepGolden)
        assertEquals("Soft cream background must be #FFFBEB", Color(0xFFFFFBEB), SoftCreamBackground)
        assertEquals("White must be #FFFFFF", Color(0xFFFFFFFF), PureWhite)
        assertEquals("Dark text must be #292524", Color(0xFF292524), DarkText)
    }
}
