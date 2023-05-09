package com.example.hyv_hpv_clinicbooking.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class ThongBao (
    var MaCuocHen: String = "",
    var MaBenhNhan: String = "",
    var MaBacSi: String = "",
    var NoiDung: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(MaCuocHen)
        parcel.writeString(MaBenhNhan)
        parcel.writeString(MaBacSi)
        parcel.writeString(NoiDung)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThongBao> {
        override fun createFromParcel(parcel: Parcel): ThongBao {
            return ThongBao(parcel)
        }

        override fun newArray(size: Int): Array<ThongBao?> {
            return arrayOfNulls(size)
        }
    }
}