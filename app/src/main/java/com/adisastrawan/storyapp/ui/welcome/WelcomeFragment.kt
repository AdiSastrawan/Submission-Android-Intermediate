package com.adisastrawan.storyapp.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentWelcomeBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
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
        playAnimation()
    }

    private fun playAnimation() {
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(300)
        val subWelcome= ObjectAnimator.ofFloat(binding.tvSubWelcome, View.ALPHA, 1f).setDuration(300)
        val signIn  = ObjectAnimator.ofFloat(binding.btnToLogin,View.ALPHA,1f).setDuration(300)
        val signUp  = ObjectAnimator.ofFloat(binding.btnToRegister,View.ALPHA,1f).setDuration(300)
        val button = AnimatorSet().apply {
            playTogether(signIn,signUp)
        }
        AnimatorSet().apply {
            playSequentially(welcome,subWelcome,button)
            start()
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