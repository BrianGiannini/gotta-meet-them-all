package com.devathons.gottameetthemall.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.databinding.FragmentScanBinding
import kotlinx.coroutines.flow.collect

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var viewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private val args: ScanFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        // Request camera permissions
        if (allPermissionsGranted()) {
            binding.cameraView.post { viewModel.startCamera(requireContext(), binding.cameraView.surfaceProvider, this) }
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.qrCodeData.collect {
//
//        }
    }

    fun navigateToUser(qrCodeContent: String) {
        val action = ScanFragmentDirections.actionScanFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel.startCamera(requireContext(), binding.cameraView.surfaceProvider, this)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}