package com.fitpeo.view_model

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.fitpeo.data_layer.exception.ApiException
import com.fitpeo.data_layer.exception.NoDataException
import com.fitpeo.data_layer.exception.NoInternetException
import com.fitpeo.data_layer.repository.PhotosRepository
import com.fitpeo.model.PhotoModel
import com.fitpeo.ui.fragment.PhotosListFragmentDirections
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photosRepository: PhotosRepository
) : BaseViewModel() {

    init {
        loadPhotos()
    }

    override fun tryAgain(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        toggleLayoutVisibility(
            contentVisibility = View.GONE,
            noInternetVisibility = View.GONE,
            noDataVisibility = View.GONE,
            errorVisibility = View.GONE
        )
        loadPhotos()
    }

    private val _photoListLiveData = MutableLiveData<List<PhotoModel>?>()
    val photoListLiveData: LiveData<List<PhotoModel>?> = _photoListLiveData

    /*
   * Below function is used to fetch list of photos
   * */

    private fun loadPhotos() {

        loaderVisibility.postValue(View.VISIBLE)

        viewModelScope.launch {
            val photosResponse = photosRepository.getPhotos()
            if (photosResponse.isSuccess) {
                val dataList = photosResponse.getOrNull()
                if (!dataList.isNullOrEmpty()) {
                    _photoListLiveData.postValue(dataList)
                    toggleLayoutVisibility(
                        contentVisibility = View.VISIBLE,
                        noInternetVisibility = View.GONE,
                        noDataVisibility = View.GONE,
                        errorVisibility = View.GONE
                    )
                } else {
                    toggleLayoutVisibility(
                        contentVisibility = View.GONE,
                        noInternetVisibility = View.GONE,
                        noDataVisibility = View.VISIBLE,
                        errorVisibility = View.GONE
                    )
                }
                loaderVisibility.postValue(View.GONE)
            } else {
                when (photosResponse.exceptionOrNull()) {
                    is NoDataException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.GONE,
                            noDataVisibility = View.VISIBLE,
                            errorVisibility = View.GONE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                    is NoInternetException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.VISIBLE,
                            noDataVisibility = View.GONE,
                            errorVisibility = View.GONE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                    is ApiException -> {
                        toggleLayoutVisibility(
                            contentVisibility = View.GONE,
                            noInternetVisibility = View.GONE,
                            noDataVisibility = View.GONE,
                            errorVisibility = View.VISIBLE
                        )
                        loaderVisibility.postValue(View.GONE)
                    }
                }
            }
        }
    }

    /*
    * Navigate user to Detail page on selection of photo to show its details
    * */

    fun onPhotoSelected(view: View, photoModel: PhotoModel) {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)

        view
            .findNavController()
            .navigate(
                PhotosListFragmentDirections.actionPhotosListFragmentToDetailFragment(
                    photoModel = photoModel
                )
            )
    }
}