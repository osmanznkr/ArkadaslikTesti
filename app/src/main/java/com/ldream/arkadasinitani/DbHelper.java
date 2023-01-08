package com.ldream.arkadasinitani;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//  CRUD işlemler
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, MainDatabase.DB_NAME, null, MainDatabase.DB_VERSION);
    }

    // Tablo Oluşturma

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(MainDatabase.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Yapısal bir değişiklik olduğunda eski tabloyu düşür ve yenisini oluştur..

        // DROP

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS '" + MainDatabase.TABLE_NAME + "'");

        // Yeniden tablo oluştur

        onCreate(sqLiteDatabase);

    }


    // Veritabanına kayıt ekleme metodu

    public long addTest(String test_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // içerik değerleri
        ContentValues values = new ContentValues();


        //veri ekleme

        values.put(MainDatabase.TEST_NAME, test_name);


        long id = sqLiteDatabase.insert(MainDatabase.TABLE_NAME, null, values);


        sqLiteDatabase.close();

        return id;
    }

    // Verileri veritabanından al

    public ArrayList<Results> getResults() {

        ArrayList<Results> allResults = new ArrayList<>();

        String selectQueryy = " SELECT * FROM SONUCLAR ";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQueryy, null);

        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") Results result = new Results(
                        "" + cursor.getString(cursor.getColumnIndex("hazirlayan")),
                        "" + cursor.getString(cursor.getColumnIndex("cozen")),
                        "" + cursor.getString(cursor.getColumnIndex("dogru")),
                        "" + cursor.getString(cursor.getColumnIndex("yanlis")),
                        "" + cursor.getInt(cursor.getColumnIndex("id")));


                allResults.add(result);


            }
            while (cursor.moveToNext());

        }

        database.close();

        return allResults;


    }

    public ArrayList<TestList> getAllTests() {


        ArrayList<TestList> testsList = new ArrayList<>();


        String selectQuery = " SELECT * FROM " + MainDatabase.TABLE_NAME;


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") TestList test = new TestList("" + cursor.getString(cursor.getColumnIndex(MainDatabase.TEST_NAME)));


                testsList.add(test);


            }
            while (cursor.moveToNext());

        }

        sqLiteDatabase.close();

        return testsList;


    }

    // Testi Sil

    public void removeTest(String testName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(MainDatabase.TABLE_NAME, MainDatabase.TEST_NAME + " =? ", new String[]{testName});


        sqLiteDatabase.close();
    }

    public void deleteResult(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("SONUCLAR", "id" + " =? ", new String[]{id});
    }


    public void dropTable() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE '" + TestAdapter.sTestName + "'");


        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public boolean nameExistInDB(String getEditTxt) {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM " + "TESTLERIN_ADI";
        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                if (getEditTxt.equals(cursor.getString(cursor.getColumnIndex(MainDatabase.TEST_NAME)))) {
                    return true;
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }


}
