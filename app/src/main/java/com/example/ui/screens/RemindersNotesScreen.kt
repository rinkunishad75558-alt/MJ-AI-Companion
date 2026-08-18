package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun RemindersNotesScreen(
    viewModel: MjViewModel,
    modifier: Modifier = Modifier
) {
    val reminders by viewModel.reminders.collectAsState()
    val notes by viewModel.notes.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddReminderDialog by remember { mutableStateOf(false) }
    var showAddNoteDialog by remember { mutableStateOf(false) }

    var reminderTitle by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var noteMood by remember { mutableStateOf("Happy 😊") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A16))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Utilities & Journal",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF1F5F9)
            )
            FloatingActionButton(
                onClick = { if (selectedTab == 0) showAddReminderDialog = true else showAddNoteDialog = true },
                modifier = Modifier.size(48.dp),
                containerColor = Color(0xFF22D3EE)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF13132B),
            border = BorderStroke(1.dp, Color(0xFF1E293B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF13132B),
                contentColor = Color(0xFF22D3EE)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Reminders (${reminders.size})", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Journal (${notes.size})", fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == 0) {
            if (reminders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No reminders set.", color = Color(0xFF94A3B8))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
                    items(reminders) { reminder ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
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
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Checkbox(
                                        checked = reminder.isCompleted,
                                        onCheckedChange = { viewModel.toggleReminder(reminder) },
                                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF22D3EE))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = reminder.title,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFF1F5F9)
                                        )
                                        Text(
                                            text = reminder.timeStr,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF22D3EE)
                                        )
                                    }
                                }
                                IconButton(onClick = { viewModel.deleteReminder(reminder.id) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFF43F5E))
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No journal entries yet.", color = Color(0xFF94A3B8))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
                    items(notes) { note ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF13132B)),
                            border = BorderStroke(1.dp, Color(0xFF1E293B))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = note.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF22D3EE)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFF9333EA).copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = note.mood,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFC084FC)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = note.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddReminderDialog) {
        AlertDialog(
            onDismissRequest = { showAddReminderDialog = false },
            title = { Text("Add Reminder") },
            text = {
                Column {
                    OutlinedTextField(
                        value = reminderTitle,
                        onValueChange = { reminderTitle = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reminderTime,
                        onValueChange = { reminderTime = it },
                        label = { Text("Time (e.g. 5:00 PM)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (reminderTitle.isNotBlank()) {
                        viewModel.addReminder(reminderTitle, reminderTime.ifBlank { "Today" })
                        reminderTitle = ""
                        reminderTime = ""
                        showAddReminderDialog = false
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddReminderDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showAddNoteDialog) {
        AlertDialog(
            onDismissRequest = { showAddNoteDialog = false },
            title = { Text("New Journal Entry") },
            text = {
                Column {
                    OutlinedTextField(
                        value = noteTitle,
                        onValueChange = { noteTitle = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteContent,
                        onValueChange = { noteContent = it },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteMood,
                        onValueChange = { noteMood = it },
                        label = { Text("Mood (e.g. Joyful ✨)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (noteTitle.isNotBlank()) {
                        viewModel.addNote(noteTitle, noteContent, noteMood)
                        noteTitle = ""
                        noteContent = ""
                        showAddNoteDialog = false
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
