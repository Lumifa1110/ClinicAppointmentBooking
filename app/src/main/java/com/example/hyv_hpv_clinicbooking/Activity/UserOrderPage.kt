package com.example.hyv_hpv_clinicbooking.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.hyv_hpv_clinicbooking.Adapter.ChooseTimeAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.AuthenticationHelper
import com.example.hyv_hpv_clinicbooking.ExpandableHeightGridView
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class UserOrderPage : AppCompatActivity() {
    var authHelper : AuthenticationHelper = AuthenticationHelper()

    //Khai báo các list thông tin
    var morningList = arrayListOf<ThoiGianRanh>()
    var afternoonList = arrayListOf<ThoiGianRanh>()
    var thoiGianRanhList =  arrayListOf<ThoiGianRanh>()
    var cuocHenList = arrayListOf<CuocHen>()
    var dayList =  ArrayList<String>()
    var dayInWeek:HashMap<Int, String> ?= null
    var doctorName:TextView ?= null
    var morningTV: TextView ?= null
    var afternoonTV: TextView ?= null


    //Khai báo nút cần xử lý
    var orderBTN: Button?= null
    var backBTN: ImageButton?= null

    //Khai báo các adapter cần thiết
    var adapter: DayAdapter? = null
    var timeAdapter: ChooseTimeAdapter? = null
    var afternoonAdapter: ChooseTimeAdapter? = null

    //Khai báo calendar
    var calendarView: CalendarView ?= null

    //Khai báo gridview expand
    var morningTimeView: ExpandableHeightGridView? = null
    var afternoonTimeView: ExpandableHeightGridView? = null

    //khai báo key
    var keyList = arrayListOf<String>()
    var keyMorning = arrayListOf<String>()
    var keyAfternoon = arrayListOf<String>()
    var selectedIndex: Int = -1
    var isClickMorning: Boolean = false
    var isClickAfternoon: Boolean = false

    //Khai báo db
    private lateinit var auth  : FirebaseAuth
    private lateinit var thoiGianRanhDB : DatabaseReference
    private lateinit var cuocHenDB : DatabaseReference
    private lateinit var userDB : DatabaseReference

    //Khai báo dữ liệu người dùng
    var doctor: BacSi?= null
    var maBacSi:String ?= null
    var dayChoose:Int = 0
    var dateChoose:String ?= null
    var maTaiKhoan:String?= null
    //Khai báo context
    var ctx: Context?= null

    //Khai báo biến người dùng chọn time rảnh của bác sĩ
    var selectedTime: ThoiGianRanh ?= null
    var selectedKeyTime: String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_page)

        doctorName = findViewById(R.id.doctorName)
        morningTV = findViewById(R.id.morningTV)
        afternoonTV = findViewById(R.id.afternoonTV)

        //Gán lịch
        calendarView = findViewById(R.id.calendar)
        val calendar = Calendar.getInstance() // Set the desired month here
        calendarView?.minDate = calendar.timeInMillis
        calendar.add(Calendar.DATE, 6)
        calendarView?.maxDate = calendar.timeInMillis

        //Gán nút
        orderBTN = findViewById(R.id.orderBTN)
        backBTN = findViewById(R.id.back_button)

        //Gán các gridview
        morningTimeView = findViewById<ExpandableHeightGridView>(R.id.morningGV)
        afternoonTimeView = findViewById<ExpandableHeightGridView>(R.id.afternoonGV)
        morningTimeView?.setExpanded(true)
        afternoonTimeView?.setExpanded(true)

        //Mảng thời gian
        morningList = arrayListOf<ThoiGianRanh>()
        afternoonList = arrayListOf<ThoiGianRanh>()
        thoiGianRanhList = arrayListOf<ThoiGianRanh>()

        // Khai báo Firebase Authentication
        auth = FirebaseAuth.getInstance()

        //Gọi bảng thời gian rảnh của bác sĩ
        thoiGianRanhDB = Firebase.database.getReference("ThoiGianRanh")
        cuocHenDB = Firebase.database.getReference("CuocHen")
        userDB = Firebase.database.getReference("Users")

        //Ma Tai Khoan hien tai
        getBenhNhanKey(auth.currentUser!!)

        doctor = intent.getParcelableExtra<BacSi>("BacSi")
        maBacSi = doctor?.MaBacSi

        doctorName?.setText(doctorName?.text.toString() + doctor?.HoTen.toString())

        //Xu ly nut back
        backBTN?.setOnClickListener {
            val intent = Intent(this, DoctorDetailPage::class.java)
            intent.putExtra("doctor", doctor)
            startActivity(intent)
        }

        ctx = this
        val queryRef: Query = thoiGianRanhDB
            .orderByChild("maBacSi")
            .equalTo(maBacSi!!)

        queryRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                morningList = arrayListOf<ThoiGianRanh>()
                afternoonList = arrayListOf<ThoiGianRanh>()
                thoiGianRanhList =  arrayListOf<ThoiGianRanh>()

                var key:String?= null;
                keyList = arrayListOf<String>()
                for (child in dataSnapshot.children) {
                    thoiGianRanhList.add(child.getValue(ThoiGianRanh::class.java)!!)
                    key = child.key.toString();
                    keyList.add(key);
                    if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 2) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(2));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 3) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(3));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 4) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(4));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 5) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(5));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 6) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(6));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 7) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(7));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 1) {
                        thoiGianRanhDB.child(key).child("ngayThang").setValue(dayInWeek!!.get(1));
                    }
                }

                sortTime()

                //set lịch hiện tại
                val cal = Calendar.getInstance()
                dayChoose = cal.get(Calendar.DAY_OF_WEEK)
                dateChoose = convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1)  + "/" + cal.get(Calendar.YEAR).toString()
                xulyThoiGianHienThi(dayChoose, dateChoose!!)

                //chuyển đổi lịch
                calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                    morningList = arrayListOf<ThoiGianRanh>()
                    afternoonList = arrayListOf<ThoiGianRanh>()

                    var currentDay = LocalDate.of(year , month + 1, dayOfMonth)
                    dayChoose = currentDay.dayOfWeek.value + 1
                    if(dayChoose == 8)
                        dayChoose = 1

                    dateChoose = convertNtoNN(dayOfMonth) + "/" + convertNtoNN(month + 1) + "/" + year.toString()
                    xulyThoiGianHienThi(dayChoose, dateChoose!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        //xu ly thu trong tuan
        dayList =  ArrayList<String>()
        dayInWeek = HashMap<Int, String>()
        for(index in 0..6) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, index)
            val day = cal.get(Calendar.DAY_OF_WEEK)
            if(day == 1) {
                dayList.add("Chủ Nhật" + "\n" + convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1)  + "/" + cal.get(Calendar.YEAR).toString())
            }
            else {
                dayList.add("Thứ " + day.toString() + "\n" + convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1)  + "/" + cal.get(Calendar.YEAR).toString())
            }

            dayInWeek!![day] = convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR).toString()
        }
    }

    fun convertNtoNN(number: Int):String {
        if(number < 10) {
            return "0$number"
        }
        return number.toString()
    }

    fun showDialogConfirm(ngay: String, gio:String, tenBacSi: String) {
        var customDialog:AlertDialog ?=null
        val builder = AlertDialog.Builder(this)
        //Hiển thị dialog để chọn time
        var view_dialog: View =
            this.layoutInflater.inflate(R.layout.dialog_confirm_order, null)

        //khai bao bien
        var _msgContent: TextView = view_dialog.findViewById(R.id.content)
        var _confirmBTN: Button = view_dialog.findViewById(R.id.confirmBTN)
        var _closeBTN:Button = view_dialog.findViewById(R.id.closeBTN)

        //setText
        _msgContent.text = _msgContent.text.toString() + " " + ngay + " " + gio + " của Bác Sĩ " + tenBacSi + "?"

        //Xu ly nut dong
        _closeBTN.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                customDialog?.dismiss()
            }
        })

        //Xu ly nut xac nhan
        _confirmBTN.setOnClickListener (object : View.OnClickListener {
            override fun onClick(view: View) {
                val queryRef: Query = cuocHenDB
                    .orderByChild("maBacSi")
                    .equalTo(maBacSi!!)

                queryRef.addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        cuocHenList = arrayListOf<CuocHen>()

                        var isEmpty = true
                        for (child in dataSnapshot.children) {
                            val cuocHen = child.getValue(CuocHen::class.java)
                            cuocHenList.add(cuocHen!!)
                            if (cuocHen.Ngay == dayInWeek!![dayChoose]) {
                                if (cuocHen.GioBatDau == selectedTime?.gioBatDau.toString() && cuocHen.GioKetThuc == selectedTime?.gioKetThuc) {
                                    if(cuocHen.MaTrangThai == 0 || cuocHen.MaTrangThai == 1){
                                        isEmpty = false
                                        break;
                                    }
                                }
                            }
                        }

                        if(isEmpty) {
                            showDialogAnnouce(
                                "THÀNH CÔNG",
                                "Đã đặt lịch hẹn thành công. Vui lòng để ý kỹ thời gian, cũng như ngày khám bệnh"
                            )

                            //Xu ly voi db
                            val key: String? = cuocHenDB.push().key

                            val newAppointment = CuocHen(
                                MaCuocHen = key.toString(),
                                MaBacSi = maBacSi.toString(),
                                MaBenhNhan = maTaiKhoan.toString(),
                                GioBatDau = selectedTime?.gioBatDau.toString(),
                                GioKetThuc = selectedTime?.gioKetThuc.toString(),
                                MaTrangThai = 0,
                                Ngay = dayInWeek!![dayChoose].toString()
                            )

                            cuocHenList.add(newAppointment)
                            cuocHenDB.child(key!!).setValue(newAppointment)
                        } else {
                            showDialogAnnouce("OOP!! LỖI", "Đặt lịch hẹn không thành công. Đã có người đặt lịch này trước bạn. Hãy đặt lịch khác nhé")
                        }

                        customDialog?.dismiss()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        showDialogAnnouce(
                            "THÀNH CÔNG",
                            "Đã đặt lịch hẹn thành công. Vui lòng để ý kỹ thời gian, cũng như ngày khám bệnh"
                        )

                        //Xu ly voi db
                        val key: String? = cuocHenDB.push().key

                        val newAppointment = CuocHen(
                            MaCuocHen = key.toString(),
                            MaBacSi = maBacSi.toString(),
                            MaBenhNhan = maTaiKhoan.toString(),
                            GioBatDau = selectedTime?.gioBatDau.toString(),
                            GioKetThuc = selectedTime?.gioKetThuc.toString(),
                            MaTrangThai = 0,
                            Ngay = dayInWeek!![dayChoose].toString()
                        )

                        cuocHenList.add(newAppointment)
                        cuocHenDB.child(key!!).setValue(newAppointment)

                        customDialog?.dismiss()
                    }
                })
            }
        })

        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    fun showDialogAnnouce(title: String, content: String) {
        var customDialog:AlertDialog ?=null
        val builder = AlertDialog.Builder(this)
        //Hiển thị dialog để chọn time
        var view_dialog: View =
            this.layoutInflater.inflate(R.layout.dialog_announce_order, null)

        //khai bao bien
        var _msgContent: TextView = view_dialog.findViewById(R.id.content)
        var _msgTitle: TextView = view_dialog.findViewById(R.id.title)
        var _closeBTN:Button = view_dialog.findViewById(R.id.closeBTN)

        //setText
        _msgContent.text = content
        _msgTitle.text = title
        if(title == "OOP!! LỖI")
            _msgTitle.setBackgroundColor(Color.parseColor("#FF3E3E"));
        else {
            _msgTitle.setBackgroundColor(Color.parseColor("#348F6C"));
        }

        //Xu ly nut dong
        _closeBTN.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if(isClickMorning) {
                    timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, thoiGianRanhDB, keyMorning, -1, dateChoose!!, cuocHenList)
                    morningTimeView?.adapter = timeAdapter
                }
                else {
                    afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, thoiGianRanhDB, keyAfternoon, -1, dateChoose!!, cuocHenList)
                    afternoonTimeView?.adapter = afternoonAdapter
                }

                customDialog?.dismiss()

                //Chuyển qua màn hình lịch sử
                if(title == "THÀNH CÔNG") {
                    val intent = Intent(ctx, UserHomePage::class.java)
                    intent.putExtra("fragment", "history_appoinment_list")
                    startActivity(intent)
                }
            }
        })
        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    fun xulyThoiGianHienThi(dayChoose: Int, dateChoose: String) {
        var index:Int = 0
        keyMorning = arrayListOf<String>()
        keyAfternoon = arrayListOf<String>()
        cuocHenList = arrayListOf<CuocHen>()

        for(time in thoiGianRanhList) {
            if(time.thuTrongTuan == dayChoose) {
                var dataStart = arrayListOf<String>()
                dataStart = time.gioBatDau?.split(":") as ArrayList<String>

                //Thêm vào list sáng chiều
                if(dataStart[0].toInt() <= 12) {
                    morningList.add(time)
                    keyMorning.add(keyList[index])
                } else {
                    afternoonList.add(time)
                    keyAfternoon.add(keyList[index])
                }
            }
            index += 1
        }

        checkHienThiGV()

        val queryRef: Query = cuocHenDB
            .orderByChild("maBacSi")
            .equalTo(maBacSi!!)

        queryRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cuocHenList = arrayListOf<CuocHen>()

                for (child in dataSnapshot.children) {
                    cuocHenList.add(child.getValue(CuocHen::class.java)!!)
                }

                chooseOneInTwoGridView()
                //Xử lí nút order
                orderBTN?.setOnClickListener {
                    if(selectedTime == null) {
                        showDialogAnnouce("OOP!! LỖI", "Vui lòng chọn khung giờ để đặt lịch khám")
                    }
                    else {
                        showDialogConfirm(
                            dayInWeek!![dayChoose].toString(),
                            selectedTime?.gioBatDau.toString() + " - "
                                    + selectedTime?.gioKetThuc.toString(),
                            doctor!!.HoTen
                        )
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
                chooseOneInTwoGridView()
                //Xử lí nút order
                orderBTN?.setOnClickListener {
                    if(selectedTime == null) {
                        showDialogAnnouce("OOP!! LỖI", "Vui lòng chọn khung giờ để đặt lịch khám")
                    }
                    else {
                        showDialogConfirm(
                            dayInWeek!![dayChoose].toString(),
                            selectedTime?.gioBatDau.toString() + " - "
                                    + selectedTime?.gioKetThuc.toString(),
                            doctor!!.HoTen
                        )
                    }
                }
            }
        })

    }

    //Xử lý để click 1 trong các ô ở 2 grid view
    fun chooseOneInTwoGridView() {
        selectedIndex = -1
        isClickMorning = false
        isClickAfternoon = false

        timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, thoiGianRanhDB, keyMorning, selectedIndex, dateChoose!!, cuocHenList)
        morningTimeView?.adapter = timeAdapter
        morningTimeView?.setOnItemClickListener { adapterView, view, position, id ->
            if(isClickAfternoon) {
                afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, thoiGianRanhDB, keyAfternoon, -1, dateChoose!!, cuocHenList)
                afternoonTimeView?.adapter = afternoonAdapter
                isClickAfternoon = false
            }

            selectedIndex = position
            timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, thoiGianRanhDB, keyMorning, selectedIndex, dateChoose!!, cuocHenList)
            morningTimeView?.adapter = timeAdapter
            isClickMorning = true

            selectedTime = morningList[selectedIndex]
            selectedKeyTime = keyMorning[selectedIndex]
        }

        afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, thoiGianRanhDB, keyAfternoon, selectedIndex, dateChoose!!, cuocHenList)
        afternoonTimeView?.adapter = afternoonAdapter
        afternoonTimeView?.setOnItemClickListener { adapterView, view, position, id ->
            if(isClickMorning) {
                timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, thoiGianRanhDB, keyMorning, -1, dateChoose!!, cuocHenList)
                morningTimeView?.adapter = timeAdapter
                isClickMorning = false
            }

            selectedIndex = position
            afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, thoiGianRanhDB, keyAfternoon, selectedIndex, dateChoose!!, cuocHenList)
            afternoonTimeView?.adapter = afternoonAdapter
            isClickAfternoon = true

            selectedTime = afternoonList[selectedIndex]
            selectedKeyTime = keyAfternoon[selectedIndex]
        }
    }

    fun checkHienThiGV() {
        if(morningList.isEmpty()) {
            morningTV?.visibility = View.INVISIBLE
            morningTimeView?.visibility = View.INVISIBLE
        } else {
            morningTV?.visibility = View.VISIBLE
            morningTimeView?.visibility = View.VISIBLE
        }

        if(afternoonList.isEmpty()) {
            afternoonTV?.visibility = View.INVISIBLE
            afternoonTimeView?.visibility = View.INVISIBLE
        } else {
            afternoonTV?.visibility = View.VISIBLE
            afternoonTimeView?.visibility = View.VISIBLE
        }
    }

    fun sortTime() {
        for(i in 0 until thoiGianRanhList.size - 1) {
            for (j in (i + 1) until thoiGianRanhList.size) {
                var timeFirst = thoiGianRanhList[i].gioBatDau?.split(":")
                var timeSecond = thoiGianRanhList[j].gioBatDau?.split(":")
                if(timeFirst!![0].toInt() * 60 + timeFirst[1].toInt() >= timeSecond!![0].toInt() * 60 + timeSecond[1].toInt()) {
                    var temp = thoiGianRanhList[i]
                    thoiGianRanhList[i] = thoiGianRanhList[j]
                    thoiGianRanhList[j] = temp

                    var keyTemp = keyList[i]
                    keyList[i] = keyList[j]
                    keyList[j] = keyTemp
                }
            }
        }
    }

    private fun getBenhNhanKey(user: FirebaseUser) {
        val query = userDB.child("BenhNhan")
            .orderByChild("email")
            .equalTo(user.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { it ->
                    maTaiKhoan = it.key!!
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}