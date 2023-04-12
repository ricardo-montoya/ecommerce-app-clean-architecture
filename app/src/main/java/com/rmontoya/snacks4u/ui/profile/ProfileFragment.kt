package com.rmontoya.snacks4u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseUser
import com.rmontoya.snacks4u.AuthActivity
import com.rmontoya.snacks4u.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var user: FirebaseUser? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        loadUiAccordingUserState()
        setupObservers()
        setupOnClickListeners()
        return binding.root
    }

    private fun setupOnClickListeners() {
        binding.localLoggedScreen.signOutButton.setOnClickListener {
            viewModel.signOut()
        }
        binding.localNotLoggedScreen.signButton.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            viewModel.reloadUser()
            loadUiAccordingUserState()
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            this.user = user
            loadUiAccordingUserState()
        }
    }

    private fun loadUiAccordingUserState() {
        viewModel.reloadUser()
        if (this.user != null) {
            binding.localLoggedScreen.root.visibility = View.VISIBLE
            binding.localNotLoggedScreen.root.visibility = View.INVISIBLE
            performLoggedBindings()
        } else {
            binding.localLoggedScreen.root.visibility = View.INVISIBLE
            binding.localNotLoggedScreen.root.visibility = View.VISIBLE
        }
    }

    private fun performLoggedBindings() {
        val screen = binding.localLoggedScreen
        screen.userEmailTextView.text = user?.email
    }

    override fun onResume() {
        super.onResume()
        loadUiAccordingUserState()
    }
}