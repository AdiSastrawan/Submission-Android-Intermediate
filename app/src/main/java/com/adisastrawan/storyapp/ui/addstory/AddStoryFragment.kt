package com.adisastrawan.storyapp.ui.addstory

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.databinding.FragmentAddStoryBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.utils.Result
import com.adisastrawan.storyapp.utils.getImageUri
import com.adisastrawan.storyapp.utils.reduceFileSize
import com.adisastrawan.storyapp.utils.uriToFile

class AddStoryFragment : Fragment() {
    private var _binding : FragmentAddStoryBinding? = null
    private val binding get()=_binding!!
    private var currentImage : Uri? = null
    private var viewModel:AddStoryViewModel? =null
    private var authViewModel:AuthViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(),factory)[AddStoryViewModel::class.java]
        authViewModel = ViewModelProvider(requireActivity(),factory)[AuthViewModel::class.java]
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnCamera.setOnClickListener {
            if(ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }else{
                startCamera()
            }

        }
        binding.buttonAdd.setOnClickListener {
            authViewModel?.getAuth()?.observe(viewLifecycleOwner){
                uploadImage(it.token)
            }
        }
    }

    private fun uploadImage(token:String) {
        currentImage?.let{
            val file = uriToFile(it,requireContext()).reduceFileSize()
            val description = binding.edAddDescription.text.toString()
            viewModel?.postStory(token,file, description)?.observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility =  View.GONE
                        view?.findNavController()?.navigate(R.id.action_addStoryFragment_to_listStoryFragment)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(result.error)
                    }
                }
            }
        }?:showToast(getString(R.string.warning_picture_cannot_empty))
    }

    private fun showToast(message:String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        currentImage = getImageUri(requireContext())
        launcherCameraIntent.launch(currentImage)
    }

    private fun startGallery() {
        launchGalleryIntent.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private val launchGalleryIntent= registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri->
        if (uri != null){
            currentImage = uri
            showImage()
        }else{
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherCameraIntent = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){isSuccess->
        if(isSuccess){
            showImage()
        }

    }
    private fun showImage() {
        binding.ivAddImage.setImageURI(currentImage)
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
        _binding = null
    }
}