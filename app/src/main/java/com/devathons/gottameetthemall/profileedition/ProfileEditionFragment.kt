package com.devathons.gottameetthemall.profileedition

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.devathons.gottameetthemall.MyApplication
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.databinding.FragmentProfileEditionBinding


class ProfileEditionFragment : Fragment() {

    private var _binding: FragmentProfileEditionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ProfileEditionViewModel by lazy {
        val factory = ProfileEditionViewModel.Factory((activity?.application as MyApplication).profileRepository)
        ViewModelProvider(this, factory)[ProfileEditionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileEditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentUser()?.let { initProfileValue(it) }
        resumeEdition()

        binding.saveProfileButton.setOnClickListener {
            viewModel.saveProfile(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.job.text.toString(),
                binding.description.text.toString()
            )

            findNavController().popBackStack()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}