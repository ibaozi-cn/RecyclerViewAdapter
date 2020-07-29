package com.julive.adapter_demo.selectable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.core.into
import com.julive.adapter.core.layoutViewModelDsl
import com.julive.adapter.core.listAdapter
import com.julive.adapter.selectable.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_selectable.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import org.jetbrains.anko.longToast
import java.util.*

class SelectableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectable)
        val adapter = listAdapter {
            (0..10).forEach { _ ->
                add(layoutViewModelDsl<ModelTest>(R.layout.item_test) {
                    model = ModelTest("title", "subTitle")
                    onBindViewHolder {
                        getView<TextView>(R.id.tv_title)?.text = model?.title
                        getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                        val isSelect = isSelected(adapterPosition)
                        if (isSelect) {
                            itemView.setBackgroundResource(R.color.cardview_dark_background)
                        } else {
                            itemView.setBackgroundResource(R.color.cardview_light_background)
                        }
                    }
                    onCreateViewHolder {
                        itemView.setOnClickListener {
                            toggleSelection(adapterPosition){
                                if(it){
                                    longToast("可选项已达到最大值")
                                }
                            }
                            Log.d("isMultiSelectable", "isMultiSelectable$isMultiSelect")
                        }
                        itemView.setOnLongClickListener {
                            true
                        }
                    }
                })
            }
            into(rv_list_selectable)
        }

        new_add.setText("切换单选").setOnClickListener {
            if(!adapter.isMultiSelect){
                new_add.setText("切换单选")
            }else{
                new_add.setText("切换多选")
            }
            adapter.setMultiSelectable(!adapter.isMultiSelect)
        }

        delete.isVisible = false

        update.setText("设置最大可选").setOnClickListener {
            val random = Random().nextInt(6)
            update.setText("设置最大可选$random")
            adapter.setSelectableMaxSize(random)
        }

    }
}