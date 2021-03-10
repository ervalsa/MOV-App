package com.ervalsa.mov.ui.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ervalsa.mov.R
import com.ervalsa.mov.model.User
import com.ervalsa.mov.ui.home.HomeActivity
import com.ervalsa.mov.utils.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.util.*

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    lateinit var user: User
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        user = intent.getParcelableExtra("data")!!
        tv_welcome.text = "Selamat Datang,\n" + user.nama

        btn_add_photo.setOnClickListener {
            if (statusAdd) {

                statusAdd = false
                btn_save.visibility = View.VISIBLE
                img_user_stroke.visibility = View.VISIBLE
                btn_add_photo.setImageResource(R.drawable.ic_add)
                img_user_photo.setImageResource(R.drawable.img_user_placeholder)

            } else {

                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
            }
        }

        btn_upload_later.setOnClickListener {
            finishAffinity()

            val showHome = Intent(
                this@SignUpPhotoActivity,
                HomeActivity::class.java)
            startActivity(showHome)
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val reference =
                    storageReference.child("images/" + UUID.randomUUID().toString())
                reference.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()

                        reference.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                        }
                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed Upload", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> val progress =
                            100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload " + progress.toInt() + " %")
                    }
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToFirebase(url: String) {
        mDatabase.child(user.username!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user.photoUrl = url
                mDatabase.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("saldo", user.saldo.toString())
                preferences.setValues("photoUrl", "")
                preferences.setValues("email", user.email.toString())
                preferences.setValues("status", "1")
                preferences.setValues("photoUrl", url)

                finishAffinity()

                val intent = Intent(this@SignUpPhotoActivity,
                    HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoActivity, ""+error.message,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo Profile",
            Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?,
                                                    token: PermissionToken?) {
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol upload nanti aja",
            Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data!!
            statusAdd = true

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(img_user_photo)

            btn_save.visibility = View.VISIBLE
            img_user_stroke.visibility = View.VISIBLE
            btn_add_photo.setImageResource(R.drawable.ic_delete)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Canceled", Toast.LENGTH_SHORT).show()
        }
    }
}