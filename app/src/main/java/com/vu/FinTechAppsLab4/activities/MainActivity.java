package com.vu.FinTechAppsLab4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vu.FinTechAppsLab4.R;
import com.vu.FinTechAppsLab4.model.Note;
import com.vu.FinTechAppsLab4.utils.NoteHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNotes;
    private TextView textViewEmptyMessage;
    private FloatingActionButton fabCreateNote;
    private FloatingActionButton fabDeleteNote;
    private NoteHelper noteHelper;
    private ArrayAdapter<Note> notesAdapter;
    private List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewNotes = findViewById(R.id.listViewNotes);
        textViewEmptyMessage = findViewById(R.id.textViewEmptyMessage);
        fabCreateNote = findViewById(R.id.fabCreateNote);
        fabDeleteNote = findViewById(R.id.fabDeleteNote);
        noteHelper = new NoteHelper(this);
        
        setupListView();
        setupFloatingActionButtons();
    }

    private void setupFloatingActionButtons() {
        fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.vu.FinTechAppsLab4.activities.AddNoteActivity.class);
                startActivity(intent);
            }
        });

        fabDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.vu.FinTechAppsLab4.activities.DeleteNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupListView() {
        notesList = noteHelper.getAllNotes();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(notesAdapter);
        
        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = notesList.get(position);
                Intent intent = new Intent(MainActivity.this, com.vu.FinTechAppsLab4.activities.ViewNoteActivity.class);
                intent.putExtra("note", selectedNote);
                startActivity(intent);
            }
        });
        
        updateEmptyMessageVisibility();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotesList();
    }

    private void refreshNotesList() {
        notesList.clear();
        notesList.addAll(noteHelper.getAllNotes());
        notesAdapter.notifyDataSetChanged();
        updateEmptyMessageVisibility();
    }

    private void updateEmptyMessageVisibility() {
        if (notesList.isEmpty()) {
            listViewNotes.setVisibility(View.GONE);
            textViewEmptyMessage.setVisibility(View.VISIBLE);
            fabDeleteNote.setVisibility(View.GONE);
        } else {
            listViewNotes.setVisibility(View.VISIBLE);
            textViewEmptyMessage.setVisibility(View.GONE);
            fabDeleteNote.setVisibility(View.VISIBLE);
        }
        // Create button is always visible
        fabCreateNote.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create_note) {
            Intent intent = new Intent(this, com.vu.FinTechAppsLab4.activities.AddNoteActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete_note) {
            Intent intent = new Intent(this, com.vu.FinTechAppsLab4.activities.DeleteNoteActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}