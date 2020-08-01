package com.julive.adapter.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

open class WrapAdapter<VH : RecyclerView.ViewHolder>(private var mWrappedAdapter: RecyclerView.Adapter<VH>) :
    RecyclerView.Adapter<VH>(), WrapAdapterDataObserver.Subscriber {

    private val emptyList by lazy {
        emptyList<Any>()
    }

    init {
        val wrapAdapterDataObserver =
            WrapAdapterDataObserver(WeakReference(this), WeakReference(mWrappedAdapter), null)
        mWrappedAdapter.registerAdapterDataObserver(wrapAdapterDataObserver)
        super.setHasStableIds(mWrappedAdapter.hasStableIds())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return mWrappedAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return mWrappedAdapter.itemCount
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(
            holder,
            position,
            emptyList
        )
    }

    override fun getItemId(position: Int): Long {
        return mWrappedAdapter.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return mWrappedAdapter.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        mWrappedAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mWrappedAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mWrappedAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        mWrappedAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        mWrappedAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: VH) {
        mWrappedAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return mWrappedAdapter.onFailedToRecycleView(holder)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        mWrappedAdapter.setHasStableIds(hasStableIds)
    }

    fun getWrappedAdapter(): RecyclerView.Adapter<VH> {
        return mWrappedAdapter
    }

    override fun onBridgedAdapterChanged(source: RecyclerView.Adapter<*>, tag: Any?) {
        notifyDataSetChanged()
    }

    override fun onBridgedAdapterItemRangeChanged(
        source: RecyclerView.Adapter<*>,
        tag: Any?,
        positionStart: Int,
        itemCount: Int
    ) {
        notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onBridgedAdapterItemRangeChanged(
        source: RecyclerView.Adapter<*>,
        tag: Any?,
        positionStart: Int,
        itemCount: Int,
        payload: Any?
    ) {
        notifyItemRangeChanged(positionStart, itemCount, payload)
    }

    override fun onBridgedAdapterItemRangeInserted(
        source: RecyclerView.Adapter<*>,
        tag: Any?,
        positionStart: Int,
        itemCount: Int
    ) {
        notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onBridgedAdapterItemRangeRemoved(
        source: RecyclerView.Adapter<*>,
        tag: Any?,
        positionStart: Int,
        itemCount: Int
    ) {
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onBridgedAdapterRangeMoved(
        source: RecyclerView.Adapter<*>,
        tag: Any?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        notifyItemMoved(fromPosition, toPosition)
    }

}