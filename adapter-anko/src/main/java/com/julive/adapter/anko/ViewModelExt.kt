package com.julive.adapter.anko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.DefaultViewModel
import com.julive.adapter_anko.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext.Companion.create
import java.lang.IllegalArgumentException

open class AnkoViewModel<M, AnkoView : AnkoComponent<ViewGroup>> : DefaultViewModel<M>() {

    private var view: AnkoView? = null

    open fun onCreateView(v: () -> AnkoView) {
        view = v()
    }

    override fun getHolderItemView(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): View {
        if (view == null) throw IllegalArgumentException("ankoView can not be null")
        val view = view!!.createView(
            create(
                parent.context,
                parent,
                false
            )
        )
        view.setTag(R.id.list_adapter_anko_view, this.view)
        return view
    }

    fun getAnkoView(viewHolder: RecyclerView.ViewHolder): AnkoView {
        return viewHolder.itemView.getTag(R.id.list_adapter_anko_view) as AnkoView
    }

    override val layoutRes: Int
        get() = 0

    override val itemViewType: Int
        get() = view.hashCode()

}

