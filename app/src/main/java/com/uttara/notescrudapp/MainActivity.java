package com.uttara.notescrudapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    NotesDBHelper helper;
    ListView lv;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new NotesDBHelper(this);
        boolean result;
    //    int val = (int)(Math.random()*100);
    //    Note n1 = new Note("title"+val,"desc"+val);

    /*    result = helper.addNote(n1);
        Log.d("lognotescrud", "MA->onCreate()-> n1 added "+result);

        List <Note> notes = helper.getAllNotes();
        Log.d("lognotescrud", "MA->onCreate()-> all notes " + notes);
    */

    //    n1 = notes.get(0);

    //    n1.setTitle("TITLE"+val);
    //    n1.setDesc("DESC" + val);

    //    result = helper.updateNote(n1.getId(),n1);
    //    Log.d("lognotescrud", "MA->onCreate()-> n1 with id "+n1.getId()+" updated "+result);
    //    result = helper.deleteNote(n1.getId());
    //    Log.d("lognotescrud", "MA->onCreate()-> n1 with id "+n1.getId()+" deleted "+result);

    //    notes = helper.getAllNotes();
    //    Log.d("lognotescrud", "MA->onCreate()-> all notes " + notes);

        lv = (ListView)findViewById(R.id.listView);
       // cursor = helper.getNotesCursor(); //push this to background!
       // adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[]{NotesDBHelper.COL_TITLE,NotesDBHelper.COL_DESC},new int[]{android.R.id.text1,android.R.id.text2},0);
       // lv.setAdapter(adapter);

        //attach the listeners
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),UpdateNoteActivity.class);

                //push this to the background using another AsyncTask
                Note n = helper.getNote(id);

                intent.putExtra("note",n); //n must be Serializable!
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //delete the note from the db depending on position and id of note!

                Log.d("lognotescrud", "in LV->longclicklistener()-> position = " + position + " id = " + id);

               // helper.deleteNote(id);
                DeleteNoteTask task = new DeleteNoteTask();
                task.execute(id);
                FetchCursorTask ftask = new FetchCursorTask(getApplicationContext());
                ftask.execute();
                return false;
            }
        });


        //start the asynctask
        FetchCursorTask task = new FetchCursorTask(this);
        task.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lognotescrud", "in MA->onRestart()");
        //get the new cursor with whatever data changes and give it to adapter
        FetchCursorTask task = new FetchCursorTask(this);
        task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        helper.getReadableDatabase().close();
    }

    public void displayAddView(View view) {
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }

    private class FetchCursorTask extends AsyncTask<Void,Void,Void>
    {

        public FetchCursorTask(Context ctx)
        {
            //super(ctx,);
            Log.d("lognotescrud", "in FetchCursorTask constr()");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("lognotescrud", "in FetchCursorTask onPreExecute()");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("lognotescrud", "in FetchCursorTask doInB()");

            cursor = helper.getNotesCursor();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("lognotescrud", "in FetchCursorTask onPostExecute()");
            if(adapter==null) {
                adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, cursor, new String[]{NotesDBHelper.COL_TITLE, NotesDBHelper.COL_DESC}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                lv.setAdapter(adapter);
            }
            else
            {
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
            }
        }
    }


    public class DeleteNoteTask extends AsyncTask<Long,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(Long... params) {

            long id = params[0];
            return helper.deleteNote(id);
            //return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean)
                Toast.makeText(getApplicationContext(),"Note deleted successfully!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Deletion failed!!",Toast.LENGTH_SHORT).show();
        }
    }
}









