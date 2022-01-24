package com.devathons.gottameetthemall.profile

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.databinding.FragmentProfileBinding
import timber.log.Timber
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile), TextToSpeech.OnInitListener {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var tts: TextToSpeech

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var userSpeech: User = User("", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        tts = TextToSpeech(requireContext(), this)

        val args = retrieveArguments()
        val user = args.user

        if (user != null) {
            initProfileValue(user)
            userSpeech = user
            preventEdition()
        } else {
            Glide.with(this).load(R.drawable.portrait_placeholder).into(binding.picture)
            viewModel.profile?.let { initProfileValue(it) }
            resumeEdition()
        }

        binding.picture.setOnClickListener {
            if (user?.firstName != null) {
                speakOut(userSpeech)
            }
        }

        binding.firstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                binding.saveProfileButton.isEnabled = s.toString().trim().isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.saveProfileButton.setOnClickListener {
            viewModel.saveProfile(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.job.text.toString(),
                binding.description.text.toString()
            )

            findNavController().popBackStack()
        }
    }

    private fun resumeEdition() {
        binding.firstName.isEnabled = true
        binding.lastName.isEnabled = true
        binding.job.isEnabled = true
        binding.description.isEnabled = true
        binding.saveProfileButton.isVisible = true
    }

    private fun preventEdition() {
        binding.firstName.isEnabled = false
        binding.lastName.isEnabled = false
        binding.job.isEnabled = false
        binding.description.isEnabled = false
        binding.saveProfileButton.isVisible = false
    }

    private fun initProfileValue(user: User) {
        binding.firstName.setText(user.firstName)
        binding.lastName.setText(user.lastName)
        binding.job.setText(user.job)
        binding.description.setText(user.description)
    }

    private fun retrieveArguments(): ProfileFragmentArgs {
        val args: ProfileFragmentArgs by navArgs()
        return args
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts.setLanguage(Locale.ENGLISH)

            Timber.d( "TTS succesfully initiallised")

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Timber.e("The Language specified is not supported!")
            }

            if (userSpeech.firstName != "") {
                speakOut(userSpeech)
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    private fun speakOut(user: User) {
        Timber.d("play TTS")
        val text = "${user.firstName} ${user.lastName}, ${user.job}. ${user.description}."
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        // Shutdown TTS
        tts.stop()
        tts.shutdown()

        super.onDestroy()
    }
}