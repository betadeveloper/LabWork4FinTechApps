package com.vu.FinTechAppsLab4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextNoteName;
    private EditText editTextNoteContent;
    private Button buttonSaveNote;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNoteName = findViewById(R.id.editTextNoteName);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);
        buttonSaveNote = findViewById(R.id.buttonSaveNote);
        noteHelper = new NoteHelper(this);

        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String noteName = editTextNoteName.getText().toString().trim();
        String noteContent = editTextNoteContent.getText().toString().trim();

        if (!validateInput(noteName, noteContent)) {
            return;
        }

        if (noteHelper.noteExists(noteName)) {
            Toast.makeText(this, "A note with this name already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        Note newNote = new Note(noteName, noteContent);
        noteHelper.saveNote(newNote);

        Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateInput(String name, String content) {
        if (name.isEmpty()) {
            editTextNoteName.setError("Note name is required");
            editTextNoteName.requestFocus();
            return false;
        }

        if (content.isEmpty()) {
            editTextNoteContent.setError("Note content is required");
            editTextNoteContent.requestFocus();
            return false;
        }

        if (name.length() < 3) {
            editTextNoteName.setError("Note name must be at least 3 characters");
            editTextNoteName.requestFocus();
            return false;
        }

        if (content.length() < 5) {
            editTextNoteContent.setError("Note content must be at least 5 characters");
            editTextNoteContent.requestFocus();
            return false;
        }

        return true;
    }
}
