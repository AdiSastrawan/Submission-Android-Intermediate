package com.adisastrawan.storyapp.ui.liststory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.databinding.FragmentListStoryBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel

class ListStoryFragment : Fragment() {
    private var _binding : FragmentListStoryBinding? = null
    private val binding get() = _binding!!
    private var viewModel:ListStoryViewModel? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListStoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(),factory)[ListStoryViewModel::class.java]
        val authViewModel : AuthViewModel by viewModels{factory}
        val layoutManager = LinearLayoutManager(activity)
        binding.rvStory.layoutManager = layoutManager
        authViewModel.getAuth().observe(viewLifecycleOwner){
            if(it.token.isEmpty()){
                view.findNavController().navigate(R.id.action_listStoryFragment_to_welcomeFragment)
            }
        }
        setStories()
        binding.fabAddStory.setOnClickListener {
            view.findNavController().navigate(R.id.action_listStoryFragment_to_addStoryFragment)
        }

    }

    private fun setStories(){
        val adapter = ListStoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer=LoadingStateAdapter{adapter.retry()}
        )
        viewModel?.getStories()?.observe(viewLifecycleOwner){stories->
            adapter.submitData(lifecycle,stories)
            if(stories == null){
                binding.tvNoExist.visibility=View.VISIBLE
            }else{
                binding.tvNoExist.visibility = View.GONE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}