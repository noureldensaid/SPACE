package com.spc.space.ui.auth.onboarding.splashscreen

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentLottiLoadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LottiLoadingFragment : Fragment(R.layout.fragment_lotti_loading) {

    private var _binding: FragmentLottiLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLottiLoadingBinding.bind(view)
        binding.lottiSplashAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}

            override fun onAnimationEnd(p0: Animator) {
                // --> todo authentication function and finish login activity
                findNavController().navigate(R.id.action_lottiLoadingFragment_to_welcomePlaceholderFragment)

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