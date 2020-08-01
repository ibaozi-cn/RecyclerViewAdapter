package com.julive.adapter_demo.dsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.julive.adapter.anko.AnkoViewModel
import com.julive.adapter.anko.ankoViewModelDsl
import com.julive.adapter.anko.getAnkoView
import com.julive.adapter.binding.BindingViewModel
import com.julive.adapter.binding.bindingViewModelDsl
import com.julive.adapter.core.*
import com.julive.adapter_demo.BR
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.AnkoItemView
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_adapter_dsl.*
import java.util.*

class AdapterDslActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter DSL"
        setContentView(R.layout.activity_adapter_dsl)

        listAdapter {
            add(
                // LayoutViewModel 对象 函数中传入布局IdRes
                layoutViewModelDsl<ModelTest> (
                    R.layout.item_test_2
                ) {

                    val tvTitle = getView<TextView>(R.id.tv_title)
                    val tvSubtitle = getView<TextView>(R.id.tv_subTitle)

                    itemView.setOnClickListener {
                        val vm = getViewModel<LayoutViewModel<ModelTest>>()
                        //这里需要注意，为什么直接从该对象获取的Model是不正确的？因为ViewHolder的复用
                        //导致click事件其实是在另外一个VM里触发的
                        Log.d("arrayItemViewModel", "adapter${getAdapter<ListAdapter>()}")
                        Log.d("arrayItemViewModel", "viewHolder${adapterPosition}")
                        //修改Model数据
                        getModel<ModelTest>()?.title = "测试更新${Random().nextInt(1000)}"
                        //用Adapter更新数据
                        getAdapter<ListAdapter>()?.set(adapterPosition, vm)
                    }
                    // 绑定数据
                    onBindViewHolder{ model, _ ->
                        tvTitle?.text = model.title
                        tvSubtitle?.text = model.subTitle
                    }
                }
            )
            add(
                //AnkoViewModel对象
                ankoViewModelDsl<ModelTest>() {
                    onBindViewHolder { model, _ ->
                        val ankoView = getAnkoView<AnkoItemView>()
                        Log.d("AnkoViewModelTest", "ankoView=${ankoView}")
                        ankoView?.tvTitle?.text = model.title
                        ankoView?.tvSubTitle?.text = model.subTitle
                    }
                    itemView.setOnClickListener {
                        val viewModel = getAdapter<ListAdapter>()?.getItem(adapterPosition)
                        getModel<ModelTest>()?.title = "点击更新"
                        getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
                    }
                }.apply {
                    onCreateView {
                        AnkoItemView()
                    }
                }
            )
            add(
                bindingViewModelDsl<ModelTest>(
                    R.layout.item_binding_layout,
                    BR.model
                ) {
                    itemView.setOnClickListener {
                        val viewModel = getAdapter<ListAdapter>()?.getItem(adapterPosition)
                        getModel<ModelTest>()?.title = "${Random().nextInt(100)}"
                        getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
                    }
                }
            )
            // 绑定 RecyclerView
            into(rv_list_dsl)
        }
    }
}