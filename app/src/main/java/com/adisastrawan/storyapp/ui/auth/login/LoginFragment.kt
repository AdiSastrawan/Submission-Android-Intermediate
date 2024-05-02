package com.adisastrawan.storyapp.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentLoginBinding
import com.adisastrawan.storyapp.ui.MainActivity
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.utils.Result

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel : LoginViewModel by viewModels { factory }
        val authViewModel : AuthViewModel by viewModels { factory }
        if(requireActivity() is MainActivity){
            (requireActivity() as MainActivity).supportActionBar?.hide()
        }
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
                        view.findNavController().navigate(R.id.action_loginFragment_to_listStoryFragment)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = result.error
                    }
                }
            }
        }

        playAnimation()

    }
    private fun playAnimation() {
        val welcomeBack = ObjectAnimator.ofFloat(binding.tvWelcomeBack, View.ALPHA, 1f).setDuration(300)
        val pleaseLogin= ObjectAnimator.ofFloat(binding.tvPleaseLogIn, View.ALPHA, 1f).setDuration(300)
        val tvEmail  = ObjectAnimator.ofFloat(binding.textView2,View.ALPHA,1f).setDuration(300)
        val editEmail  = ObjectAnimator.ofFloat(binding.edLoginEmail,View.ALPHA,1f).setDuration(300)
        val tvPassword  = ObjectAnimator.ofFloat(binding.textView3,View.ALPHA,1f).setDuration(300)
        val editPassword  = ObjectAnimator.ofFloat(binding.edLoginPassword,View.ALPHA,1f).setDuration(300)
        val signIn  = ObjectAnimator.ofFloat(binding.btnLogin,View.ALPHA,1f).setDuration(300)

        val email = AnimatorSet().apply {
            playTogether(tvEmail,editEmail)
        }
        val password = AnimatorSet().apply {
            playTogether(tvPassword,editPassword)
        }
        AnimatorSet().apply {
            playSequentially(welcomeBack,pleaseLogin,email,password,signIn)
            start()
        }
    }
    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (requireActivity() as MainActivity).supportActionBar?.hide()
        }
    }

    override fun onPause() {
        super.onPause()
        if(requireActivity() is MainActivity){
            (requireActivity() as MainActivity).supportActionBar?.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        if(requireActivity() is MainActivity){
            (requireActivity() as MainActivity).supportActionBar?.show()
        }
    }
}