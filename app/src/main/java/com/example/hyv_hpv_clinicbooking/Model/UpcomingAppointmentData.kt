package com.example.hyv_hpv_clinicbooking.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingAppointmentData (
    var HoTen: String = "",
    var TenChuyenKhoa: String = "",
    var Ngay: String = "",
    var ThoiGian: String = "",
): Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(HoTen)
        parcel.writeString(TenChuyenKhoa)
        parcel.writeString(Ngay)
        parcel.writeString(ThoiGian)
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
