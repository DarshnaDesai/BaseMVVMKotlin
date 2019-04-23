package com.basemvvmkotlin.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.basemvvmkotlin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View,  visibility: MutableLiveData<Int>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}


/**
 * Sets an Image to an ImageView
 * @param view the ImageView on which to set the Image
 * @param url the url to get the image and set to the ImageView
 */
@BindingAdapter("imageUrl")
fun loadImageUrl(view: ImageView, url: String) {
    Glide.with(view.context).load(url).apply(RequestOptions()
            .error(R.mipmap.ic_launcher)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop())
            .into(view)
}

/**
 * Sets an Image to an ImageView
 * @param view the ImageView on which to set the Image
 * @param url the url to get the image and set to the ImageView
 * @param drawable the drawable image which you want to load as placeholder
 */
@BindingAdapter("imageUrl","imageDefault")
fun loadImage(view: ImageView, url: String, drawable: Drawable) {
    Glide.with(view.context).load(url).apply(RequestOptions()
            .error(drawable)
            .placeholder(drawable)
            .centerCrop())
            .into(view)
}


/**
 * Sets an Image to an ImageView
 * @param view the ImageView on which to set the Image
 * @param url the url to get the image and set to the ImageView
 * @param drawable the resource id which you want to load as placeholder
 */
@BindingAdapter("imageUrl","imageDefault")
fun loadImage(view: ImageView, url: String, resId: Int) {
    var drawable = ContextCompat.getDrawable(view.context,resId)
    Glide.with(view.context).load(url).apply(RequestOptions()
            .error(drawable)
            .placeholder(drawable)
            .centerCrop())
            .into(view)
}