package com.devathons.gottameetthemall.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.MyApplication
import com.devathons.gottameetthemall.databinding.DialogQrCodeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class QrCodeDialogFragment : DialogFragment(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

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
                launch {
                    viewModel.qrCode()?.let { bitmap ->
                        viewBinding.qrcode.setImageBitmap(bitmap)
                        viewBinding.progress.isVisible = false
                    }
                }
            }

            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
