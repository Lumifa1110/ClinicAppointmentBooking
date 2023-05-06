package com.example.hyv_hpv_clinicbooking.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hyv_hpv_clinicbooking.Activity.*
import com.example.hyv_hpv_clinicbooking.Adapter.DayAdapter
import com.example.hyv_hpv_clinicbooking.Adapter.TimeAdapter
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.Model.KhungGio
import com.example.hyv_hpv_clinicbooking.Model.ThoiGianRanh
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorChooseFreeTimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorChooseFreeTimeFragment : Fragment(){


    // TODO: Rename and change types of parameters
    var customListView: RecyclerView? = null
    var morningList = arrayListOf<ThoiGianRanh>()
    var afternoonList = arrayListOf<ThoiGianRanh>()
    var thoiGianRanhList =  arrayListOf<ThoiGianRanh>()
    var dayList =  ArrayList<String>()
    var dayInWeek:HashMap<Int, String> ?= null

    //Khai báo adapter
    var adapter: DayAdapter? = null
    var timeAdapter: TimeAdapter? = null
    var afternoonAdapter: TimeAdapter? = null
    var keyMorning = arrayListOf<String>()
    var keyAfternoon = arrayListOf<String>()
    var keyList = arrayListOf<String>()

    //Khai báo GridView
    var morningTimeView: GridView? = null
    var afternoonTimeView: GridView? = null

    var addTimeBTN: Button?= null

    //Dialog để chọn time
    var timeStart:TimePicker?=null
    var timeEnd:TimePicker?=null
    var addBTN:Button?= null
    var cancelBTN:Button?= null
    var customDialog:AlertDialog ?=null

    //Khai báo database
    private lateinit var database : DatabaseReference

    //Mã Tài Khoản hiện tại
    var maBacSi:String ?= null

    var dayChoose:Int = 0
    var khoangThoiGianTrongNgay = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_choose_free_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customListView = view.findViewById(R.id.dayRV)
        morningTimeView = view.findViewById<GridView>(R.id.morningGV)
        afternoonTimeView = view.findViewById<GridView>(R.id.afternoonGV)

        addTimeBTN = view.findViewById(R.id.addTimeBTN)

        morningList = arrayListOf<ThoiGianRanh>()
        afternoonList = arrayListOf<ThoiGianRanh>()
        thoiGianRanhList =  arrayListOf<ThoiGianRanh>()


        database = Firebase.database.getReference("ThoiGianRanh")
        //Thêm vào db để test
//        thoiGianRanhList.add(ThoiGianRanh(2, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(3, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(4, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(5, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(6, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(7, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(1, "03/04", "01:30", "02:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//
//        thoiGianRanhList.add(ThoiGianRanh(2, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(3, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(4, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(5, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(6, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(7, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        thoiGianRanhList.add(ThoiGianRanh(1, "03/04", "02:30", "03:00", "-NUGq3OnCBW17tiSzuyZ", 0, 0));
//        for(item in thoiGianRanhList) {
//            val key: String? = database.push().key
//            database.child(key!!).setValue(item)
//        }

        maBacSi = "-NUGq3OnCBW17tiSzuyZ"

        val queryRef: Query = database
            .orderByChild("maBacSi")
            .equalTo(maBacSi)

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
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
                hienThiTimeTrongNgay(dayChoose)

                adapter?.onItemClick = {date, vitri ->
                    khoangThoiGianTrongNgay = ArrayList<String>()
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

                    hienThiTimeTrongNgay(dayChoose)
                }
                addTimeBTN?.setOnClickListener {
                    val builder = AlertDialog.Builder(requireContext())
                    //Hiển thị dialog để chọn time
                    var view_dialog: View =
                        requireActivity().layoutInflater.inflate(R.layout.dialog_picktime, null)

                    timeStart = view_dialog.findViewById(R.id.timeStart)
                    timeEnd = view_dialog.findViewById(R.id.timeEnd)
                    addBTN = view_dialog.findViewById(R.id.addTimeBTN)
                    cancelBTN = view_dialog.findViewById(R.id.cancelBTN)

                    addBTN?.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View) {
                            var hourStart = convertNtoNN(timeStart!!.currentHour)
                            var minuteStart = convertNtoNN(timeStart!!.currentMinute)

                            var hourEnd = convertNtoNN(timeEnd!!.currentHour)
                            var minuteEnd = convertNtoNN(timeEnd!!.currentMinute)

                            var checkTimeValid = checkTimeValid(timeStart!!.currentHour, timeStart!!.currentMinute, timeEnd!!.currentHour, timeEnd!!.currentMinute)
                            //ThoiGian hợp lệ
                            if(checkTimeValid) {
                                var newTime:ThoiGianRanh = ThoiGianRanh(dayChoose,
                                    dayInWeek!![dayChoose], "$hourStart:$minuteStart",
                                    "$hourEnd:$minuteEnd", maBacSi, 0)
                                val key: String? = database.push().key
                                database.child(key!!).setValue(newTime)

                                //Cập Nhật Adapter
                                if(timeStart!!.currentHour <= 12) {
                                    morningList.add(newTime)
                                    keyMorning.add(key)
                                    timeAdapter = TimeAdapter(requireContext(), morningList, database, keyMorning)
                                    morningTimeView?.adapter = timeAdapter
                                }
                                else {
                                    afternoonList.add(newTime)
                                    keyAfternoon.add(key)
                                    afternoonAdapter = TimeAdapter(requireContext(), afternoonList, database, keyAfternoon)
                                    afternoonTimeView?.adapter = afternoonAdapter
                                }
                                thoiGianRanhList.add(newTime)
                                keyList.add(key)
                                customDialog?.dismiss()
                            }
                        }
                    })

                    cancelBTN?.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View) {
                            customDialog?.dismiss()
                        }
                    })

                    builder.setView(view_dialog)
                    customDialog = builder.create()
                    customDialog?.show()
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

    fun checkTimeValid(hourStart: Int, minStart: Int, hourEnd: Int, minEnd: Int): Boolean {
        if(hourStart > hourEnd)
            return false;
        if(hourStart == hourEnd && minStart >= minEnd)
            return false;

        var minStart = hourStart * 60 + minStart
        var minEnd = hourEnd * 60 + minEnd

        for(timeCheck in khoangThoiGianTrongNgay) {
            var dataCheck = arrayListOf<String>()
            dataCheck = timeCheck.split("-") as ArrayList<String>
            if(dataCheck[0].toInt() <= minStart && minStart < dataCheck[1].toInt())
                return false
            if(dataCheck[0].toInt() < minEnd && minEnd <= dataCheck[1].toInt())
                return false
        }
        return true;
    }

    fun hienThiTimeTrongNgay(dayChoose: Int) {
        var index:Int = 0
        keyMorning = arrayListOf<String>()
        keyAfternoon = arrayListOf<String>()
        for(time in thoiGianRanhList) {
            if(time.thuTrongTuan == dayChoose) {
                //Thêm các khoảng thời gian đổi ra phút vào list
                var dataStart = arrayListOf<String>()
                dataStart = time.gioBatDau?.split(":") as ArrayList<String>

                var dataEnd = arrayListOf<String>()
                dataEnd = time.gioKetThuc?.split(":") as ArrayList<String>

                var minStart = dataStart[0].toInt() * 60 + dataStart[1].toInt()
                var minEnd = dataEnd[0].toInt() * 60 + dataEnd[1].toInt()

                khoangThoiGianTrongNgay.add("$minStart-$minEnd")

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

        timeAdapter = TimeAdapter(requireContext(), morningList, database, keyMorning)
        morningTimeView?.adapter = timeAdapter

        afternoonAdapter = TimeAdapter(requireContext(), afternoonList, database, keyAfternoon)
        afternoonTimeView?.adapter = afternoonAdapter
    }
}
