package com.devathons.gottameetthemall.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.qrCodeData
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val userRepo = ProfileRepository()
    private val barcodeEncoder = BarcodeEncoder()

    fun getProfile() = userRepo.user

    fun generateQrCode(): Bitmap {
        return barcodeEncoder.encodeBitmap(
            userRepo.user.qrCodeData,
            BarcodeFormat.QR_CODE,
            512,
            512
        )
    }

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        userRepo.updateUser(firstName, lastName, job, description)
    }
}