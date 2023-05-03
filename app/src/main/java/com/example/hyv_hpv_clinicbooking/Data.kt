package com.example.hyv_hpv_clinicbooking

import BenhNhan
import android.content.ContentValues.TAG
import android.util.Log
import com.example.hyv_hpv_clinicbooking.Model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Data {
    private lateinit var databaseAppoinment : DatabaseReference
    fun generateDoctorData(): ArrayList<BacSi> {
        var result = ArrayList<BacSi>()

        var doctor: BacSi = BacSi()
        doctor.HoTen = "Lưu Minh Phát"
        doctor.Email = "phat@gmail.com"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.PassWord = "1234"
        doctor.SoNamTrongNghe = 13
        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.TenChuyenKhoa = "Nhi Khoa"
        doctor.HoTen = "Pham Tran Minh Ngoc 1"
        doctor.Email = "ngoc1@gmail.com"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.PassWord = "1234"
        doctor.SoNamTrongNghe = 11
        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.HoTen = "Pham Tran Minh Ngoc 2"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.Email = "ngoc2@gmail.com"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.PassWord = "1234"
        doctor.SoNamTrongNghe = 12
        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5
        result.add(doctor)

        doctor = BacSi()
        doctor.HoTen = "Ngô Anh Vũ"
        doctor.TenChuyenKhoa = "Da liễu"
        doctor.Email = "vu@gmail.com"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.PassWord = "1234"
        doctor.SoNamTrongNghe = 10
        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.HoTen = "Nguyễn Đình Văn"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.Email = "phat@gmail.com"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.PassWord = "1234"
        doctor.SoNamTrongNghe = 15
        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5

        result.add(doctor)
        return result
    }

//    fun generateKeDonData(): ArrayList<KeDon> {
//        var result = ArrayList<KeDon>()
//        val currentDate = Date()
//        val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDate)
//        val currentTime = Calendar.getInstance().time
//
//        // Format the time as a string in "HH:mm" format
//        val timeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentTime)
//        var kedon: KeDon = KeDon()
//        kedon.MaDon = 1
//        kedon.Ngay = dateString
//        kedon.Gio = "08 giờ 30 phút"
//        kedon.MaBacSi = 1
//        kedon.MaBenhNhan = 1
//        kedon.ChuanDoan = "Viem Da Co Dia"
//        kedon.DonThuoc =
//            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
//        kedon.LoiDan =
//            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
//        result.add(kedon)
//
//        kedon = KeDon()
//        kedon.MaDon = 1
//        kedon.Ngay = dateString
//        kedon.Gio = "08 giờ 30 phút"
//        kedon.MaBacSi = 2
//        kedon.MaBenhNhan = 1
//        kedon.ChuanDoan = "Viem Da Co Dia"
//        kedon.DonThuoc =
//            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
//        kedon.LoiDan =
//            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
//        result.add(kedon)
//
//        kedon = KeDon()
//        kedon.MaDon = 1
//        kedon.Ngay = dateString
//        kedon.Gio = "08 giờ 30 phút"
//        kedon.MaBacSi = 3
//        kedon.MaBenhNhan = 1
//        kedon.ChuanDoan = "Viem Da Co Dia"
//        kedon.DonThuoc =
//            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
//        kedon.LoiDan =
//            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
//        result.add(kedon)
//
//        kedon = KeDon()
//        kedon.MaDon = 1
//        kedon.Ngay = dateString
//        kedon.Gio = "08 giờ 30 phút"
//        kedon.MaBacSi = 4
//        kedon.MaBenhNhan = 1
//        kedon.ChuanDoan = "Viem Da Co Dia"
//        kedon.DonThuoc =
//            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
//        kedon.LoiDan =
//            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
//        result.add(kedon)
//
//        kedon = KeDon()
//        kedon.MaDon = 1
//        kedon.Ngay = dateString
//        kedon.Gio = "08 giờ 30 phút"
//        kedon.MaBacSi = 5
//        kedon.MaBenhNhan = 1
//        kedon.ChuanDoan = "Viem Da Co Dia"
//        kedon.DonThuoc =
//            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
//        kedon.LoiDan =
//            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
//        result.add(kedon)
//
//        return result
//    }


//    fun generateScheduleData(): ArrayList<LichHenKham> {
//        var result = ArrayList<LichHenKham>()
//
//        var schedule: LichHenKham = LichHenKham()
//        schedule.MaLichHen = 1
//        schedule.MaBacSi = 1
//        schedule.MaBenhNhan = 1
//        schedule.MaThoiGian = 1
//        schedule.MaTrangThai = 2
//        result.add(schedule)
//
//        schedule = LichHenKham()
//        schedule.MaLichHen = 2
//        schedule.MaBacSi = 1
//        schedule.MaBenhNhan = 2
//        schedule.MaThoiGian = 2
//        schedule.MaTrangThai = 1
//        result.add(schedule)
//
//        schedule = LichHenKham()
//        schedule.MaLichHen = 3
//        schedule.MaBacSi = 1
//        schedule.MaBenhNhan = 3
//        schedule.MaThoiGian = 3
//        schedule.MaTrangThai = 0
//        result.add(schedule)
//
//        schedule = LichHenKham()
//        schedule.MaLichHen = 4
//        schedule.MaBacSi = 1
//        schedule.MaBenhNhan = 4
//        schedule.MaThoiGian = 4
//        schedule.MaTrangThai = 0
//        result.add(schedule)
//
//        schedule = LichHenKham()
//        schedule.MaLichHen = 5
//        schedule.MaBacSi = 1
//        schedule.MaBenhNhan = 5
//        schedule.MaThoiGian = 5
//        schedule.MaTrangThai = 0
//        result.add(schedule)
//
//
//        return result
//    }

    fun generateAppoinmentData(): ArrayList<CuocHen> {
        var result = ArrayList<CuocHen>()
        val currentDate = Date()
        val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDate)

        val currentTime = Calendar.getInstance().time

// Format the time as a string in "HH:mm" format
        val timeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentTime)

