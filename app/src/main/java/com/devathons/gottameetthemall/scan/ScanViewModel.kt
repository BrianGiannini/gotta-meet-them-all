package com.devathons.gottameetthemall.scan

import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class ScanViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    var isScanned = false

    private val channelData = Channel<User>()
    val qrCodeData: Flow<User> = channelData.receiveAsFlow()

    private var gson = Gson()

    fun startCamera(context: Context, surfaceProvider: Preview.SurfaceProvider, lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .setTargetResolution(Size(512, 512))
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }

            val imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetResolution(Size(512, 512))
                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits requested capture mode
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .build()

            val executor = ContextCompat.getMainExecutor(context)

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(executor, QrCodeAnalyzer { qrCodes ->
                    qrCodes?.forEach { barcode ->
                        if (!isScanned) {
                            barcode.displayValue?.let { data ->
                                if (data != "") {
                                    Timber.d("QR Code detected: $data")
                                    try {
                                        val user = gson.fromJson(data, User::class.java)
                                        if (user != null) {
                                            isScanned = true
                                        }
                                        UsersRepository.addNewUser(user)
                                        launch {
                                            channelData.send(user)
                                        }
                                    } catch (e: Exception) {
                                        Timber.d("Not a valid QR Cede", e)
                                    }
                                }
                            }
                        }
                    }
                })
            }


            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalyzer
                )

            } catch (exc: Exception) {
                Timber.e(exc, "Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(context))
    }

}