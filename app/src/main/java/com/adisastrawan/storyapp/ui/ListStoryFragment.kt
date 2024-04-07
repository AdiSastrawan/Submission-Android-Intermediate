package com.adisastrawan.storyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adisastrawan.storyapp.databinding.FragmentListStoryBinding
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ListStoryFragment : Fragment() {
    private var _binding : FragmentListStoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListStoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        val authViewModel : AuthViewModel by viewModels{factory}

        authViewModel.getToken().observe(viewLifecycleOwner){
            Log.d("TOKEN_KEY",it)
        }
        authViewModel.getUserId().observe(viewLifecycleOwner){
            Log.d("USER_ID",it)
        }
        authViewModel.getUsername().observe(viewLifecycleOwner){
            binding.tvTest.text = "Hi $it"
        }
    }

}