package com.spc.space.ui.main.explore.workspaceDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.spc.space.R
import com.spc.space.databinding.FragmentGoogleMapBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GoogleMapFragment : Fragment(R.layout.fragment_google_map) {
    private var _binding: FragmentGoogleMapBinding? = null
    private val binding get() = _binding!!
    private val args: GoogleMapFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGoogleMapBinding.bind(view)
        val wsLocation = args.wsLocation

        binding.mapWebView.apply {
            webViewClient = WebViewClient()
            val webSettings = settings
            webSettings.javaScriptEnabled = true
            webSettings.supportZoom()
            webSettings.defaultTextEncodingName = "utf-8"
            loadUrl(wsLocation)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}