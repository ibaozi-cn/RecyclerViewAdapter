package com.julive.adapter_demo.anko

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.julive.adapter.anko.AnkoViewModel
import com.julive.adapter_demo.R
import com.julive.adapter_demo.ext.cardView
import com.julive.adapter_demo.sorted.ModelTest
import org.jetbrains.anko.*


/**
 * AnkoItemView
 */
class AnkoItemView : AnkoComponent<ViewGroup> {

    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null
    var view: View? = null
    var itemClick: (() -> Unit)? = null

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

                setOnClickListener {
                    itemClick?.invoke()
                }

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
class AnkoViewModelTest : AnkoViewModel<ModelTest, AnkoItemView>() {
    init {
        onBindViewHolder { viewHolder ->
            getAnkoView(viewHolder).tvTitle?.text = model?.title
            getAnkoView(viewHolder).tvSubTitle?.text = model?.subTitle
            getAnkoView(viewHolder).itemClick = {
                Log.d("AnkoViewModelTest", "正确的model${model}")
                Log.d("AnkoViewModelTest", "正确的model${model}")

                Log.d("AnkoViewModelTest", "adapter$adapter")
                Log.d("AnkoViewModelTest", "viewHolder${viewHolder.adapterPosition}")
                model?.title = "点击更新"
                adapter?.set(viewHolder.adapterPosition, this)
            }
        }
    }
    override fun onCreateView(): AnkoItemView {
        return AnkoItemView()
    }
}