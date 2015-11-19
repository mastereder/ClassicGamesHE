package de.classicgameshe.classicgameshe.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import de.classicgameshe.classicgameshe.support.DataBaseHelper;

/**
 * Created by marinus on 16.10.15.
 */

public class TicTacToeDataBaseAdapter extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "statistic.db";
    public static final String TABLE_NAME = "tictactoe";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_userID = "userID";
    public static final String COLUMN_x = "x";
    public static final String COLUMN_o = "o";
    public static final String COLUMN_multiplayer = "multiplayer";


    public TicTacToeDataBaseAdapter(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table tictactoe " +
                        "(id integer primary key, userID String,x int,o int, multiplayer int)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tictactoe");
        onCreate(db);
    }


    public boolean insertEntry  (String userID, int x, int o, int multiplayer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("userID", userID);
        newValues.put("x", x);
        newValues.put("o", o);
        newValues.put("multiplayer", multiplayer);
        db.insert("tictactoe", null, newValues);
        return true;
    }

    public ArrayList getData(String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select x,o, multiplayer from tictactoe where userID=" + userID + "", null);
        ArrayList arrayList=new ArrayList();
        for(int i=0; i<res.getColumnCount(); i++)
        {
            arrayList.add( String.valueOf(res.getString(i)) );
        }
        return arrayList;
    }

    public int getXWins (String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor =  db.rawQuery("select x from tictactoe where userID=" + userID + "", null);
        if (mCursor.moveToFirst()){
        int test = mCursor.getInt(mCursor.getCount()-1);
        return test;}
        else {
            return 0;
        }
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateEntry (String userID, int x, int o, int multiplayer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("x", x);
        updateValues.put("o", o);
        updateValues.put("multiplayer", multiplayer);
        db.update("tictactoe", updateValues, "userID = ? ", new String[] { (userID) } );
        return true;
    }


    public Integer deleteEntry (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tictactoe",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public boolean checkIfStatisticExists (String userID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE userID=?", new String[]{userID});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    public ArrayList<ArrayList<String>> getAllEntrys(String tableName, String[] tableColumns,String whereClase, String whereArgs[], String groupBy,String having, String orderBy)
    {

        ArrayList<ArrayList<String>> retList = new ArrayList<ArrayList<String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(tableName, tableColumns, whereClase, whereArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst())
        {
            do
            {
                list = new ArrayList<String>();
                for(int i=0; i<cursor.getColumnCount(); i++)
                {
                    list.add( cursor.getString(i) );
                }
                retList.add(list);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return retList;

    }
}
