package com.ervalsa.mov.ui.sign.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ervalsa.mov.R
import com.ervalsa.mov.model.User
import com.ervalsa.mov.ui.sign.signin.SignInActivity
import com.ervalsa.mov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var inputUsername: String
    lateinit var inputPassword: String
    lateinit var inputName: String
    lateinit var inputEmail: String
    var inputSaldo: String = "100000"

    lateinit var mDatabase: DatabaseReference

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        preferences = Preferences(this)

        btn_next.setOnClickListener {
            inputUsername = edt_username.text.toString()
            inputPassword = edt_password.text.toString()
            inputName = edt_name.text.toString()
            inputEmail = edt_email_address.text.toString()

            if (inputUsername.equals("")) {
                edt_username.error = "Silahkan isi Username Anda"
                edt_username.requestFocus()
            } else if (inputPassword.equals("")) {
                edt_password.error = "Silahkan isi Password Anda"
                edt_password.requestFocus()
            } else if (inputName.equals("")) {
                edt_name.error = "Silahkan isi Nama Anda"
                edt_name.requestFocus()
            } else if (inputEmail.equals("")) {
                edt_email_address.error = "Silahkan isi Email Anda"
                edt_email_address.requestFocus()
            } else {
                saveUserAccount(inputUsername, inputPassword, inputName, inputEmail, inputSaldo)
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun saveUserAccount(inputUsername: String, inputPassword: String, inputName: String,
                                inputEmail: String, inputSaldo: String) {
        val user = User()
        user.username = inputUsername
        user.password = inputPassword
        user.nama = inputName
        user.email = inputEmail
        user.saldo = inputSaldo

        checkInputUsername(inputUsername, user)
    }

    private fun checkInputUsername(inputUsername: String, data: User) {
        mDatabase.child(inputUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mDatabase.child(inputUsername).setValue(data)

                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("saldo", data.saldo.toString())
                    preferences.setValues("photoUrl", "")
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    val showSignUpPhoto = Intent(this@SignUpActivity,
                        SignUpPhotoActivity::class.java).putExtra("data", data)
                    startActivity(showSignUpPhoto)

                    Toast.makeText(
                        this@SignUpActivity,
                        "Selamat. Rp 100000 telah ditambahkan",
                        Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + databaseError.message,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }
}