package com.vu.FinTechAppsLab4.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vu.FinTechAppsLab4.R;
import com.vu.FinTechAppsLab4.model.Note;

public class ViewNoteActivity extends AppCompatActivity {
    private TextView textViewNoteName;
    private TextView textViewNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewNoteName = findViewById(R.id.textViewNoteName);
        textViewNoteContent = findViewById(R.id.textViewNoteContent);

        // Get the note from intent
        Note note = (Note) getIntent().getSerializableExtra("note");
        
        if (note != null) {
            textViewNoteName.setText(note.getName());
            textViewNoteContent.setText(note.getContent());
            
            // Set the activity title to the note name
            setTitle(note.getName());
        }
    }
}
