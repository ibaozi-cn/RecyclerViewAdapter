package com.julive.adapter.core

import android.annotation.SuppressLint
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

interface IAdapter<VM>{
    fun getItem(position: Int): VM?
}

@SuppressLint("RestrictedApi")
interface ILifecycleAdapter : GenericLifecycleObserver {
    fun onDestroy(source: LifecycleOwner) {}
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