package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AbuDhabiZone
import com.example.data.model.CartItem
import com.example.data.model.DeliveryAddress
import com.example.ui.theme.BadgeApproved
import com.example.ui.theme.DarkText
import com.example.ui.theme.DarkTextMuted
import com.example.ui.theme.DarkTextSecondary
import com.example.ui.theme.DeepGolden
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedBg
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.LightYellowContainer
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftCreamBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartCheckoutScreen(
    cartItems: List<CartItem>,
    deliveryAddress: DeliveryAddress,
    selectedZone: AbuDhabiZone,
    onUpdateQuantity: (String, Int) -> Unit,
    onClearCart: () -> Unit,
    onChangeAddressClick: () -> Unit,
    onPlaceOrder: (String) -> Unit,
    onBrowseStores: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("Apple Pay / Credit Card") }

    val isAddressInAbuDhabi = deliveryAddress.emirate.equals("Abu Dhabi", ignoreCase = true)
    val subtotal = cartItems.sumOf { it.product.priceAed * it.quantity }
    val deliveryFee = if (cartItems.isNotEmpty()) selectedZone.deliveryFeeAed else 0.0
    val total = subtotal + deliveryFee

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCreamBackground)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SoftCreamBackground,
                titleContentColor = DarkText
            ),
            title = {
                Text(
                    text = "Your VAYORA Cart",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DarkText
                )
            },
            actions = {
                if (cartItems.isNotEmpty()) {
                    IconButton(onClick = onClearCart, modifier = Modifier.testTag("clear_cart_button")) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear Cart",
                            tint = ErrorRed
                        )
                    }
                }
            }
        )

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(LightYellowContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = DeepGolden,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your cart is currently empty",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Explore verified Abu Dhabi roasteries, cafes, and restaurants to order delicious fresh items.",
                        fontSize = 13.sp,
                        color = DarkTextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onBrowseStores,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryYellow,
                            contentColor = DarkText
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("browse_stores_button")
                    ) {
                        Text("Browse Abu Dhabi Stores", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Store Info
                item {
                    val storeName = cartItems.first().storeName
                    Surface(
                        color = PureWhite,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(PrimaryYellow, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = DarkText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Ordering from: $storeName",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DarkText
                                )
                                Text(
                                    text = "Abu Dhabi Licensed Store • ADAFSA Verified",
                                    fontSize = 11.sp,
                                    color = DeepGolden
                                )
                            }
                        }
                    }
                }

                // Cart Items
                items(cartItems) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.product.nameEn,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DarkText
                                )
                                Text(
                                    text = item.product.nameAr,
                                    fontSize = 11.sp,
                                    color = DeepGolden
                                )
                                Text(
                                    text = "${item.product.priceAed.toInt()} AED each",
                                    fontSize = 12.sp,
                                    color = DarkTextSecondary
                                )
                            }

                            // Quantity controls
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = LightYellowContainer,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .clickable { onUpdateQuantity(item.product.id, -1) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        tint = DarkText,
                                        modifier = Modifier.padding(6.dp)
                                    )
                                }

                                Text(
                                    text = "${item.quantity}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DarkText,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )

                                Surface(
                                    color = PrimaryYellow,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .clickable { onUpdateQuantity(item.product.id, 1) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = DarkText,
                                        modifier = Modifier.padding(6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = "${(item.product.priceAed * item.quantity).toInt()} AED",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = DeepGolden
                                )
                            }
                        }
                    }
                }

                // Delivery Address & Validation Card
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isAddressInAbuDhabi) PureWhite else ErrorRedBg
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = if (isAddressInAbuDhabi) GoldBorder else ErrorRed
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isAddressInAbuDhabi) Icons.Default.LocationOn else Icons.Default.Block,
                                        contentDescription = null,
                                        tint = if (isAddressInAbuDhabi) DeepGolden else ErrorRed,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Delivery Destination",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isAddressInAbuDhabi) DarkText else ErrorRed
                                    )
                                }

                                Text(
                                    text = "Change",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepGolden,
                                    modifier = Modifier
                                        .clickable { onChangeAddressClick() }
                                        .testTag("cart_change_address_button")
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (isAddressInAbuDhabi) {
                                Text(
                                    text = "${deliveryAddress.buildingName}, ${deliveryAddress.apartmentOrVilla}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = DarkText
                                )
                                Text(
                                    text = "${deliveryAddress.street}, ${deliveryAddress.zoneName}, Abu Dhabi",
                                    fontSize = 12.sp,
                                    color = DarkTextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Surface(
                                    color = LightYellowContainer,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "Verified Abu Dhabi Service Area (${selectedZone.estimatedMinutes}m transit)",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepGolden,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            } else {
                                Text(
                                    text = "OUT OF SERVICE AREA ERROR",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = ErrorRed
                                )
                                Text(
                                    text = "Selected address is in '${deliveryAddress.emirate}'. VAYORA delivers exclusively to Abu Dhabi, UAE. Orders outside Abu Dhabi cannot be accepted.",
                                    fontSize = 11.sp,
                                    color = ErrorRed
                                )
                            }
                        }
                    }
                }

                // Payment Method Selector
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Payment Method",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            val methods = listOf(
                                "Apple Pay / Credit Card" to Icons.Default.CreditCard,
                                "Card on Delivery (Mobile POS)" to Icons.Default.Payments,
                                "Cash on Delivery (AED)" to Icons.Default.Payments
                            )

                            methods.forEach { (name, icon) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedPaymentMethod = name }
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedPaymentMethod == name,
                                        onClick = { selectedPaymentMethod = name },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = DeepGolden,
                                            unselectedColor = DarkTextMuted
                                        )
                                    )
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = DeepGolden,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = name,
                                        fontSize = 13.sp,
                                        color = DarkText,
                                        fontWeight = if (selectedPaymentMethod == name) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }

                // Order Price Breakdown
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Payment Summary (AED)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = DarkText
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Items Subtotal", fontSize = 12.sp, color = DarkTextSecondary)
                                Text("${subtotal.toInt()} AED", fontSize = 12.sp, color = DarkText)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Abu Dhabi Zone Delivery (${selectedZone.nameEn})",
                                    fontSize = 12.sp,
                                    color = DarkTextSecondary
                                )
                                Text("${deliveryFee.toInt()} AED", fontSize = 12.sp, color = DeepGolden)
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = GoldBorder
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Total Order Amount",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = DarkText
                                )
                                Text(
                                    "${total.toInt()} AED",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = DeepGolden
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Sticky Checkout Button
            Surface(
                color = PureWhite,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (!isAddressInAbuDhabi) {
                        Text(
                            text = "Checkout Disabled: Address must be in Abu Dhabi.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ErrorRed,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Button(
                        onClick = { onPlaceOrder(selectedPaymentMethod) },
                        enabled = isAddressInAbuDhabi,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryYellow,
                            contentColor = DarkText,
                            disabledContainerColor = Color(0xFFE7E5E4),
                            disabledContentColor = Color(0xFFA8A29E)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("place_order_button")
                    ) {
                        Text(
                            text = "Place Order • ${total.toInt()} AED",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
