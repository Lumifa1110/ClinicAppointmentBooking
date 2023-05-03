package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class CuocHen (
    var MaCuocHen: String = "",
    var MaBenhNhan: String = "",
    var MaBacSi: String = "",
    var Ngay: String = "",
    var GioBatDau: String = "",
    var GioKetThuc: String = "",
    var MaTrangThai: Int = 0,
    var ChuanDoan: String = "",
    var DonThuoc: String = "",
    var LoiDan: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(MaCuocHen)
        parcel.writeString(MaBenhNhan)
        parcel.writeString(MaBacSi)
        parcel.writeString(Ngay)
        parcel.writeString(GioBatDau)
        parcel.writeString(GioKetThuc)
        parcel.writeInt(MaTrangThai)
        parcel.writeString(ChuanDoan)
        parcel.writeString(DonThuoc)
        parcel.writeString(LoiDan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CuocHen> {
        override fun createFromParcel(parcel: Parcel): CuocHen {
            return CuocHen(parcel)
        }

        override fun newArray(size: Int): Array<CuocHen?> {
            return arrayOfNulls(size)
        }
    }
}