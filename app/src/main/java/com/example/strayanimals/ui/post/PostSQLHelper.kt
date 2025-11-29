package com.example.strayanimals.ui.post

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.location.Location
import com.example.strayanimals.MyContract
import com.example.strayanimals.data.Post
import java.sql.Timestamp
import java.util.*

class PostSQLHelper(ctx: Context): SQLiteOpenHelper(ctx, MyContract.DATABASE_NAME, null, MyContract.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            try {
                execSQL(MyContract.PostTable.SQL_CREATE_TABLE)
            } catch (e: Exception) {
                execSQL(MyContract.PostTable.SQL_DELETE_ENTRIES)
                execSQL(MyContract.PostTable.SQL_CREATE_TABLE)
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.run {
            execSQL(MyContract.PostTable.SQL_DELETE_ENTRIES)
            onCreate(this)
        }
    }

    public fun readSQL(): MutableList<Post> {
        val ret: MutableList<Post> = ArrayList()
        val db: SQLiteDatabase = this.readableDatabase

        // the column we need
        val projection = arrayOf(
            MyContract.PostTable.COLUMN_NAME_ID,
            MyContract.PostTable.COLUMN_NAME_TITLE,
            MyContract.PostTable.COLUMN_NAME_CONTENT,
            MyContract.PostTable.COLUMN_NAME_AUTHOR,
            MyContract.PostTable.COLUMN_NAME_CREATETIME,
            MyContract.PostTable.COLUMN_NAME_IMAGE,
            MyContract.PostTable.COLUMN_NAME_LOCATION,
            MyContract.PostTable.COLUMN_NAME_ADDRESS,
            MyContract.PostTable.COLUMN_NAME_REPOSTS,
            MyContract.PostTable.COLUMN_NAME_LIKES,
            MyContract.PostTable.COLUMN_NAME_COMMENTS,
        )
        // how to order the result
        val sortOrder: String = "${MyContract.PostTable.COLUMN_NAME_ID} DESC"
        // query and get cursor
        // with can close the resource automatically
        this.readableDatabase.use { d ->
            d.query(
                MyContract.PostTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
            ).use { c ->
                // iterate all data and add them to "data"
                while (c.moveToNext()) {
                    val tempData: Post = Post()
                    tempData.id = c.getInt(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_ID))
                    tempData.title = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_TITLE))
                    tempData.content = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_CONTENT))
                    tempData.author = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_AUTHOR))
                    tempData.createTime = Timestamp(c.getLong(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_CREATETIME)))
                    // tempData.image = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_IMAGE))
                    tempData.location = Location("").apply {
                        this.latitude = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_LOCATION)).split(",")[0].toDouble()
                        this.longitude = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_LOCATION)).split(",")[1].toDouble()
                    }
                    tempData.address = c.getString(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_ADDRESS))
                    tempData.reposts = c.getInt(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_REPOSTS))
                    tempData.likes = c.getInt(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_LIKES))
                    tempData.comments = c.getInt(c.getColumnIndex(MyContract.PostTable.COLUMN_NAME_COMMENTS))
                    ret.add(tempData);
                }
            }
        }
        return ret
    }

    fun valuesFromPost(post: Post): ContentValues {
        val values = ContentValues()
        values.put(MyContract.PostTable.COLUMN_NAME_TITLE, post.title)
        values.put(MyContract.PostTable.COLUMN_NAME_CONTENT, post.content)
        values.put(MyContract.PostTable.COLUMN_NAME_AUTHOR, post.author)
        values.put(MyContract.PostTable.COLUMN_NAME_CREATETIME, post.createTime.time)
        values.put(MyContract.PostTable.COLUMN_NAME_IMAGE, "")
        values.put(MyContract.PostTable.COLUMN_NAME_LOCATION, post.location.latitude.toString() + "," + post.location.longitude.toString())
        values.put(MyContract.PostTable.COLUMN_NAME_ADDRESS, post.address)
        values.put(MyContract.PostTable.COLUMN_NAME_REPOSTS, post.reposts)
        values.put(MyContract.PostTable.COLUMN_NAME_LIKES, post.likes)
        values.put(MyContract.PostTable.COLUMN_NAME_COMMENTS, post.comments)

        return values;
    }

    fun addPost(post: Post) {
        this.writableDatabase.use {
            it.insert(MyContract.PostTable.TABLE_NAME, null, valuesFromPost(post))
        }
    }

    fun modifyPost(newpost: Post, oldpost: Post) {
        this.writableDatabase.use {
            it.update(MyContract.PostTable.TABLE_NAME, valuesFromPost(newpost), "id=?", arrayOf(oldpost.id.toString()))
        }
    }
}