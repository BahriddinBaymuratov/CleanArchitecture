package com.example.cleanarchitecture.presentation.main.product

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentProductListBinding
import com.example.cleanarchitecture.presentation.base.BaseFragment
import com.example.cleanarchitecture.presentation.main.MainActivity
import kotlinx.coroutines.launch


class ProductListFragment : BaseFragment(R.layout.fragment_product_list) {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductListViewModel>()
    private val productListAdapter by lazy { ProductListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.btnLogOut.click {
            showDialog()
        }
        binding.fab.click {
            findNavController().navigate(R.id.action_productListFragment_to_addUpdateFragment)
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productListAdapter
        }
        productListAdapter.onDeleteClick = {
            val product = bundleOf("product" to it)
            findNavController().navigate(R.id.action_productListFragment_to_detailFragment, product)
        }
        productListAdapter.onEditClick = {
            val product = bundleOf("product" to it)
            findNavController().navigate(R.id.action_productListFragment_to_addUpdateFragment)
        }
        observeViewModel()
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ProductListState.Idle -> Unit
                    is ProductListState.Loading -> {
                        binding.prg.isVisible = true
                    }
                    is ProductListState.Error -> {
                        binding.prg.isVisible = false
                        snackBar(it.text)
                    }
                    is ProductListState.Success -> {
                        binding.prg.isVisible = false
                        productListAdapter.submitList(it.productList)
                    }
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Log Out")
            setMessage("Do you want to log out?")
            setPositiveButton("Yes") { di, _ ->
                di.dismiss()
                (activity as MainActivity).finish()
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}