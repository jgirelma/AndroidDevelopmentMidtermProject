package com.example.androiddevelopmentmidtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class note extends AppCompatActivity {
    private String title;
    private String body;
    private Note note;
    private Boolean institated;
    private int position;
    private EditText noteTitle;
    private EditText noteBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         noteTitle = findViewById(R.id.noteTitle);
         noteBody =  findViewById(R.id.noteBody);

        FloatingActionButton fab = findViewById(R.id.save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        institated = false;
        if(getIntent().getParcelableExtra("note") != null) {
            Intent intent = getIntent();
            note = intent.getParcelableExtra("note");
            institated = true;
            position = getIntent().getIntExtra("position", -1);


            noteTitle.setText(note.getTitle());
            noteBody.setText(note.getBody());
        }
    }

    public void saveNote(View view) {

        title = noteTitle.getText().toString();
        body = noteBody.getText().toString();
        note = new Note(title, body);
        int result = RESULT_OK;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("note", (Parcelable) note);
        if(institated){
            result = 1;
            intent.putExtra("position", position);
        }
        setResult(result, intent);

        finish();
    }

}
