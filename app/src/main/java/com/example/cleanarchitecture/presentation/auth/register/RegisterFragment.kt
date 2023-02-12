package com.example.cleanarchitecture.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.databinding.FragmentRegisterBinding
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.presentation.base.BaseFragment
import com.example.cleanarchitecture.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : BaseFragment(R.layout.fragment_register) {
    private val viewModel: RegisterViewModel by viewModels()
    private val binding by viewBinding { FragmentRegisterBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.apply {
            btnRegister.click {
                val fullName = fullName.text.toString().trim()
                val email = email.text.toString().trim()
                val password = password.text.toString().trim()

                if (validate(email, password) && fullName.isNotBlank()) {
                    viewModel.register(User(fullName, email, password))
                } else {
                    snackBar("Enter correct data")
                }
            }
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                when (it) {
                    is RegisterState.Idle -> Unit
                    is RegisterState.Loading -> {
                        binding.prg.isVisible = true
                        binding.btnRegister.isVisible = false
                    }
                    is RegisterState.Error -> {
                        binding.prg.isVisible = false
                        binding.btnRegister.isVisible = true
                        snackBar(it.message)
                    }
                    is RegisterState.Success -> {
                        binding.prg.isVisible = false
                        binding.btnRegister.isVisible = true
                        snackBar("Successfully registed")
                        startActivity()
                    }
                }
            }
        }
    }

}