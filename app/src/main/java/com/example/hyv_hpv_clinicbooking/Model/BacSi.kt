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
    var DiaChi: String = "",
    var Image:Int ?= null,
    var Email: String = "",
    var Mota: String = "",
    var MaAdmin: Int = 0,
    var PassWord: String = "",
    var GioiTinh: String = "",
    var BiKhoa: Boolean = true
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
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
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
        parcel.writeString(Mota)
        parcel.writeInt(MaAdmin!!)
        parcel.writeString(PassWord)
        parcel.writeString(GioiTinh)
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
