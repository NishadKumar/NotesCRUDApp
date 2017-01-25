package com.uttara.notescrudapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends Activity {

    EditText etTitle,etDesc;
    NotesDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = (EditText) findViewById(R.id.editText);
        etDesc = (EditText) findViewById(R.id.editText2);
        helper = new NotesDBHelper(this);
    }

    public void saveNote(View view) {

        Log.d("lognotescrud", "in ANA->saveNote()");
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();

        Log.d("lognotescrud", "in ANA->saveNote() title ="+title+" desc = "+desc );
        if(title.trim().equals("") || desc.trim().equals(""))
            Toast.makeText(this,"Enter title and description!",Toast.LENGTH_SHORT).show();
        else {

            Note n = new Note(title,desc);
           // helper.addNote(n);
            AddNoteTask task = new AddNoteTask();
            task.execute(n);

        }
    }

    public class AddNoteTask extends AsyncTask<Note,Void,Void>
    {
        boolean result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("lognotescrud", "in AddNoteTask onPreEx()");
        }

        @Override
        protected Void doInBackground(Note... params) {

            Log.d("lognotescrud", "in AddNoteTask doInB()");

            Note n = params[0];

           result = helper.addNote(n);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("lognotescrud", "in AddNoteTask onPostEx()");

            if(result)
            {
                Toast.makeText(getApplicationContext(),"Note added successfully!",Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(getApplicationContext(),"Addition to db unsuccessful!",Toast.LENGTH_SHORT).show();
        }
    }

}
