package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable


@Serializable
data class ChuyenKhoa (
    var TenChuyenKhoa: String ?= null
) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString()!!,
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(TenChuyenKhoa)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<ChuyenKhoa> {
        override fun createFromParcel(parcel: Parcel): ChuyenKhoa {
            return ChuyenKhoa(parcel)
        }

        override fun newArray(size: Int): Array<ChuyenKhoa?> {
            return arrayOfNulls(size)
        }
    }
}
