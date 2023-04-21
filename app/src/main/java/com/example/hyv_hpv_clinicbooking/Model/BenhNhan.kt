import android.os.Parcel
import android.os.Parcelable
import com.example.hyv_hpv_clinicbooking.R
import kotlinx.serialization.Serializable

@Serializable
data class BenhNhan (
    var MaBenhNhan: Int = 0,
    var SoLanKham: Int = 0,
    var HoTen: String = "",
    var SoDienThoai: String = "",
    var Email: String = "",
    var GioiTinh: String = "",
    var PassWord: String = "",
    var MaAdmin: Int = 0,
    var Image: Int = R.drawable.avatar,
    var BiKhoa: Boolean = true
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(MaBenhNhan)
        parcel.writeInt(SoLanKham)
        parcel.writeString(HoTen)
        parcel.writeString(SoDienThoai)
        parcel.writeString(Email)
        parcel.writeString(GioiTinh)
        parcel.writeString(PassWord)
        parcel.writeInt(MaAdmin)
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