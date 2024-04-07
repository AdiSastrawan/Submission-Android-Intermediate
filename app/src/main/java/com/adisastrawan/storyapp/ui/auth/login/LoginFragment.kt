package com.adisastrawan.storyapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentLoginBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.utils.Result

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel : LoginViewModel by viewModels { factory }
        val authViewModel : AuthViewModel by viewModels { factory }
        binding.btnLogin.setOnClickListener{
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            viewModel.login(email,password).observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data.loginResult
                        authViewModel.saveAuth(data.token,data.name,data.userId)
                        showToast(result.data.message)
                        view.findNavController().navigate(R.id.action_loginFragment_to_listStoryFragment)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(result.error)
                    }
                }
            }
        }
        binding.tvToRegister.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }
    private fun showToast(message:String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
}