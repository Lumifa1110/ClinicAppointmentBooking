package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

//class BacSi {
//    var MaBacSi: Int = 0
//    var TenChuyenKhoa: String = ""
//    var SLBenhNhan: Int = 0
//    var SoNamTrongNghe: Int = 0
//    var HoTen: String = ""
//    var SoDienThoai: String = ""
//    var SoCuocHen: Int = 0
//    var DiaChi: String =""
//    var Image:Int ?= null
//}

@Serializable
data class BacSi (
    var MaBacSi: Int = 0,
    var TenChuyenKhoa: String = "",
    var SLBenhNhan: Int = 0,
    var SoNamTrongNghe: Int = 0,
    var HoTen: String = "",
    var SoDienThoai: String = "",
    var SoCuocHen: Int = 0,
    var DiaChi: String ="",
    var Image:Int ?= null,
    var Email: String = ""
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(MaBacSi)
        parcel.writeString(TenChuyenKhoa)
        parcel.writeInt(SLBenhNhan)
        parcel.writeInt(SoNamTrongNghe)
        parcel.writeString(HoTen)
        parcel.writeString(SoDienThoai)
        parcel.writeInt(SoCuocHen)
        parcel.writeString(DiaChi)
        parcel.writeInt(Image!!)
        parcel.writeString(Email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BacSi> {
        override fun createFromParcel(parcel: Parcel): BacSi {
            return BacSi(parcel)
        }

        override fun newArray(size: Int): Array<BacSi?> {
            return arrayOfNulls(size)
        }
    }
}
