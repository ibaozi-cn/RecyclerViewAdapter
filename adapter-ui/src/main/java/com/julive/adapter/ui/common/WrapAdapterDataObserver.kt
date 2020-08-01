package com.julive.adapter.ui.common

import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class WrapAdapterDataObserver(
    private var mRefSubscriber: WeakReference<Subscriber>,
    private var mRefSourceHolder: WeakReference<RecyclerView.Adapter<*>>,
    private var mTag: Any? = null
) : RecyclerView.AdapterDataObserver() {

    interface Subscriber {
        fun onBridgedAdapterChanged(
            source: RecyclerView.Adapter<*>,
            tag: Any?
        )

        fun onBridgedAdapterItemRangeChanged(
            source: RecyclerView.Adapter<*>,
            tag: Any?,
            positionStart: Int,
            itemCount: Int
        )

        fun onBridgedAdapterItemRangeChanged(
            source: RecyclerView.Adapter<*>,
            tag: Any?,
            positionStart: Int,
            itemCount: Int,
            payload: Any?
        )

        fun onBridgedAdapterItemRangeInserted(
            source: RecyclerView.Adapter<*>,
            tag: Any?,
            positionStart: Int,
            itemCount: Int
        )

        fun onBridgedAdapterItemRangeRemoved(
            source: RecyclerView.Adapter<*>,
            tag: Any?,
            positionStart: Int,
            itemCount: Int
        )

        fun onBridgedAdapterRangeMoved(
            source: RecyclerView.Adapter<*>,
            tag: Any?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        )
    }

    override fun onChanged() {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterChanged(source, mTag)
        }
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeChanged(source, mTag, positionStart, itemCount)
        }
    }

    override fun onItemRangeChanged(
        positionStart: Int,
        itemCount: Int,
        payload: Any?
    ) {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeChanged(
                source,
                mTag,
                positionStart,
                itemCount,
                payload
            )
        }
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeInserted(source, mTag, positionStart, itemCount)
        }
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeRemoved(source, mTag, positionStart, itemCount)
        }
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        val subscriber: Subscriber? = mRefSubscriber.get()
        val source = mRefSourceHolder.get()
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterRangeMoved(source, mTag, fromPosition, toPosition, itemCount)
        }
    }

}