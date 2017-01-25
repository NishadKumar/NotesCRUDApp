package com.uttara.notescrudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class UpdateNoteActivity extends Activity {

    EditText etTitle, etDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        etTitle = (EditText) findViewById(R.id.editText3);
        etDesc = (EditText) findViewById(R.id.editText4);

        Intent intent = getIntent();
        Note n = (Note)intent.getSerializableExtra("note");

        etTitle.setText(n.getTitle());
        etDesc.setText(n.getDesc());
    }

    public void updateNote(View view) {

        //validate and invoke helper.updateNote(..) in background!
    }
}
