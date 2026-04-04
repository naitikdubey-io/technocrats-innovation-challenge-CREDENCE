package com.credence.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.credence.app.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.credence.app.data.LogResponse
import com.credence.app.ui.theme.*
import com.credence.app.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(navController: NavHostController, viewModel: MainViewModel) {
    val timelineLogs by viewModel.timelineLogs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // State to control the Bottom Sheet
    var selectedLog by remember { mutableStateOf<LogResponse?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.fetchTimeline()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // HEADER
            Text(
                text = "Your Ledger",
                style = MaterialTheme.typography.headlineLarge,
                color = DeepEspresso
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Encrypted • Device-Only Storage",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftMushroom,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Secure",
                    tint = DeepEspresso,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // TIMELINE LIST
            if (isLoading && timelineLogs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BlushPink)
                }
            } else if (timelineLogs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Your ledger is currently empty.", color = SoftMushroom)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(timelineLogs) { log ->
                        // Pass the click event up to open the sheet
                        LedgerCard(log = log, onClick = { selectedLog = log })
                    }
                }
            }
        }

        // FLOATING ACTION BUTTON
        Button(
            onClick = { navController.navigate(Screen.ShareQR.route) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(50), spotColor = DeepEspresso, ambientColor = DeepEspresso),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepEspresso,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Share with Doctor",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter =  painterResource(R.drawable.qr_code_scan),
                contentDescription = "QR Share",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }


    if (selectedLog != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedLog = null },
            sheetState = sheetState,
            containerColor = WarmIvory, // Matches app background
            dragHandle = { BottomSheetDefaults.DragHandle(color = SoftMushroom.copy(alpha = 0.5f)) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp) // Extra padding for navigation bars
            ) {
                // Sheet Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Clinical Dossier",
                        style = MaterialTheme.typography.titleLarge,
                        color = DeepEspresso
                    )
                    IconButton(onClick = { selectedLog = null }) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = DeepEspresso)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Date Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SoftCream)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = selectedLog!!.date,
                        style = MaterialTheme.typography.labelMedium,
                        color = SoftMushroom
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // The Actual Clinical Report Text (Scrollable)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, SubtleGray, RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Text(
                        text = selectedLog!!.clinicalReport,
                        style = MaterialTheme.typography.bodyLarge,
                        color = DeepEspresso,
                        modifier = Modifier.verticalScroll(rememberScrollState()) // Ensures long reports don't get cut off
                    )
                }
            }
        }
    }
}


@Composable
fun LedgerCard(log: LogResponse, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp), spotColor = SubtleGray, ambientColor = SubtleGray)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() } // Triggers the bottom sheet
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(BlushPink)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.link),
                            contentDescription = "Document",
                            tint = PlaceholderGray,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Medical Intake Report",
                            style = MaterialTheme.typography.titleMedium,
                            color = DeepEspresso
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter= painterResource(R.drawable.schedule),
                            contentDescription = "Time",

                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = log.date,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                            color = SoftMushroom
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View Details",
                    tint = PlaceholderGray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}