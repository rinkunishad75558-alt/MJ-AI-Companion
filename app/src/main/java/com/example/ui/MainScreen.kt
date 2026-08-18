package com.example.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.RemindersNotesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsScreen
import com.example.viewmodel.MjViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Chat : Screen("chat", "MJ ❤️", Icons.Default.Chat)
    object Tools : Screen("tools", "OS Tools", Icons.Default.PhoneAndroid)
    object Reminders : Screen("reminders", "Utilities", Icons.Default.CheckCircle)
    object Settings : Screen("settings", "Custom", Icons.Default.Palette)
}

@Composable
fun MainScreen(viewModel: MjViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Chat) }
    val items = listOf(Screen.Chat, Screen.Tools, Screen.Reminders, Screen.Settings)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF13132B),
                contentColor = Color(0xFF94A3B8)
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF22D3EE),
                            selectedTextColor = Color(0xFF22D3EE),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8),
                            indicatorColor = Color(0xFF1E1E3F)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (currentScreen) {
            is Screen.Chat -> ChatScreen(viewModel = viewModel, modifier = modifier)
            is Screen.Tools -> ToolsScreen(viewModel = viewModel, modifier = modifier)
            is Screen.Reminders -> RemindersNotesScreen(viewModel = viewModel, modifier = modifier)
            is Screen.Settings -> SettingsScreen(viewModel = viewModel, modifier = modifier)
        }
    }
}
