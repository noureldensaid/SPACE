package com.spc.space.ui.main.explore.workspaceDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.FragmentWorkspaceDetailsBinding
import com.spc.space.ui.main.home.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkspaceDetailsFragment : Fragment(R.layout.fragment_workspace_details) {
    private var _binding: FragmentWorkspaceDetailsBinding? = null
    private val binding get() = _binding!!
    private val locationViewModel by viewModels<LocationViewModel>()
    private val args: WorkspaceDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWorkspaceDetailsBinding.bind(view)
        val data = args.data
        var destLat = 0.0
        var destLng = 0.0
        locationViewModel.fetchLocation()


        locationViewModel.location.observe(viewLifecycleOwner, Observer {
            destLat = it?.latitude ?: 0.0
            destLng = it?.longitude ?: 0.0
            Log.e("location", "$destLat -- $destLng")
        })



        binding.apply {
            Glide.with(view)
                .load(data.urls.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop(), RoundedCorners(24))
                .error(R.drawable.error_placeholder)
                .placeholder(R.drawable.placeholder)
                .into(workspaceIv)
        }

        binding.pickRoomBtn.setOnClickListener {
            findNavController().navigate(R.id.action_workspaceDetailsFragment_to_chooseRoomFragment)
        }

        binding.googleMap.setOnClickListener {
            // directions format =>
            // "https://www.google.com/maps/dir/?api=1&origin=37.4220,-122.0841&destination=37.8199,-122.4783"

            // el origin mkan el user
            // el destination mkan el ws
            //
            val wsLocation =
                "https://www.google.com/maps/dir/?api=1&origin=$destLat,$destLng&destination=31.246053526480758,29.97454506864482"

            val args = Bundle()
            args.putString("wsLocation", wsLocation)
            findNavController().navigate(
                R.id.action_workspaceDetailsFragment_to_googleMapFragment,
                args
            )
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}