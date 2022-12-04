package com.example.orderplatform.entity

class Product(
    val name: String,
    val kind: Int,
    val price: Int,
    val word: Int,
    val picture: Int,
    var num: Int
) {
    private var id: Int? = null

    constructor(
        id: Int,
        name: String,
        kind: Int,
        price: Int,
        word: Int,
        picture: Int,
        num: Int
    ) : this(name, kind, price, word, picture, num) {
        this.id = id
    }

    override fun toString(): String {
        return "Product(name='$name', kind=$kind, price=$price, word=$word, picture=$picture, num=$num)"
    }
}