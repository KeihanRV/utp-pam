package com.example.utp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoId: String?,
    todoList: List<TodoItem>,
    onToggleDone: (TodoItem) -> Unit,
    onBack: () -> Unit
) {
    val todo = todoList.find { it.id == todoId }
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (todo != null) {
                Text(text = todo.title, style = MaterialTheme.typography.headlineMedium)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                
                DetailItem(label = "Mata Kuliah", value = todo.courseName)
                DetailItem(label = "Deadline", value = todo.deadline.format(formatter))
                DetailItem(label = "Status", value = if (todo.isDone) "Selesai" else "Belum Selesai")
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = { onToggleDone(todo) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (todo.isDone) "Tandai Belum Selesai" else "Tandai Selesai")
                }
            } else {
                Text("Tugas tidak ditemukan")
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
