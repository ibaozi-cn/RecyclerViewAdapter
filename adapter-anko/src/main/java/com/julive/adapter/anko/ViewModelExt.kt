package com.julive.adapter.anko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julive.adapter.core.DefaultViewModel
import com.julive.adapter_anko.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext.Companion.create
import java.lang.IllegalArgumentException

open class AnkoViewModel<M> : DefaultViewModel<M>() {

    private var ankoView: () -> AnkoComponent<ViewGroup>? = { null }

    open fun onCreateView(v: () -> AnkoComponent<ViewGroup>) {
        ankoView = v
    }

    override fun getHolderItemView(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): View {
        val ankoView = ankoView() ?: throw IllegalArgumentException("ankoView can not be null")
        val view = ankoView.createView(
            create(
                parent.context,
                parent,
                false
            )
        )
        view.setTag(R.id.list_adapter_anko_view, ankoView)
        return view
    }

    override val layoutRes: Int
        get() = 0

    override val itemViewType: Int
        get() = ankoView.hashCode()

}

