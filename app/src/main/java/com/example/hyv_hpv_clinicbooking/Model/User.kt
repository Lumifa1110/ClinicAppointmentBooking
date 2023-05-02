package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

class User (
    var name : String = "",
    var email : String = "",
    var phone : String = "",
    var password : String = "",
    var role : String = ""
) {
//    constructor(name: String, email: String, phone: String, password: String) : this() {
//        this.name = name
//        this.email = email
//        this.phone = phone
//        this.password = password
//    }
}

//@Serializable
//data class User(
//    var MaNguoiDung: String = "",
//    var SoLanKham: Int = 0,
//    var HoTen: String = "",
//    var SoDienThoai: String = "",
//    var Role: String = "",
//    var Email: String = "",
//    var GioiTinh: String = "",
//    var PassWord: String = "",
//    var MaAdmin: Int = 0,
//    var TenChuyenKhoa: String = "",
//    var SLBenhNhan: Int = 0,
//    var SoNamTrongNghe: Int = 0,
//    var SoCuocHen: Int = 0,
//    var DiaChi: String = "",
//    var Image:Int = R.drawable.avatar,
//    var Mota: String = "",
//    var BiKhoa: Boolean = true
//): Parcelable {
//    @RequiresApi(Build.VERSION_CODES.Q)
//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!,
//        parcel.readInt()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readInt()!!,
//        parcel.readString()!!,
//        parcel.readInt()!!,
//        parcel.readInt()!!,
//        parcel.readInt()!!,
//        parcel.readString()!!,
//        parcel.readInt()!!,
//        parcel.readString()!!,
//        parcel.readBoolean()!!,
//    )
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(MaNguoiDung)
//        parcel.writeInt(SoLanKham)
//        parcel.writeString(HoTen)
//        parcel.writeString(SoDienThoai)
//        parcel.writeString(Role)
//        parcel.writeString(Email)
//        parcel.writeString(GioiTinh)
//        parcel.writeString(PassWord)
//        parcel.writeInt(MaAdmin)
//        parcel.writeString(TenChuyenKhoa)
//        parcel.writeInt(SLBenhNhan)
//        parcel.writeInt(SoNamTrongNghe)
//        parcel.writeInt(SoCuocHen)
//        parcel.writeString(DiaChi)
//        parcel.writeInt(Image)
//        parcel.writeString(Mota)
//        parcel.writeBoolean(BiKhoa)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<User> {
//        @RequiresApi(Build.VERSION_CODES.Q)
//        override fun createFromParcel(parcel: Parcel): User {
//            return User(parcel)
//        }
//
//        override fun newArray(size: Int): Array<User?> {
//            return arrayOfNulls(size)
//        }
//    }
//}