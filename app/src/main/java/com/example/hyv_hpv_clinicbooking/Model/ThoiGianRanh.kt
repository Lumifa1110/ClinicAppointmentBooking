package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class ThoiGianRanh (
    var maKhungGio: Long = 0,
    var ngayThang: String ?= null,
    var maBacSy: Long = 0,
    var trangThai: Int = 0,
): Parcelable {
    constructor() : this(0, null, 0, 0)

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(maKhungGio)
        parcel.writeString(ngayThang)
        parcel.writeLong(maBacSy)
        parcel.writeInt(trangThai)
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