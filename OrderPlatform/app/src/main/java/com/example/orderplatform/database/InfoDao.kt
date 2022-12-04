package com.example.orderplatform.database

import android.content.ContentValues
import com.example.orderplatform.entity.InfoEntity


class InfoDao(private val mHelper: MDataBaseHelper) {
    fun insert(info: InfoEntity) {
        val values = ContentValues()
        values.put("address", info.address)
        values.put("radio", info.radio)
        values.put("normal", info.normal)
        val db = mHelper.writableDatabase
        db.insert(MDataBaseHelper.INFO_TABLE, null, values)
        db.close()
    }

    fun findDefault(default: Int = 1): List<InfoEntity> {
        val ret = mutableListOf<InfoEntity>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            MDataBaseHelper.INFO_TABLE,
            null,
            "normal=?",
            arrayOf(default.toString()),
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val info = InfoEntity(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            ret.add(info)
        }
        cursor.close()
        db.close()
        return ret
    }

    fun updatePayById(id: Int, default: Int): Int {
        val values = ContentValues()
        val db = mHelper.writableDatabase
        values.put("normal", default)
        val value = db.update(
            MDataBaseHelper.INFO_TABLE,
            values,
            "id=?",
            arrayOf(id.toString())
        )
        db.close()
        return value
    }
}