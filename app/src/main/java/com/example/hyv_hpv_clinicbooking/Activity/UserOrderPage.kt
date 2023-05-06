package com.example.hyv_hpv_clinicbooking.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Adapter.ChooseTimeAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.TimeAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.CuocHen
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import java.util.*

class UserOrderPage : AppCompatActivity() {
    //Khai báo các list thông tin
    var morningList = arrayListOf<ThoiGianRanh>()
    var afternoonList = arrayListOf<ThoiGianRanh>()
    var thoiGianRanhList =  arrayListOf<ThoiGianRanh>()
    var dayList =  ArrayList<String>()
    var dayInWeek:HashMap<Int, String> ?= null

    //Khai báo nút cần xử lý
    var orderBTN: Button?= null
    var backBTN: ImageButton?= null

    //Khai báo các adapter cần thiết
    var adapter: DayAdapter? = null
    var timeAdapter: ChooseTimeAdapter? = null
    var afternoonAdapter: ChooseTimeAdapter? = null

    //Khai báo các recycleview, gridview
    var customListView: RecyclerView? = null
    var morningTimeView: GridView? = null
    var afternoonTimeView: GridView? = null

    //khai báo key
    var keyList = arrayListOf<String>()
    var keyMorning = arrayListOf<String>()
    var keyAfternoon = arrayListOf<String>()
    var selectedIndex: Int = -1
    var isClickMorning: Boolean = false
    var isClickAfternoon: Boolean = false

    //Khai báo db
    private lateinit var database : DatabaseReference

    //Khai báo dữ liệu người dùng
    var maBacSi:String ?= null
    var dayChoose:Int = 0
    var maTaiKhoan:String?= null
    //Khai báo context
    var ctx: Context?= null

