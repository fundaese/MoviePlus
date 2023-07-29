package com.example.movieplus.data.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val imgMovie: Bitmap,
    val title: String,
    val desc: String,
    val movieCategory: String,
    var isWatched: Boolean
) : Parcelable