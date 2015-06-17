package com.example.anthony.refree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 16.06.2015.
 */
public class TableControllerTeam extends DatabaseHandler {
    public TableControllerTeam(Context context) {
        super(context);}

        public boolean create(ObjectTeam objectTeam) {

            ContentValues values = new ContentValues();

            values.put("teamname", objectTeam.teamname);


            SQLiteDatabase db = this.getWritableDatabase();

            boolean createSuccessful = db.insert("team", null, values) > 0;
            db.close();

            return createSuccessful;
        }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM team";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
    public List<ObjectTeam> read() {

        List<ObjectTeam> recordsList = new ArrayList<ObjectTeam>();

        String sql = "SELECT * FROM team ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String TeamFirstname = cursor.getString(cursor.getColumnIndex("teamname"));


                ObjectTeam objectTeam = new ObjectTeam();
                objectTeam.id = id;
                objectTeam.teamname = TeamFirstname;


                recordsList.add(objectTeam);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    }

