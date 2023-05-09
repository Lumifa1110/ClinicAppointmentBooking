package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class BacSi (
    var MaBacSi: String = "",
    var TenChuyenKhoa: String = "",
    var SLBenhNhan: Int = 0,
    var SoNamTrongNghe: Int = 0,
    var HoTen: String = "",
    var SoDienThoai: String = "",
    var SoCuocHen: Int = 0,
    var DiaChi: String = "",
    var Image:Int = R.drawable.avatar,
    var Email: String = "",
    var Mota: String = "",
    var PassWord: String = "",
    var BiKhoa: Boolean = true,
    var KhungGioLamViec:String = "",
    var DaDuyet: Boolean = false, //false la chua duoc duyet - true la da duyet
    var Cccd: String = ""
    ): Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
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
        parcel.readString()!!,
        parcel.readBoolean(),
        parcel.readString()!!,
        parcel.readBoolean(),
        parcel.readString()!!
        )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(MaBacSi)
        parcel.writeString(TenChuyenKhoa)
        parcel.writeInt(SLBenhNhan)
        parcel.writeInt(SoNamTrongNghe)
        parcel.writeString(HoTen)
        parcel.writeString(SoDienThoai)
        parcel.writeInt(SoCuocHen)
        parcel.writeString(DiaChi)
        parcel.writeInt(Image)
        parcel.writeString(Email)
        parcel.writeString(Mota)
        parcel.writeString(PassWord)
        parcel.writeBoolean(BiKhoa)
        parcel.writeString(KhungGioLamViec)
        parcel.writeBoolean(DaDuyet)
        parcel.writeString(Cccd)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BacSi> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): BacSi {
            return BacSi(parcel)
        }

        override fun newArray(size: Int): Array<BacSi?> {
            return arrayOfNulls(size)
        }
    }
}
