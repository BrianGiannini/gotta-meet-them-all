package com.devathons.gottameetthemall.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String? = null,
    val job: String? = null,
    val description: String? = null,
) : Parcelable

//val User.qrCodeData: String get() = toString()

