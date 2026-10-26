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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.theme.BadgeApproved
import com.example.ui.theme.BadgeApprovedBg
import com.example.ui.theme.DarkText
import com.example.ui.theme.DarkTextMuted
import com.example.ui.theme.DarkTextSecondary
import com.example.ui.theme.DeepGolden
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.LightYellowContainer
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftCreamBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    order: Order,
    onBack: () -> Unit,
    onAdvanceStatus: (String) -> Unit
) {
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
            navigationIcon = {
                IconButton(onClick = onBack, modifier = Modifier.testTag("tracking_back_button")) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkText
                    )
                }
            },
            title = {
                Column {
                    Text(
                        text = "Order #${order.id}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = DarkText
                    )
                    Text(
                        text = "Abu Dhabi Live Delivery Tracker",
                        fontSize = 11.sp,
                        color = DeepGolden
                    )
                }
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Live Status Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PrimaryYellow),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = order.status.title,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = DarkText
                            )

                            Surface(
                                color = DeepGolden,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = order.estimatedArrival,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = order.status.description,
                            fontSize = 13.sp,
                            color = DarkTextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Status Progression Steps
                        OrderStatusProgress(status = order.status)
                    }
                }
            }

            // Test Simulation Action
            item {
                Surface(
                    color = LightYellowContainer,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Abu Dhabi Delivery Simulation",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = DarkText
                            )
                            Text(
                                text = "Advance courier status across capital zones",
                                fontSize = 11.sp,
                                color = DarkTextSecondary
                            )
                        }

                        Button(
                            onClick = { onAdvanceStatus(order.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepGolden,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("advance_order_status_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Next Step", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Destination Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = DeepGolden,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Delivering in Abu Dhabi to:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = DarkText
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${order.deliveryAddress.buildingName}, ${order.deliveryAddress.apartmentOrVilla}",
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = DarkText
                        )
                        Text(
                            text = "${order.deliveryAddress.street}, ${order.deliveryAddress.zoneName}, Abu Dhabi, UAE",
                            fontSize = 12.sp,
                            color = DarkTextSecondary
                        )
                    }
                }
            }

            // Store & Items Summary
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Merchant: ${order.storeName}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DarkText
                        )
                        Text(
                            text = "Abu Dhabi Licensed Store",
                            fontSize = 11.sp,
                            color = DeepGolden
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        order.items.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${item.quantity}x ${item.product.nameEn}",
                                    fontSize = 12.sp,
                                    color = DarkText
                                )
                                Text(
                                    text = "${(item.product.priceAed * item.quantity).toInt()} AED",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepGolden
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = GoldBorder
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Fee", fontSize = 12.sp, color = DarkTextSecondary)
                            Text("${order.deliveryFeeAed.toInt()} AED", fontSize = 12.sp, color = DarkText)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid (${order.paymentMethod})", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = DarkText)
                            Text("${order.totalAed.toInt()} AED", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = DeepGolden)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusProgress(status: OrderStatus) {
    val steps = listOf(
        OrderStatus.CONFIRMED to "Confirmed",
        OrderStatus.PREPARING to "Preparing",
        OrderStatus.ON_THE_WAY to "On The Way",
        OrderStatus.DELIVERED to "Delivered"
    )

    val currentStepIndex = when (status) {
        OrderStatus.CONFIRMED -> 0
        OrderStatus.PREPARING -> 1
        OrderStatus.ON_THE_WAY -> 2
        OrderStatus.DELIVERED -> 3
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, pair ->
            val isPassed = index <= currentStepIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(if (isPassed) DeepGolden else PureWhite, CircleShape)
                        .border(1.dp, DeepGolden, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (isPassed) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Text(
                            text = "${index + 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkTextSecondary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = pair.second,
                    fontSize = 10.sp,
                    fontWeight = if (isPassed) FontWeight.Bold else FontWeight.Normal,
                    color = DarkText
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    orders: List<Order>,
    onSelectOrder: (Order) -> Unit,
    onBrowseStores: () -> Unit
) {
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
                    text = "Abu Dhabi Orders",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DarkText
                )
            }
        )

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No orders placed yet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onBrowseStores,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryYellow,
                            contentColor = DarkText
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Browse Abu Dhabi Stores", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(orders) { order ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectOrder(order) }
                            .testTag("order_history_item_${order.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = order.storeName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = DarkText
                                )

                                Surface(
                                    color = if (order.status == OrderStatus.DELIVERED) BadgeApprovedBg else LightYellowContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = order.status.title,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (order.status == OrderStatus.DELIVERED) BadgeApproved else DeepGolden,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Order #${order.id} • ${order.createdAtFormatted}",
                                fontSize = 11.sp,
                                color = DarkTextSecondary
                            )

                            Text(
                                text = "${order.items.size} items • ${order.deliveryAddress.zoneName}, Abu Dhabi",
                                fontSize = 12.sp,
                                color = DarkTextSecondary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total: ${order.totalAed.toInt()} AED",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = DeepGolden
                                )

                                Text(
                                    text = "Track Order >",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepGolden
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
