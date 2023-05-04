package com.spc.space.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentEditProfile2Binding
import com.spc.space.databinding.FragmentProfileBinding

class EditProfile : Fragment(R.layout.fragment_edit_profile2) {
    private var _binding: FragmentEditProfile2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfile2Binding.bind(view)
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}