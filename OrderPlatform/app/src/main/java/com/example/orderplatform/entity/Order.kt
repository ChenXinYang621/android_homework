package com.example.orderplatform.entity

class Order(
    val name: String, val address: String,
    val other: String, val time: String, val radio: Int,
    val tool: Int, val touch: Int, var pay: Int
) {
    var id: Int? = null
    var feedback: String? = null
    var star: Double? = null

    constructor(
        id: Int, name: String, address: String,
        other: String, time: String, radio: Int,
        tool: Int, touch: Int, pay: Int, feedback: String?,
        star: Double
    ) : this(
        name, address, other, time,
        radio, tool, touch, pay
    ) {
        this.id = id
        this.feedback = feedback
        this.star = star
    }

    override fun toString(): String {
        return "Order(name='$name', address='$address', " +
                "other='$other', time='$time', " +
                "radio=$radio, tool=$tool, touch=$touch, " +
                "pay=$pay, feedback=$feedback, star=$star)"
    }
}