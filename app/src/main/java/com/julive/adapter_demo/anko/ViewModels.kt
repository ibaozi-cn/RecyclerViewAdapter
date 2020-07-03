package com.julive.adapter_demo.anko

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.julive.adapter.anko.AnkoItemViewModel
import com.julive.adapter.anko.AnkoListAdapter
import com.julive.adapter.anko.cardView
import com.julive.adapter_demo.R
import org.jetbrains.anko.*

/**
 * Model
 */
data class ModelTest(var title: String, var subTitle: String)

/**
 * ItemView
 */
class AnkoItemView() : AnkoComponent<ViewGroup> {

    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null

    @SuppressLint("ResourceType")
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        cardView {

            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                margin = dip(5)
            }

            verticalLayout {

                val typedValue = TypedValue()
                context.theme
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                val attribute = intArrayOf(android.R.attr.selectableItemBackground)
                val typedArray =
                    context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)

                background = typedArray.getDrawable(0)

                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    padding = dip(10)
                }

                tvTitle = textView {
                    textSize = px2dip(60)
                    textColorResource = R.color.colorPrimary
                }.lparams(matchParent, wrapContent)

                tvSubTitle = textView {
                    textSize = px2dip(45)
                    textColorResource = R.color.colorAccent
                }.lparams(matchParent, wrapContent)

            }

        }

    }
}

/**
 * ViewModel
 */
class AnkoViewModelTest : AnkoItemViewModel<ModelTest, AnkoItemView>() {

    var index = 0

    override fun onBindView(adapter: AnkoListAdapter) {

        ankoView.tvTitle?.text = model.title
        ankoView.tvSubTitle?.text = model.subTitle

        viewHolder.itemView.setOnClickListener {
            model.title = "${index++}"
            reBindView()
        }

    }

    override fun onCreateView(): AnkoItemView {
        return AnkoItemView()
    }
}