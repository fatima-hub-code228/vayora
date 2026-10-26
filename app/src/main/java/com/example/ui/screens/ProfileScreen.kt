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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.data.model.DeliveryAddress
import com.example.data.model.UserProfile
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
fun ProfileScreen(
    userProfile: UserProfile,
    savedAddresses: List<DeliveryAddress>,
    selectedAddressId: String,
    onSelectAddress: (DeliveryAddress) -> Unit,
    onManageLocationsClick: () -> Unit
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
                    text = "Customer Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DarkText
                )
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // User Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(PrimaryYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = DarkText,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = userProfile.name,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = DarkText
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = DeepGolden,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = userProfile.phone,
                                    fontSize = 12.sp,
                                    color = DarkTextSecondary
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = DeepGolden,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = userProfile.email,
                                    fontSize = 12.sp,
                                    color = DarkTextMuted
                                )
                            }
                        }
                    }
                }
            }

            // Abu Dhabi Service Area Enforcement Information Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = LightYellowContainer),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = DeepGolden,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Abu Dhabi Exclusive Service Area",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = DarkText
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• Operates exclusively in Abu Dhabi, UAE. Orders and addresses in Dubai, Sharjah, and other emirates are rejected at checkout.\n" +
                                    "• Stores & roasteries must hold verified ADAFSA (Abu Dhabi Agriculture & Food Safety Authority) licensing.\n" +
                                    "• Active delivery sectors: Al Danah, Al Reem Island, Al Maryah Island, Al Khalidiya, Al Nahyan, Khalifa City, Yas Island, MBZ City, and Saadiyat Island.",
                            fontSize = 12.sp,
                            color = DarkTextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Saved Abu Dhabi Addresses Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saved Abu Dhabi Addresses",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = DarkText
                    )

                    Button(
                        onClick = onManageLocationsClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryYellow,
                            contentColor = DarkText
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("add_new_address_profile_button")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add / Change", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Saved Addresses List
            items(savedAddresses) { address ->
                val isSelected = address.id == selectedAddressId
                Surface(
                    color = if (isSelected) GoldContainer else PureWhite,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.dp,
                        color = if (isSelected) DeepGolden else GoldBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectAddress(address) }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (isSelected) PrimaryYellow else SoftCreamBackground,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.Check else Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = if (isSelected) DarkText else DeepGolden,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = address.label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = DarkText
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = LightYellowContainer,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = address.emirate,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepGolden,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Text(
                                text = "${address.buildingName}, ${address.apartmentOrVilla}",
                                fontSize = 12.sp,
                                color = DarkText
                            )

                            Text(
                                text = "${address.street}, ${address.zoneName}",
                                fontSize = 11.sp,
                                color = DarkTextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
