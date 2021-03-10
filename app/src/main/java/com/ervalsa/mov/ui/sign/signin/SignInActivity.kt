package com.ervalsa.mov.ui.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ervalsa.mov.R
import com.ervalsa.mov.model.User
import com.ervalsa.mov.ui.home.HomeActivity
import com.ervalsa.mov.ui.sign.signup.SignUpActivity
import com.ervalsa.mov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var inputUsername: String
    lateinit var inputPassword: String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        preferences = Preferences(this)
        preferences.setValues("onboarding", "1")

        if (preferences.getValues("status").equals("1")) {
            finishAffinity()

            val showHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(showHome)
        }

        btn_login.setOnClickListener {
            login()
        }

        btn_signup.setOnClickListener {
            val showSignUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(showSignUp)
        }
    }

    private fun login() {
        inputUsername = edt_username.text.toString()
        inputPassword = edt_password.text.toString()

        if (inputUsername.equals("")) {
            edt_username.error = "Silahkan isi Username Anda"
            edt_username.requestFocus()
        } else if (inputPassword.equals("")) {
            edt_password.error = "Silahkan isi Password Anda"
            edt_password.requestFocus()
        } else {
            pushDataLogin(inputUsername, inputPassword)
        }
    }

    private fun pushDataLogin(inputUsername: String, inputPassword: String) {
        mDatabase.child(inputUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(
                        this@SignInActivity, "User Tidak Ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    if (user.password.equals(inputPassword)) {

                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("photoUrl", user.photoUrl.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("status", "1")

                        val showHome = Intent(
                            this@SignInActivity,
                            HomeActivity::class.java)
                        startActivity(showHome)

                        finishAffinity()
                    } else {
                        Toast.makeText(
                            this@SignInActivity, "Password Anda Salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@SignInActivity,
                    "" + databaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}