package com.adisastrawan.storyapp.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    ): View {
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
                        showToast(result.error)
                    }
                }
            }
        }
        playAnimation()

    }
    private fun playAnimation() {
        val createAccount = ObjectAnimator.ofFloat(binding.tvCreateAccount, View.ALPHA, 1f).setDuration(300)
        val join= ObjectAnimator.ofFloat(binding.tvJoin, View.ALPHA, 1f).setDuration(300)
        val tvUsername  = ObjectAnimator.ofFloat(binding.tvRegisterName,View.ALPHA,1f).setDuration(300)
        val editUsername  = ObjectAnimator.ofFloat(binding.edRegisterName,View.ALPHA,1f).setDuration(300)
        val tvEmail  = ObjectAnimator.ofFloat(binding.tvRegisterEmail,View.ALPHA,1f).setDuration(300)
        val editEmail  = ObjectAnimator.ofFloat(binding.edRegisterEmail,View.ALPHA,1f).setDuration(300)
        val tvPassword  = ObjectAnimator.ofFloat(binding.tvRegisterPassword,View.ALPHA,1f).setDuration(300)
        val editPassword  = ObjectAnimator.ofFloat(binding.edRegisterPassword,View.ALPHA,1f).setDuration(300)
        val signUp  = ObjectAnimator.ofFloat(binding.btnRegister,View.ALPHA,1f).setDuration(300)

        val username = AnimatorSet().apply {
            playTogether(tvUsername,editUsername)
        }

        val email = AnimatorSet().apply {
            playTogether(tvEmail, editEmail)
        }
        val password = AnimatorSet().apply {
            playTogether(tvPassword,editPassword)
        }
        AnimatorSet().apply {
            playSequentially(createAccount,join,username,email,password,signUp)
            start()
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