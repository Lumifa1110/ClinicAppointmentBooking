package com.example.hyv_hpv_clinicbooking.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hyv_hpv_clinicbooking.Activity.*
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


/**
 * A simple [Fragment] subclass.
 * Use the [DoctorProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorProfile : Fragment() {
    //Khai báo các biến cần thiết
    var editBTN: Button?= null
    var nameTV: TextView?= null
    var phoneTV: TextView?= null
    var addressTV: TextView?= null
    var emailTV: TextView?= null
    var dangXuatBTN: Button?= null
    var changePasswordBTN: Button?=null
    var chinhsachbm: Button?= null
    var dieukhoandv: Button?= null
    var quydinhsd: Button?= null
    var avatar: CircleImageView?= null

    //Khai báo tài khoản bác sĩ hiện tại
    var accountCurrent: BacSi ?= null
    var maBacSi: String ?= null

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
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false)
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
        addressTV = view.findViewById(R.id.address)
        emailTV = view.findViewById(R.id.email)
        avatar = view.findViewById(R.id.avatar)

        //Các điều khoản và dịch vụ
        chinhsachbm = view.findViewById(R.id.chinhsachbmBTN)
        dieukhoandv = view.findViewById(R.id.dieukhoandvBTN)
        quydinhsd = view.findViewById(R.id.quydinhsdBTN)

        //Gọi database từ bảng Bác Sĩ
        database = Firebase.database.getReference("Users").child("BacSi")

        //maBacSi hiện tại
        maBacSi = "-NUGq3OnCBW17tiSzuyZ";

        //lấy Avatar từ firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.reference;
        var ref: StorageReference = storageReference!!.child("BacSi/" + maBacSi)

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
            .orderByChild("maBacSi")
            .equalTo(maBacSi)

        queryRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    accountCurrent = child.getValue(BacSi::class.java)
                }

                nameTV?.text = accountCurrent?.HoTen
                phoneTV?.text = "Số điện thoại: " + accountCurrent?.SoDienThoai
                addressTV?.text = "Địa chỉ: " + accountCurrent?.DiaChi
                emailTV?.text = "Email: " + accountCurrent?.Email

                editBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), EditProfilePage::class.java)
                    intent.putExtra("loaiTaiKhoan", "BacSi")
                    intent.putExtra("taiKhoan", accountCurrent)
                    startActivity(intent)
                }

                changePasswordBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), ChangePasswordPage::class.java)
                    intent.putExtra("loaiTaiKhoan", "BacSi")
                    startActivity(intent)
                }

                dangXuatBTN?.setOnClickListener {
                    val intent = Intent(requireContext(), LoginPage::class.java)
                    startActivity(intent)
                }

                chinhsachbm?.setOnClickListener {
                    val intent = Intent(requireContext(), ChinhSachBaoMat::class.java)
                    intent.putExtra("loaiTaiKhoan", "BacSi")
                    startActivity(intent)
                }

                dieukhoandv?.setOnClickListener {
                    val intent = Intent(requireContext(), DieuKhoanDichVu::class.java)
                    intent.putExtra("loaiTaiKhoan", "BacSi")
                    startActivity(intent)
                }

                quydinhsd?.setOnClickListener {
                    val intent = Intent(requireContext(), QuyDinhSuDung::class.java)
                    intent.putExtra("loaiTaiKhoan", "BacSi")
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}