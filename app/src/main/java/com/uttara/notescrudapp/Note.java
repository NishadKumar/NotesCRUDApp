package com.uttara.notescrudapp;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by vikramshastry on 06/11/15.
 */
public class Note implements Serializable{


    private int id;
    private String title, desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Note()
    {
        Log.d("lognotescrud", "in Note no-arg constr");
    }

    public Note(String title, String desc) {
        this.title = title;
        this.desc = desc;
        Log.d("lognotescrud", "in Note param constr "+title+" "+desc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (!title.equals(note.title)) return false;
        return desc.equals(note.desc);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + desc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
