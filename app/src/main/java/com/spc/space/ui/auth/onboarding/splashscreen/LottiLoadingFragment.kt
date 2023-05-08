package com.spc.space.ui.auth.onboarding.splashscreen

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentLottiLoadingBinding
import com.spc.space.ui.DataStoreViewModel
import com.spc.space.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LottiLoadingFragment : Fragment(R.layout.fragment_lotti_loading) {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentLottiLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLottiLoadingBinding.bind(view)
        binding.lottiSplashAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                // -->  authentication function and finish login activity
                dataStoreViewModel.token.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        startActivity(Intent(activity, MainActivity::class.java))
                        activity?.finish()
                        Log.e("token value", "onAnimationEnd: ${dataStoreViewModel.token.value}")
                    } else {
                        findNavController().navigate(R.id.action_lottiLoadingFragment_to_welcomeViewPagerFragment)
                    }
                })
            }

            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}