package com.vu.FinTechAppsLab4.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class NoteTest {
    private static final String TEST_NAME = "Test Note";
    private static final String TEST_CONTENT = "This is test content for the note.";

    @Test
    public void testNoteCreationWithParameters() {
        Note testNote = new Note(TEST_NAME, TEST_CONTENT);
        assertEquals(TEST_NAME, testNote.getName());
        assertEquals(TEST_CONTENT, testNote.getContent());
    }

    @Test
    public void testNoteCreationWithDefaultConstructor() {
        Note note = new Note();
        assertNull(note.getName());
        assertNull(note.getContent());
    }

    @Test
    public void testSetAndGetNoteName() {
        Note note = new Note();
        note.setName(TEST_NAME);
        assertEquals(TEST_NAME, note.getName());
    }

    @Test
    public void testSetAndGetNoteContent() {
        Note note = new Note();
        note.setContent(TEST_CONTENT);
        assertEquals(TEST_CONTENT, note.getContent());
    }

    @Test
    public void testToStringReturnsNoteName() {
        Note note = new Note();
        note.setName(TEST_NAME);
        assertEquals(TEST_NAME, note.toString());
    }

    @Test
    public void testToStringWithNullName() {
        Note note = new Note();
        assertNull(note.toString());
    }

    @Test
    public void testNoteWithEmptyStrings() {
        Note emptyNote = new Note("", "");
        assertEquals("", emptyNote.getName());
        assertEquals("", emptyNote.getContent());
        assertEquals("", emptyNote.toString());
    }
}
