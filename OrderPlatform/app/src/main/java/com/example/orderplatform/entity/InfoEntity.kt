package com.example.orderplatform.entity

class InfoEntity(val address: String, val radio: Int, val normal: Int) {
    var id: Int = 1

    constructor(id: Int, address: String, radio: Int, normal: Int) : this(address, radio, normal) {
        this.id = id
    }

    override fun toString(): String {
        return "Info(address='$address', radio=$radio, normal=$normal)"
    }
}