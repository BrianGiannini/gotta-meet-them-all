package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.devathons.gottameetthemall.R
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel


    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


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

        val args = retrieveArguments()

        val user: User by lazy { arguments?.getParcelable("user") as User }

        if (user != null) initProfileValue(user)
        else initProfileValue(viewModel.getProfile())

        binding.picture.setOnClickListener {
            viewModel.getProfile()
        }

        binding.saveProfileButton.setOnClickListener {
            viewModel.saveProfile(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.job.text.toString(),
                binding.description.text.toString()
            )

            binding.qrcode.setImageBitmap(viewModel.generateQrCode())
        }
    }

    private fun initProfileValue(user: User) {
        binding.firstName.setText(user.firstName)
        binding.lastName.setText(user.lastName)
        binding.job.setText(user.job)
        binding.description.setText(user.description)
        Glide.with(this).load(R.drawable.portrait_placeholder).into(binding.picture)
    }

    private fun retrieveArguments(): ProfileFragmentArgs {
        val args: ProfileFragmentArgs by navArgs()
        return args
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}