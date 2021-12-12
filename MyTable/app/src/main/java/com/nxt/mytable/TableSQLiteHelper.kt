package com.nxt.mytable

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TableSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        const val TAG = "SQLiteOpenHelper"
        const val DATABASE_NAME = "engineer"
        const val DATABASE_VERSION = 136;

        const val ENGINEER_TABLE = "engineer_table";
        const val ID = "id";
        const val MONDAY = "monday"
        const val TUESDAY = "tuesday"
        const val WEDNESDAY = "wednesday"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val command = ("create table " + ENGINEER_TABLE
                + " (" + ID + " integer primary key autoincrement, "
                + MONDAY + " text, " + TUESDAY + " text, " + WEDNESDAY + " text);")
        Log.d(TAG, "onCreate $command")
        db!!.execSQL(command)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val command =
            "drop table if exists $ENGINEER_TABLE"
        db!!.execSQL(command)
        onCreate(db)
    }
}