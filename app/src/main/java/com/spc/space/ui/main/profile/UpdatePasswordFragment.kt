package com.spc.space.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spc.space.R
import com.spc.space.databinding.FragmentUpdatePasswordBinding
import com.spc.space.models.updatePassword.UpdatePasswordRequest
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password) {

    private val updatePasswordViewModel: UpdatePasswordViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    private var _binding: FragmentUpdatePasswordBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdatePasswordBinding.bind(view)
        val token = dataStoreViewModel.token.value.toString()
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnResetPassword.setOnClickListener {
            val oldPass = binding.editTextOldpass.editText?.text.toString().trim()
            val newPass = binding.editTextNewPassword.editText?.text.toString().trim()
            val confirmNewPassword =
                binding.editTextNewPasswordAgain.editText?.text.toString().trim()
            if (oldPass != newPass && newPass == confirmNewPassword) {
                val updatePasswordRequest = UpdatePasswordRequest(
                    currentPassword = oldPass,
                    newPassword = newPass,
                    newCPassword = confirmNewPassword
                )
                updatePasswordViewModel.updatePassword(token, updatePasswordRequest)
                findNavController().navigateUp()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
