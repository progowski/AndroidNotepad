package com.example.root.notepad;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private String noteFileName;
    private Note loadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        //load note to intent
        title = (EditText) findViewById(R.id.note_title);
        content = (EditText) findViewById(R.id.note_content);
        noteFileName = getIntent().getStringExtra("NOTE_FILE");
        if (noteFileName != null && !noteFileName.isEmpty()) {
            loadedNote = Utilities.getNoteByName(this, noteFileName);
            if (loadedNote !=null) {
                title.setText(loadedNote.getTitle());
                content.setText(loadedNote.getContent());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu); // create menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_note_save:
                saveNote();
                break;
            case R.id.action_note_delete:
                deleteNote();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() { // handling back button
        if(!title.getText().toString().trim().isEmpty() || !content.getText().toString().trim().isEmpty()) {
            // Dialog alert if you are sure
            AlertDialog.Builder backDialog = new AlertDialog.Builder(this)
                    .setTitle("Leaving")
                    .setMessage("Are you sure to leave?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .setCancelable(false);

            backDialog.show();

        }
        else {
            finish();
        }
    }


    private void saveNote() {
        Note note;
        if(title.getText().toString().trim().isEmpty() || content.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, " please enter a title and a content", Toast.LENGTH_SHORT).show();
            return;
        }
        if(loadedNote == null) { // if new, add
            note = new Note(System.currentTimeMillis(), title.getText().toString(),
                    content.getText().toString());
        } else { //if not new, override
            note = new Note(loadedNote.getDateTime(), title.getText().toString(),
                    content.getText().toString());
        }
        if(Utilities.saveNotes(this, note)) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cannot save the note, check free space on your deice", Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    private void deleteNote() {
        if(loadedNote == null) {
            if(!title.getText().toString().trim().isEmpty() || !content.getText().toString().trim().isEmpty()) {
                // Dialog alert if you are sure
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this)
                        .setTitle("Deleting")
                        .setMessage("Are you sure to leave?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("no", null)
                        .setCancelable(false);

                deleteDialog.show();

            }
            else {
                finish();
            }
        } else {
            // Dialog alert if you are sure
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Deleting")
                    .setMessage("You are about to delete " + title.getText().toString() + ", are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(), loadedNote.getDateTime()
                                    + Utilities.EXTENSION);
                            Toast.makeText(getApplicationContext(), title.getText().toString() + "is deleted"
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .setCancelable(false);

            dialog.show();


        }
    }
}
