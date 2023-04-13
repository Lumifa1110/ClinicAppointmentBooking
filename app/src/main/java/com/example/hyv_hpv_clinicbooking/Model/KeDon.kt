package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

//class KeDon {
//    var MaDon: Int ?= 0
//    var Ngay: String ?= ""
//    var Gio: String ?= ""
//    var MaBacSi: Int ?= 0
//    var MaBenhNhan: Int ?= 0
//    var ChuanDoan: String ?= ""
//    var DonThuoc: String ?= ""
//    var LoiDan: String ?= ""
//}

@Serializable
data class KeDon (
    var MaDon: Int = 0,
    var Ngay: String = "",
    var Gio: String = "",
    var MaBacSi: Int = 0,
    var MaBenhNhan: Int = 0,
    var ChuanDoan: String = "",
    var DonThuoc: String = "",
    var LoiDan: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(MaDon)
        parcel.writeString(Ngay)
        parcel.writeString(Gio)
        parcel.writeInt(MaBacSi)
        parcel.writeInt(MaBenhNhan)
        parcel.writeString(ChuanDoan)
        parcel.writeString(DonThuoc)
        parcel.writeString(LoiDan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KeDon> {
        override fun createFromParcel(parcel: Parcel): KeDon {
            return KeDon(parcel)
        }

        override fun newArray(size: Int): Array<KeDon?> {
            return arrayOfNulls(size)
        }
    }
}
