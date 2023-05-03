package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import java.util.*


class EditProfilePage : AppCompatActivity() {
    //Khai báo các nút
    var backBTN: ImageButton?= null
    var saveBTN: Button?= null
    var editAvatarBTN: Button ?= null

    //Khai báo các thuộc tính có thể edit
    var nameET: EditText?= null
    var phoneET: EditText?= null
    var addressET: EditText?= null
    var emailET: EditText?= null
    var chuyenKhoaET: EditText?= null
    var moTaET: EditText?= null
    var khungGioET: EditText?= null
    var avatar: CircleImageView?= null

    //Khai báo các thuộc tính textview
    var chuyenKhoaTV: TextView?= null
    var addressTV: TextView?= null
    var moTaTV: TextView?= null
    var khungGioTV: TextView?= null

    //Khai báo tài khoản hiện tại
    var taiKhoanBS:BacSi?= null
    var taiKhoanBN:BenhNhan?= null
    var maTaiKhoan: String?= null

    //Xử lí db bên realm database
    private lateinit var database : DatabaseReference

    //upload ảnh lên clound storage
    lateinit var storage: FirebaseStorage
    var storageReference: StorageReference? = null

    //Đường dẫn để lưu ảnh
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    //Context màn hiện tại
    var ctx:Context ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        //Gán các nút
        backBTN = findViewById(R.id.backBTN)
        saveBTN = findViewById(R.id.saveBTN)
        editAvatarBTN = findViewById(R.id.editAvatarButton)

        //Gán các edit text
        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        addressET = findViewById(R.id.addressET)
        emailET = findViewById(R.id.emailET)
        chuyenKhoaET = findViewById(R.id.chuyenKhoaET)
        khungGioET = findViewById(R.id.khungGioET)
        moTaET = findViewById(R.id.moTaET)

        //Gán các các textview, imageview
        addressTV = findViewById(R.id.addressTV)
        chuyenKhoaTV = findViewById(R.id.chuyenKhoaTV)
        moTaTV = findViewById(R.id.moTaTV)
        khungGioTV = findViewById(R.id.khungGioTV)

        //Context = EditprofilePage
        ctx = this

        //Lấy loại tài khoản
        val loaiTaiKhoan = intent.getStringExtra("loaiTaiKhoan")
        if(loaiTaiKhoan == "BacSi") {
            maTaiKhoan = "-NUGq3OnCBW17tiSzuyZ";

            //Gán các thông tin hiện tại
            taiKhoanBS = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBS?.HoTen)
            phoneET?.setText(taiKhoanBS?.SoDienThoai)
            addressET?.setText(taiKhoanBS?.DiaChi)
            emailET?.setText(taiKhoanBS?.Email)
            chuyenKhoaET?.setText(taiKhoanBS?.TenChuyenKhoa)
            moTaET?.setText(taiKhoanBS?.Mota)
            khungGioET?.setText(taiKhoanBS?.KhungGioLamViec)

