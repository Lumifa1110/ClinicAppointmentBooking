package com.example.hyv_hpv_clinicbooking.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.hyv_hpv_clinicbooking.Activity.ChangePasswordPage
import com.example.hyv_hpv_clinicbooking.Activity.EditProfilePage
import com.example.hyv_hpv_clinicbooking.Activity.LoginPage
import com.example.hyv_hpv_clinicbooking.Activity.UserHomePage
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R



/**
 * A simple [Fragment] subclass.
 * Use the [DoctorProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorProfile : Fragment() {
    var editBTN: Button?= null
    var nameTV: TextView?= null
    var phoneTV: TextView?= null
    var addressTV: TextView?= null
    var emailTV: TextView?= null
    var dangXuatBTN: Button?= null
    var changePasswordBTN: Button?=null

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

        editBTN = view.findViewById(R.id.editProfileBTN)
        nameTV = view.findViewById(R.id.name)
        phoneTV = view.findViewById(R.id.phone)
        addressTV = view.findViewById(R.id.address)
        emailTV = view.findViewById(R.id.email)
        dangXuatBTN = view.findViewById(R.id.dangXuatBTN)
        changePasswordBTN = view.findViewById(R.id.changePasswordBTN)

        var bsExample: BacSi = BacSi(1, "Đa Khoa", 0, 5, "Lê Trần Phi Hùng",
            "0123456789", 0, "227 Nguyễn Văn Cừ, Quận 5, Thành Phố Hồ Chí Minh", 1, "helloworld@gmail.com")

        nameTV?.text = bsExample.HoTen
        phoneTV?.text = "Số điện thoại: " + bsExample.SoDienThoai
        addressTV?.text = "Địa chỉ: " + bsExample.DiaChi
        emailTV?.text = "Email: " + bsExample.Email

        editBTN?.setOnClickListener {
            val intent = Intent(requireContext(), EditProfilePage::class.java)
            intent.putExtra("loaiTaiKhoan", "BacSi")
            intent.putExtra("taiKhoan", bsExample)
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
    }
}