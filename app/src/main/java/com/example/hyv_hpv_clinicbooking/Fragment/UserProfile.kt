package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
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
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R

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
    var editBTN: Button?= null
    var nameTV: TextView?= null
    var phoneTV: TextView?= null
    var emailTV: TextView?= null
    var dangXuatBTN: Button?= null
    var changePasswordBTN: Button?=null

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

        editBTN = view.findViewById(R.id.editProfileBTN)
        nameTV = view.findViewById(R.id.name)
        phoneTV = view.findViewById(R.id.phone)
        emailTV = view.findViewById(R.id.email)
        dangXuatBTN = view.findViewById(R.id.dangXuatBTN)
        changePasswordBTN = view.findViewById(R.id.changePasswordBTN)

        var bnExample: BenhNhan = BenhNhan()
        bnExample.HoTen = "Hello World"
        bnExample.SoDienThoai = "0123456789"
        bnExample.Email = "hihi@gmail.com"

        nameTV?.text = bnExample.HoTen
        phoneTV?.text = "Số điện thoại: " + bnExample.SoDienThoai
        emailTV?.text = "Email: " + bnExample.Email

        editBTN?.setOnClickListener {
            val intent = Intent(requireContext(), EditProfilePage::class.java)
            intent.putExtra("loaiTaiKhoan", "BenhNhan")
            intent.putExtra("taiKhoan", bnExample)
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
    }
}