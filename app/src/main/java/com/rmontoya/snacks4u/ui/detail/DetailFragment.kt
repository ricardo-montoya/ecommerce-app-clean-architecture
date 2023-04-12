package com.rmontoya.snacks4u.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.rmontoya.snacks4u.AuthActivity
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var authResultAcitvity: ActivityResultLauncher<Intent>? = null
    private var authResponse: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authResultAcitvity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    authResponse = result.data?.getBooleanExtra("AUTH_STATE", false) ?: false
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.getProductById(args.id)
        setupUiBinds()
        setUIClickListener()
        return binding.root
    }

    private fun setupUiBinds() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            //SetupImage
            Glide.with(this.requireContext()).load(product.imageUrl).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_downloading)
                    .error(R.drawable.ic_error)
            ).into(binding.productImageView)
            //Setup texts
            binding.productNameTextView.text = product.name
            binding.productPriceTextView.text = StringBuilder().append('$').append("${product.price}")
            binding.productPrepTimeTextView.text = StringBuilder().append("${product.prepTime}").append("mins")
            binding.productDescriptionTextView.text = product.description
            binding.productVendorTextView.text = StringBuilder().append(getString(R.string.by)).append("${product.vendor}")
        }

        viewModel.quantity.observe(viewLifecycleOwner) { amount ->
            binding.quantityNumberTextView.text = amount.toString()
            binding.addToCartButton.isEnabled = amount > 0
        }
    }

    private fun setUIClickListener() {
        binding.quantityPlusButton.setOnClickListener {
            viewModel.increaseAmount()
        }
        binding.quantityMinusButton.setOnClickListener {
            viewModel.decreaseAmount()
        }
        binding.addToCartButton.setOnClickListener {
            viewModel.reloadUser()
            if (viewModel.userNotNull) {
                viewModel.addProductToCart()
                Snackbar.make(requireView(), "Product added to the cart", Snackbar.LENGTH_SHORT).show()
            } else {
                launchAuthActivity()
                viewModel.reloadUser()
            }

        }
    }

    private fun launchAuthActivity() {
        authResultAcitvity?.launch(Intent(this.requireContext(), AuthActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadUser()
    }
}