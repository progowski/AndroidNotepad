package com.example.root.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = (EditText) findViewById(R.id.note_title);
        content = (EditText) findViewById(R.id.note_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_note_save:
                saveNote();
                break;
        }
        return true;
    }
    private void saveNote() {
        Note note = new Note(System.currentTimeMillis(), title.getText().toString(),
                content.getText().toString());
        if(Utilities.saveNotes(this, note)) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cannot save the note, check free space on your deice", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
