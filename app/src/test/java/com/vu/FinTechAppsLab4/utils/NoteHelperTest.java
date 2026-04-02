package com.vu.FinTechAppsLab4.utils;

import android.content.Context;

import com.vu.FinTechAppsLab4.model.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class NoteHelperTest {
    private NoteHelper noteHelper;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        noteHelper = new NoteHelper(context);
        clearAllNotes();
    }

    private void clearAllNotes() {
        android.content.SharedPreferences prefs = 
            context.getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE);
        prefs.edit().remove("notes_list").apply();
    }

    @Test
    public void testGetAllNotesReturnsEmptyListInitially() {
        List<Note> notes = noteHelper.getAllNotes();
        assertNotNull(notes);
        assertEquals(0, notes.size());
    }

    @Test
    public void testSaveNote() {
        Note testNote = new Note("Test Note", "Test content");
        noteHelper.saveNote(testNote);
        
        List<Note> notes = noteHelper.getAllNotes();
        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).getName());
        assertEquals("Test content", notes.get(0).getContent());
    }

    @Test
    public void testSaveMultipleNotes() {
        Note note1 = new Note("Note 1", "Content 1");
        Note note2 = new Note("Note 2", "Content 2");
        Note note3 = new Note("Note 3", "Content 3");
        
        noteHelper.saveNote(note1);
        noteHelper.saveNote(note2);
        noteHelper.saveNote(note3);
        
        List<Note> notes = noteHelper.getAllNotes();
        assertEquals(3, notes.size());
    }

    @Test
    public void testDeleteNote() {
        Note testNote = new Note("Test Note", "Test content");
        noteHelper.saveNote(testNote);
        
        assertEquals(1, noteHelper.getAllNotes().size());
        
        noteHelper.deleteNote(testNote);
        assertEquals(0, noteHelper.getAllNotes().size());
    }

    @Test
    public void testDeleteSpecificNoteFromMultiple() {
        Note note1 = new Note("Note 1", "Content 1");
        Note note2 = new Note("Note 2", "Content 2");
        Note note3 = new Note("Note 3", "Content 3");
        
        noteHelper.saveNote(note1);
        noteHelper.saveNote(note2);
        noteHelper.saveNote(note3);
        
        assertEquals(3, noteHelper.getAllNotes().size());
        
        noteHelper.deleteNote(note2);
        
        List<Note> remainingNotes = noteHelper.getAllNotes();
        assertEquals(2, remainingNotes.size());
        assertEquals("Note 1", remainingNotes.get(0).getName());
        assertEquals("Note 3", remainingNotes.get(1).getName());
    }

    @Test
    public void testNoteExists() {
        Note testNote = new Note("Unique Note", "Unique content");
        
        assertFalse(noteHelper.noteExists("Unique Note"));
        
        noteHelper.saveNote(testNote);
        
        assertTrue(noteHelper.noteExists("Unique Note"));
    }

    @Test
    public void testNoteExistsWithNonExistentNote() {
        assertFalse(noteHelper.noteExists("Non Existent Note"));
    }

    @Test
    public void testNoteExistsWithEmptyString() {
        assertFalse(noteHelper.noteExists(""));
    }

    @Test
    public void testPersistenceAfterSave() {
        Note testNote = new Note("Persistent Note", "Persistent content");
        noteHelper.saveNote(testNote);
        
        NoteHelper newNoteHelper = new NoteHelper(context);
        List<Note> notes = newNoteHelper.getAllNotes();
        
        assertEquals(1, notes.size());
        assertEquals("Persistent Note", notes.get(0).getName());
        assertEquals("Persistent content", notes.get(0).getContent());
    }

    @Test
    public void testSaveNoteWithNullValues() {
        Note nullNote = new Note(null, null);
        noteHelper.saveNote(nullNote);
        
        List<Note> notes = noteHelper.getAllNotes();
        assertEquals(1, notes.size());
        assertNull(notes.get(0).getName());
        assertNull(notes.get(0).getContent());
    }

    @Test
    public void testSaveNoteWithEmptyStrings() {
        Note emptyNote = new Note("", "");
        noteHelper.saveNote(emptyNote);
        
        List<Note> notes = noteHelper.getAllNotes();
        assertEquals(1, notes.size());
        assertEquals("", notes.get(0).getName());
        assertEquals("", notes.get(0).getContent());
    }
}
