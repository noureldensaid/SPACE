package com.spc.space.ui.main.explore.workspaceDetails

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
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
import com.spc.space.models.createReviewRequest.CreateReviewRequest
import com.spc.space.ui.main.favourites.FavouritesViewModel
import com.spc.space.ui.main.home.LocationViewModel
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import com.spc.space.ui.main.shared_viewmodels.RatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class WorkspaceDetailsFragment : Fragment(R.layout.fragment_workspace_details) {
    private var _binding: FragmentWorkspaceDetailsBinding? = null
    private val binding get() = _binding!!
    private val workspaceDetailsViewModel: WorkSpaceDetailsViewModel by viewModels()
    private val ratingViewModel: RatingViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private val args: WorkspaceDetailsFragmentArgs by navArgs()
    private var isFavorite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWorkspaceDetailsBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = ratingViewModel
        // user locations
        var destLat: Double? = 0.0
        var destLng: Double? = 0.0
        // workspace data
        val workSpaceItem = args.data

        val userToken = dataStoreViewModel.token.value.toString()

        locationViewModel.fetchLocation()
        locationViewModel.location.observe(viewLifecycleOwner, Observer {
            destLat = it?.latitude
            destLng = it?.longitude
            Log.e("location", "$destLat -- $destLng")
        })

        binding.apply {
            workSpaceItem?.let {
                Glide.with(view)
                    .load(it.images?.firstOrNull())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.error_placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(workspaceIv)

                name.text = it.name
                workspaceRegion.text = it.location.region
                workspaceRatingBar.rating = it.avgRate?.plus(0.5F) ?: 0F
                workspaceTime.text =
                    it.schedule.openingTime + " to " + it.schedule.closingTime
            }
            ////
            binding.comment.setOnClickListener {
                val args = Bundle()
                args.putString("workspaceId", workSpaceItem.id)
                findNavController().navigate(
                    R.id.action_workspaceDetailsFragment_to_reportProblemFragment,
                    args
                )
                //findNavController().navigate(WorkspaceDetailsFragmentDirections.actionWorkspaceDetailsFragmentToReportProblemFragment())
            }
            googleMap.setOnClickListener {
                // directions format =>
                // "https://www.google.com/maps/dir/?api=1&origin=37.4220,-122.0841&destination=37.8199,-122.4783"
                // el origin makan el user
                // el destination makan el ws

                val wsLocation =
                    "https://www.google.com/maps/dir/?api=1&origin=$destLat,$destLng&destination=${workSpaceItem.location.latitude},${workSpaceItem.location.longitude}"

                val args = Bundle()
                args.putString("wsLocation", wsLocation)
                findNavController().navigate(
                    R.id.action_workspaceDetailsFragment_to_googleMapFragment,
                    args
                )
            }
            pickRoomBtn.setOnClickListener {
                // send ws id to get it's specific rooms
                val args = Bundle()
                args.putParcelable("workspace", workSpaceItem)
                findNavController().navigate(
                    R.id.action_workspaceDetailsFragment_to_chooseRoomFragment,
                    args
                )
            }
            addFavBtn.setOnClickListener {
                isFavorite = !isFavorite
                if (isFavorite) {
                    binding.addFavBtn.setImageResource(R.drawable.red_fav_ic)

                    // hat require anotation
                    favouritesViewModel.addFavourites(userToken, workSpaceItem!!.id!!)
                } else {
                    binding.addFavBtn.setImageResource(R.drawable.ic_add_fav)
                }


            }
//////////
            binding.workspaceRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
                val reviewRequest =
                    CreateReviewRequest(rating.toInt())
                Log.e("TAG", "onViewCreated: On RATING CHANGED")
                ratingViewModel.createReview(userToken, workSpaceItem!!.id!!, reviewRequest)
            }

            //////////////
            ratingViewModel.getReview(userToken, workSpaceItem.id!!)

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}