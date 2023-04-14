package com.example.hyv_hpv_clinicbooking

import BenhNhan
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
import com.example.hyv_hpv_clinicbooking.Model.LichHenKham
import com.example.hyv_hpv_clinicbooking.Model.ThoiGian
import java.time.LocalDateTime

class Data {
    fun generateDoctorData(): ArrayList<BacSi> {
        var result = ArrayList<BacSi>()

        var doctor: BacSi = BacSi()
        doctor.MaBacSi = 1
        doctor.HoTen = "Lưu Minh Phát"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.MaBacSi = 2
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.HoTen = "Pham Tran Minh Ngoc 1"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.MaBacSi = 3
        doctor.HoTen = "Pham Tran Minh Ngoc 2"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5
        result.add(doctor)

        doctor = BacSi()
        doctor.MaBacSi = 4
        doctor.HoTen = "Nga Anh VU"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor1
        result.add(doctor)

        doctor = BacSi()
        doctor.MaBacSi = 5
        doctor.HoTen = "Nguyen Dinh Van"
        doctor.TenChuyenKhoa = "Nha Sĩ"
        doctor.DiaChi = "34-36 đường Đinh Tiên Hoàng, phường Đakao, quận 1, TP.HCM"
        doctor.SoDienThoai = "0123456789"
        doctor.Image = R.drawable.doctor5

        result.add(doctor)
        return result
    }

    fun generateKeDonData(): ArrayList<KeDon> {
        var result = ArrayList<KeDon>()

        var kedon: KeDon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc =
            "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan =
            "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        return result
    }

    fun generateScheduleData(): ArrayList<LichHenKham> {
        var result = ArrayList<LichHenKham>()

        var schedule: LichHenKham = LichHenKham()
        schedule.MaLichHen = 1
        schedule.MaBacSi = 1
        schedule.MaBenhNhan = 1
        schedule.MaThoiGian = 1
        schedule.MaTrangThai = 0
        result.add(schedule)

        schedule = LichHenKham()
        schedule.MaLichHen = 2
        schedule.MaBacSi = 1
        schedule.MaBenhNhan = 2
        schedule.MaThoiGian = 2
        schedule.MaTrangThai = 1
        result.add(schedule)

        schedule = LichHenKham()
        schedule.MaLichHen = 3
        schedule.MaBacSi = 1
        schedule.MaBenhNhan = 3
        schedule.MaThoiGian = 3
        schedule.MaTrangThai = 2
        result.add(schedule)

        schedule = LichHenKham()
        schedule.MaLichHen = 4
        schedule.MaBacSi = 1
        schedule.MaBenhNhan = 4
        schedule.MaThoiGian = 4
        schedule.MaTrangThai = 0
        result.add(schedule)

        schedule = LichHenKham()
        schedule.MaLichHen = 5
        schedule.MaBacSi = 1
        schedule.MaBenhNhan = 5
        schedule.MaThoiGian = 5
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
        thoiGian.Ngay = "13/04/2023"
        thoiGian.GioBatDau = "8h00"
        thoiGian.GioKetThuc = "8h30"
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
        benhNhan.MaBenhNhan = 1
        benhNhan.HoTen = "Nguyen Dinh Van 1"
        benhNhan.SoDienThoai = "0123456789"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.MaBenhNhan = 2
        benhNhan.HoTen = "Nguyen Dinh Van 2"
        benhNhan.SoDienThoai = "0123456789"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.MaBenhNhan = 3
        benhNhan.HoTen = "Nguyen Dinh Van 3"
        benhNhan.SoDienThoai = "0123456789"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.MaBenhNhan = 4
        benhNhan.HoTen = "Nguyen Dinh Van 4"
        benhNhan.SoDienThoai = "0123456789"
        result.add(benhNhan)

        benhNhan = BenhNhan()
        benhNhan.MaBenhNhan = 5
        benhNhan.HoTen = "Nguyen Dinh Van 5"
        benhNhan.SoDienThoai = "0123456789"
        result.add(benhNhan)
        return result

    }
}