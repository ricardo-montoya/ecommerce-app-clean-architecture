package com.rmontoya.snacks4u.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rmontoya.snacks4u.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        setOnClickListeners()
        return (binding.root)
    }

    private fun setOnClickListeners() {
        binding.emailButton.setOnClickListener {
            this.findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToEmailAuthFragment())
        }
    }
}
