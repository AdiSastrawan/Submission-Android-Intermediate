package com.adisastrawan.storyapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
            tvDetailDescription.text = arguments?.getString("description")
            tvDetailName.text = arguments?.getString("name")
            Glide.with(requireContext())
                .load(arguments?.getString("photoUrl"))
                .into(ivDetailPhoto)
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