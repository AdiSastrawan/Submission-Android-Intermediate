package com.adisastrawan.storyapp.ui.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.api.response.ListStoryResponse
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.databinding.FragmentMapsBinding
import com.adisastrawan.storyapp.ui.ViewModelFactory
import com.adisastrawan.storyapp.ui.auth.AuthViewModel
import com.adisastrawan.storyapp.utils.Result

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private lateinit var viewModel: MapsViewModel
    private val boundsBuilder = LatLngBounds.Builder()
    private val binding get() = _binding!!
    private val callback = OnMapReadyCallback { googleMap ->

            viewModel.getStoriesWithLocation().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        data.forEach { data ->
                            val latLng = LatLng(data.lat!!, data.lon!!)
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title(data.username)
                                    .snippet(data.description)
                            )
                            boundsBuilder.include(latLng)
                        }
                        val bounds: LatLngBounds = boundsBuilder.build()
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showToast(result.error)
                    }
                }

            }



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)[MapsViewModel::class.java]
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}