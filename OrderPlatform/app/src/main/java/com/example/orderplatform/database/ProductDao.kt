package com.example.orderplatform.database

import android.content.ContentValues
import com.example.orderplatform.entity.Product

class ProductDao private constructor() {

    // 单例模式进行初始化
    companion object {
        @Volatile
        private var instance: ProductDao? = null

        fun getInstance(): ProductDao {
            val i = instance
            if (i != null) {
                return i
            }
            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = ProductDao()
                    instance = created
                    created
                }
            }
        }
    }

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
        MDataBaseHelper.mWDB!!.insert(MDataBaseHelper.TABLE_NAME, null, values)
    }

}