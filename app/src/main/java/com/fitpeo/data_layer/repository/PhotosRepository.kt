package com.fitpeo.data_layer.repository

import com.fitpeo.data_layer.exception.ApiException
import com.fitpeo.data_layer.exception.NoDataException
import com.fitpeo.data_layer.exception.NoInternetException
import com.fitpeo.data_layer.network.PhotoService
import com.fitpeo.model.PhotoModel
import retrofit2.Response
import java.net.UnknownHostException


/*
* Data layer:
* Repository to fetch Photos
* */

class PhotosRepository(
    private val photoService: PhotoService
) {

    private suspend fun <T> handleResponse(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()
            val responseBody = response.body()
            return if (response.isSuccessful) {

                return if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure<T>(NoDataException())
                }
            } else {
                Result.failure<T>(ApiException())
            }
        } catch (e: UnknownHostException) {
            Result.failure<T>(NoInternetException())
        }
    }

    suspend fun getPhotos(): Result<List<PhotoModel>> {
        return handleResponse {
            photoService.getPhotos()
        }
    }
}