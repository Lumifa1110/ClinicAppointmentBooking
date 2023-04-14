package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class ThoiGian (
    var MaThoiGian: Int = 0,
    var MaBacSi: Int = 0,
    var MaTrangThai: Int = 0,
    var Ngay: String = "",
    var GioBatDau: String = "",
    var GioKetThuc: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(MaBacSi)
        parcel.writeInt(MaThoiGian)
        parcel.writeInt(MaTrangThai)
        parcel.writeString(Ngay)
        parcel.writeString(GioBatDau)
        parcel.writeString(GioKetThuc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThoiGian> {
        override fun createFromParcel(parcel: Parcel): ThoiGian {
            return ThoiGian(parcel)
        }

        override fun newArray(size: Int): Array<ThoiGian?> {
            return arrayOfNulls(size)
        }
    }
}