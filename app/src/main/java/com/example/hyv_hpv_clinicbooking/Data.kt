package com.example.hyv_hpv_clinicbooking

import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KeDon
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
        kedon.DonThuoc = "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan = "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc = "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan = "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc = "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan = "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc = "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan = "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        kedon = KeDon()
        kedon.MaDon = 1
        kedon.Ngay = "10/02/2023"
        kedon.Gio = "08 giờ 30 phút"
        kedon.MaBacSi = 1
        kedon.MaBenhNhan = 1
        kedon.ChuanDoan = "Viem Da Co Dia"
        kedon.DonThuoc = "Cetirizine 10mg;20;Viên;uống tối 1 viên sau ăn\nHighttamine 5,0mg+ 25mg;40;Viên;uống ngày 2 lần sáng chiều mỗi lần 1 viên\nKẽm (udoiws dạng kẽm gluconat 10mg);20;Ống;uống sáng 1 ống\nMometason furoat 0,1% (Locgoda 0,1% 15g);2;Tuýp;bôi chỗ ngứa ngáy 2 lần sáng chiều, bôi mỏng trong 7 - 10 ngày"
        kedon.LoiDan = "Đã tư vấn kỹ cho bệnh nhân về đơn thuốc và đơn tư vấn và bệnh nhân đồng ý sử dụng, khám lại sau 3 tuần"
        result.add(kedon)

        return result
    }
}