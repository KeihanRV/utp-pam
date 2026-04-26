package com.example.utp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class SortType {
    NAME, DEADLINE
}

enum class SortDirection {
    ASCENDING, DESCENDING
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoList: List<TodoItem>,
    onAddTodo: (String, String, LocalDateTime) -> Unit,
    onRemoveTodo: (TodoItem) -> Unit,
    onToggleDone: (TodoItem) -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onInsertDummyData: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var sortType by remember { mutableStateOf(SortType.NAME) }
    var sortDirection by remember { mutableStateOf(SortDirection.ASCENDING) }
    
    var isInputExpanded by remember { mutableStateOf(false) }

    val sortedList = remember(todoList.size, todoList.map { it.isDone }, sortType, sortDirection) {
        val baseSorted = when (sortType) {
            SortType.NAME -> todoList.sortedBy { it.title.lowercase() }
            SortType.DEADLINE -> todoList.sortedBy { it.deadline }
        }
        if (sortDirection == SortDirection.DESCENDING) baseSorted.reversed() else baseSorted
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ingyh, I am Fine") })
        }
    ) { paddingValues ->
        // We use LazyColumn for the entire screen to handle scrolling properly
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // GIF Image
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.fine)
                        .decoderFactory(GifDecoder.Factory())
                        .build()
                )
                
                Image(
                    painter = painter,
                    contentDescription = "Fine GIF",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(bottom = 8.dp)
                )

                Button(
                    onClick = onInsertDummyData,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Data Dummy Insert")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Expandable Input Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isInputExpanded = !isInputExpanded }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle, 
                                    contentDescription = null, 
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Tambah Tugas Baru",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Icon(
                                imageVector = if (isInputExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null
                            )
                        }

                        AnimatedVisibility(visible = isInputExpanded) {
                            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                                OutlinedTextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    label = { Text("Judul Tugas") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = courseName,
                                    onValueChange = { courseName = it },
                                    label = { Text("Nama Mata Kuliah") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = selectedDate.format(dateFormatter),
                                        onValueChange = { },
                                        label = { Text("Tanggal") },
                                        readOnly = true,
                                        modifier = Modifier.weight(1f),
                                        trailingIcon = {
                                            IconButton(onClick = { showDatePicker = true }) {
                                                Icon(Icons.Default.CalendarMonth, contentDescription = "Pilih Tanggal")
                                            }
                                        }
                                    )
                                    OutlinedTextField(
                                        value = selectedTime.format(timeFormatter),
                                        onValueChange = { },
                                        label = { Text("Waktu") },
                                        readOnly = true,
                                        modifier = Modifier.weight(1f),
                                        trailingIcon = {
                                            IconButton(onClick = { showTimePicker = true }) {
                                                Icon(Icons.Default.Schedule, contentDescription = "Pilih Waktu")
                                            }
                                        }
                                    )
                                }
                                
                                Button(
                                    onClick = {
                                        if (title.isNotBlank() && courseName.isNotBlank()) {
                                            val deadline = LocalDateTime.of(selectedDate, selectedTime)
                                            onAddTodo(title, courseName, deadline)
                                            title = ""
                                            courseName = ""
                                            isInputExpanded = false
                                        }
                                    },
                                    modifier = Modifier.align(Alignment.End).padding(top = 12.dp)
                                ) {
                                    Text("Simpan")
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Sorting Controls
                Column {
                    Text("Urutkan Berdasarkan:", style = MaterialTheme.typography.labelLarge)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilterChip(
                                selected = sortType == SortType.NAME,
                                onClick = { sortType = SortType.NAME },
                                label = { Text("Nama") }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FilterChip(
                                selected = sortType == SortType.DEADLINE,
                                onClick = { sortType = SortType.DEADLINE },
                                label = { Text("Deadline") }
                            )
                        }
                        
                        IconButton(onClick = {
                            sortDirection = if (sortDirection == SortDirection.ASCENDING) 
                                SortDirection.DESCENDING else SortDirection.ASCENDING
                        }) {
                            Icon(
                                imageVector = if (sortDirection == SortDirection.ASCENDING) 
                                    Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                contentDescription = "Arah Urutan",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(sortedList, key = { it.id }) { todo ->
                TodoItemRow(
                    todo = todo,
                    onToggle = { onToggleDone(todo) },
                    onClick = { onNavigateToDetail(todo.id) },
                    onDelete = { onRemoveTodo(todo) }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Picker Dialogs
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTime.hour,
            initialMinute = selectedTime.minute
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Batal") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}

@Composable
fun TodoItemRow(
    todo: TodoItem,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { onToggle() }
            )
            
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (todo.isDone) TextDecoration.LineThrough else null,
                    color = if (todo.isDone) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "MK: ${todo.courseName}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Deadline: ${todo.deadline.format(formatter)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (todo.deadline.isBefore(LocalDateTime.now()) && !todo.isDone) 
                        MaterialTheme.colorScheme.error 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
