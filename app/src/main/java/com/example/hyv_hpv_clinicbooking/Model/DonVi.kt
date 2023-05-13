package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcelable
import android.os.Parcel
import androidx.annotation.RequiresApi
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class DonVi(
    var tenDonVi: String = "",
) : Parcelable {
    constructor(parcel: Parcel): this (
        parcel.readString()!!,
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tenDonVi)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<DonVi> {
        override fun createFromParcel(parcel: Parcel): DonVi {
            return DonVi(parcel)
        }
        override fun newArray(size: Int): Array<DonVi?> {
            return arrayOfNulls(size)
        }
    }
}
