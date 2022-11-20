package com.example.addsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Login.db"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE users(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
    }

    fun insert(username: String, password: String) : Boolean{
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = sqLiteDatabase.insert("users", null, contentValues)
        if(result.toInt() == -1){
            return false
        }

        return true

    }

    fun checkUserName(username: String) : Boolean{
        val sqLiteDatabase = this.writableDatabase
        val cursor : Cursor =  sqLiteDatabase.rawQuery("SELECT * FROM users WHERE username=?", arrayOf<String>(username))
        return cursor.count > 0
    }

    fun checkLogin(username: String, password: String): Boolean {
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        return cursor.count > 0
    }
}