package com.example.cleanarchitecture.presentation.main.add_update

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentAddUpdateBinding
import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddUpdateFragment : BaseFragment(R.layout.fragment_add_update) {
    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddUpdateViewModel by viewModels()
    private var product: Product? = null
    private val uid by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable("product")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddUpdateBinding.bind(view)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        if (product != null) {
            with(binding) {
                toolBar.title = "Update"
                createUpdate.text = "Update Product"
                name.setText(product?.name)
                price.setText(product?.price.toString())
            }
        } else {
            binding.toolBar.title = "Create"
            binding.createUpdate.text = "Create Product"
        }
        binding.createUpdate.click {
            if (product != null) {
                viewModel.onEvent(AddUpdateEvent.OnUpdateProduct(product!!, getNewProduct()))
            } else {
                val name = binding.name.text.toString().trim()
                val price = binding.price.text.toString().trim().toInt()
                viewModel.onEvent(
                    AddUpdateEvent.OnCreateProduct(
                        Product(
                            name = name,
                            price = price
                        )
                    )
                )
            }
        }
        observeViewModel()

    }

    private fun getNewProduct(): Map<String, Any> {
        val name = binding.name.text.toString().trim()
        val price = binding.price.text.toString().trim()
        val map = mutableMapOf<String, Any>()
        if (name.isNotEmpty()) {
            map["name"] = name
        }
        if (price.isNotEmpty()) {
            map["price"] = price.toInt()
        }
        if (uid != null) {
            map["userId"] = uid!!
        }
        return map

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is AddUpdateState.Idle -> Unit
                    is AddUpdateState.Loading -> {
                        binding.prg.isVisible = true
                        binding.createUpdate.isVisible = false
                    }
                    is AddUpdateState.Error -> {
                        binding.prg.isVisible = false
                        binding.createUpdate.isVisible = true
                        snackBar(it.message)
                    }
                    is AddUpdateState.SuccessCreate -> {
                        binding.apply {
                            prg.isVisible = false
                            createUpdate.isVisible = true
                            name.text?.clear()
                            price.text?.clear()
                            name.requestFocus()
                        }
                        snackBar("Created")
                    }
                    is AddUpdateState.SuccessUpdate -> {
                        with(binding) {
                            prg.isVisible = false
                            createUpdate.isVisible = true
                        }
                        snackBar("Updated")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}