// Convert the time string to a Date object
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(timeString)

// Add 30 minutes to the date
        val calendar = Calendar.getInstance()
        calendar.time = time
        calendar.add(Calendar.MINUTE, 30)
        val newTime = calendar.time

// Convert the new time to a string in "HH:mm" format
        val newTimeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(newTime)

        var schedule: CuocHen = CuocHen()
        schedule.Ngay = dateString
        schedule.GioBatDau = timeString
        schedule.GioKetThuc = newTimeString
        schedule.MaTrangThai = 2
        schedule.ChuanDoan = "Viem Da Co Dia"
        schedule.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm gluconat 10mg;20;Ống;uống sáng 1 ống\nMometason furoat 0,1%;2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        schedule.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(schedule)

        schedule = CuocHen()
        schedule.Ngay = dateString
        schedule.GioBatDau = timeString
        schedule.GioKetThuc = newTimeString
        schedule.MaTrangThai = 1
        result.add(schedule)

        schedule = CuocHen()
        schedule.Ngay = dateString
        schedule.GioBatDau = timeString
        schedule.GioKetThuc = newTimeString
        schedule.MaTrangThai = 0
        result.add(schedule)

        schedule = CuocHen()
        schedule.Ngay = dateString
        schedule.GioBatDau = timeString
        schedule.GioKetThuc = newTimeString
        schedule.MaTrangThai = 0
        result.add(schedule)

        schedule = CuocHen()
        schedule.Ngay = dateString
        schedule.GioBatDau = timeString
        schedule.GioKetThuc = newTimeString
        schedule.MaTrangThai = 0
        result.add(schedule)
        return result
    }


    fun generateTimeData(): ArrayList<ThoiGian> {
        var result = ArrayList<ThoiGian>()

        var thoiGian: ThoiGian = ThoiGian()
        thoiGian.MaThoiGian = 1
        thoiGian.MaBacSi = 1
        thoiGian.MaTrangThai = 0

        result.add(thoiGian)

        thoiGian = ThoiGian()
        thoiGian.MaThoiGian = 2
        thoiGian.MaBacSi = 1
        thoiGian.MaTrangThai = 0
        thoiGian.Ngay = "13/04/2023"
        thoiGian.GioBatDau = "8h30"
        thoiGian.GioKetThuc = "9h00"
        result.add(thoiGian)

        thoiGian = ThoiGian()
        thoiGian.MaThoiGian = 3
        thoiGian.MaBacSi = 1
        thoiGian.MaTrangThai = 0
        thoiGian.Ngay = "13/04/2023"
        thoiGian.GioBatDau = "9h00"
        thoiGian.GioKetThuc = "9h30"
        result.add(thoiGian)

        thoiGian = ThoiGian()
        thoiGian.MaThoiGian = 4
        thoiGian.MaBacSi = 1
        thoiGian.MaTrangThai = 0
        thoiGian.Ngay = "13/04/2023"
        thoiGian.GioBatDau = "9h30"
        thoiGian.GioKetThuc = "10h00"
        result.add(thoiGian)

        thoiGian = ThoiGian()
        thoiGian.MaThoiGian = 5
        thoiGian.MaBacSi = 1
        thoiGian.MaTrangThai = 0
        thoiGian.Ngay = "13/04/2023"
        thoiGian.GioBatDau = "10h00"
        thoiGian.GioKetThuc = "10h30"
        result.add(thoiGian)
        return result
    }

    fun generatePatientData(): ArrayList<BenhNhan> {
        var result = ArrayList<BenhNhan>()
        var benhNhan: BenhNhan = BenhNhan()
        benhNhan.HoTen = "Nguyễn Đình Văn 1"
        benhNhan.SoDienThoai = "0123456789"
        benhNhan.Email = "van1@gmail.com"
        benhNhan.PassWord = "1234"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.HoTen = "Nguyễn Đình Văn 2"
        benhNhan.SoDienThoai = "0123456789"
        benhNhan.Email = "van2@gmail.com"
        benhNhan.PassWord = "1234"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.HoTen = "Nguyễn Đình Văn 3"
        benhNhan.SoDienThoai = "0123456789"
        benhNhan.Email = "van3@gmail.com"
        benhNhan.PassWord = "1234"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.HoTen = "Nguyễn Đình Văn 4"
        benhNhan.SoDienThoai = "0123456789"
        benhNhan.Email = "van4@gmail.com"
        benhNhan.PassWord = "1234"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.Email = "van5@gmail.com"
        benhNhan.HoTen = "Nguyễn Đình Văn 5"
        benhNhan.SoDienThoai = "0123456789"
        benhNhan.PassWord = "1234"
        result.add(benhNhan)
        return result
    }

