package com.spc.space.ui.main.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentProfileBinding
import com.spc.space.ui.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: Dialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.apply {
            editProfileText.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
            changePasswordText.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment())
            }
            reportProblem.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToReportProblemFragment())
            }
            logoutBtn.setOnClickListener { logout() }


        }


    }


    private fun logout() {
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = dialogBinding.findViewById<Button>(R.id.btnYes).setOnClickListener {
            // signOut()
            dataStoreViewModel.clearToken()
            activity?.finish()
        }
        val btnNo = dialogBinding.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        dialog = Dialog(requireContext())
        dialog.dismiss()
    }


}

