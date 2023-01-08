package com.ldream.arkadasinitani;

public class MainDatabase {

    // Veritabanı adı
    public static final String DB_NAME = "ArkadasiniTani";

    // Veritabanı versiyon
    public static final int DB_VERSION = 1;

    // Tablo adı
    public static final String TABLE_NAME = "TESTLERIN_ADI";

    // Tablonun sütun alanları
    public static final String TEST_NAME = "TEST_NAME";

    // Sql tablo oluştur
    public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "("
            + TEST_NAME + " VARCHAR "
            + ")";

}
