package com.example.appchat_firebase.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appchat_firebase.UserOj;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB_CHAT_APP";
    private static final String TABLE_NAME = "USER";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String GENDER = "gender";
    private static final String STATUS = "status";
    private Context context;

    public DBManager(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" + ID
                + " TEXT, "+ EMAIL + " TEXT, "
                + FIRST_NAME+ " TEXT, " + LAST_NAME + " TEXT, " +PHONE + " TEXT, "
                + PASSWORD + " TEXT, "+ GENDER + " INTEGER, " + STATUS + " INTEGER )";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addUser(UserOj user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(EMAIL, user.getEmail());
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(PHONE, user.getSdt());
        values.put(PASSWORD, user.getPassword());
        values.put(GENDER, user.isGioiTinh());
        values.put(STATUS, user.isTrangThai());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public int Update(UserOj user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL, user.getEmail());
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(PHONE, user.getSdt());
        values.put(PASSWORD, user.getPassword());
        values.put(GENDER, user.isGioiTinh());
        values.put(STATUS, user.isTrangThai());
        return db.update(TABLE_NAME,values,ID +"=?",
                new String[] { String.valueOf(user.getId())});
    }
    public void Delete(UserOj user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }
    public UserOj getUser() {
        UserOj user = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                user = new UserOj();
                user.setId(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setFirstName(cursor.getString(2));
                user.setLastName(cursor.getString(3));
                user.setSdt(cursor.getString(4));
                user.setPassword(cursor.getString(5));
                boolean gender = cursor.getInt(6) > 0;
                user.setGioiTinh(gender);
                boolean status = cursor.getInt(7) > 0;
                user.setTrangThai(status);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }
}
