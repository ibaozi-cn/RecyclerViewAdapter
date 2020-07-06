package com.julive.adapter_demo.anko

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.julive.adapter.anko.AnkoItemViewModel
import com.julive.adapter.anko.AnkoListAdapter
import com.julive.adapter_demo.ModelTest
import com.julive.adapter_demo.R
import com.julive.adapter_demo.ext.cardView
import org.jetbrains.anko.*


/**
 * AnkoItemView
 */
class AnkoItemView(val itemClick: () -> Unit) : AnkoComponent<ViewGroup> {

    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null
    var view: View? = null

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
                    itemClick()
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
class AnkoViewModelTest : AnkoItemViewModel<ModelTest, AnkoItemView>() {

    var index = 0

    override fun onBindView(adapter: AnkoListAdapter) {
        ankoView.tvTitle?.text = model.title
        ankoView.tvSubTitle?.text = model.subTitle
    }

    override fun onCreateView(): AnkoItemView {
        Log.d("onCreateView","AnkoListAdapter================================================${index++}")
        return AnkoItemView{
            model.title = "${index++}"
            reBindView()
        }
    }

}