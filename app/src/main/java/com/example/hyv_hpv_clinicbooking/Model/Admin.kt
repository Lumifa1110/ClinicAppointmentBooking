import android.os.Parcel
import android.os.Parcelable
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class Admin (
    var Email: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BenhNhan> {
        override fun createFromParcel(parcel: Parcel): BenhNhan {
            return BenhNhan(parcel)
        }

        override fun newArray(size: Int): Array<BenhNhan?> {
            return arrayOfNulls(size)
        }
    }
}