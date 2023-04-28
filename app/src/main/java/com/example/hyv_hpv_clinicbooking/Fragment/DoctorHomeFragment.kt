package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorHomeFragment : Fragment() {
    private lateinit var database : DatabaseReference
/*
    private var scheduleList = ArrayList<LichHenKham>()
    private var timeList = ArrayList<ThoiGian>()
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = Data()
        var kedon = data.generateKeDonData()
        database = Firebase.database.reference
//        for(i in kedon) {
//            val userId = database.push().key
//            writeNewPer(userId!!, i)
//        }
    }

    private fun writeNewPatient(patientId: String, patient: BenhNhan) {
        val user = BenhNhan(MaBenhNhan = patient.MaBenhNhan, SoLanKham = patient.SoLanKham, HoTen = patient.HoTen, SoDienThoai = patient.SoDienThoai, Email = patient.Email, GioiTinh = patient.GioiTinh, PassWord = patient.PassWord, MaAdmin = patient.MaAdmin, Image = patient.Image)
        database.child("BenhNhan").child(patientId).setValue(user)
        Toast.makeText(requireContext()
            , "Register successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewDoctor(doctorId: String, doctor: BacSi) {
        val user = BacSi(MaBacSi = doctor.MaBacSi,
            TenChuyenKhoa=  doctor.TenChuyenKhoa,
            HoTen = doctor.HoTen,
            SoDienThoai = doctor.SoDienThoai,
            Email = doctor.Email,
            GioiTinh = doctor.GioiTinh,
            PassWord = doctor.PassWord,
            SoNamTrongNghe = doctor.SoNamTrongNghe,
            MaAdmin = doctor.MaAdmin,
            Image = doctor.Image,
            Mota = doctor.Mota)
        database.child("BacSi").child(doctorId).setValue(user)
        Toast.makeText(requireContext()
            , "Register successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewSchedule(scheduleId: String, schedule: LichHenKham) {
        val schedule = LichHenKham(
            MaLichHen=  schedule.MaLichHen,
            MaBacSi = schedule.MaBacSi,
            MaBenhNhan = schedule.MaBenhNhan,
            MaThoiGian = schedule.MaThoiGian,
            MaTrangThai= schedule.MaTrangThai,
        )

        database.child("LichHenKham").child(scheduleId).setValue(schedule)
        Toast.makeText(requireContext()
            , "Add Schedule successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewTime(timeId: String, time: ThoiGian) {
        val time = ThoiGian(
            MaThoiGian=  time.MaThoiGian,
            MaBacSi = time.MaBacSi,
            Ngay = time.Ngay,
            GioBatDau = time.GioBatDau,
            GioKetThuc = time.GioKetThuc,
            MaTrangThai= time.MaTrangThai,
        )

        database.child("ThoiGian").child(timeId).setValue(time)
        Toast.makeText(requireContext()
            , "Add Schedule successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewPer(timeId: String, kedon: KeDon) {
        val kedon1 = KeDon(
            Ngay = kedon.Ngay ,
            Gio = kedon.Gio ,
            MaBacSi = kedon.MaBacSi,
            MaBenhNhan = kedon.MaBenhNhan,
            ChuanDoan = kedon.ChuanDoan ,
            DonThuoc = kedon.DonThuoc ,
            LoiDan = kedon.LoiDan ,
        )

        database.child("KeDon").child(timeId).setValue(kedon1)
        Toast.makeText(requireContext()
            , "Add Schedule successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }
}