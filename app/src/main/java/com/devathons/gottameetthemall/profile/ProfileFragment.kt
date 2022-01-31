package com.devathons.gottameetthemall.profile

import android.os.Bundle
import android.speech.tts.TextToSpeech
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.coroutines.CoroutineContext

class ProfileFragment : Fragment(R.layout.fragment_profile), CoroutineScope,
    TextToSpeech.OnInitListener {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    private lateinit var tts: TextToSpeech

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var userSpeech: User = User("", "")

    private val viewModel: ProfileViewModel by lazy {
        val factory =
            ProfileViewModel.Factory((activity?.application as MyApplication).profileRepository)
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()


        tts = TextToSpeech(requireContext(), this)

        val args = retrieveArguments()
        val user = args.user

        if (user == null) {
            launch {
                viewModel.getFlowUser()
                    .distinctUntilChanged()
                    .collect {
                        if (it != null) {
                            initProfileValue(it)
                        }
                        binding.editProfileButton.isVisible = true
                    }
            }
        } else {
            initProfileValue(user)
            userSpeech = user
        }

        binding.picture.setOnClickListener {
            if (user?.firstName != null) {
                speakOut(userSpeech)
            }
        }

        binding.editProfileButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditionFragment()
            findNavController().navigate(action)
        }
    }

    private fun initProfileValue(user: User) {
        with(binding) {
            firstName.text = user.firstName
            lastName.text = user.lastName
            job.text = user.job
            description.text = user.description
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

            Timber.d("TTS succesfully initiallised")

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