//    fun generateUserData(): ArrayList<User> {
//        var result = ArrayList<User>()
//        var benhNhan: User = User()
//        benhNhan.HoTen = "Nguyễn Đình Văn 1"
//        benhNhan.SoDienThoai = "0123456789"
//        benhNhan.Email = "van1@gmail.com"
//        benhNhan.GioiTinh = "Nam"
//        benhNhan.PassWord = "1234"
//        result.add(benhNhan)
//
//        benhNhan = User()
//        benhNhan.Role = "patient"
//        benhNhan.HoTen = "Nguyễn Đình Văn 2"
//        benhNhan.SoDienThoai = "0123456789"
//        benhNhan.Email = "van2@gmail.com"
//        benhNhan.GioiTinh = "Nư"
//        benhNhan.PassWord = "1234"
//        result.add(benhNhan)
//
//        benhNhan = User()
//        benhNhan.Role = "patient"
//        benhNhan.HoTen = "Nguyễn Đình Văn 3"
//        benhNhan.SoDienThoai = "0123456789"
//        benhNhan.Email = "van3@gmail.com"
//        benhNhan.GioiTinh = "Nam"
//        benhNhan.PassWord = "1234"
//        result.add(benhNhan)
//
//        benhNhan = User()
//        benhNhan.Role = "patient"
//        benhNhan.HoTen = "Nguyễn Đình Văn 4"
//        benhNhan.SoDienThoai = "0123456789"
//        benhNhan.Email = "van4@gmail.com"
//        benhNhan.GioiTinh = "Nữ"
//        benhNhan.PassWord = "1234"
//        result.add(benhNhan)
//
//        benhNhan = User()
//        benhNhan.Role = "patient"
//        benhNhan.Email = "van5@gmail.com"
//        benhNhan.HoTen = "Nguyễn Đình Văn 5"
//        benhNhan.SoDienThoai = "0123456789"
//        benhNhan.GioiTinh = "Nam"
//        benhNhan.PassWord = "1234"
//        result.add(benhNhan)
//
//        var doctor = User()
//        benhNhan.Role = "doctor"
//        doctor.HoTen = "Lưu Minh Phát"
//        doctor.GioiTinh = "Nam"
//        doctor.Email = "phat@gmail.com"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.PassWord = "1234"
//        doctor.SoNamTrongNghe = 13
//        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
//        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor.SoDienThoai = "0123456789"
//        doctor.Image = R.drawable.doctor1
//        result.add(doctor)
//
//        doctor = User()
//        benhNhan.Role = "doctor"
//        doctor.TenChuyenKhoa = "Nhi Khoa"
//        doctor.HoTen = "Pham Tran Minh Ngoc 1"
//        doctor.GioiTinh = "Nữ"
//        doctor.Email = "ngoc1@gmail.com"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.PassWord = "1234"
//        doctor.SoNamTrongNghe = 11
//        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
//        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor.SoDienThoai = "0123456789"
//        doctor.Image = R.drawable.doctor1
//        result.add(doctor)
//
//        doctor = User()
//        benhNhan.Role = "doctor"
//        doctor.HoTen = "Pham Tran Minh Ngoc 2"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.GioiTinh = "Nữ"
//        doctor.Email = "ngoc2@gmail.com"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.PassWord = "1234"
//        doctor.SoNamTrongNghe = 12
//        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
//        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor.SoDienThoai = "0123456789"
//        doctor.Image = R.drawable.doctor5
//        result.add(doctor)
//
//        doctor = User()
//        benhNhan.Role = "doctor"
//        doctor.HoTen = "Ngô Anh Vũ"
//        doctor.TenChuyenKhoa = "Da liễu"
//        doctor.GioiTinh = "Nam"
//        doctor.Email = "vu@gmail.com"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.PassWord = "1234"
//        doctor.SoNamTrongNghe = 10
//        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
//        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor.SoDienThoai = "0123456789"
//        doctor.Image = R.drawable.doctor1
//        result.add(doctor)
//
//        doctor = User()
//        benhNhan.Role = "doctor"
//        doctor.HoTen = "Nguyễn Đình Văn"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.GioiTinh = "Nam"
//        doctor.Email = "phat@gmail.com"
//        doctor.TenChuyenKhoa = "Nha Sĩ"
//        doctor.PassWord = "1234"
//        doctor.SoNamTrongNghe = 15
//        doctor.Mota = "Nha sĩ là chuyên gia trong việc chăm sóc răng miệng và hệ thống răng. Họ có thể thực hiện các dịch vụ từ chẩn đoán các vấn đề răng miệng đến các phương pháp điều trị như sửa chữa răng, tẩy trắng răng. Nha sĩ cần có trình độ chuyên môn cao và kỹ năng tay nghề tốt để thực hiện các thủ tục liên quan đến răng miệng"
//        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
//        doctor.SoDienThoai = "0123456789"
//        doctor.Image = R.drawable.doctor5
//
//        result.add(doctor)
//        return result
//    }

}