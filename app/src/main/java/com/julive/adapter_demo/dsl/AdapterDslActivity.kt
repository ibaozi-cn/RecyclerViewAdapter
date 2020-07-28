package com.julive.adapter_demo.dsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.julive.adapter.anko.ankoViewModelDsl
import com.julive.adapter.binding.bindingViewModelDsl
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.layoutViewModelDsl
import com.julive.adapter.core.listAdapter
import com.julive.adapter.core.into
import com.julive.adapter_demo.BR
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.AnkoItemView
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_adapter_dsl.*
import java.util.*

class AdapterDslActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "ArrayListAdapter DSL"

        setContentView(R.layout.activity_adapter_dsl)

        listAdapter {
            add(
                // LayoutViewModel 对象 函数中传入布局IdRes
                layoutViewModelDsl<ModelTest>(R.layout.item_test_2) {
                    // Model 数据模型
                    model = ModelTest("title", "layoutViewModelDsl")
                    // 绑定数据
                    onBindViewHolder { _ ->
                        getView<TextView>(R.id.tv_title)?.text = model?.title
                        getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                    }
                    // 点击处理
                    onItemClick { vm ->
                        //这里需要注意，为什么直接从该对象获取的Model是不正确的？因为ViewHolder的复用
                        //导致click事件其实是在另外一个VM里触发的
                        Log.d("arrayItemViewModel", "不正确的model${model}")
                        Log.d("arrayItemViewModel", "正确的model${vm.model}")
                        Log.d("arrayItemViewModel", "adapter${getAdapter<ListAdapter>()}")
                        Log.d("arrayItemViewModel", "viewHolder${adapterPosition}")
                        //修改Model数据
                        vm.model?.title = "测试更新"
                        //用Adapter更新数据
                        getAdapter<ListAdapter>()?.set(adapterPosition, vm)
                    }
                }
            )
            add(
                //AnkoViewModel对象
                ankoViewModelDsl<ModelTest, AnkoItemView> {
                    model = ModelTest("title", "ankoViewModelDsl")
                    onCreateView {
                        AnkoItemView()
                    }
                    onBindViewHolder { _ ->
                        val ankoView = getAnkoView(this)
                        Log.d("AnkoViewModelTest", "ankoView=${ankoView}")
                        ankoView.tvTitle?.text = model?.title
                        ankoView.tvSubTitle?.text = model?.subTitle
                    }
                    onItemClick { viewModel ->
                        viewModel.model?.title = "点击更新"
                        getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
                    }
                }
            )
            add(
                bindingViewModelDsl<ModelTest>(R.layout.item_binding_layout, BR.model) {
                    model = ModelTest("title", "bindingViewModelDsl")
                    onItemClick { viewModel ->
                        viewModel.model?.title = "${Random().nextInt(100)}"
                        getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
                    }
                }
            )
            // 绑定 RecyclerView
            into(rv_list_dsl)
        }
    }
}