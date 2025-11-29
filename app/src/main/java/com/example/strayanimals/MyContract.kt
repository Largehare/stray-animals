package com.example.strayanimals

class MyContract {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "user.db"
        const val TEXT_TYPE = " TEXT"
        const val INTEGER_TYPE = "INTEGER"
        const val COMMA_SEP = " ,"
    }

    class UserTable {
        companion object {
            const val TABLE_NAME = "user"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_NAME_CREATETIME = "createtime"
            const val COLUMN_NAME_PWD = "password"

            val SQL_CREATE_TABLE = "CREATE TABLE " +
                    "$TABLE_NAME (" +
                    "$COLUMN_NAME_USERNAME ${TEXT_TYPE} PRIMARY KEY${COMMA_SEP}" +
                    "$COLUMN_NAME_CREATETIME ${TEXT_TYPE}${COMMA_SEP} " +
                    "$COLUMN_NAME_PWD ${TEXT_TYPE}" +
                    ")"
            const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        }
    }

    class PostTable {
        companion object {
            const val TABLE_NAME = "post"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_AUTHOR = "author"
            const val COLUMN_NAME_CREATETIME = "createtime"
            const val COLUMN_NAME_CONTENT = "content"
            const val COLUMN_NAME_IMAGE = "image"
            const val COLUMN_NAME_LOCATION = "location"
            const val COLUMN_NAME_ADDRESS = "address"
            const val COLUMN_NAME_REPOSTS = "reposts"
            const val COLUMN_NAME_LIKES = "likes"
            const val COLUMN_NAME_COMMENTS = "comments"

            val SQL_CREATE_TABLE = "CREATE TABLE " +
                    "$TABLE_NAME (" +
                    "$COLUMN_NAME_ID ${INTEGER_TYPE} PRIMARY KEY AUTOINCREMENT${COMMA_SEP}" +
                    "$COLUMN_NAME_TITLE ${TEXT_TYPE}${COMMA_SEP} " +
                    "$COLUMN_NAME_AUTHOR ${TEXT_TYPE}${COMMA_SEP} " +
                    "$COLUMN_NAME_CREATETIME ${INTEGER_TYPE}${COMMA_SEP} " +
                    "$COLUMN_NAME_CONTENT ${INTEGER_TYPE}${COMMA_SEP} " +
                    "$COLUMN_NAME_IMAGE ${TEXT_TYPE}${COMMA_SEP}" +
                    "$COLUMN_NAME_LOCATION ${TEXT_TYPE}${COMMA_SEP}" +
                    "$COLUMN_NAME_ADDRESS ${TEXT_TYPE}${COMMA_SEP}" +
                    "$COLUMN_NAME_REPOSTS ${INTEGER_TYPE}${COMMA_SEP}" +
                    "$COLUMN_NAME_LIKES ${INTEGER_TYPE}${COMMA_SEP}" +
                    "$COLUMN_NAME_COMMENTS ${INTEGER_TYPE}" +
                    ")"
            const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        }
    }
}