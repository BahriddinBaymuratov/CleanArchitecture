package com.example.cleanarchitecture.presentation.main.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentDetailBinding
import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.presentation.base.BaseFragment
import com.example.cleanarchitecture.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    private val binding by viewBinding { FragmentDetailBinding.bind(it) }
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getParcelable("product")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            toolBar.title = product.name
            prg.isVisible = false
            btnDelete.isVisible = true
            name.text = product.name
            price.text = product.price.toString()
            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnDelete.click {
                viewModel.deleteProduct(product)
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is DetailState.Idle -> Unit
                    is DetailState.Loading -> {
                        binding.apply {
                            prg.isVisible = true
                            btnDelete.isVisible = false
                        }
                    }
                    is DetailState.Success -> {
                        binding.apply {
                            prg.isVisible = false
                            btnDelete.isVisible = true
                            snackBar("Deleted")
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
}