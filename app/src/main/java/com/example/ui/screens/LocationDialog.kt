package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.AbuDhabiServiceRegistry
import com.example.data.model.AbuDhabiZone
import com.example.data.model.DeliveryAddress
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

@Composable
fun AbuDhabiLocationDialog(
    currentZone: AbuDhabiZone,
    savedAddresses: List<DeliveryAddress>,
    rejectionNotice: String?,
    onSelectZone: (AbuDhabiZone) -> Unit,
    onSelectAddress: (DeliveryAddress) -> Unit,
    onAttemptUnsupportedEmirate: (String) -> Unit,
    onAddNewAddress: (
        label: String,
        emirate: String,
        zoneName: String,
        building: String,
        street: String,
        apartment: String,
        instructions: String
    ) -> Boolean,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Abu Dhabi Zones, 1 = Add Address, 2 = Check Other Emirates

    // Form states for new address
    var formLabel by remember { mutableStateOf("Home") }
    var formEmirate by remember { mutableStateOf("Abu Dhabi") }
    var formZoneName by remember { mutableStateOf(currentZone.nameEn) }
    var formBuilding by remember { mutableStateOf("") }
    var formStreet by remember { mutableStateOf("") }
    var formApartment by remember { mutableStateOf("") }
    var formInstructions by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SoftCreamBackground,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Abu Dhabi Delivery Location",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                    Text(
                        text = "VAYORA Service Area: Abu Dhabi, UAE Only",
                        fontSize = 12.sp,
                        color = DeepGolden,
                        fontWeight = FontWeight.Medium
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = DarkTextSecondary
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Rejection Notice Banner
                if (rejectionNotice != null) {
                    Surface(
                        color = ErrorRedBg,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .testTag("location_rejection_notice")
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Block,
                                contentDescription = "Rejected",
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = rejectionNotice,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ErrorRed
                            )
                        }
                    }
                }

                // Tabs: Abu Dhabi Zones vs Add Address vs Test Rejection
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = PureWhite,
                    contentColor = DarkText,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = PrimaryYellow
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                "Capital Zones",
                                fontSize = 11.sp,
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "Enter Address",
                                fontSize = 11.sp,
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = {
                            Text(
                                "Other Emirates",
                                fontSize = 11.sp,
                                fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                when (selectedTab) {
                    0 -> {
                        // Abu Dhabi Delivery Zones List
                        Text(
                            text = "Select your active Abu Dhabi delivery zone:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkTextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        ) {
                            items(AbuDhabiServiceRegistry.SERVICE_ZONES) { zone ->
                                val isCurrent = zone.id == currentZone.id
                                Surface(
                                    color = if (isCurrent) LightYellowContainer else PureWhite,
                                    shape = RoundedCornerShape(10.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isCurrent) 1.5.dp else 1.dp,
                                        color = if (isCurrent) DeepGolden else GoldBorder
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { onSelectZone(zone) }
                                        .testTag("zone_item_${zone.id}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    if (isCurrent) PrimaryYellow else SoftCreamBackground,
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isCurrent) Icons.Default.Check else Icons.Default.LocationOn,
                                                contentDescription = null,
                                                tint = if (isCurrent) DarkText else DeepGolden,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = zone.nameEn,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = DarkText
                                            )
                                            Text(
                                                text = "${zone.nameAr} • ${zone.district}",
                                                fontSize = 11.sp,
                                                color = DarkTextSecondary
                                            )
                                        }

                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(
                                                text = "${zone.deliveryFeeAed.toInt()} AED",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = DeepGolden
                                            )
                                            Text(
                                                text = "${zone.estimatedMinutes}m",
                                                fontSize = 11.sp,
                                                color = DarkTextMuted
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // Custom Address Entry Form with Abu Dhabi validation
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        ) {
                            item {
                                Text(
                                    text = "Enter Genuine Abu Dhabi Delivery Address",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                                Text(
                                    text = "Only Abu Dhabi addresses are supported and validated.",
                                    fontSize = 11.sp,
                                    color = DarkTextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            item {
                                OutlinedTextField(
                                    value = formEmirate,
                                    onValueChange = { formEmirate = it },
                                    label = { Text("Emirate (Must be Abu Dhabi)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("address_emirate_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = PureWhite,
                                        unfocusedContainerColor = PureWhite,
                                        focusedBorderColor = DeepGolden,
                                        unfocusedBorderColor = GoldBorder
                                    )
                                )
                            }

                            item {
                                OutlinedTextField(
                                    value = formZoneName,
                                    onValueChange = { formZoneName = it },
                                    label = { Text("Abu Dhabi District / Zone") },
                                    placeholder = { Text("e.g. Al Reem Island, Al Danah") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("address_zone_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = PureWhite,
                                        unfocusedContainerColor = PureWhite,
                                        focusedBorderColor = DeepGolden,
                                        unfocusedBorderColor = GoldBorder
                                    )
                                )
                            }

                            item {
                                OutlinedTextField(
                                    value = formBuilding,
                                    onValueChange = { formBuilding = it },
                                    label = { Text("Building / Tower / Villa Name *") },
                                    placeholder = { Text("e.g. Sky Tower, ADGM Tower") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("address_building_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = PureWhite,
                                        unfocusedContainerColor = PureWhite,
                                        focusedBorderColor = DeepGolden,
                                        unfocusedBorderColor = GoldBorder
                                    )
                                )
                            }

                            item {
                                OutlinedTextField(
                                    value = formStreet,
                                    onValueChange = { formStreet = it },
                                    label = { Text("Street Name / Number") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = PureWhite,
                                        unfocusedContainerColor = PureWhite,
                                        focusedBorderColor = DeepGolden,
                                        unfocusedBorderColor = GoldBorder
                                    )
                                )
                            }

                            item {
                                OutlinedTextField(
                                    value = formApartment,
                                    onValueChange = { formApartment = it },
                                    label = { Text("Apartment / Suite / Villa Number") },
                                    placeholder = { Text("e.g. Apt 1402") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = PureWhite,
                                        unfocusedContainerColor = PureWhite,
                                        focusedBorderColor = DeepGolden,
                                        unfocusedBorderColor = GoldBorder
                                    )
                                )
                            }

                            item {
                                Button(
                                    onClick = {
                                        val success = onAddNewAddress(
                                            formLabel,
                                            formEmirate,
                                            formZoneName,
                                            formBuilding,
                                            formStreet,
                                            formApartment,
                                            formInstructions
                                        )
                                        if (success) {
                                            onDismiss()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryYellow,
                                        contentColor = DarkText
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                        .testTag("save_address_button")
                                ) {
                                    Text(
                                        text = "Validate & Save Abu Dhabi Address",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        // Testing / Verification: Rejection of other Emirates (Dubai, Sharjah, etc.)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        ) {
                            Surface(
                                color = LightYellowContainer,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "Out-of-Area Address Testing",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DarkText
                                    )
                                    Text(
                                        text = "Per system configuration, VAYORA strictly rejects any address outside Abu Dhabi (such as Dubai, Sharjah, etc.)",
                                        fontSize = 11.sp,
                                        color = DarkTextSecondary
                                    )
                                }
                            }

                            Text(
                                text = "Test rejection on other UAE emirates:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            AbuDhabiServiceRegistry.UNSERVICEABLE_EMIRATES.forEach { emirate ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { onAttemptUnsupportedEmirate(emirate) }
                                        .testTag("test_reject_$emirate")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Block,
                                                contentDescription = null,
                                                tint = ErrorRed,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = emirate,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp,
                                                color = DarkText
                                            )
                                        }

                                        Text(
                                            text = "Not Supported (Reject)",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ErrorRed
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryYellow,
                    contentColor = DarkText
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("dialog_done_button")
            ) {
                Text("Done", fontWeight = FontWeight.Bold)
            }
        }
    )
}
