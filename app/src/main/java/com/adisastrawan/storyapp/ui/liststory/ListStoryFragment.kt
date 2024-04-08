package com.adisastrawan.storyapp.ui.liststory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adisastrawan.storyapp.data.api.response.ListStoryItem
import com.adisastrawan.storyapp.databinding.FragmentListStoryBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.utils.Result

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
        val viewModel : ListStoryViewModel by viewModels {factory  }
        val authViewModel : AuthViewModel by viewModels{factory}
        val layoutManager = LinearLayoutManager(activity)
        binding.rvStory.layoutManager = layoutManager
        getStories(viewModel)
        authViewModel.getAuth().observe(viewLifecycleOwner){
            binding.tvTest.text = "Hi,${it.name} with id ${it.userId}"
        }

    }

    private fun getStories(viewModel:ListStoryViewModel){
        viewModel.getStories().observe(viewLifecycleOwner){result->
            when(result){
                is Result.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility =  View.GONE
                    val stories = result.data
                    setStories(stories)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(result.error)
                }
            }

        }
    }

    private fun setStories(stories:List<ListStoryItem>){
        val adapter = ListStoryAdapter()
        adapter.submitList(stories)
        binding.rvStory.adapter = adapter

    }

    private fun showToast(message:String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}