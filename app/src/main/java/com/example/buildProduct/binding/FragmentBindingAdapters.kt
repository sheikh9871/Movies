package com.example.buildProduct.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.buildProduct.R
import com.example.buildProduct.extensions.tryCatch
import com.example.buildProduct.helpers.AppExecutors
import javax.inject.Inject

object FragmentBindingAdapters  {


    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, placeholder: Drawable? = null) {
        tryCatch {
            imageView.context.applicationContext.let { context ->
                if (placeholder != null) {
                    Glide.with(context).load(url)
                        .error(placeholder)
                        .placeholder(placeholder)
                        .into(imageView)
                } else {
                    Glide.with(context).load(url)
                        .error(R.drawable.image_not_available)
                        .into(imageView)
                }
            }
        }
    }
}