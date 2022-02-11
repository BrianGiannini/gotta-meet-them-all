package com.devathons.gottameetthemall.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.BaseViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.profile.ProfileViewModel
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class QrCodeViewModel(private val profileRepository: ProfileRepository) : BaseViewModel() {

    private val barcodeEncoder = BarcodeEncoder()
    private var gson = Gson()

    suspend fun qrCode(): Bitmap? =
        profileRepository.getCurrentUser().let {
            barcodeEncoder.encodeBitmap(
                gson.toJson(profileRepository.getCurrentUser().first()?.generateQRCode()),
                BarcodeFormat.QR_CODE,
                512,
                512
            )
        }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QrCodeViewModel(profileRepository) as T
        }
    }
}