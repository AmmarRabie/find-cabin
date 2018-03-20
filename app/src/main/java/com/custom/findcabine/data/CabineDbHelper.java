package com.custom.findcabine.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.custom.findcabine.data.CabineContract.CabineEntry.SQL_CREATE_CABINES_TABLE;
import static com.custom.findcabine.data.CabineContract.CabineEntry.SQL_DELETE_CABINES_TABLE;

/**
 * Created by AmmarRabie on 14/08/2017.
 */

public class CabineDbHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cabine.db";
    public CabineDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CABINES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_CABINES_TABLE);
        onCreate(sqLiteDatabase);
    }
}