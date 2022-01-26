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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devathons.gottameetthemall.MyApplication
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.databinding.FragmentProfileBinding
import timber.log.Timber
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var userSpeech: User = User("", "")

    private val viewModel: ProfileViewModel by lazy {
        val factory = ProfileViewModel.Factory((activity?.application as MyApplication).profileRepository)
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tts = TextToSpeech(requireContext(), this)

        val args = retrieveArguments()
        val user = args.user

        if (user != null) {
            initProfileValue(user)
            userSpeech = user
            preventEdition()
        } else {
            viewModel.getCurrentUser()?.let { initProfileValue(it) }
            resumeEdition()
        }

        binding.picture.setOnClickListener {
            if (user?.firstName != null) {
                speakOut(userSpeech)
            }
        }

        if (binding.firstName.text.isEmpty()) {
            binding.firstName.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    binding.saveProfileButton.isEnabled = s.toString().trim().isNotEmpty()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }


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
        with(binding) {
            firstName.isEnabled = firstName.text.isEmpty()
            lastName.isEnabled = lastName.text.isEmpty()
            job.isEnabled = true
            description.isEnabled = true
            saveProfileButton.isVisible = true
            saveProfileButton.isEnabled = firstName.text.isNotEmpty()
        }
    }

    private fun preventEdition() {
        with(binding) {
            firstName.isEnabled = false
            lastName.isEnabled = false
            job.isEnabled = false
            description.isEnabled = false
            saveProfileButton.isVisible = false
        }

    }

    private fun initProfileValue(user: User) {
        with(binding) {
            firstNameTextInputLayout.isHintAnimationEnabled = false
            lastNameTextInputLayout.isHintAnimationEnabled = false
            jobTextInputLayout.isHintAnimationEnabled = false
            descriptionTextInputLayout.isHintAnimationEnabled = false
            firstName.setText(user.firstName)
            lastName.setText(user.lastName)
            job.setText(user.job)
            description.setText(user.description)
            firstNameTextInputLayout.isHintAnimationEnabled = true
            lastNameTextInputLayout.isHintAnimationEnabled = true
            jobTextInputLayout.isHintAnimationEnabled = true
            descriptionTextInputLayout.isHintAnimationEnabled = true
        }
        Glide.with(this).load(user.picture).into(binding.picture)
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