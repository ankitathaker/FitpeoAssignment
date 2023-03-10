package com.fitpeo

import com.fitpeo.data_layer.exception.NoDataException
import com.fitpeo.data_layer.exception.NoInternetException
import com.fitpeo.data_layer.network.PhotoService
import com.fitpeo.data_layer.repository.PhotosRepository
import com.fitpeo.model.PhotoModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class PhotosRepositoryTest {

    @MockK
    private lateinit var photoService: PhotoService

    @MockK
    private lateinit var photosRepository: PhotosRepository

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
    }

    @Test
    fun `should return photos list on success`(): Unit = runBlocking {
        val resultResponse = listOf(
            mockkClass(PhotoModel::class),
            mockkClass(PhotoModel::class),
            mockkClass(PhotoModel::class)
        )
        coEvery { photoService.getPhotos() } returns Response.success(resultResponse)
        coEvery { photosRepository.getPhotos() } returns Result.success(resultResponse)

        val actualResponse = photosRepository.getPhotos()

        assertTrue {
            actualResponse.getOrNull()?.isNotEmpty() == true
        }
    }

    @Test
    fun `should return failure NoDataException on fetch photos empty response`() = runBlocking {
        val resultResponse = listOf<PhotoModel>()

        coEvery { photoService.getPhotos() } returns Response.success(resultResponse)
        coEvery { photosRepository.getPhotos() } returns Result.failure(NoDataException())

        val actualResponse = photosRepository.getPhotos()

        assertTrue {
            actualResponse.exceptionOrNull() is NoDataException
        }
    }

    @Test
    fun `should return failure NoInternet when internet is not connected`() = runBlocking {
        val resultResponse = listOf<PhotoModel>()

        coEvery { photoService.getPhotos() } returns Response.success(resultResponse)
        coEvery { photosRepository.getPhotos() } returns Result.failure(NoInternetException())

        val actualResponse = photosRepository.getPhotos()

        assertTrue {
            actualResponse.exceptionOrNull() is NoInternetException
        }
    }
}