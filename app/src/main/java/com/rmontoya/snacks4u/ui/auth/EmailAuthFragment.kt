package com.rmontoya.snacks4u.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rmontoya.snacks4u.databinding.FragmentEmailAuthBinding
import dagger.hilt.android.AndroidEntryPoint

const val SIGN_IN = "SIGN_IN"
const val SIGN_UP = "SIGN_UP"

@AndroidEntryPoint
class EmailAuthFragment : Fragment() {

    private lateinit var binding: FragmentEmailAuthBinding
    private var mode: String = SIGN_UP
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailAuthBinding.inflate(inflater, container, false)
        redesignViewByMode()
        setupOnClickListener()
        setupObservers()
        return binding.root
    }

    private fun setupObservers(){
        viewModel.user.observe(viewLifecycleOwner){
            if(it != null) activity?.finish()
        }
        viewModel.exception.observe(viewLifecycleOwner){errorMessage ->
            errorMessage?.let {
                performToast(it)
                viewModel.errorActionPerformed()
            }
        }
    }

    private fun setupOnClickListener() {
        binding.signInButton.setOnClickListener {
            this.mode = SIGN_IN
            redesignViewByMode()
        }
        binding.signUpButton.setOnClickListener {
            this.mode = SIGN_UP
            redesignViewByMode()
        }
        binding.doneButton.setOnClickListener {
            performSignActionByMode()
        }
    }

    private fun redesignViewByMode() {
        when (this.mode) {
            SIGN_UP -> {
                binding.nameInputLayout.visibility = View.VISIBLE
                binding.passwordInputConfirmLayout.visibility = View.VISIBLE
            }
            SIGN_IN -> {
                binding.nameInputLayout.visibility = View.GONE
                binding.passwordInputConfirmLayout.visibility = View.GONE
            }
        }
    }

    private fun arePasswordsTheSame(): Boolean =
        binding.passwordInputEdittext.text.toString() == binding.passwordInputConfirmEdittext.text.toString()

    private fun noFieldIsEmptySignUp(): Boolean {
        return (
                binding.passwordInputEdittext.text!!.isNotEmpty() &&
                        binding.emailInputEdittext.text!!.isNotEmpty()
                )
    }

    private fun noFieldIsEmptySignIn(): Boolean {
        return (
                binding.passwordInputEdittext.text!!.isNotEmpty() &&
                        binding.emailInputEdittext.text!!.isNotEmpty()
                )
    }

    private fun performSignActionByMode() {
        when (mode) {
            SIGN_UP -> {
                if (arePasswordsTheSame() && noFieldIsEmptySignUp()) {
                    val email = binding.emailInputEdittext.text.toString()
                    val password = binding.passwordInputEdittext.text.toString()
                    viewModel.registerUserWithEmail(email, password)
                } else {
                    if (!arePasswordsTheSame()) performToast("Passwords are not the same")
                    if (!noFieldIsEmptySignIn()) performToast("You need to fill all the fields")
                }
            }
            SIGN_IN -> {
                if (noFieldIsEmptySignUp()) {
                    val email = binding.emailInputEdittext.text.toString()
                    val password = binding.passwordInputEdittext.text.toString()
                    viewModel.loginUserWithEmail(email, password)
                } else {
                    performToast("You need to fill all the fields")
                }
            }
        }
    }

    private fun performToast(content: String) {
        Toast.makeText(this.requireContext(), content, Toast.LENGTH_SHORT).show()
    }
}