package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MjViewModel

@Composable
fun ToolsScreen(
    viewModel: MjViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A16))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "OS Controller & Utilities",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage APKs, sync cross-device files, and optimize system telemetry.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
            )
        }

        // Feature Grid matching Sleek Design
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                    border = BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "📦", fontSize = 24.sp)
                        Column {
                            Text(text = "APK Manager", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                            Text(text = "Install & Package", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                    border = BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "🔄", fontSize = 24.sp)
                        Column {
                            Text(text = "OS Sync", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                            Text(text = "Cross-device Hub", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        }
                    }
                }
            }
        }

        // Device Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                border = BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.PhoneAndroid, contentDescription = null, tint = Color(0xFF22D3EE))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Device Telemetry", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    DeviceStatRow(label = "Battery Health", value = "94% (Good)", icon = Icons.Default.BatteryChargingFull)
                    DeviceStatRow(label = "RAM Usage", value = "6.2 GB / 8.0 GB", icon = Icons.Default.Memory)
                    DeviceStatRow(label = "Storage Free", value = "128 GB Available", icon = Icons.Default.Storage)
                    DeviceStatRow(label = "Network", value = "Wi-Fi 5G (350 Mbps)", icon = Icons.Default.Wifi)
                }
            }
        }

        // APK Action
        item {
            Button(
                onClick = { Toast.makeText(context, "APK packaged successfully!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(listOf(Color(0xFF22D3EE), Color(0xFF9333EA))),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Build & Package APK", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun DeviceStatRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF94A3B8))
        }
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = Color(0xFF22D3EE))
    }
}
