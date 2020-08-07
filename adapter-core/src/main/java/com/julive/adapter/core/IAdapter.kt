package com.julive.adapter.core

import android.annotation.SuppressLint
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

@SuppressLint("RestrictedApi")
interface IAdapter<VM> : GenericLifecycleObserver {

    fun getItem(position: Int): VM?
    fun onDestroy(source: LifecycleOwner)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                onDestroy(source)
            }
            else -> {
            }
        }
    }

}
