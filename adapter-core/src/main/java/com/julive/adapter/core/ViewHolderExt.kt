package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R

inline fun <Adapter> RecyclerView.ViewHolder.getAdapter(): Adapter? {
    return this.itemView.getTag(R.id.adapter) as? Adapter
}

inline fun RecyclerView.ViewHolder.getRecyclerView(): RecyclerView? {
    return this.itemView.getTag(R.id.adapter_recyclerView) as? RecyclerView
}

inline fun <VM : ViewModelType> RecyclerView.ViewHolder.getViewModel(): VM? {
    return this.itemView.getTag(R.id.adapter_item) as? VM
}

inline fun <M> RecyclerView.ViewHolder.getModel(): M? {
    return (this.itemView.getTag(R.id.adapter_item) as? ViewModelType)?.model as? M
}

inline fun <T : View> RecyclerView.ViewHolder.getView(@IdRes viewId: Int): T {
    return itemView.findViewById(viewId) as T
}

typealias BindView = DefaultViewHolder.(payloads: List<Any>) -> Unit
typealias ViewHolderType = DefaultViewHolder.() -> Unit

open class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Subscriber {

    private var bindView: BindView? = null
    private var unBindView: ViewHolderType? = null
    private var onViewAttached: ViewHolderType? = null
    private var onViewDetached: ViewHolderType? = null

    fun onBindViewHolder(f: BindView) { bindView = f }
    fun onUnBindViewHolder(f: ViewHolderType) { unBindView = f }
    fun onViewAttachedToWindow(f: ViewHolderType) { onViewAttached = f }
    fun onViewDetachedFromWindow(f: ViewHolderType) { onViewAttached = f }

    override fun onBindViewHolder(position: Int, payloads: List<Any>) { bindView?.invoke(this, payloads) }
    override fun unBindViewHolder(position: Int) { unBindView?.invoke(this) }
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder, position: Int) {
        onViewAttached?.invoke(holder as DefaultViewHolder)
    }
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder, position: Int) {
        onViewDetached?.invoke(holder as DefaultViewHolder)
    }

}
