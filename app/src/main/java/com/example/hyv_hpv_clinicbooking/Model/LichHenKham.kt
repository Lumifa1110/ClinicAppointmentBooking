package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class LichHenKham (
    var MaLichHen: Int = 0,
    var MaBenhNhan: String = "",
    var MaBacSi: String = "",
    var MaThoiGian: Int = 0,
    var MaTrangThai: Int = 0,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(MaLichHen)
        parcel.writeString(MaBenhNhan)
        parcel.writeString(MaBacSi)
        parcel.writeInt(MaThoiGian)
        parcel.writeInt(MaTrangThai)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LichHenKham> {
        override fun createFromParcel(parcel: Parcel): LichHenKham {
            return LichHenKham(parcel)
        }

        override fun newArray(size: Int): Array<LichHenKham?> {
            return arrayOfNulls(size)
        }
    }
}