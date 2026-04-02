package com.vu.FinTechAppsLab4.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vu.FinTechAppsLab4.model.Note;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteHelper {
    private static final String PREFS_NAME = "NotesPrefs";
    private static final String NOTES_KEY = "notes_list";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public NoteHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<Note> getAllNotes() {
        String json = sharedPreferences.getString(NOTES_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Note>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveNote(Note note) {
        List<Note> notes = getAllNotes();
        notes.add(note);
        saveNotesList(notes);
    }

    public void deleteNote(Note note) {
        List<Note> notes = getAllNotes();
        notes.removeIf(n -> n.getName().equals(note.getName()));
        saveNotesList(notes);
    }

    public boolean noteExists(String noteName) {
        List<Note> notes = getAllNotes();
        for (Note note : notes) {
            if (note.getName().equals(noteName)) {
                return true;
            }
        }
        return false;
    }

    private void saveNotesList(List<Note> notes) {
        String json = gson.toJson(notes);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NOTES_KEY, json);
        editor.apply();
    }
}
