package com.fitpeo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.fitpeo.data_layer.network.PhotoService
import com.fitpeo.data_layer.network.RetrofitInitializer
import com.fitpeo.data_layer.repository.PhotosRepository
import com.fitpeo.view_model.PhotoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        /*
        * Enable vector drawable support.
        * */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        /*
        * Initialize dependency injection.
        * */
        startKoin {

            androidContext(this@AppController)

            modules(
                module {

                    single {
                        OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                            .build()
                    }

                    single {
                        RetrofitInitializer.invoke(
                            okHttpClient = get(),
                            remoteServiceClass = PhotoService::class.java,
                            baseUrl = "https://jsonplaceholder.typicode.com/"
                        )
                    }

                    singleOf(::PhotosRepository)

                    viewModelOf(::PhotoViewModel)
                }
            )
        }
    }
}