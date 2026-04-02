package com.vu.FinTechAppsLab4.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vu.FinTechAppsLab4.R;
import com.vu.FinTechAppsLab4.model.Note;
import com.vu.FinTechAppsLab4.utils.NoteHelper;

import java.util.List;

public class DeleteNoteActivity extends AppCompatActivity {
    private Spinner spinnerNotes;
    private TextView textViewNoteContent;
    private Button buttonDeleteNote;
    private NoteHelper noteHelper;
    private List<Note> notesList;
    private ArrayAdapter<Note> notesAdapter;
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerNotes = findViewById(R.id.spinnerNotes);
        textViewNoteContent = findViewById(R.id.textViewNoteContent);
        buttonDeleteNote = findViewById(R.id.buttonDeleteNote);
        noteHelper = new NoteHelper(this);

        setupSpinner();
        
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedNote();
            }
        });
    }

    private void setupSpinner() {
        notesList = noteHelper.getAllNotes();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notesList);
        notesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(notesAdapter);

        spinnerNotes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNote = notesList.get(position);
                if (selectedNote != null) {
                    textViewNoteContent.setText(selectedNote.getContent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewNoteContent.setText("");
                selectedNote = null;
            }
        });

        if (notesList.isEmpty()) {
            textViewNoteContent.setText("No notes available to delete");
            buttonDeleteNote.setEnabled(false);
        }
    }

    private void deleteSelectedNote() {
        if (selectedNote == null) {
            Toast.makeText(this, "Please select a note to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        noteHelper.deleteNote(selectedNote);
        Toast.makeText(this, "Note deleted successfully!", Toast.LENGTH_SHORT).show();

        notesList.remove(selectedNote);
        notesAdapter.notifyDataSetChanged();
        
        selectedNote = null;
        textViewNoteContent.setText("");

        if (notesList.isEmpty()) {
            textViewNoteContent.setText("No notes available to delete");
            buttonDeleteNote.setEnabled(false);
        }
    }
}
