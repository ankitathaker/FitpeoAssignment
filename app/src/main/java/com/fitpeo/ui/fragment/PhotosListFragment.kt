package com.fitpeo.ui.fragment

import com.fitpeo.R
import com.fitpeo.adapter.PhotoAdapter
import com.fitpeo.databinding.FragmentPhotosListBinding
import com.fitpeo.view_model.PhotoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
* This fragment is used to show list of photos.
* On clicking any photo the user gets redirected to DetailsFragment
* */

class PhotosListFragment : BaseFragment<FragmentPhotosListBinding>(
    layoutId = R.layout.fragment_photos_list
) {

    private val photoViewModel: PhotoViewModel by viewModel()

    override fun initiate(binding: FragmentPhotosListBinding) {

        binding.apply {

            lifecycleOwner = this@PhotosListFragment
            viewModel = photoViewModel
            noDataFound.viewModel = photoViewModel
            noInternetConnection.viewModel = photoViewModel
            somethingWentWrong.viewModel = photoViewModel
        }

        photoViewModel.photoListLiveData.observe(viewLifecycleOwner) { photosList ->

            binding.rvPhotos.adapter = PhotoAdapter(
                photosList = photosList ?: emptyList(),
                viewModel = photoViewModel
            )
        }
    }
}