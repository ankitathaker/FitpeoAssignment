package com.fitpeo.utilities

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.fitpeo.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

@BindingAdapter(
    "app:imageUrl",
    "app:loader"
)
fun loadImage(
    imageView: ImageView,
    url: String,
    loader: ProgressBar
) {
    loader.visibility = View.VISIBLE
    Picasso.get()
        .load(url)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                loader.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                loader.visibility = View.GONE
            }
        })
}