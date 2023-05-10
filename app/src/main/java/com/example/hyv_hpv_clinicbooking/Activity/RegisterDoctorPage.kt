package com.example.hyv_hpv_clinicbooking.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.example.hyv_hpv_clinicbooking.Model.BacSi
import com.example.hyv_hpv_clinicbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class RegisterDoctorPage : AppCompatActivity() {
    //Khai bao cac bien thong tin ca nhan
    var nameET:EditText?= null
    var phoneET:EditText?= null
    var addressET:EditText?= null
    var soNamTrongNgheET:EditText?=null
    var emailET:EditText?= null
    var passwordET:EditText?= null
    var chuyenKhoaET:EditText?= null
    var cccdET:EditText?= null

    //Khai bao anh chung thuc
    var cccd_truoc_TV:ImageView?= null
    var cccd_sau_TV:ImageView?= null
    var giay_phep_TV:ImageView?= null

    //Khai bao nut can xu li
    var cccdTruocBTN:Button?= null
    var cccdSauBTN:Button?= null
    var giayPhepBTN:Button?= null
    var registerBTN: Button?= null
    var backBTN:ImageButton?= null

    private val PICK_IMAGE_REQUEST = 71
    var isClickCCCDTruoc:Boolean = false
    var isClickCCCDSau:Boolean = false
    var isClickGiayPhep:Boolean = false

    var filePath: Uri?= null
    var pathCCCDTruoc: Uri?= null
    var pathCCCDSau: Uri?= null
    var pathGiayPhep: Uri?= null

    //upload ảnh lên clound storage
    //khai báo database
    private lateinit var userDB : DatabaseReference

    lateinit var storage: FirebaseStorage
    var storageReference: StorageReference? = null
    lateinit var auth : FirebaseAuth

    var ctx: Context?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor_page)

        ctx = this

        auth = FirebaseAuth.getInstance()
        userDB = Firebase.database.getReference("Users")

        //gan cac bien thong tin ca nhan
        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        addressET = findViewById(R.id.addressET)
        soNamTrongNgheET = findViewById(R.id.soNamTrongNgheET)
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        chuyenKhoaET = findViewById(R.id.chuyenKhoaET)
        cccdET = findViewById(R.id.cccdET)

        //gan bien anh chung thuc
        cccd_truoc_TV = findViewById(R.id.cccd_mat_truoc)
        cccd_sau_TV = findViewById(R.id.cccd_mat_sau)
        giay_phep_TV = findViewById(R.id.giay_phep)

        //gan bien cac cai nut
        cccdTruocBTN = findViewById(R.id.cccdTruocBTN)
        cccdSauBTN = findViewById(R.id.cccdSauBTN)
        giayPhepBTN = findViewById(R.id.giayPhepBTN)
        registerBTN = findViewById(R.id.registerBtn)
        backBTN = findViewById(R.id.backBTN)

        //xu li nut upload anh
        cccdTruocBTN?.setOnClickListener {
            isClickCCCDTruoc = true
            isClickCCCDSau = false
            isClickGiayPhep = false

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST)
        }

        cccdSauBTN?.setOnClickListener {
            isClickCCCDTruoc = false
            isClickCCCDSau = true
            isClickGiayPhep = false

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST)
        }

        giayPhepBTN?.setOnClickListener {
            isClickCCCDTruoc = false
            isClickCCCDSau = false
            isClickGiayPhep = true

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST)
        }

        //Xử lí chọn chuyên khoa
        chuyenKhoaET?.setOnClickListener {
            showDialogChuyenKhoa()
        }

        //Lưu dữ liệu
        registerBTN?.setOnClickListener {
            println("bat")
            val key: String? = userDB.push().key
            val name = nameET?.text.toString()
            val phone = phoneET?.text.toString()
            val address = addressET?.text.toString()
            var soNamTrongNghe = soNamTrongNgheET?.text.toString()
            val chuyenKhoa = chuyenKhoaET?.text.toString()
            var cccd = cccdET?.text.toString()
            val email = emailET?.text.toString()
            val password = passwordET?.text.toString()

            //Gán firebase storage, up load anh len firebase storage
            storage = FirebaseStorage.getInstance();
            storageReference = storage.reference;
            if (filePath != null) {
                val progressDialog = ProgressDialog(ctx)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref1: StorageReference =
                    storageReference!!.child("BacSi/" + key + "_CCCD_mat_truoc")
                ref1.putFile(pathCCCDTruoc!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
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

                val ref2: StorageReference =
                    storageReference!!.child("BacSi/" + key + "_CCCD_mat_sau")
                ref2.putFile(pathCCCDSau!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
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

                val ref3: StorageReference =
                    storageReference!!.child("BacSi/" + key + "_giay_phep")
                ref3.putFile(pathGiayPhep!!)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
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
            }

            // Init Firebase Authentication
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        auth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) { task2 ->
                            if (task2.isSuccessful) {
                                // create User
                                val bacSi: BacSi = BacSi(
                                    "", chuyenKhoa, 0, 0, name,
                                    phone, 0, address, 0, email, "", "1234",
                                    false, "16 gio", false
                                )

                                bacSi.MaBacSi = key!!
                                userDB.child("BacSi").child(key).setValue(bacSi)
                                // Move to Login page
                                val intent = Intent(this, LoginPage::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    } else {
                        // Register fail
                        Toast.makeText(applicationContext
                            , getString(R.string.toastRegisterFail)
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == PICK_IMAGE_REQUEST) && (resultCode == RESULT_OK && data != null) && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                if(isClickCCCDTruoc) {
                    pathCCCDTruoc = filePath
                    cccd_truoc_TV?.setImageBitmap(bitmap)
                }
                else if(isClickCCCDSau) {
                    pathCCCDSau = filePath
                    cccd_sau_TV?.setImageBitmap(bitmap)
                }
                else if(isClickGiayPhep) {
                    pathGiayPhep = filePath
                    giay_phep_TV?.setImageBitmap(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun showDialogChuyenKhoa() {
        var customDialog: AlertDialog?=null
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
                    chuyenKhoa.add(child.child("tenChuyenKhoa").value.toString())
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