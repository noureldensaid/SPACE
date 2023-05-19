package com.spc.space.ui.main.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.spc.space.R
import com.spc.space.databinding.FragmentEditProfileBinding
import com.spc.space.ui.main.shared_viewmodels.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream


@AndroidEntryPoint
class EditProfile : Fragment(R.layout.fragment_edit_profile) {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    var imgLink: Uri? = null

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
                    // Load the selected image into the profileCircleImageView using Glide
                    Glide.with(this)
                        .load(imageUri)
                        .into(binding.profilePicture)
                    imgLink = imageUri
                }
            }
        }

    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)


        val token = dataStoreViewModel.token.value.toString()
        // Navigate up when the backIc is clicked
        binding.backIc.setOnClickListener {
            findNavController().navigateUp()
        }

        profileViewModel.user.observe(viewLifecycleOwner, Observer {
            binding.apply {
                Glide.with(view)
                    .load(it.user.profilePic)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(24))
                    .error(R.drawable.ic_dummy_user)
                    .placeholder(R.drawable.ic_dummy_user)
                    .into(binding.profilePicture)
            }
        })

        // Open the photo picker when the profileCircleImageView is clicked
        binding.editProfilePictureButton.setOnClickListener {
            checkPermissionAndPickPhoto()
        }


        binding.profileUserNameTv.setOnClickListener {
//            Toast.makeText(
//                requireContext(),
//                imgLink.,
//                Toast.LENGTH_SHORT
//            ).show()
        }

        binding.saveBtn.setOnClickListener {
            if (token.isNotEmpty() && imgLink != null) {
                val file: File? = uriToFile(requireContext(), imgLink!!)
                if (file != null) {

//                     val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestFile)


//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    val formData = MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("image", file.name, requestFile)
//                        .build()


                    val mediaType = "image/jpg".toMediaTypeOrNull()
                    val inputStream: InputStream? = context?.contentResolver?.openInputStream(
                        imgLink!!
                    )
                    val data: ByteArray? = inputStream?.readBytes()


                    val requestBody1 = RequestBody.create(mediaType, data!!)
                    Log.d("TAG", "requestBody: " + requestBody1.toString())
                    val requestBody2 = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "t.jpg", requestBody1)
                        .build()
                    Log.d("TAG", "requestBody: " + requestBody2.toString())


////                    uploadProfilePic(token, file)
//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    val imagePart =
//                        MultipartBody.Part.createFormData("image", file.name, requestFile)

                    editProfileViewModel.updateUserProfilePic(
                        token,
                        requestBody2,
                    )
                 }
            }
        }

        binding.apply {
            profileViewModel.user.observe(viewLifecycleOwner, Observer {
                if (it.user != null) {
                    profileUserName.editText?.setText(it.user.userName.toString())
                    profileUserPhone.editText?.setText("0" + it.user.phone.toString())
                    profileUserEmail.editText?.setText(it.user.email.toString())
                }
            })
        }

        binding.dismissBtn.setOnClickListener {
            binding.apply {
                profileUserName.clearFocus()
                profileUserPhone.clearFocus()
                profileUserEmail.clearFocus()
            }
        }

    }

//    private fun uploadProfilePic(token: String, img: File) {
//        editProfileViewModel.updateUserProfilePic(token, img)
//
//    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val filePath = it.getString(columnIndex)
                if (filePath != null) {
                    return File(filePath)
                }
            }
        }
        return null
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
