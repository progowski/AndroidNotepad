package com.example.root.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNotes = (ListView) findViewById(R.id.notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_new:
                //we open NoteActivity to create new one
                startActivity(new Intent(this, NoteActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSaveNotes(this);
        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "You have no saved notes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Collections.sort(notes, new Comparator<Note>() {
                @Override
                public int compare(Note n1, Note n2) {
                    if(n2.getDateTime() > n2.getDateTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            NoteAdapter na = new NoteAdapter(this, R.layout.note, notes);
            listViewNotes.setAdapter(na);

            listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long lid) {
                    String fileName = ((Note)listViewNotes.getItemAtPosition(position)).getDateTime() +
                            Utilities.EXTENSION;
                    Intent viewNote = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNote.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNote);
                }
            });

        }
    }
}
