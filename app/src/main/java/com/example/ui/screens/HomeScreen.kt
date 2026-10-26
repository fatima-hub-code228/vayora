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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Store
import com.example.data.model.StoreCategory
import com.example.ui.components.ServiceAreaAlertBanner
import com.example.ui.components.StoreCard
import com.example.ui.components.StoreCategoryRow
import com.example.ui.components.VayoraSearchBar
import com.example.ui.theme.DarkText
import com.example.ui.theme.DarkTextMuted
import com.example.ui.theme.DarkTextSecondary
import com.example.ui.theme.DeepGolden
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.LightYellowContainer
import com.example.ui.theme.PrimaryYellow
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftCreamBackground

@Composable
fun HomeScreen(
    stores: List<Store>,
    selectedCategory: StoreCategory,
    searchQuery: String,
    onCategorySelect: (StoreCategory) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onStoreClick: (Store) -> Unit,
    onLocationBannerClick: () -> Unit
) {
    val filteredStores = stores.filter { store ->
        val matchesCategory = selectedCategory == StoreCategory.ALL || store.category == selectedCategory
        val matchesQuery = searchQuery.isBlank() ||
                store.nameEn.contains(searchQuery, ignoreCase = true) ||
                store.nameAr.contains(searchQuery, ignoreCase = true) ||
                store.address.contains(searchQuery, ignoreCase = true) ||
                store.zoneName.contains(searchQuery, ignoreCase = true) ||
                store.description.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesQuery
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCreamBackground)
            .testTag("home_screen_lazy_column")
    ) {
        // Service Area Restriction Banner
        item {
            ServiceAreaAlertBanner(onAreaDetailsClick = onLocationBannerClick)
        }

        // Hero Card: VAYORA Abu Dhabi Luxury Food & Roastery Experience
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = PrimaryYellow),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_brand_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = DeepGolden,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "EXCLUSIVE CAPITAL SERVICE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PureWhite,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = DeepGolden,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ADAFSA Verified",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Abu Dhabi's Finest Roasteries, Dining & Groceries",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkText,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Authentic Lebanese roasteries, artisan specialty cafes, gourmet dates, and fresh markets delivered directly to your doorstep in Abu Dhabi.",
                        fontSize = 12.sp,
                        color = DarkTextSecondary
                    )
                }
            }
        }

        // Search Bar
        item {
            VayoraSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange
            )
        }

        // Category Filter Chips
        item {
            StoreCategoryRow(
                selectedCategory = selectedCategory,
                onSelectCategory = onCategorySelect
            )
        }

        // Section Title: Abu Dhabi Verified Establishments
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Verified Abu Dhabi Stores",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = "Showing ${filteredStores.size} active merchants in Abu Dhabi",
                        fontSize = 12.sp,
                        color = DarkTextSecondary
                    )
                }
            }
        }

        // Store Cards
        if (filteredStores.isEmpty()) {
            item {
                Surface(
                    color = PureWhite,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = DeepGolden,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No Abu Dhabi stores found for this filter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DarkText
                        )
                        Text(
                            text = "Try switching categories or searching for 'Roaster', 'Karak', or 'Waitrose'.",
                            fontSize = 12.sp,
                            color = DarkTextSecondary
                        )
                    }
                }
            }
        } else {
            items(filteredStores) { store ->
                StoreCard(
                    store = store,
                    onClick = { onStoreClick(store) }
                )
            }
        }
    }
}
