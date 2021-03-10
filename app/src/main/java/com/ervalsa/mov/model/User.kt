package com.ervalsa.mov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var email: String? = "",
    var nama: String? = "",
    var password: String? = "",
    var photoUrl: String? = "",
    var username: String? = "",
    var saldo: String? = ""
) : Parcelable