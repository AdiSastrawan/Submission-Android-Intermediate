package com.adisastrawan.storyapp.ui.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentRegisterBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.utils.Result


class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel : RegisterViewModel by viewModels { factory }
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.btnRegister.setOnClickListener{
            val email = binding.edRegisterEmail.text.toString()
            val username = binding.edRegisterName.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            Log.d("username",username)
            viewModel.register(username,email, password).observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding.progressBar.visibility = View.GONE
                        val message = result.data.message
                        showToast(message)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    is Result.Error->{
                        binding.progressBar.visibility = View.GONE
                        showToast("There's something wrong "+ result.error)
                    }
                }
            }
        }

    }

    private fun showToast(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}