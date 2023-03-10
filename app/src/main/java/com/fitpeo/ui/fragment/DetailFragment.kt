package com.fitpeo.ui.fragment

import androidx.navigation.fragment.navArgs
import com.fitpeo.R
import com.fitpeo.databinding.FragmentDetailBinding

/*
* This fragment is used to show the details of a photo.
* Opens when user clicks on any photo from PhotosListFragment
* */

class DetailFragment : BaseFragment<FragmentDetailBinding>(
    layoutId = R.layout.fragment_detail
) {

    private val args: DetailFragmentArgs by navArgs()

    override fun initiate(binding: FragmentDetailBinding) {

        binding.photoModel = args.photoModel
    }

}