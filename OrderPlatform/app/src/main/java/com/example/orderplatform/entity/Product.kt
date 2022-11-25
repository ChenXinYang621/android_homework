package com.example.orderplatform.entity

class Product(
    val id: Int,
    val name: String,
    val kind: Int,
    val price: Int,
    val word: Int,
    val picture: Int,
    var num: Int
) {
    override fun toString(): String {
        return "Product(id=$id, name='$name', kind=$kind, price=$price, word=$word, picture=$picture, num=$num)"
    }
}