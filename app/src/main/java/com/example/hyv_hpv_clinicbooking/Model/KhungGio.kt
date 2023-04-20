package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable

class KhungGio (
    var maKhungGio: Long = 0,
    var gioBatDau: String ?= null,
    var gioKetThuc: String ?= null
): Parcelable {
    constructor() : this(0, "", "")

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(maKhungGio)
        parcel.writeString(gioBatDau)
        parcel.writeString(gioKetThuc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KhungGio> {
        override fun createFromParcel(parcel: Parcel): KhungGio {
            return KhungGio(parcel)
        }

        override fun newArray(size: Int): Array<KhungGio?> {
            return arrayOfNulls(size)
        }
    }
}