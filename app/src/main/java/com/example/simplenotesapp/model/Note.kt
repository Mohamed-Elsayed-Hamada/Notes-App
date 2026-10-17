package com.example.simplenotesapp.model

import java.util.UUID

data class ChecklistItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isChecked: Boolean = false
)

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String = "",
    val category: String = "Personal",
    val isPinned: Boolean = false,
    val checklist: List<ChecklistItem> = emptyList(),
    val attachmentsCount: Int = 0,
    val cardIcon: String? = null, // "book", "palette", "chat"
    val dateFormatted: String = "Today",
    val updatedAt: Long = System.currentTimeMillis()
)




