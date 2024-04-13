package com.adisastrawan.storyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentWelcomeBinding
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.ui.auth.dataStore
import kotlinx.coroutines.flow.first


class WelcomeFragment : Fragment() {
    private var _binding : FragmentWelcomeBinding? = null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val authViewModel : AuthViewModel by viewModels{factory}
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        authViewModel.getAuth().observe(viewLifecycleOwner){
            if(it.token.isNotEmpty()){
                view.findNavController().navigate(R.id.action_welcomeFragment_to_listStoryFragment)
            }
        }
        binding.btnToLogin.setOnClickListener{
            view.findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.btnToRegister.setOnClickListener{
            view.findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
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
        _binding = null
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}