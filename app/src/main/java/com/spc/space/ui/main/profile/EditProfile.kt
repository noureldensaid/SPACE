package com.spc.space.ui.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.spc.space.R
import com.spc.space.databinding.FragmentEditProfileBinding
import com.spc.space.models.updateProfile.UpdateProfileRequest
import com.spc.space.ui.auth.AuthActivity
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfile : Fragment(R.layout.fragment_edit_profile) {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    // Permission launcher for requesting storage permission
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickPhoto()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission denied. Unable to pick a photo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    // Activity result launcher for picking a photo
    private val pickPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                if (imageUri != null) {
                    dataStoreViewModel.saveUri(imageUri)
                    Log.e("imageUri", ": $imageUri")
                    // Load the selected image into the profileCircleImageView using Glide
                    Glide.with(this)
                        .load(imageUri)
                        .into(binding.profilePicture)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)

        val token = dataStoreViewModel.token.value.toString()
        // Navigate up when the backIc is clicked
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.apply {
            profileViewModel.user.observe(viewLifecycleOwner, Observer {
                if (it.user != null) {
                    profileUserName.editText?.hint = it.user.userName.toString().capitalize()
                    profileUserPhone.editText?.hint = "0" + it.user.phone.toString().capitalize()
                    profileUserEmail.editText?.hint = it.user.email.toString().capitalize()
                }
            })
        }


        dataStoreViewModel.userPic.observe(viewLifecycleOwner, Observer {
            Glide.with(view)
                .load(it)
                .transform(CenterCrop(), RoundedCorners(24))
                .error(R.drawable.ic_dummy_user)
                .placeholder(R.drawable.ic_dummy_user)
                .into(binding.profilePicture)
            Log.e("TAG", "onViewCreated: $it ")
        })

        // Open the photo picker when the profileCircleImageView is clicked
        binding.editProfilePictureButton.setOnClickListener {
            checkPermissionAndPickPhoto()
        }


        val oldUsername =
            profileViewModel.user.value?.user?.userName.toString().lowercase().capitalize().trim()
        val oldEmail =
            profileViewModel.user.value?.user?.email.toString().lowercase().capitalize().trim()

        val oldPhone =
            profileViewModel.user.value?.user?.phone.toString().lowercase().capitalize().trim()

        Log.e("oldUsername", oldUsername)

        binding.saveBtn.setOnClickListener {
            binding.apply {
                val username = profileUserName.editText?.text.toString().trim()
                val email = profileUserEmail.editText?.text.toString().trim()
                val phone = profileUserPhone.editText?.text.toString().trim()
                val userId = profileViewModel.user.value?.user?.id.toString()

                if (username.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()
                    && token.isNotEmpty() && userId.isNotEmpty()
                ) {
                    if (!oldPhone.equals(phone) && !oldUsername.equals(username) && !oldEmail.equals(
                            email
                        )
                    ) {
                        if (isPhoneNumberValid(phone) && isEmailValid(email)) {
                            Log.e("username", username)
                            Log.e("oldUsername", oldUsername)
                            editProfileViewModel.updateUserProfile(
                                token, userId, UpdateProfileRequest(
                                    username = username,
                                    phone = phone,
                                    email = email
                                )
                            )
                            // navigate to sign in
                            dataStoreViewModel.clearToken()
                            activity?.finish()
                            startActivity(Intent(activity, AuthActivity::class.java))
                        }
                    }
                }

            }
        }


        binding.dismissBtn.setOnClickListener {
            binding.apply {
                profileUserName.clearFocus()
                profileUserPhone.clearFocus()
                profileUserEmail.clearFocus()
            }
        }

    }

    private fun checkPermissionAndPickPhoto() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, proceed with photo picking
            pickPhoto()
        } else {
            // Permission not granted, request storage permission
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            // Explain why the permission is needed
            Toast.makeText(
                requireContext(),
                "Storage permission is required to pick a photo",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Launch the permission request
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun pickPhoto() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }

        // Launch the photo picker activity
        pickPhotoLauncher.launch(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
        private const val PICK_IMAGE_REQUEST_CODE = 456

        fun newInstance(): EditProfile {
            return EditProfile()
        }
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val phoneRegex = Regex("^01[0-9]{9}\$")
        return phoneNumber.matches(phoneRegex)
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        return email.matches(emailRegex)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
