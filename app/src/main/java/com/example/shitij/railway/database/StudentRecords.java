package com.example.shitij.railway.database;

import android.database.sqlite.SQLiteDatabase;

import com.example.shitij.railway.log.Logging;
import com.example.shitij.railway.utils.CommonLibs;

/**
 * Created by Shitij on 26/09/15.
 */
public class StudentRecords {
    public static final String TABLE_DATA_TEXT = "table_student";
    public static final String COLUMN_ID = "_id";
    public static final String STUDENT_NAME = "student_name";
    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_SCORE = "student_score";
    public static final String STUDENT_YEAR = "student_year";
    public static final String TEAM_NUMBER = "team_number";
    public static final String STUDENT_SELECTED = "student_selected";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DATA_TEXT
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + STUDENT_NAME + " text not null unique, "
            + STUDENT_ID + " text not null unique, "
            + TEAM_NUMBER + " text default -1, "
            + STUDENT_SCORE + " int default 0, "
            + STUDENT_YEAR + " int not null, "
            + STUDENT_SELECTED + " boolean not null "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(DATABASE_CREATE);
        }
        catch (Exception e){
            Logging.logError("StoreDataTable", e.toString(), CommonLibs.Priority.VERY_HIGH);
        }
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion)throws Exception {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_TEXT);
        onCreate(database);
    }
}
