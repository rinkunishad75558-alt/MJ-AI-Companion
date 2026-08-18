package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessageEntity
import com.example.viewmodel.MjViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: MjViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val isVoiceActive by viewModel.isVoiceActive.collectAsState()

    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A16))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF22D3EE), Color(0xFF9333EA))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "MJ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "MJ ❤️",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF1F5F9)
                    )
                    Text(
                        text = "ACTIVE ASSISTANT",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF22D3EE),
                        letterSpacing = 1.5.sp
                    )
                }
            }

            IconButton(
                onClick = { viewModel.toggleVoiceActive() },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E1E3F))
                    .border(1.dp, Color(0xFF334155), CircleShape)
            ) {
                Icon(
                    imageVector = if (isVoiceActive) Icons.Default.Mic else Icons.Default.Settings,
                    contentDescription = "Settings / Voice",
                    tint = Color(0xFF22D3EE),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Main Content Area (Hero Pulse & Messages)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (messages.isEmpty()) {
                // Sleek Interface Hero View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(176.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF22D3EE).copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.sweepGradient(
                                        listOf(Color(0xFF22D3EE), Color(0xFF6366F1), Color(0xFF9333EA))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                            val scale by infiniteTransition.animateFloat(
                                initialValue = 0.85f,
                                targetValue = 1.05f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1200, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "scale"
                            )
                            Box(
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "\"Kaise ho, Dost?\"",
                        style = MaterialTheme.typography.headlineSmall,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFFF1F5F9)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ready to manage your APKs or sync your local files today?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF94A3B8),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages) { msg ->
                        ChatBubble(message = msg)
                    }
                    if (isGenerating) {
                        item {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color(0xFF22D3EE))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "MJ is thinking...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Suggestion Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            val suggestions = listOf("📦 APK Manager", "🔄 OS Sync", "🔋 Battery Status", "🌤️ Weather", "⏰ Reminder")
            items(suggestions) { suggestion ->
                Surface(
                    onClick = { viewModel.sendMessage(suggestion.substring(3)) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF13132B),
                    border = BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Text(
                        text = suggestion,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFF1F5F9)
                    )
                }
            }
        }

        // Sleek Footer Input Bar matching Sleek Interface design HTML
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF13132B).copy(alpha = 0.9f))
                .border(1.dp, Color(0xFF334155).copy(alpha = 0.5f), RoundedCornerShape(32.dp))
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.toggleVoiceActive() },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E1E3F))
                ) {
                    Text(text = "⌨️", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ask MJ or command OS...", color = Color(0xFF64748B), fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color(0xFFF1F5F9),
                        unfocusedTextColor = Color(0xFFF1F5F9)
                    ),
                    maxLines = 1
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.sendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF22D3EE), Color(0xFF9333EA))
                            )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessageEntity) {
    val isUser = message.sender == "user"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) Color(0xFF22D3EE).copy(alpha = 0.2f) else Color(0xFF13132B),
            border = BorderStroke(1.dp, if (isUser) Color(0xFF22D3EE).copy(alpha = 0.4f) else Color(0xFF1E293B)),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFF1F5F9)
            )
        }
    }
}
