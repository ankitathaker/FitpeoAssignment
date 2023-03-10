package com.fitpeo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    val id: Int = 0,
    val albumId: Int = 0,
    val title: String = "",
    val url: String = "",
    val thumbnailUrl: String = "",
) : Parcelable
