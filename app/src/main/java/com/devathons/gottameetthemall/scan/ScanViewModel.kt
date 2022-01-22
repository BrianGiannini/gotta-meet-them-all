package com.devathons.gottameetthemall.scan

import android.Manifest
import android.content.Context
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScanViewModel : ViewModel() {

    fun startCamera(context: Context, surfaceProvider: Preview.SurfaceProvider, lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .setTargetResolution(Size(400, 400))
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }

            val imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetResolution(Size(400, 400))
                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits requested capture mode
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .build()

            val executor = ContextCompat.getMainExecutor(context)

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(executor, QrCodeAnalyzer { qrCodes ->
                    qrCodes?.forEach {
                        Toast.makeText(context, it.rawValue, Toast.LENGTH_SHORT).show()
                        Timber.d("QR Code detected: " + it.rawValue + ".")
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

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}