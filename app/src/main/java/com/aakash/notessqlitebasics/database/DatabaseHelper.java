package com.aakash.notessqlitebasics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aakash.notessqlitebasics.database.model.Note;
import com.aakash.notessqlitebasics.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.aakash.notessqlitebasics.utils.Utils.COLUMN_ID;
import static com.aakash.notessqlitebasics.utils.Utils.COLUMN_NOTE;
import static com.aakash.notessqlitebasics.utils.Utils.COLUMN_TIMESTAMP;
import static com.aakash.notessqlitebasics.utils.Utils.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME="notes_db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(Utils.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertNote(String note){

        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(COLUMN_NOTE,note);

        long id = sqLiteDatabase.insert(TABLE_NAME,null,values);

        sqLiteDatabase.close();

        return id;
    }

    public Note getNote(long id){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_TIMESTAMP}, COLUMN_ID + "=?",new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note =new Note(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

        cursor.close();

        return note;
    }

    public ArrayList<Note> getAllNotes(){

        ArrayList<Note> notes = new ArrayList<>();

        String selectAllQuery ="SELECT * FROM "+ TABLE_NAME
                +" ORDER BY " +COLUMN_TIMESTAMP + " DESC";


        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(selectAllQuery,null);

        if(cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

                notes.add(note);

            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        Log.d("CHECK", Arrays.toString(notes.toArray()));

        return notes;

    }

    public int getNoteCount(){

        String countQuery = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(Note note){

        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_NOTE,note.getNote());

        return sqLiteDatabase.update(TABLE_NAME,contentValues,COLUMN_ID + " = ?",new String[]{String.valueOf(note.getId())});

    }

    public void deleteNote(Note note){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+" =?",new String[]{String.valueOf(note.getId())});
        sqLiteDatabase.close();

    }
}