            //lấy Avatar từ db
            storage = FirebaseStorage.getInstance();
            storageReference = storage.reference;
            val ref: StorageReference =
                storageReference!!.child("BacSi/" + maTaiKhoan)
            ref.downloadUrl.addOnSuccessListener { uri ->
                avatar = findViewById(R.id.avatar)
                Picasso.get().load(uri).into(avatar);
                Log.d("Test", " Success!")
            }.addOnFailureListener { Log.d("Test", " Failed!") }
        }

        if(loaiTaiKhoan == "BenhNhan") {
            maTaiKhoan = "-NUGq3NRrFTwUKz84O6P"

            //Gán các thông tin hiện tại
            taiKhoanBN = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBN?.HoTen)
            phoneET?.setText(taiKhoanBN?.SoDienThoai)
            emailET?.setText(taiKhoanBN?.Email)

            //Ẩn thuộc tính của bác sĩ đi
            addressET?.visibility = View.GONE
            addressTV?.visibility = View.GONE
            chuyenKhoaET?.visibility = View.GONE
            chuyenKhoaTV?.visibility = View.GONE
            moTaET?.visibility = View.GONE
            moTaTV?.visibility = View.GONE
            khungGioET?.visibility = View.GONE
            khungGioTV?.visibility = View.GONE

            //lấy Avatar từ db
            storage = FirebaseStorage.getInstance();
            storageReference = storage.reference;
            val ref: StorageReference =
                storageReference!!.child("BenhNhan/" + maTaiKhoan)
            ref.downloadUrl.addOnSuccessListener { uri ->
                avatar = findViewById(R.id.avatar)
                Picasso.get().load(uri).into(avatar);
                Log.d("Test", " Success!")
            }.addOnFailureListener { Log.d("Test", " Failed!") }
        }

        if (loaiTaiKhoan == "adminBacSi") {
            taiKhoanBS = intent.getParcelableExtra("taiKhoan")!!
            nameET?.setText(taiKhoanBS?.HoTen)
            phoneET?.setText(taiKhoanBS?.SoDienThoai)
            addressET?.setText(taiKhoanBS?.DiaChi)
            emailET?.setText(taiKhoanBS?.Email)
        }

        //Xử lí chọn chuyên khoa
        chuyenKhoaET?.setOnClickListener {
            showDialogChuyenKhoa()
        }

        //Xử lí nút back
        backBTN?.setOnClickListener {
            if(loaiTaiKhoan == "BacSi") {
                val intent = Intent(this, DoctorHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }

            if(loaiTaiKhoan == "BenhNhan") {
                val intent = Intent(this, UserHomePage::class.java)
                intent.putExtra("fragment", "profile")
                startActivity(intent)
            }

            if(loaiTaiKhoan == "adminBacSi") {
                val intent = Intent(this, AdminHomePage::class.java)
                intent.putExtra("loadfragment", "doctorManagement")
                startActivity(intent)
            }
        }

        //Xử lí nút edit avatar
        //Cho phép chọn hình từ service
        editAvatarBTN?.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST)
        }

        //Xử lí nút lưu sau khi edit các thông tin
        saveBTN?.setOnClickListener {
            var check = checkValue(loaiTaiKhoan!!)

            if(check) {
                saveNewData(maTaiKhoan!!, loaiTaiKhoan)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == PICK_IMAGE_REQUEST) && (resultCode == RESULT_OK && data != null) && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                avatar = findViewById(R.id.avatar)
                avatar?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    //Xử lý lưu dữ liệu mới lên db và back về
    fun saveNewData(maTaiKhoan:String, loaiTaiKhoan:String) {
        var queryRef: Query? = null
        if(loaiTaiKhoan == "BacSi") {
            database = Firebase.database.getReference("Users").child("BacSi")
            queryRef = database
                .orderByChild("maBacSi")
                .equalTo(maTaiKhoan)
            queryRef.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    taiKhoanBS?.HoTen = nameET?.text.toString()
                    taiKhoanBS?.SoDienThoai = phoneET?.text.toString()
                    taiKhoanBS?.DiaChi = addressET?.text.toString()
                    taiKhoanBS?.Email = emailET?.text.toString()
                    taiKhoanBS?.TenChuyenKhoa = chuyenKhoaET?.text.toString()
                    taiKhoanBS?.Mota = moTaET?.text.toString()
                    taiKhoanBS?.KhungGioLamViec = khungGioET?.text.toString()

                    var key: String? = null;
                    for (child in dataSnapshot.children) {
                        key = child.key.toString();
                    }

                    //Load lên db realm
                    database.child(key!!).child("hoTen").setValue(taiKhoanBS?.HoTen);
                    database.child(key).child("soDienThoai").setValue(taiKhoanBS?.SoDienThoai)
                    database.child(key).child("email").setValue(taiKhoanBS?.Email)
                    database.child(key).child("diaChi").setValue(taiKhoanBS?.DiaChi)
                    database.child(key).child("tenChuyenKhoa").setValue(taiKhoanBS?.TenChuyenKhoa)
                    database.child(key).child("mota").setValue(taiKhoanBS?.Mota)
                    database.child(key).child("khungGioLamViec").setValue(taiKhoanBS?.KhungGioLamViec)

                    //Gán firebase storage
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.reference;

                    if (filePath != null) {
                        val progressDialog = ProgressDialog(ctx)
                        progressDialog.setTitle("Uploading...")
                        progressDialog.show()
                        val ref: StorageReference =
                            storageReference!!.child("BacSi/" + maTaiKhoan)
                        ref.putFile(filePath!!)
                            .addOnSuccessListener {
                                progressDialog.dismiss()

                                val intent = Intent(ctx, DoctorHomePage::class.java)
                                intent.putExtra("fragment", "profile")
                                intent.putExtra("function", "edit")
                                startActivity(intent)

                            }
                            .addOnFailureListener { e ->
                                progressDialog.dismiss()
                                Toast.makeText(ctx, "Failed " + e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnProgressListener { taskSnapshot ->
                                val progress =
                                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                        .totalByteCount
                                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                            }
                    } else {
                        val intent = Intent(ctx, DoctorHomePage::class.java)
                        intent.putExtra("fragment", "profile")
                        intent.putExtra("function", "edit")
                        startActivity(intent)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }else {
            database = Firebase.database.getReference("Users").child("BenhNhan")
            queryRef = database
                .orderByChild("maBenhNhan")
                .equalTo(maTaiKhoan)
            queryRef?.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    taiKhoanBN?.HoTen = nameET?.text.toString()
                    taiKhoanBN?.SoDienThoai = phoneET?.text.toString()
                    taiKhoanBN?.Email = emailET?.text.toString()

                    var key: String? = null;
                    for (child in dataSnapshot.children) {
                        key = child.key.toString();
                    }

                    //Load lên db realm
                    database.child(key!!).child("hoTen").setValue(taiKhoanBN?.HoTen);
                    database.child(key!!).child("soDienThoai").setValue(taiKhoanBN?.SoDienThoai)
                    database.child(key!!).child("email").setValue(taiKhoanBN?.Email)

                    //Gán firebase storage
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.reference;

                    if (filePath != null) {
                        val progressDialog = ProgressDialog(ctx)
                        progressDialog.setTitle("Uploading...")
                        progressDialog.show()
                        val ref: StorageReference =
                            storageReference!!.child("BenhNhan/" + maTaiKhoan)
                        ref.putFile(filePath!!)
                            .addOnSuccessListener {
                                progressDialog.dismiss()

                                val intent = Intent(ctx, UserHomePage::class.java)
                                intent.putExtra("fragment", "profile")
                                intent.putExtra("function", "edit")
                                startActivity(intent)

                            }
                            .addOnFailureListener { e ->
                                progressDialog.dismiss()
                                Toast.makeText(ctx, "Failed " + e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnProgressListener { taskSnapshot ->
                                val progress =
                                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                        .totalByteCount
                                progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                            }
                    } else {
                        val intent = Intent(ctx, UserHomePage::class.java)
                        intent.putExtra("fragment", "profile")
                        intent.putExtra("function", "edit")
                        startActivity(intent)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }


    }
    //Hiển thị thông báo lỗi
    fun showDialog(msgContent: String, msgTitle: String) {
        var customDialog:AlertDialog ?=null
        val builder = AlertDialog.Builder(this)
        //Hiển thị dialog để chọn time
        var view_dialog: View =
            this.layoutInflater.inflate(R.layout.dialog_err_msg, null)

        //khai bao bien
        var _msgTitle:TextView = view_dialog.findViewById(R.id.errTitle)
        var _msgContent:TextView = view_dialog.findViewById(R.id.errContent)
        var _closeBTN:Button = view_dialog.findViewById(R.id.closeBTN)

        //setText
        _msgTitle.text = msgTitle.toString()
        _msgContent.text = msgContent.toString()

        //Xu ly button
        _closeBTN.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                customDialog?.dismiss()
            }
        })

        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }

    //Kiểm tra tính hợp lệ của các thông tin mới
    fun checkValue(typeAccount: String):Boolean {
        var check = true;
        var msgContent = "";

        var newName = nameET?.text.toString()
        if(newName.isEmpty()) {
            check = false
            msgContent += "Họ và tên\n"
        }

        var newPhone = phoneET?.text.toString()
        if(newPhone.isEmpty()) {
            check = false
            msgContent += "Số điện thoại\n"
        }

        if(typeAccount == "BacSi") {
            var newAddress = addressET?.text.toString()
            if (newAddress.isEmpty()) {
                check = false
                msgContent += "Địa Chỉ\n"
            }
        }

        var newEmail = emailET?.text.toString()
        if(newEmail.isEmpty()) {
            check = false
            msgContent += "Email\n"
        }

        if(typeAccount == "BacSi") {
            var newChuyenKhoa = chuyenKhoaET?.text.toString()
            if (newChuyenKhoa.isEmpty()) {
                check = false
                msgContent += "Chuyên Khoa\n"
            }
        }

        if(!check) {
            showDialog(msgContent, "\nVui lòng không để trống thông tin:")
            return check
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            check = false
            msgContent += "Email không hợp lệ\n"
        }

        if(!Patterns.PHONE.matcher(newPhone).matches() || newPhone.length != 10) {
            check = false
            msgContent += "Số điện thoại không hợp lệ\n"
        }

        if(!check) {
            showDialog(msgContent, "\nVui lòng nhập lại thông tin:")
        }

        return check
    }

    fun showDialogChuyenKhoa() {
        var customDialog:AlertDialog ?=null
        val builder = AlertDialog.Builder(this)
        //Hiển thị dialog để chọn time
        var view_dialog: View =
            this.layoutInflater.inflate(R.layout.dialog_chuyenkhoa, null)

        //khai bao bien
        var chuyenKhoaList = view_dialog.findViewById<ListView>(R.id.chuyenKhoaList)
        var _closeBTN:Button = view_dialog.findViewById(R.id.closeBTN)

        //Xu li nut close
        _closeBTN.setOnClickListener {
            customDialog?.dismiss()
        }

        //Goi db de co list chuyenkhoa
        var chuyenKhoa = arrayListOf<String>()

        var chuyenKhoaDB = Firebase.database.getReference("DanhSach").child("ChuyenKhoa")
        chuyenKhoaDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(child in dataSnapshot.children) {
                    chuyenKhoa.add(child.child("TenChuyenKhoa").value.toString())
                }

                val arrayAdapter: ArrayAdapter<*>
                arrayAdapter = ArrayAdapter(ctx!!, android.R.layout.simple_list_item_1, chuyenKhoa)
                chuyenKhoaList.adapter = arrayAdapter

                chuyenKhoaList.setOnItemClickListener { parent, view, position, id ->
                    chuyenKhoaET?.setText(chuyenKhoa[position])
                    customDialog?.dismiss()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        builder.setView(view_dialog)
        customDialog = builder.create()
        customDialog?.show()
    }
}
