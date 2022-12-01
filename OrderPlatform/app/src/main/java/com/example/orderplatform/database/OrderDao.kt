package com.example.orderplatform.database

import android.content.ContentValues
import com.example.orderplatform.entity.Order

class OrderDao(private val mHelper: MDataBaseHelper) {
    fun insert(order: Order) {
        val values = ContentValues()
        values.put("name", order.name)
        values.put("address", order.address)
        values.put("other", order.other)
        values.put("time", order.time)
        values.put("radio", order.radio)
        values.put("tool", order.tool)
        values.put("touch", order.touch)
        values.put("pay", order.pay)
        val db = mHelper.writableDatabase
        db.insert(MDataBaseHelper.ORDER_TABLE, null, values)
        db.close()
    }

    fun findNotPay(pay: Int = 0): List<Order> {
        val ret = mutableListOf<Order>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.ORDER_TABLE,
            null,
            "pay=?",
            arrayOf(pay.toString()),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val order = Order(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getString(9),
                cursor.getInt(10)
            )
            ret.add(0, order)
        }
        cursor.close()
        db.close()
        return ret
    }

    fun findProductByName(name: String): Order? {
        var order: Order? = null
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.ORDER_TABLE,
            null,
            "name=?",
            arrayOf(name),
            null,
            null,
            null,
            null
        )
        if (cursor.moveToNext()) {
            order = Order(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getString(9),
                cursor.getInt(10)
            )
        }
        cursor.close()
        db.close()
        return order
    }
}