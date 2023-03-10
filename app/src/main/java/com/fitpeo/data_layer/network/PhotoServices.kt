package com.fitpeo.data_layer.network

import com.fitpeo.model.PhotoModel
import retrofit2.Response
import retrofit2.http.GET

/*
* Remote API service.
* */

interface PhotoService {
    @GET("photos")
    suspend fun getPhotos():Response<List<PhotoModel>>
}