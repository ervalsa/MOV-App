package com.ervalsa.mov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Checkout (
    var seat: String? = "",
    var balance: String? = ""
) : Parcelable