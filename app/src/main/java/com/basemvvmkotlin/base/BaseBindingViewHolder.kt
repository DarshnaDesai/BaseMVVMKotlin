package com.basemvvmkotlin.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

//Created by imobdev-rujul on 23/11/18
class BaseBindingViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        clickListener.onViewClick(v!!, adapterPosition)
    }

    var binding: ViewDataBinding
    var clickListener: ClickListener

    constructor(binding: ViewDataBinding, clickListener: ClickListener) : super(binding.root) {
        binding.root.setOnClickListener(this)
        this.binding = binding
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onViewClick(view: View, position: Int)
    }
}