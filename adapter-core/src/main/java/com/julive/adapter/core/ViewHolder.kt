package com.julive.adapter.core


import android.content.Context
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

class DefaultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    /**
     * views缓存
     */
    private val views: SparseArray<View> = SparseArray()
    val mContext: Context = view.context

    fun <T : View> getView(@IdRes viewId: Int): T? {
        return retrieveView(viewId)
    }

    private fun <T : View> retrieveView(@IdRes viewId: Int): T? {
        var view = views[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            if (view == null) return null
            views.put(viewId, view)
        }
        return view as T
    }

}