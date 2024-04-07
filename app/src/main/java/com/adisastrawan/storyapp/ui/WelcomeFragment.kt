package com.adisastrawan.storyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentWelcomeBinding
import com.adisastrawan.storyapp.ui.auth.AuthViewModel


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
        authViewModel.getToken().observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}