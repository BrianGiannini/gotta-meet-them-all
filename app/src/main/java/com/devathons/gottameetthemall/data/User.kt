package com.devathons.gottameetthemall.data

data class User(
    val firstName: String,
    val lastName: String? = null,
    val job: String? = null,
    val description: String? = null,
)

val User.qrCodeData: String get() = firstName
