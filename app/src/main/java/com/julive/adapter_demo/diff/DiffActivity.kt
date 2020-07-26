package com.julive.adapter_demo.diff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.layoutViewModelDsl
import com.julive.adapter.core.listAdapter
import com.julive.adapter.core.into
import com.julive.adapter.diff.calculateDiff
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_diff.*
import kotlinx.android.synthetic.main.include_button_bottom.*

class DiffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff)

        supportActionBar?.title = "ArrayListAdapter Diff"

        val adapter = listAdapter {
            //循环添加ItemViewModel
            (0..2).map {
                add(
                    // ItemViewModel 对象 函数中传入布局IdRes
                    layoutViewModelDsl<ModelTest>(if (it % 2 == 0) R.layout.item_test else R.layout.item_test_2) {
                        // Model 数据模型
                        model = ModelTest("title$it", "subTitle$it")
                        // 绑定数据
                        onBindViewHolder { viewHolder ->
                            viewHolder.getView<TextView>(R.id.tv_title)?.text = model?.title
                            viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                        }
                        onBindViewHolder { vh, payload ->
                            vh.getView<TextView>(R.id.tv_title)?.text =
                                (payload as? ModelTest)?.title
                            vh.getView<TextView>(R.id.tv_subTitle)?.text =
                                (payload as? ModelTest)?.subTitle
                        }
                        // 点击处理
                        onItemClick { vm, vh ->
                            //这里需要注意，为什么直接从该对象获取的Model是不正确的？因为ViewHolder的复用
                            //导致click事件其实是在另外一个VM里触发的
                            Log.d("arrayItemViewModel", "不正确的model${model}")
                            Log.d("arrayItemViewModel", "正确的model${vm.model}")
                            Log.d("arrayItemViewModel", "adapter${vh.getAdapter<ListAdapter>()}")
                            Log.d("arrayItemViewModel", "viewHolder${vh.adapterPosition}")
                            //修改Model数据
                            vm.model?.title = "测试更新"
                            //用Adapter更新数据
                            vh.getAdapter<ListAdapter>()?.set(vh.adapterPosition, vm)
                        }
                    }
                )
            }
            // 绑定 RecyclerView
            into(rv_diff_list)
        }
        new_add.isVisible = false
        delete.isVisible = false
        update.setText("更新").setOnClickListener {
            val list = buildDiffModelList()
            adapter.calculateDiff(list)
        }
    }
}

fun buildDiffModelList() = (0..3).map {
    // ItemViewModel 对象 函数中传入布局IdRes
    // 布局和老数据正好相反，看看能不能直接替换更新
    layoutViewModelDsl<ModelTest>(if (it % 2 == 0) R.layout.item_test else R.layout.item_test_2) {
        // Model 数据模型
        model = ModelTest("title$it", "Diff更新$it")
        // 绑定数据
        onBindViewHolder { viewHolder ->
            viewHolder.getView<TextView>(R.id.tv_title)?.text = model?.title
            viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
        }
        onBindViewHolder { vh, payload ->
            vh.getView<TextView>(R.id.tv_title)?.text =
                (payload as? ModelTest)?.title
            vh.getView<TextView>(R.id.tv_subTitle)?.text =
                (payload as? ModelTest)?.subTitle
        }
    }
}


