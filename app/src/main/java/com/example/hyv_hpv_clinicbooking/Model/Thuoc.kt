package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcelable
import android.os.Parcel
import androidx.annotation.RequiresApi
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class Thuoc(
    var tenThuoc: String = "",
) : Parcelable {
    constructor(parcel: Parcel): this (
        parcel.readString()!!,
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tenThuoc)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Thuoc> {
        override fun createFromParcel(parcel: Parcel): Thuoc {
            return Thuoc(parcel)
        }
        override fun newArray(size: Int): Array<Thuoc?> {
            return arrayOfNulls(size)
        }
    }
}
