package com.example.orderplatform.utils

import android.os.Parcel
import android.os.Parcelable

// 通过自定义 Parcelable 解决 Java kotlin 类型不匹配的问题
class BaseParcelable() : Parcelable {
    lateinit var value: Any

    constructor(value: Any) : this() {
        this.value = value
    }

    constructor(parcel: Parcel) : this() {
        this.value = Any()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {}

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<BaseParcelable> {

        override fun createFromParcel(parcel: Parcel): BaseParcelable {
            return BaseParcelable(parcel)
        }

        override fun newArray(size: Int): Array<BaseParcelable?> {
            return arrayOfNulls(size)
        }
    }
}