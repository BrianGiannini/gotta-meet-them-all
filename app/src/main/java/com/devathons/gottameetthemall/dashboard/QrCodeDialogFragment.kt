package com.devathons.gottameetthemall.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.MyApplication
import com.devathons.gottameetthemall.databinding.DialogQrCodeBinding

class QrCodeDialogFragment : DialogFragment() {

    private val viewBinding by lazy { DialogQrCodeBinding.inflate(layoutInflater) }

    private val viewModel: QrCodeViewModel by lazy {
        val factory = QrCodeViewModel.Factory((activity?.application as MyApplication).profileRepository)
        ViewModelProvider(this, factory)[QrCodeViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setView(viewBinding.root)

            val dialog = builder.create()
            dialog.setOnShowListener {
                //viewModel.qrCode()?.let { viewBinding.qrcode.setImageBitmap(viewModel.qrCode()) }
                viewBinding.progress.isVisible = false
            }

            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
