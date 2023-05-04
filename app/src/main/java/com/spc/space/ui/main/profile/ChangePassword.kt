package com.spc.space.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentChangePassword2Binding
import com.spc.space.databinding.FragmentEditProfile2Binding

class ChangePassword : Fragment(R.layout.fragment_change_password2) {
    private var _binding: FragmentChangePassword2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChangePassword2Binding.bind(view)
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}