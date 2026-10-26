package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.data.model.Store
import com.example.data.repository.VayoraData
import com.example.ui.components.ProductListItem
import com.example.ui.theme.BadgeApproved
import com.example.ui.theme.BadgeApprovedBg
import com.example.ui.theme.BadgePending
import com.example.ui.theme.BadgePendingBg
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
fun StoreDetailScreen(
    store: Store,
    cartItemCount: Int,
    onBack: () -> Unit,
    onAddToCart: (Product, Store) -> Unit,
    onViewCart: () -> Unit
) {
    val storeProducts = VayoraData.PRODUCTS.filter { it.storeId == store.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCreamBackground)
    ) {
        // Top App Bar
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SoftCreamBackground,
                titleContentColor = DarkText
            ),
            navigationIcon = {
                IconButton(onClick = onBack, modifier = Modifier.testTag("store_back_button")) {
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
                        text = store.nameEn,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = "Abu Dhabi, UAE",
                        fontSize = 11.sp,
                        color = DeepGolden,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Store Overview Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = store.nameEn,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = DarkText
                                )
                                Text(
                                    text = store.nameAr,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DeepGolden
                                )
                            }

                            Surface(
                                color = GoldContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = DeepGolden,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${store.rating} (${store.reviewCount})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DarkText
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = store.description,
                            fontSize = 12.sp,
                            color = DarkTextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Address & Phone
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = DeepGolden,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${store.address}, ${store.zoneName}, Abu Dhabi",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkText
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = DeepGolden,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = store.phone,
                                fontSize = 12.sp,
                                color = DarkTextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Approval Status Banner
                        if (store.isApproved) {
                            Surface(
                                color = BadgeApprovedBg,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Approved",
                                        tint = BadgeApproved,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = store.approvalAuthority,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = BadgeApproved
                                        )
                                        Text(
                                            text = "License #${store.approvalNumber} • Verified Active",
                                            fontSize = 11.sp,
                                            color = DarkTextSecondary
                                        )
                                    }
                                }
                            }
                        } else {
                            Surface(
                                color = BadgePendingBg,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("unapproved_warning_banner")
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Pending Inspection",
                                        tint = BadgePending,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "Ordering Prohibited (Inspection Pending)",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = BadgePending
                                        )
                                        Text(
                                            text = "This merchant is awaiting final food safety audit from Abu Dhabi authorities. Per regulations, ordering is currently disabled.",
                                            fontSize = 11.sp,
                                            color = DarkText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Products Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Store Catalog & Roastery Offerings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = "${storeProducts.size} Items",
                        fontSize = 12.sp,
                        color = DarkTextSecondary
                    )
                }
            }

            // Products list
            if (storeProducts.isEmpty()) {
                item {
                    Surface(
                        color = PureWhite,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "No catalog items currently listed for this store.",
                            fontSize = 13.sp,
                            color = DarkTextSecondary,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            } else {
                items(storeProducts) { product ->
                    ProductListItem(
                        product = product,
                        isStoreApproved = store.isApproved,
                        onAddToCart = { onAddToCart(product, store) }
                    )
                }
            }
        }

        // Bottom View Cart Bar
        if (cartItemCount > 0) {
            Surface(
                color = PureWhite,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$cartItemCount items in cart",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                        Text(
                            text = "Delivering to Abu Dhabi address",
                            fontSize = 11.sp,
                            color = DeepGolden
                        )
                    }

                    Button(
                        onClick = onViewCart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryYellow,
                            contentColor = DarkText
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("view_cart_floating_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "View Cart",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
