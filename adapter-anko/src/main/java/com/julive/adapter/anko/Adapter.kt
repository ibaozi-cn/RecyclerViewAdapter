package com.julive.adapter.anko

import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ObservableAdapter

class AnkoListAdapter : ObservableAdapter<AnkoItemViewModel<*, *>, DefaultViewHolder>() {

    override fun set(index: Int, element: AnkoItemViewModel<*, *>): AnkoItemViewModel<*, *> {
        if (contains(element))
            element.onBindView(this)
        else
            return super.set(index, element)
        return element
    }

}
