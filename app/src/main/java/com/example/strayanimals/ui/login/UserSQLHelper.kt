package com.example.strayanimals.ui.login
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.strayanimals.MyContract
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap


class UserSQLHelper(ctx: Context) :
    SQLiteOpenHelper(ctx, MyContract.DATABASE_NAME, null, MyContract.DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            try {
                execSQL(MyContract.UserTable.SQL_CREATE_TABLE)
            }catch (e: Exception){
                execSQL(MyContract.UserTable.SQL_DELETE_ENTRIES)
                execSQL(MyContract.UserTable.SQL_CREATE_TABLE)
            }
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.run {
            execSQL(MyContract.UserTable.SQL_DELETE_ENTRIES)
            onCreate(this)
        }
    }
    public fun readSQL(): MutableList<Map<String, Any>> {
        val ret: MutableList<Map<String, Any>> = ArrayList()
        val db: SQLiteDatabase = this.readableDatabase

        // the column we need
        val projection = arrayOf(
            MyContract.UserTable.COLUMN_NAME_USERNAME,
            MyContract.UserTable.COLUMN_NAME_PWD,
        )
        // how to order the result
        val sortOrder: String = "${MyContract.UserTable.COLUMN_NAME_USERNAME} DESC"
        // query and get cursor
        // with can close the resource automatically
        this.readableDatabase.use { d ->
            d.query(
                MyContract.UserTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
            ).use { c ->
                // iterate all data and add them to "data"
                while (c.moveToNext()) {
                    val tempData: MutableMap<String, Any> = HashMap()
                    tempData[MyContract.UserTable.COLUMN_NAME_USERNAME] =
                        c.getString(c.getColumnIndex(MyContract.UserTable.COLUMN_NAME_USERNAME))
                    tempData[MyContract.UserTable.COLUMN_NAME_PWD] =
                        c.getString(c.getColumnIndex(MyContract.UserTable.COLUMN_NAME_PWD))
                    ret.add(tempData)
                }
            }
        }
        return ret
    }
    public fun addUser(t: String?, c: String?,createtime: Long) {
        // construct the key-value data to insert into the database
        val values = ContentValues()
        values.put(MyContract.UserTable.COLUMN_NAME_USERNAME, t)
        values.put(MyContract.UserTable.COLUMN_NAME_PWD, c)
        values.put(MyContract.UserTable.COLUMN_NAME_CREATETIME, createtime)
        // INSERT INTO MemoContract.MemoTable.TABLE_NAME (values.KEYS) VALUES (values.VALUES)
        this.writableDatabase.use {
            it.insert(MyContract.UserTable.TABLE_NAME, null, values)
        }
    }





}