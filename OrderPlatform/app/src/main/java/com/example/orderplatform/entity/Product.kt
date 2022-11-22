package com.example.orderplatform.entity

class Product {

    constructor(
        name: String, kind: Int, price: Int, word: Int, picture: Int, num: Int
    ) {
        this.name = name
        this.kind = kind
        this.price = price
        this.word = word
        this.picture = picture
        this.num = num
    }

    constructor(
        id: Int, name: String, kind: Int, price: Int, word: Int, picture: Int, num: Int
    ) : this(name, kind, price, word, picture, num) {
        this.id = id
    }

    var id: Int? = null
    var name: String? = null
    var kind: Int? = null
    var price: Int? = null
    var word: Int? = null
    var picture: Int? = null
    var num: Int? = null
}