package com.devathons.gottameetthemall.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.qrCodeData
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class ProfileViewModel : ViewModel() {
    private val barcodeEncoder = BarcodeEncoder()

    fun getProfile() = ProfileRepository.user

    fun generateQrCode(): Bitmap {
        return barcodeEncoder.encodeBitmap(
            ProfileRepository.user.qrCodeData,
            BarcodeFormat.QR_CODE,
            512,
            512
        )
    }

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        ProfileRepository.updateUser(firstName, lastName, job, description)
    }
}