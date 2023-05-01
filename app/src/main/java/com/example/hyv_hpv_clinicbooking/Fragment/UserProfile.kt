package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Activity.*
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfile : Fragment() {
    //Khai báo các biến cần thiết
    var editBTN: Button?= null
    var nameTV: TextView?= null
    var phoneTV: TextView?= null
    var emailTV: TextView?= null
    var dangXuatBTN: Button?= null
    var changePasswordBTN: Button?=null
    var chinhsachbm: Button?= null
    var dieukhoandv: Button?= null
    var quydinhsd: Button?= null
    var avatar: CircleImageView?= null

    //Khai báo tài khoản bác sĩ hiện tại
    var accountCurrent: BenhNhan ?= null
    var maBenhNhan: String ?= null

    //khai báo database
    private lateinit var database : DatabaseReference

    //Khai báo firebase storage để lấy ảnh
    lateinit var storage: FirebaseStorage
    var storageReference: StorageReference? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Gán các nút xử lý
        editBTN = view.findViewById(R.id.editProfileBTN)
        dangXuatBTN = view.findViewById(R.id.dangXuatBTN)
        changePasswordBTN = view.findViewById(R.id.changePasswordBTN)

        //Gán các textview hiển thị
        nameTV = view.findViewById(R.id.name)
        phoneTV = view.findViewById(R.id.phone)
        emailTV = view.findViewById(R.id.email)
        avatar = view.findViewById(R.id.avatar)

        //Các điều khoản và dịch vụ
        chinhsachbm = view.findViewById(R.id.chinhsachbmBTN)
        dieukhoandv = view.findViewById(R.id.dieukhoandvBTN)
        quydinhsd = view.findViewById(R.id.quydinhsdBTN)

        //Gọi database từ bảng Bác Sĩ
        database = Firebase.database.getReference("Users").child("BenhNhan")

        //Mã bệnh nhân hiện tại
        maBenhNhan = "-NUGq3NRrFTwUKz84O6P";

        //lấy Avatar từ firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.reference;
        var ref: StorageReference = storageReference!!.child("BenhNhan/" + maBenhNhan)

        ref.downloadUrl
            .addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(avatar);
                Log.d("Test", " Success!")
            }
            .addOnFailureListener {
                Log.d("Test", " Failed!")
            }

        //lấy dữ liệu của bác sĩ hiện tại
        val queryRef: Query = database
            .orderByChild("maBenhNhan")
            .equalTo(maBenhNhan)

        queryRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    accountCurrent = child.getValue(BenhNhan::class.java)
                }

                nameTV?.text = accountCurrent?.HoTen
                phoneTV?.text = "Số điện thoại: " + accountCurrent?.SoDienThoai
                emailTV?.text = "Email: " + accountCurrent?.Email

                editBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), EditProfilePage::class.java)
                    intent.putExtra("loaiTaiKhoan", "BenhNhan")
                    intent.putExtra("taiKhoan", accountCurrent)
                    startActivity(intent)
                }

                changePasswordBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), ChangePasswordPage::class.java)
                    intent.putExtra("loaiTaiKhoan", "BenhNhan")
                    startActivity(intent)
                }

                dangXuatBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), LoginPage::class.java)
                    startActivity(intent)
                }

                chinhsachbm?.setOnClickListener {
                    val intent = Intent(requireContext(), ChinhSachBaoMat::class.java)
                    intent.putExtra("loaiTaiKhoan", "BenhNhan")
                    startActivity(intent)
                }

                dieukhoandv?.setOnClickListener {
                    val intent = Intent(requireContext(), DieuKhoanDichVu::class.java)
                    intent.putExtra("loaiTaiKhoan", "BenhNhan")
                    startActivity(intent)
                }

                quydinhsd?.setOnClickListener {
                    val intent = Intent(requireContext(), QuyDinhSuDung::class.java)
                    intent.putExtra("loaiTaiKhoan", "BenhNhan")
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}