package com.example.utp

import java.time.LocalDateTime

object DummyData {
    val sampleTodos = listOf(
        TodoItem(
            title = "Tugas Praktikum 1",
            courseName = "Pemrograman Perangkat Bergerak",
            deadline = LocalDateTime.now().plusDays(2),
            isDone = false
        ),
        TodoItem(
            title = "Laporan Akhir",
            courseName = "Basis Data",
            deadline = LocalDateTime.now().plusDays(5),
            isDone = true
        ),
        TodoItem(
            title = "Persiapan Quiz",
            courseName = "Kecerdasan Buatan",
            deadline = LocalDateTime.now().plusDays(1),
            isDone = false
        ),
        TodoItem(
            title = "Proyek Akhir Semester",
            courseName = "Rekayasa Perangkat Lunak",
            deadline = LocalDateTime.now().plusWeeks(2),
            isDone = false
        ),
        TodoItem(
            title = "Review Jurnal",
            courseName = "Interaksi Manusia dan Komputer",
            deadline = LocalDateTime.now().plusDays(3),
            isDone = false
        ),
        TodoItem(
            title = "Tugas Kelompok 2",
            courseName = "Jaringan Komputer",
            deadline = LocalDateTime.now().plusDays(7),
            isDone = false
        ),
        TodoItem(
            title = "Analisis Algoritma",
            courseName = "Desain dan Analisis Algoritma",
            deadline = LocalDateTime.now().plusDays(4),
            isDone = true
        ),
        TodoItem(
            title = "Presentasi Business Plan",
            courseName = "Kewirausahaan",
            deadline = LocalDateTime.now().plusDays(6),
            isDone = false
        ),
        TodoItem(
            title = "Ujian Tengah Semester",
            courseName = "Sistem Operasi",
            deadline = LocalDateTime.now().plusDays(10),
            isDone = false
        ),
        TodoItem(
            title = "Tugas Mandiri 3",
            courseName = "Keamanan Informasi",
            deadline = LocalDateTime.now().minusDays(1),
            isDone = true
        )
    )
}
