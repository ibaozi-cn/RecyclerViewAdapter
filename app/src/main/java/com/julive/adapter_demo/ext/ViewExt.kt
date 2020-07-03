package com.julive.adapter_demo.ext

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.julive.adapter_demo.R
import kotlinx.android.synthetic.main.include_card_button.view.*
import org.jetbrains.anko.include

/**
 * 自定义Card样式的Button
 */
class CardButtonView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        include<CardView>(R.layout.include_card_button)
    }

    fun setText(text: String): CardButtonView {
        cardText.text = text
        return this
    }

}