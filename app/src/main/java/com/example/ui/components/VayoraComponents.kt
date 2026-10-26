package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AbuDhabiZone
import com.example.data.model.Product
import com.example.data.model.Store
import com.example.data.model.StoreCategory
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
fun VayoraTopHeader(
    selectedZone: AbuDhabiZone,
    cartItemCount: Int,
    onLocationClick: () -> Unit,
    onCartClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SoftCreamBackground,
            titleContentColor = DarkText
        ),
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "VAYORA",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        letterSpacing = 1.sp,
                        color = DeepGolden
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = PrimaryYellow,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "ABU DHABI",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                // Location Selector Chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onLocationClick() }
                        .padding(vertical = 2.dp)
                        .testTag("location_selector_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Current Abu Dhabi Location",
                        tint = DeepGolden,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = selectedZone.nameEn,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = " (Change)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = DeepGolden
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onCartClick,
                modifier = Modifier.testTag("cart_header_button")
            ) {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = DeepGolden,
                                contentColor = PureWhite
                            ) {
                                Text(
                                    text = cartItemCount.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = DarkText,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ServiceAreaAlertBanner(onAreaDetailsClick: () -> Unit) {
    Surface(
        color = LightYellowContainer,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
            .clickable { onAreaDetailsClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(PrimaryYellow, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = DarkText,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Restricted Service Area: Abu Dhabi Only",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = DarkText
                )
                Text(
                    text = "Fast delivery across Capital sectors (Al Reem, Maryah, Danah, Yas & MBZ)",
                    fontSize = 11.sp,
                    color = DarkTextSecondary
                )
            }
            Text(
                text = "Zones",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = DeepGolden
            )
        }
    }
}

@Composable
fun VayoraSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                "Search Abu Dhabi stores, roasteries, nuts...",
                fontSize = 13.sp,
                color = DarkTextMuted
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = DeepGolden
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = DarkTextSecondary
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = PureWhite,
            unfocusedContainerColor = PureWhite,
            focusedBorderColor = DeepGolden,
            unfocusedBorderColor = GoldBorder,
            focusedTextColor = DarkText,
            unfocusedTextColor = DarkText
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("home_search_input")
    )
}

@Composable
fun StoreCategoryRow(
    selectedCategory: StoreCategory,
    onSelectCategory: (StoreCategory) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(StoreCategory.values()) { category ->
            val isSelected = category == selectedCategory
            val icon = when (category) {
                StoreCategory.ALL -> Icons.Default.Storefront
                StoreCategory.ROASTERIES_NUTS -> Icons.Default.LocalCafe
                StoreCategory.RESTAURANTS -> Icons.Default.Restaurant
                StoreCategory.GROCERY -> Icons.Default.LocalGroceryStore
                StoreCategory.SWEETS_CAFES -> Icons.Default.BakeryDining
            }

            Surface(
                color = if (isSelected) PrimaryYellow else PureWhite,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) DeepGolden else GoldBorder
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onSelectCategory(category) }
                    .testTag("category_chip_${category.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) DarkText else DeepGolden,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = category.displayName,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = DarkText
                    )
                }
            }
        }
    }
}

@Composable
fun StoreCard(
    store: Store,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("store_card_${store.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Category tag & Approval Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = LightYellowContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = store.bannerTag,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepGolden,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Approval Status Badge
                if (store.isApproved) {
                    Surface(
                        color = BadgeApprovedBg,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Approved",
                                tint = BadgeApproved,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ADAFSA Approved",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BadgeApproved
                            )
                        }
                    }
                } else {
                    Surface(
                        color = BadgePendingBg,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Pending Inspection",
                                tint = BadgePending,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Approval Pending",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BadgePending
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Store Name & Arabic Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = store.nameEn,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = store.nameAr,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = DeepGolden
                    )
                }

                // Rating Box
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
                            text = "${store.rating}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = DarkText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Address in Abu Dhabi
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = DarkTextMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${store.address}, ${store.zoneName}",
                    fontSize = 12.sp,
                    color = DarkTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Meta Info: Delivery time, fee, phone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${store.deliveryMinutes} mins",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = " • ",
                        fontSize = 12.sp,
                        color = DarkTextMuted
                    )
                    Text(
                        text = "${store.deliveryFeeAed.toInt()} AED Delivery",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = DeepGolden
                    )
                }

                if (!store.isApproved) {
                    Text(
                        text = "Orders Disabled",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadgePending
                    )
                } else {
                    Text(
                        text = "Min: ${store.minOrderAed.toInt()} AED",
                        fontSize = 11.sp,
                        color = DarkTextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun ProductListItem(
    product: Product,
    isStoreApproved: Boolean,
    onAddToCart: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .testTag("product_item_${product.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.nameEn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = DarkText
                )
                Text(
                    text = product.nameAr,
                    fontSize = 12.sp,
                    color = DeepGolden,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    color = DarkTextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.weightOrPortion,
                    fontSize = 11.sp,
                    color = DarkTextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${product.priceAed.toInt()} AED",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepGolden
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (isStoreApproved) {
                Surface(
                    color = PrimaryYellow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onAddToCart() }
                        .testTag("add_to_cart_button_${product.id}")
                ) {
                    Text(
                        text = "Add +",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            } else {
                Surface(
                    color = Color(0xFFF5F5F4),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Disabled",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VayoraBottomNavigation(
    currentScreen: String,
    cartCount: Int,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = PureWhite,
        contentColor = DarkText
    ) {
        val items = listOf(
            Triple("home", "Home", Icons.Default.Home),
            Triple("cart", "Cart", Icons.Default.ShoppingCart),
            Triple("orders", "Orders", Icons.Default.History),
            Triple("profile", "Profile", Icons.Default.Person)
        )

        items.forEach { (route, label, icon) ->
            val isSelected = currentScreen == route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(route) },
                icon = {
                    if (route == "cart" && cartCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = DeepGolden,
                                    contentColor = PureWhite
                                ) {
                                    Text(text = cartCount.toString())
                                }
                            }
                        ) {
                            Icon(imageVector = icon, contentDescription = label)
                        }
                    } else {
                        Icon(imageVector = icon, contentDescription = label)
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkText,
                    selectedTextColor = DarkText,
                    indicatorColor = PrimaryYellow,
                    unselectedIconColor = DarkTextSecondary,
                    unselectedTextColor = DarkTextSecondary
                ),
                modifier = Modifier.testTag("nav_item_$route")
            )
        }
    }
}
