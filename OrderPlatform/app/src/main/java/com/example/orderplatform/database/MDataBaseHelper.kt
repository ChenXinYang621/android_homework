package com.example.orderplatform.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MDataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // 单例模式进行初始化
    // 针对内存泄露问题 https://juejin.cn/post/6987258309648597005
    // 但是因为涉及多页面切换，所以放弃单例模式初始化
    companion object {
        const val PRODUCT_TABLE = "product"

        const val ORDER_TABLE = "buy"

        const val INFO_TABLE = "info"

        const val DB_NAME = "main.db"

        const val DB_VERSION = 1

        const val createProduct =
            "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE +
                    "(id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar, " +
                    "kind integer, " +
                    "price integer, " +
                    "word integer, " +
                    "picture integer, " +
                    "num INTEGER)"

        const val createORDER =
            "CREATE TABLE IF NOT EXISTS " + ORDER_TABLE +
                    "(id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar, " +
                    "address varchar, " +
                    "other varchar, " +
                    "time varchar, " +
                    "radio integer, " +
                    "tool integer, " +
                    "touch integer, " +
                    "pay integer, " +
                    "feedback varchar, " +
                    "star double)"

        const val createInfo =
            "CREATE TABLE IF NOT EXISTS " + INFO_TABLE +
                    "(id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "address varchar, " +
                    "radio integer, " +
                    "normal integer)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createProduct)
        db?.execSQL(createORDER)
        db?.execSQL(createInfo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}