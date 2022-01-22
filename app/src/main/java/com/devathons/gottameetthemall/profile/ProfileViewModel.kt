package com.devathons.gottameetthemall.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.gson.Gson

class ProfileViewModel : ViewModel() {
    private val barcodeEncoder = BarcodeEncoder()
    private var gson = Gson()

    val profile get() = ProfileRepository.user

    fun generateQrCode(): Bitmap {
        return barcodeEncoder.encodeBitmap(
            gson.toJson(ProfileRepository.user),
            BarcodeFormat.QR_CODE,
            512,
            512
        )
    }

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        ProfileRepository.user = User(firstName, lastName, job, description)
    }
}