    //Khai báo biến người dùng chọn time rảnh của bác sĩ
    var selectedTime: ThoiGianRanh ?= null
    var selectedKeyTime: String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_page)
        //Gán nút
        orderBTN = findViewById(R.id.orderBTN)
        backBTN = findViewById(R.id.back_button)

        //Gán các biến recycleview, gridview
        customListView = findViewById(R.id.dayRV)
        morningTimeView = findViewById<GridView>(R.id.morningGV)
        afternoonTimeView = findViewById<GridView>(R.id.afternoonGV)

        //Mảng thời gian
        morningList = arrayListOf<ThoiGianRanh>()
        afternoonList = arrayListOf<ThoiGianRanh>()
        thoiGianRanhList = arrayListOf<ThoiGianRanh>()

        //Ma Tai Khoan hien tai
        maTaiKhoan = "-NUGq3NRrFTwUKz84O6P"
        //Gọi bảng thời gian rảnh của bác sĩ
        database = Firebase.database.getReference("ThoiGianRanh")

        var doctor = intent.getParcelableExtra<BacSi>("BacSi")
        maBacSi = doctor?.MaBacSi

        //Xu ly nut back
        backBTN?.setOnClickListener {
            val intent = Intent(this, DoctorDetailPage::class.java)
            intent.putExtra("doctor", doctor)
            startActivity(intent)
        }

        ctx = this
        val queryRef: Query = database
            .orderByChild("maBacSi")
            .equalTo(maBacSi!!)

        queryRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
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
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(2));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 3) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(3));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 4) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(4));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 5) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(5));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 6) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(6));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 7) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(7));
                    }
                    else if(thoiGianRanhList[thoiGianRanhList.size - 1].thuTrongTuan == 1) {
                        database.child(key).child("ngayThang").setValue(dayInWeek!!.get(1));
                    }
                }

                adapter = DayAdapter(dayList)
                customListView!!.adapter = adapter
                val layoutManager =
                    LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                customListView!!.layoutManager = layoutManager
                adapter?.notifyDataSetChanged()

                var current = dayList[0].split("\n")
                var day = current[0]
                dayChoose = 0
                when (day) {
                    "Thứ 2" -> dayChoose = 2
                    "Thứ 3" -> dayChoose = 3
                    "Thứ 4" -> dayChoose = 4
                    "Thứ 5" -> dayChoose = 5
                    "Thứ 6" -> dayChoose = 6
                    "Thứ 7" -> dayChoose = 7
                    "Chủ Nhật" -> dayChoose = 1
                }
                xulyThoiGianHienThi(dayChoose)

                adapter?.onItemClick = {date, vitri ->
                    morningList = arrayListOf<ThoiGianRanh>()
                    afternoonList = arrayListOf<ThoiGianRanh>()
                    var data = date.split("\n").toTypedArray()
                    var day = data[0]

                    dayChoose = 0
                    when (day) {
                        "Thứ 2" -> dayChoose = 2
                        "Thứ 3" -> dayChoose = 3
                        "Thứ 4" -> dayChoose = 4
                        "Thứ 5" -> dayChoose = 5
                        "Thứ 6" -> dayChoose = 6
                        "Thứ 7" -> dayChoose = 7
                        "Chủ Nhật" -> dayChoose = 1
                    }

                    xulyThoiGianHienThi(dayChoose)
                    //Xử lí nút order
                    orderBTN?.setOnClickListener {
                        showDialogConfirm(dayInWeek!![dayChoose].toString(), selectedTime?.gioBatDau.toString() + " - "
                        + selectedTime?.gioKetThuc.toString(), doctor!!.HoTen)
                    }
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
                dayList.add("Chủ Nhật" + "\n" + convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1))
            }
            else {
                dayList.add("Thứ " + day.toString() + "\n" + convertNtoNN(cal.get(Calendar.DATE)) + "/" + convertNtoNN(cal.get(Calendar.MONTH) + 1))
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
                var checkTimeEmpty = Firebase.database.getReference("ThoiGianRanh").child(selectedKeyTime!!).child("duocDat")

                var isEmpty = true
                checkTimeEmpty.get()
                    .addOnSuccessListener {
                        if(it.value.toString() != "0")
                            isEmpty = false

                        if(isEmpty) {
                            showDialogAnnouce(
                                "THÀNH CÔNG",
                                "Đã đặt lịch hẹn thành công. Vui lòng để ý kỹ thời gian, cũng như ngày khám bệnh"
                            )

                            //Xu ly voi db
                            database.child(selectedKeyTime!!).child("duocDat").setValue(1)
                            var cuochenDB = Firebase.database.getReference("CuocHen")
                            val key: String? = cuochenDB.push().key

                            var newAppointment = CuocHen()
                            newAppointment.MaCuocHen = key.toString()
                            newAppointment.MaBacSi = maBacSi.toString()
                            newAppointment.MaBenhNhan = maTaiKhoan.toString()
                            newAppointment.GioBatDau = selectedTime?.gioBatDau.toString()
                            newAppointment.GioKetThuc = selectedTime?.gioKetThuc.toString()
                            newAppointment.MaTrangThai = 0
                            newAppointment.Ngay = dayInWeek!![dayChoose].toString()

                            cuochenDB.child(key!!).setValue(newAppointment)

                        } else {
                            showDialogAnnouce("OOP!! LỖI", "Đặt lịch hẹn không thành công. Đã có người đặt lịch này trước bạn. Hãy đặt lịch khác nhé")
                        }

                        customDialog?.dismiss()
                    }.addOnFailureListener{
                        customDialog?.dismiss()
                    }
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
                    morningList[selectedIndex].duocDat = 1
                    timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, database, keyMorning, -1)
                    morningTimeView?.adapter = timeAdapter
                }
                else {
                    afternoonList[selectedIndex].duocDat = 1
                    afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, database, keyAfternoon, -1)
                    afternoonTimeView?.adapter = afternoonAdapter
                }
                customDialog?.dismiss()
            }
        })
        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    fun xulyThoiGianHienThi(dayChoose: Int) {
        var index:Int = 0
        keyMorning = arrayListOf<String>()
        keyAfternoon = arrayListOf<String>()
        for(time in thoiGianRanhList) {
            if(time.thuTrongTuan == dayChoose) {
                //Thêm các khoảng thời gian đổi ra phút vào list
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

        //Xử lý để click 1 trong các ô ở 2 grid view
        selectedIndex = -1
        isClickMorning = false
        isClickAfternoon = false

        timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, database, keyMorning, selectedIndex)
        morningTimeView?.adapter = timeAdapter
        morningTimeView?.setOnItemClickListener { adapterView, view, position, id ->
            if(isClickAfternoon) {
                afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, database, keyAfternoon, -1)
                afternoonTimeView?.adapter = afternoonAdapter
                isClickAfternoon = false
            }

            selectedIndex = position
            timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, database, keyMorning, selectedIndex)
            morningTimeView?.adapter = timeAdapter
            isClickMorning = true

            selectedTime = morningList[selectedIndex]
            selectedKeyTime = keyMorning[selectedIndex]
        }

        afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, database, keyAfternoon, selectedIndex)
        afternoonTimeView?.adapter = afternoonAdapter
        afternoonTimeView?.setOnItemClickListener { adapterView, view, position, id ->
            if(isClickMorning) {
                timeAdapter = ChooseTimeAdapter(ctx as UserOrderPage, morningList, database, keyMorning, -1)
                morningTimeView?.adapter = timeAdapter
                isClickMorning = false
            }

            selectedIndex = position
            afternoonAdapter = ChooseTimeAdapter(ctx as UserOrderPage, afternoonList, database, keyAfternoon, selectedIndex)
            afternoonTimeView?.adapter = afternoonAdapter
            isClickAfternoon = true

            selectedTime = afternoonList[selectedIndex]
            selectedKeyTime = keyAfternoon[selectedIndex]
        }
    }
}