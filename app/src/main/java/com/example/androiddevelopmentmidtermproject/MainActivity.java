package com.example.androiddevelopmentmidtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Reads notes from file if file exists
        if(fileExists("savedInput")) {
            System.out.println("fileexists");
            notes = open("savedInput");
        } else {
            notes = new ArrayList<Note>();
        }



        if(notes.size() > 0) {
            renderNotes(notes);
        }

        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, note.class);
                intent.putExtra("note", (Parcelable) notes.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            }
        });


    }

    public void renderNotes(ArrayList<Note> notes) {
        ArrayList<String> noteTitles = new ArrayList<String>();
        for(Note note : notes) {
            noteTitles.add(note.getTitle());
        }

        ArrayAdapter<String> notesAdapter =

                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteTitles);
        ListView listView = (ListView) findViewById(R.id.notesListView);

        listView.setAdapter(notesAdapter);

    }
    public void makeNote(View view) {
        Intent intent = new Intent(this, note.class);
        startActivityForResult(intent, 1);
    }

    public void onResume(){
        System.out.println("d");
        super.onResume();
        if(notes != null) {
            renderNotes(notes);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                if(data.getParcelableExtra("note") != null) {
                    Note note = (Note)(data.getParcelableExtra("note"));

                    System.out.println(note);

                    notes.add(note);
                }
                System.out.println("a");
                if(notes.size() > 0 ) {
                    save("savedInput", notes);
                    System.out.println("saved");
                }
                System.out.println("here");
                onResume();
            }
            if(resultCode == 1) {
                if(data.getParcelableExtra("note") != null) {
                    Note note = (Note)(data.getParcelableExtra("note"));
                    int position = data.getIntExtra("position", -1);
                    System.out.println(note);

                    notes.set(position, note);
                }
                if(notes.size() > 0 ) {
                    save("savedInput", notes);
                    System.out.println("saved");
                }
                System.out.println("here");
                onResume();
            }
            if(resultCode == 2){}
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
                System.out.println("result cancled");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Writes textToSave to the file denoted by fileName. **/
    private void save(String fileName, ArrayList<Note> notes) {
        try
        {
            FileOutputStream fos =  openFileOutput(fileName, 0);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notes);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /** Checks if the file denoted by fileName exists. **/
    private boolean fileExists(String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    /** Opens the file denoted by fileName and returns the contents of the file. **/
    private ArrayList<Note> open(String fileName) {
        ArrayList<Note> content = new ArrayList<Note>();
        if (fileExists(fileName)) {
            try
            {
                FileInputStream fis = openFileInput(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);

                content = (ArrayList<Note>) ois.readObject();

                ois.close();
                fis.close();
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            catch (ClassNotFoundException c)
            {
                System.out.println("Class not found");
                c.printStackTrace();
            }
        }
        return content;
    }

    public void pram(ArrayList<Note> s) {
        for(Note a : s){
            System.out.println(a.getTitle());
        }
    }
}
