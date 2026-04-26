package com.example.utp

import java.time.LocalDateTime
import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val courseName: String,
    val deadline: LocalDateTime,
    val isDone: Boolean = false
)
