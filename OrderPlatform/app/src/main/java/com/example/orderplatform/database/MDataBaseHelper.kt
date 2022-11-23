package com.example.orderplatform.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MDataBaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // 单例模式进行初始化
    // 针对内存泄露问题 https://juejin.cn/post/6987258309648597005
    companion object {
        const val PRODUCT_TABLE = "product"

        const val DB_NAME = "main.db"

        const val DB_VERSION = 1

        var mWDB: SQLiteDatabase? = null

        var mRDB: SQLiteDatabase? = null

        @Volatile
        private var instance: MDataBaseHelper? = null

        fun getInstance(context: Context): MDataBaseHelper {
            val i = instance
            if (i != null) {
                return i
            }
            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = MDataBaseHelper(context.applicationContext)
                    instance = created
                    created
                }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create =
            "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE +
                    "(id integer NOT NULL PRIMARY KEY, " +
                    "name varchar, " +
                    "kind integer, " +
                    "price integer, " +
                    "word integer, " +
                    "picture integer, " +
                    "num INTEGER)"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    // 打开读取链接
    fun openReadLink(): SQLiteDatabase {
        if (mRDB == null || !mRDB!!.isOpen) {
            mRDB = instance!!.readableDatabase
        }
        return mRDB!!
    }

    // 打开写入链接
    fun openWriteLink(): SQLiteDatabase {
        if (mWDB == null || !mWDB!!.isOpen) {
            mWDB = instance!!.writableDatabase
        }
        return mWDB!!
    }

    // 关闭读写链接
    fun closeLink() {
        if (mRDB != null && mRDB!!.isOpen) {
            mRDB!!.close()
            mRDB = null
        }

        if (mWDB != null && mWDB!!.isOpen) {
            mWDB!!.close()
            mWDB = null
        }
    }
}