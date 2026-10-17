package com.example.simplenotesapp

import com.example.simplenotesapp.model.ChecklistItem
import com.example.simplenotesapp.model.Note
import com.example.simplenotesapp.repository.NoteRepositoryImp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NoteRepositoryImpTest {

    private lateinit var repository: NoteRepositoryImp

    @Before
    fun setUp() {
        repository = NoteRepositoryImp()
    }

    @Test
    fun getNotes_initialNotesContainMockData() = runBlocking {
        val notes = repository.getNotes().first()
        assertTrue(notes.isNotEmpty())
        assertTrue(notes.any { it.title == "Weekly Grocery & Meal Plan" })
    }

    @Test
    fun togglePin_switchesPinnedState() = runBlocking {
        val initialNotes = repository.getNotes().first()
        val target = initialNotes.first()
        val originalPinState = target.isPinned

        repository.togglePin(target.id)

        val updatedNotes = repository.getNotes().first()
        val updatedTarget = updatedNotes.find { it.id == target.id }
        assertNotNull(updatedTarget)
        assertEquals(!originalPinState, updatedTarget!!.isPinned)
    }

    @Test
    fun toggleChecklistItem_updatesItemCheckedStatus() = runBlocking {
        val initialNotes = repository.getNotes().first()
        val groceryNote = initialNotes.find { it.title == "Weekly Grocery & Meal Plan" }
        assertNotNull(groceryNote)
        val firstItem = groceryNote!!.checklist.first()

        repository.toggleChecklistItem(groceryNote.id, firstItem.id)

        val updatedNotes = repository.getNotes().first()
        val updatedNote = updatedNotes.find { it.id == groceryNote.id }
        val updatedItem = updatedNote!!.checklist.find { it.id == firstItem.id }
        assertEquals(!firstItem.isChecked, updatedItem!!.isChecked)
    }

    @Test
    fun deleteNote_removesNoteAndReturnsIt() = runBlocking {
        val initialNotes = repository.getNotes().first()
        val noteToDelete = initialNotes.first()

        val deleted = repository.deleteNote(noteToDelete.id)
        assertEquals(noteToDelete.id, deleted?.id)

        val updatedNotes = repository.getNotes().first()
        assertNull(updatedNotes.find { it.id == noteToDelete.id })
    }

    @Test
    fun duplicateNote_addsNewNoteWithCopyTitle() = runBlocking {
        val initialNotes = repository.getNotes().first()
        val noteToDuplicate = initialNotes.first()

        repository.duplicateNote(noteToDuplicate.id)

        val updatedNotes = repository.getNotes().first()
        assertEquals(initialNotes.size + 1, updatedNotes.size)
        assertTrue(updatedNotes.any { it.title.contains("${noteToDuplicate.title} (Copy)") })
    }
}

