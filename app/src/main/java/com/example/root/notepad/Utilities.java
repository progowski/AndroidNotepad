package com.example.root.notepad;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 04.05.17.
 */

public class Utilities {
    public static final String EXTENSION = ".bin";

    public static boolean saveNotes(Context context, Note note) {
        String filaName = String.valueOf(note.getDateTime()) + EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(filaName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false; // sww

        }

        return true;
    }
}
