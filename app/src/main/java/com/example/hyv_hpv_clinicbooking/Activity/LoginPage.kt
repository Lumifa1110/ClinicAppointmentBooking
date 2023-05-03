package com.example.hyv_hpv_clinicbooking.Activity

import BenhNhan
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hyv_hpv_clinicbooking.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var dangKi: TextView
    private lateinit var resetPasswordBTN: TextView
    private lateinit var createNewAccountBTN: TextView
    private lateinit var loginGoogleBTN: ImageButton

    private val roles = arrayOf<String>("BenhNhan", "BacSi")

    // Firebase
    private lateinit var database : DatabaseReference
    private lateinit var auth  : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient


    private fun initWidgets() {
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        loginBTN = findViewById(R.id.loginBTN)
        dangKi = findViewById(R.id.dangKi)
        resetPasswordBTN = findViewById(R.id.resetPasswordBTN)
        createNewAccountBTN = findViewById(R.id.createNewAccountBTN)
        loginGoogleBTN = findViewById(R.id.googleButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        initWidgets()

        database = Firebase.database.getReference("Users")
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        initListener()
    }

    private fun initListener() {
        dangKi.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
        loginBTN.setOnClickListener {
            loginEmailPassword()
        }
        resetPasswordBTN.setOnClickListener {
            resetPassword()
        }
        createNewAccountBTN.setOnClickListener {
            createNewAccount()
        }
        loginGoogleBTN.setOnClickListener {
            loginGoogle()
        }
    }

    private fun loginEmailPassword() {
        if (!editTextIsEmpty()) {
            // Get EditText input
            val email = emailET.text.toString()
            val password = passwordET.text.toString()

            // Init Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login success
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.toastLoginSuccess),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        // Get User role and Switch to Homepage
                        val user = auth.currentUser
                        getUserRole(user!!)
                    }
                }
        }
    }

    private fun editTextIsEmpty() : Boolean {
        if (emailET.text.toString() == "" ||
            passwordET.text.toString() == "")
        {
            Toast.makeText(applicationContext
                , getString(R.string.toastEmptyEditText)
                , Toast.LENGTH_SHORT)
                .show()
            return true
        }
        return false
    }

    private fun resetPassword() {
        val email = emailET.text.toString()
        if (email == "") {
            Toast.makeText(applicationContext
                , getString(R.string.toastEmptyEditText)
                , Toast.LENGTH_SHORT)
                .show()
        }
        else {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra("reset password", email)
            startActivity(intent)
        }
    }

    private fun createNewAccount() {
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    private fun getUserRole(user: FirebaseUser) {
        for (role in roles) {
            database.child(role).child(user.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        Log.i("Login as ", role)
                        startHomePage(role)
                    }
                }
            }
        }
    }

    private fun startHomePage(role: String) {
        val intent : Intent
        when (role) {
            "BenhNhan" -> {
                intent = Intent(this, UserHomePage::class.java)
                startActivity(intent)
            }
            "BacSi" -> {
                intent = Intent(this, DoctorHomePage::class.java)
                startActivity(intent)
            }
            "Admin" -> {
                intent = Intent(this, AdminHomePage::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                // create User
                val user = BenhNhan(
                    Email = auth.currentUser!!.email!!,
                )
                // update User profile in database
                database.child("BenhNhan").child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Register success
                        Toast.makeText(applicationContext
                            , getString(R.string.toastRegisterSuccess)
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        // Register fail
                        Toast.makeText(applicationContext
                            , getString(R.string.toastRegisterFail)
                            , Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                // Get User role and Switch to Homepage
                getUserRole(auth.currentUser!!)
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