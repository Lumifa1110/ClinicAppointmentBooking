import android.os.Parcel
import android.os.Parcelable
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class BenhNhan (
    var MaBenhNhan: String = "",
    var SoLanKham: Int = 0,
    var HoTen: String = "",
    var SoDienThoai: String = "",
    var Email: String = "",
    var Image: Int = R.drawable.avatar,
    var BiKhoa: Boolean = false
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(MaBenhNhan)
        parcel.writeInt(SoLanKham)
        parcel.writeString(HoTen)
        parcel.writeString(SoDienThoai)
        parcel.writeString(Email)
        parcel.writeInt(Image)
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