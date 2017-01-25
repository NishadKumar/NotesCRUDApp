package com.uttara.notescrudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikramshastry on 06/11/15.
 */
public class NotesDBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "notesdb";
    public static final int DBVER = 1;
    public static final String TBL_NOTES = "notes";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESC = "desc";

    public NotesDBHelper(Context ctx)
    {
        super(ctx,DBNAME,null,DBVER);
        Log.d("lognotescrud", "in NotesDBHelper no-arg constr");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TBL_NOTES+"("+COL_ID+" integer primary key autoincrement, "+COL_TITLE+" text,"+COL_DESC+" text)";
        db.execSQL(sql);
        Log.d("lognotescrud", "in NotesDBHelper->onCreate() sql = "+sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("lognotescrud", "in NotesDBHelper->onUpgrade oldV "+oldVersion+" newV "+newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+TBL_NOTES);
        onCreate(db);
    }

    public boolean addNote(Note bean)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_TITLE, bean.getTitle());
            values.put(COL_DESC, bean.getDesc());
            long id = db.insert(TBL_NOTES, null, values);
            Log.d("lognotescrud","NotesDBHelper->addNote()-> added note "+id);
            return true;
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
            Log.d("lognotescrud","NotesDBHelper->addNote()->exception during adding "+e.getMessage());
            return false;
        }

    }
    public boolean updateNote(int id, Note bean)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_TITLE, bean.getTitle());
            values.put(COL_DESC, bean.getDesc());

            db.update(TBL_NOTES, values, COL_ID + "=?", new String[]{String.valueOf(id)});

            Log.d("lognotescrud","NotesDBHelper->updateNote()-> updated note "+id);
            return true;
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
            Log.d("lognotescrud","NotesDBHelper->updateNote()->exception during updating "+e.getMessage());
            return false;
        }

    }

    public boolean deleteNote(long id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TBL_NOTES, COL_ID + "=?", new String[]{String.valueOf(id)});

            Log.d("lognotescrud","NotesDBHelper->deleteNote()-> deleted note "+id);
            return true;
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
            Log.d("lognotescrud","NotesDBHelper->deleteNote()->exception during deleting "+e.getMessage());
            return false;
        }

    }
    public List<Note> getAllNotes()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_NOTES, null, null, null, null, null, COL_TITLE + " asc");

        List<Note> notes = new ArrayList<Note>();

        if(c.getCount()>0)
        {
            c.moveToFirst();
            int id;
            String title,desc;
            do {

                id = c.getInt(c.getColumnIndex(COL_ID));
                title = c.getString(c.getColumnIndex(COL_TITLE));
                desc = c.getString(c.getColumnIndex(COL_DESC));

                Note bean = new Note();
                bean.setId(id);
                bean.setTitle(title);
                bean.setDesc(desc);

                notes.add(bean);

            }
            while(c.moveToNext());
        }
        return notes;
    }

    public Note getNote(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_NOTES,null,COL_ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();
            Note bean = new Note();
            bean.setId(c.getInt(c.getColumnIndex(COL_ID)));
            bean.setTitle(c.getString(c.getColumnIndex(COL_TITLE)));
            bean.setDesc(c.getString(c.getColumnIndex(COL_DESC)));

            return bean;
        }
        else
            return null;

    }

    public Cursor getNotesCursor()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TBL_NOTES,null,null,null,null,null,null);
        return c;
    }

}
