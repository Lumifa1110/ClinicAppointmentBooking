package com.example.hyv_hpv_clinicbooking.Fragment

import BenhNhan
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hyv_hpv_clinicbooking.Data
import com.example.hyv_hpv_clinicbooking.Model.*
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
        var benhnhan = data.generatePatientData()
        database = Firebase.database.reference
//        var id :String ?= ""
//        var id2 :String ?= ""
//        var count = 0
//        for(i in benhnhan) {
//
//            val userId = database.push().key
//            if(count == 0) {
//                id = userId
//            }
//            count++
//            i.MaBenhNhan = userId!!
//            writeNewPatient(userId!!, i)
//        }
//        count = 0
//        var bacsi = data.generateDoctorData()
//        database = Firebase.database.reference
//        for(i in bacsi) {
//            val userId = database.push().key
//            if(count == 0) {
//                id2 = userId
//            }
//            i.MaBacSi = userId!!
////            writeNewDoctor(userId!!, i)
//        }

        var cuochen = data.generateAppoinmentData()
        database = Firebase.database.reference
        for(i in cuochen) {
            val userId = database.push().key
            i.MaCuocHen = userId!!
            i.MaBacSi = "-NUGq3OXu1_goPLqieYI"
            i.MaBenhNhan = "-NUGq3NRrFTwUKz84O6P"
            writeNewAppoinment(userId!!, i)
        }

        var donViList = arrayOf("Viên", "Lọ", "Ống", "Chai", "Tuýp", "Vỉ")
        val tenThuocList = arrayOf("Paracetamol", "Ibuprofen", "Aspirin", "Loperamide", "Diazepam", "Ciprofloxacin", "Metronidazole", "Doxycycline",
            "Azithromycin", "Omeprazole", "Simvastatin", "Losartan", "Amoxicillin", "Clarithromycin", "Erythromycin",
            "Ranitidine", "Hydrocortisone", "Prednisone", "Phenoxymethylpenicillin", "Aciclovir", "Furosemide",
            "Metformin", "Gliclazide", "Insulin", "Salbutamol", "Montelukast", "Cetirizine", "Loratadine",
            "Fluticasone", "Budesonide"
        )

        val chuyenKhoaList = arrayOf(
            "Nội khoa",
            "Ngoại khoa",
            "Nhi khoa",
            "Sản khoa",
            "Da liễu",
            "Tai mũi họng",
            "Răng hàm mặt",
            "Thần kinh học",
            "Tiết niệu - sinh dục",
            "Y học cộng đồng"
        )

        for(i in donViList) {
            val userId = database.push().key
            writeNewDonVi(userId!!, i)
        }

        for(i in tenThuocList) {
            val userId = database.push().key
            writeNewThuoc(userId!!, i)
        }

        for(i in chuyenKhoaList) {
            val userId = database.push().key
            writeNewChuyenKhoa(userId!!, i)
        }
    }

    private fun writeNewAppoinment(prescriptionId: String, cuochen: CuocHen) {
        val cuochen1 = CuocHen(
            MaCuocHen = cuochen.MaCuocHen,
            MaBacSi = cuochen.MaBacSi,
            MaBenhNhan = cuochen.MaBenhNhan,
            Ngay = cuochen.Ngay,
            GioBatDau = cuochen.GioBatDau,
            GioKetThuc = cuochen.GioKetThuc,
            MaTrangThai = cuochen.MaTrangThai,
            ChuanDoan = cuochen.ChuanDoan ,
            DonThuoc = cuochen.DonThuoc ,
            LoiDan = cuochen.LoiDan ,
        )

        database.child("CuocHen").child(prescriptionId).setValue(cuochen1)
        Toast.makeText(requireContext()
            , "Add Schedule successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewPatient(patientId: String, patient: BenhNhan) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        val user = BenhNhan(MaBenhNhan = patient.MaBenhNhan, SoLanKham = patient.SoLanKham, HoTen = patient.HoTen, SoDienThoai = patient.SoDienThoai, Email = patient.Email, PassWord = patient.PassWord, Image = patient.Image)
        databaseRef.child("BenhNhan").child(patientId).setValue(user)
        Toast.makeText(requireContext()
            , "Register successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewDoctor(doctorId: String, doctor: BacSi) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        val user = BacSi(
            MaBacSi = doctor.MaBacSi,
            TenChuyenKhoa =  doctor.TenChuyenKhoa,
            HoTen = doctor.HoTen,
            SoDienThoai = doctor.SoDienThoai,
            Email = doctor.Email,
            PassWord = doctor.PassWord,
            SoNamTrongNghe = doctor.SoNamTrongNghe,
            Image = doctor.Image,
            Mota = doctor.Mota)
        databaseRef.child("BacSi").child(doctorId).setValue(user)
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

    private fun writeNewDonVi(scheduleId: String, name: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach")
        val newNote = hashMapOf(
            "tenDonVi" to name
        )

        databaseRef.child("DonVi").child(scheduleId).setValue(newNote)
        Toast.makeText(requireContext()
            , "Add DonVi successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewChuyenKhoa(scheduleId: String, name: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach")
        val newNote = hashMapOf(
            "tenChuyenKhoa" to name
        )

        databaseRef.child("ChuyenKhoa").child(scheduleId).setValue(newNote)
        Toast.makeText(requireContext()
            , "Add ChuyenKhoa successfully"
            , Toast.LENGTH_SHORT)
            .show()
    }

    private fun writeNewThuoc(scheduleId: String, name: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("DanhSach")
        val newNote = hashMapOf(
            "tenThuoc" to name
        )

        databaseRef.child("Thuoc").child(scheduleId).setValue(newNote)
        Toast.makeText(requireContext()
            , "Add Thuoc successfully"
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