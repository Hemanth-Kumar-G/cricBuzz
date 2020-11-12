package com.hemanth.cricbuzz.common

import android.app.Activity
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.drawee.view.SimpleDraweeView


object AppBindingAdapters {

    @BindingAdapter("android:imageUri")
    @JvmStatic
    fun setImageUri(view: SimpleDraweeView, imageUri: String?) {
        imageUri?.let {
            view.setImageURI(imageUri)
        } ?: view.setImageURI("")
    }


    @BindingAdapter("viewLoading")
    @JvmStatic
    fun hidingWhenLoading(view: View, loading: Boolean) {
        if (!loading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("onRefresh")
    @JvmStatic
    fun setRefresh(view: SwipeRefreshLayout, listener: SwipeRefreshLayout.OnRefreshListener) {
        val newValue = SwipeRefreshLayout.OnRefreshListener {
            view.isRefreshing = false
            listener.onRefresh()
        }
        view.setOnRefreshListener(newValue)
    }

    @BindingAdapter("onBackClick")
    @JvmStatic
    fun setOnBackPress(view: View, value: Boolean) {
        view.setOnClickListener {
            val activity: Activity = view.context as Activity
            activity.onBackPressed()
        }
    }

}
