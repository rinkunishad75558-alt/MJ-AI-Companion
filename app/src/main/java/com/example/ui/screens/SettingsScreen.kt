package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MjViewModel

@Composable
fun SettingsScreen(
    viewModel: MjViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()

    val avatarStyles = listOf("Classic", "Neon", "Cyberpunk")
    val outfits = listOf("Neon Suit", "Casual", "Elegant")
    val themes = listOf("Sleek Interface", "Starry Night", "Sunset Glow")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A16))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Companion Customization",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Customize avatar, outfit & theme modes.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
            )
        }

        // Avatar Style
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                border = BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = Color(0xFF22D3EE))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Avatar Style", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        avatarStyles.forEach { style ->
                            val selected = settings.avatarStyle == style
                            FilterChip(
                                selected = selected,
                                onClick = { viewModel.updateSettings(style, settings.outfit, settings.theme, settings.voiceEnabled) },
                                label = { Text(style) }
                            )
                        }
                    }
                }
            }
        }

        // Outfit
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                border = BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Checkroom, contentDescription = null, tint = Color(0xFF22D3EE))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Companion Outfit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        outfits.forEach { outfit ->
                            val selected = settings.outfit == outfit
                            FilterChip(
                                selected = selected,
                                onClick = { viewModel.updateSettings(settings.avatarStyle, outfit, settings.theme, settings.voiceEnabled) },
                                label = { Text(outfit) }
                            )
                        }
                    }
                }
            }
        }

        // Voice Feedback
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                border = BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = Color(0xFF22D3EE))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Voice Feedback", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFF1F5F9))
                            Text(text = "Low latency voice simulation", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                        }
                    }
                    Switch(
                        checked = settings.voiceEnabled,
                        onCheckedChange = { checked ->
                            viewModel.updateSettings(settings.avatarStyle, settings.outfit, settings.theme, checked)
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF22D3EE))
                    )
                }
            }
        }
    }
}
