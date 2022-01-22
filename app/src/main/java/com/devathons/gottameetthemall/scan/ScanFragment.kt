package com.devathons.gottameetthemall.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.databinding.FragmentScanBinding

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var viewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(layoutInflater)
        return binding.root
    }

}