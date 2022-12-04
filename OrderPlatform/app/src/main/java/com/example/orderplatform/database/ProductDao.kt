package com.example.orderplatform.database

import android.content.ContentValues
import com.example.orderplatform.entity.Product

// 对 Product 表进行数据库操作
class ProductDao(private val mHelper: MDataBaseHelper) {

    fun insert(product: Product) {
        val values = ContentValues()
        values.put("name", product.name)
        values.put("kind", product.kind)
        values.put("price", product.price)
        values.put("word", product.word)
        values.put("picture", product.picture)
        values.put("num", product.num)
        // nullColumnHack 防止插入时出现 empty body 的情况。可以通过指定对应列插入 null
        // 因为 values 已经不为空，所以不需要指定，设定为 null 即可
        val db = mHelper.writableDatabase
        db.insert(MDataBaseHelper.PRODUCT_TABLE, null, values)
        db.close()
    }

    fun findAll(): List<Product> {
        val ret = mutableListOf<Product>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.PRODUCT_TABLE,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        /**
         *
        table: 表名称
        columns: 列名称数组
        selection: 条件语句，相当于 where
        selectionArgs: 条件字句，参数数组
        groupBy: 分组列
        having: 分组条件
        orderBy: 排序列
        limit: 分页查询限制
        Cursor: 返回值，相当于结果集 ResultSet
         */
        while (cursor.moveToNext()) {
            val product = Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            )
            ret.add(product)
        }
        cursor.close()
        db.close()
        return ret
    }

    fun findProductByCatalogue(kind: Int): List<Product> {
        val ret = mutableListOf<Product>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.PRODUCT_TABLE,
            null,
            "kind=?",
            arrayOf(kind.toString()),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val product = Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            )
            ret.add(product)
        }
        cursor.close()
        db.close()
        return ret
    }

    // 找出大于某个值的数据，默认值为 0
    fun findProductOverNum(num: Int = 0): List<Product> {
        val ret = mutableListOf<Product>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.PRODUCT_TABLE,
            null,
            "num>?",
            arrayOf(num.toString()),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val product = Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            )
            ret.add(product)
        }
        cursor.close()
        db.close()
        return ret
    }

    fun searchProductByName(name: String): List<Product> {
        val ret = mutableListOf<Product>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.PRODUCT_TABLE,
            null,
            "name like '%$name%'",
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val product = Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            )
            ret.add(product)
        }
        cursor.close()
        db.close()
        return ret
    }

    fun findProductByName(name: String): Product? {
        var product: Product? = null
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.PRODUCT_TABLE,
            null,
            "name=?",
            arrayOf(name),
            null,
            null,
            null,
            null
        )
        if (cursor.moveToNext()) {
            product = Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            )
        }
        cursor.close()
        db.close()
        return product
    }

    fun updateNum(name: String, num: Int): Int {
        val values = ContentValues()
        val db = mHelper.writableDatabase
        values.put("num", num)
        val value = db.update(
            MDataBaseHelper.PRODUCT_TABLE,
            values,
            "name=?",
            arrayOf(name)
        )
        db.close()
        return value
    }

}