package com.julive.adapter.core

import android.annotation.SuppressLint
import android.util.SparseArray
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
}

@SuppressLint("RestrictedApi")
interface LifecycleAdapter : GenericLifecycleObserver {
    val arrayLifeObservers: SparseArray<(source: LifecycleOwner, event: Lifecycle.Event) -> Boolean>
    fun registerLifeObserver(key: Int, observer: (LifecycleOwner, Lifecycle.Event) -> Boolean) {
        if (arrayLifeObservers.indexOfKey(key) < 0)
            arrayLifeObservers.put(key, observer)
    }
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        (0 until arrayLifeObservers.size()).forEach {
            arrayLifeObservers.valueAt(it).invoke(source, event)
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            arrayLifeObservers.clear()
        }
    }
}




