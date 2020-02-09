package com.aakash.notessqlitebasics.utils;

public class Utils {

    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
            "("+COLUMN_ID+" INTEGER PRIMARY KEY  AUTOINCREMENT," +COLUMN_NOTE+" TEXT,"+COLUMN_TIMESTAMP + " TEXT DEFAULT CURRENT_TIMESTAMP"+")";
}
