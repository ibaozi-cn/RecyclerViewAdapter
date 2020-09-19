package com.julive.adapter.filter

import android.util.SparseArray
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.LifecycleAdapter

inline fun <reified Adapter : RecyclerView.Adapter<*>> RecyclerView.Adapter<*>.filter(
    constraint: String,
    filter: Adapter.(String) -> Adapter
): Adapter {
    return filter.invoke(this as Adapter, constraint)
}

val filterAdapterCache by lazy { SparseArray<RecyclerView.Adapter<*>>() }

val filterAdapterCacheKey by lazy {
    filterAdapterCache.hashCode()
}

val lifecycleObserver by lazy {
    { _: LifecycleOwner, event: Lifecycle.Event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            filterAdapterCache.clear()
        }
        true
    }
}

inline fun <reified Adapter : RecyclerView.Adapter<*>> RecyclerView.filter(constraint: String?, filter: Adapter.(String) -> Adapter) {

    if (adapter == null) throw NullPointerException("adapter must be set first")

    val firstAdapterKey = hashCode()
    filterAdapterCache.get(firstAdapterKey)?: filterAdapterCache.put(firstAdapterKey,adapter)

    if (constraint.isNullOrEmpty()) {
        adapter = filterAdapterCache.get(firstAdapterKey)
        with(adapter as? LifecycleAdapter) {
            this?.registerLifeObserver(filterAdapterCacheKey, lifecycleObserver)
        }
        return
    }

    val newAdapter =
        filterAdapterCache.get(constraint.hashCode()) ?: filterAdapterCache.get(firstAdapterKey)?.filter(constraint, filter).also {
            filterAdapterCache.put(constraint.hashCode(), it)
        }

    this.adapter = newAdapter

    with(adapter as? LifecycleAdapter) {
        this?.registerLifeObserver(filterAdapterCacheKey, lifecycleObserver)
    }

}