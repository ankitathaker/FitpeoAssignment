package com.fitpeo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.R
import com.fitpeo.databinding.CardPhotoBinding
import com.fitpeo.model.PhotoModel
import com.fitpeo.view_model.PhotoViewModel

class PhotoAdapter(
    private val photosList: List<PhotoModel>,
    private val viewModel: PhotoViewModel
) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_photo, parent, false)
        return PhotoHolder(CardPhotoBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photoModel = photosList[position]
        holder.bind(photoModel)
    }

    inner class PhotoHolder(val layoutBinding: CardPhotoBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun bind(model: PhotoModel) {
            layoutBinding.apply {
                viewModel = this@PhotoAdapter.viewModel
                photoModel = model
            }
        }
    }
}