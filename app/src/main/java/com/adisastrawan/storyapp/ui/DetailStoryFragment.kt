package com.adisastrawan.storyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryFragment : Fragment() {

    private var _binding : FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        with(binding){
            tvDescription.text = arguments?.getString("description")
            tvName.text = arguments?.getString("name")
            Glide.with(requireContext())
                .load(arguments?.getString("photoUrl"))
                .into(ivStoryImage)
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
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}