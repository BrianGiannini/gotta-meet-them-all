package com.devathons.gottameetthemall.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeViewModel : ViewModel() {

    private val barcodeEncoder = BarcodeEncoder()
    private var gson = Gson()

    val qrCode: Bitmap
        get() = barcodeEncoder.encodeBitmap(
            gson.toJson(ProfileRepository.user),
            BarcodeFormat.QR_CODE,
            512,
            512
        )
}