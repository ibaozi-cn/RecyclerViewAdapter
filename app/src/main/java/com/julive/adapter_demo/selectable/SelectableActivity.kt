package com.julive.adapter_demo.selectable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.core.*
import com.julive.adapter.selectable.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.main.setBtnText
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_selectable.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import java.util.*

class SelectableActivity : AppCompatActivity() {

    private val mListAdapter =
        listAdapter {
            (0..10).forEach { _ ->
                add(
                    layoutViewModelDsl(
                        R.layout.item_test,
                        ModelTest("title", "subTitle")
                    ) {
                        val title = getView<TextView>(R.id.tv_title)
                        val subTitle = getView<TextView>(R.id.tv_subTitle)
                        itemView.setOnClickListener {
                            toggleSelection(adapterPosition) {
                                if (it) {
//                                    longToast("可选项已达到最大值")
                                }
                            }
                            Log.d("isMultiSelectable", "isMultiSelectable$isMultiSelect")
                        }
                        onBindViewHolder {
                            firstAnimation()
                            val model = getModel<ModelTest>()
                            title.text = model?.title
                            subTitle.text = model?.subTitle
                            val isSelect = isSelected(adapterPosition)
                            if (isSelect) {
                                itemView.setBackgroundResource(R.color.cardview_dark_background)
//                                title.textColorResource = R.color.cardview_light_background
                            } else {
                                itemView.setBackgroundResource(R.color.cardview_light_background)
//                                title.textColorResource = R.color.cardview_dark_background
                            }
                        }
                    })
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter"
        setContentView(R.layout.activity_selectable)
        mListAdapter.into(rv_list_selectable)
        btn_left.setBtnText("切换单选").setOnClickListener {
            if (!mListAdapter.isMultiSelect) {
                btn_left.setText("切换单选")
            } else {
                btn_left.setText("切换多选")
            }
            mListAdapter.setMultiSelectable(!mListAdapter.isMultiSelect)
        }
        btn_middle.isVisible = false
        btn_right.setBtnText("设置最大可选").setOnClickListener {
            val random = Random().nextInt(6)
            btn_right.setText("设置最大可选$random")
            mListAdapter.setSelectableMaxSize(random)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mListAdapter.onDestroy()
    }
}