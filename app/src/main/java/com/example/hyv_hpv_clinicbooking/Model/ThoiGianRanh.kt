package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class ThoiGianRanh (
    var thuTrongTuan: Int = 0,
    var ngayThang: String ?= null,
    var gioBatDau:String ?= null,
    var gioKetThuc:String ?= null,
    var maBacSi: String ?= null,
    var trangThai: Int = 0, //0 - Rãnh, 1 - Bận
    var duocDat: Int = 0, //0 - Chưa bị đặt, 1 - Đã có người đặt
): Parcelable {
    constructor() : this(0, null, null, null, null, 0, 0)

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(thuTrongTuan)
        parcel.writeString(ngayThang)
        parcel.writeString(gioBatDau)
        parcel.writeString(gioKetThuc)
        parcel.writeString(maBacSi)
        parcel.writeInt(trangThai)
        parcel.writeInt(duocDat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThoiGian> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFromParcel(parcel: Parcel): ThoiGian {
            return ThoiGian(parcel)
        }

        override fun newArray(size: Int): Array<ThoiGian?> {
            return arrayOfNulls(size)
        }
    }
}