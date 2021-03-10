package com.ervalsa.mov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    var desc: String? = "",
    var genre: String? = "",
    var judul: String? = "",
    var poster: String? = "",
    var rating: String? = "",
    var background: String? = "",
    var playName: String? = "",
    var playUrl: String? = ""
) : Parcelable