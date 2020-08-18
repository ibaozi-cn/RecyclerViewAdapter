package com.julive.adapter_demo

import android.widget.TextView
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.animators.updateAnimation
import com.julive.adapter.anko.AnkoViewModel
import com.julive.adapter.anko.ankoViewModelDsl
import com.julive.adapter.anko.getAnkoView
import com.julive.adapter.binding.BindingViewModel
import com.julive.adapter.binding.bindingViewModelDsl
import com.julive.adapter.core.*
import com.julive.adapter_demo.anko.AnkoItemView
import com.julive.adapter_demo.sorted.ModelTest
import kotlin.random.Random

fun createViewModelList(max: Int = 10, subTitle: String = "subTitle") = (0..max).map { _ ->
    layoutViewModelDsl(R.layout.item_test, ModelTest("title", subTitle)) {
        val titleText = getView<TextView>(R.id.tv_title)
        val subTitleText = getView<TextView>(R.id.tv_subTitle)
        itemView.setOnClickListener {
            val vm = getViewModel<LayoutViewModel<ModelTest>>()
            //修改Model数据
            vm?.model?.title = "测试更新${Random.nextInt(10000)}"
            //用Adapter更新数据
            getAdapter<ListAdapter>()?.set(adapterPosition, vm)
        }
        onViewAttachedToWindow {
            firstAnimation()
        }
        onBindViewHolder {
            updateAnimation()
            val model = getModel<ModelTest>()
            titleText.text = model?.title
            subTitleText.text = model?.subTitle
        }
        onViewDetachedFromWindow {

        }
    }
}

fun createAnkoViewModelList(max: Int = 10) = (0..max).map { _ ->
    //AnkoViewModel对象
    ankoViewModelDsl(ModelTest("title", "ankoViewModelDsl"), { AnkoItemView() }) {
        itemView.setOnClickListener {
            val viewModel = getViewModel<AnkoViewModel<ModelTest, AnkoItemView>>()
            viewModel?.model?.title = "点击更新${Random.nextInt(10000)}"
            getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
        }
        onViewAttachedToWindow {
            firstAnimation()
        }
        onBindViewHolder { _ ->
            updateAnimation()
            val model = getModel<ModelTest>()
            val ankoView = getAnkoView<AnkoItemView>()
            ankoView?.tvTitle?.text = model?.title
            ankoView?.tvSubTitle?.text = model?.subTitle
        }
    }
}

fun createBindingViewModelList(max: Int = 10) = (0..max).map {
    bindingViewModelDsl(
        R.layout.item_binding_layout,
        BR.model,
        ModelTest("title", "bindingViewModelDsl")
    ) {
        itemView.setOnClickListener {
            val viewModel = getViewModel<BindingViewModel<ModelTest>>()
            viewModel?.model?.title = "${java.util.Random().nextInt(100)}"
            getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
        }
        onViewAttachedToWindow {
            firstAnimation()
            updateAnimation()
        }
    }
}

fun mapData() =
    listOf<ModelTest>(
        ModelTest("张三" , "男"),
        ModelTest("张三" , "4-25"),
        ModelTest("张三" , "1990"),
        ModelTest("李四" , "女"),
        ModelTest("李四" , "8-9"),
        ModelTest("李四" , "1991"),
        ModelTest("王五" , "不男"),
        ModelTest("王五" , "2-3"),
        ModelTest("王五" , "1880"),
        ModelTest("猴哥" , "未知"),
        ModelTest("猴哥" , "9-0"),
        ModelTest("猴哥" , "2000"),
        ModelTest("无能" , "男"),
        ModelTest("无能" , "5-30"),
        ModelTest("无能" , "1990"),
        ModelTest("爱贝" , "1991")
    )

fun createMapDataViewModel() = mapData().map {
    layoutViewModelDsl(R.layout.item_test, ModelTest(it.title, it.subTitle)) {
        val titleText = getView<TextView>(R.id.tv_title)
        val subTitleText = getView<TextView>(R.id.tv_subTitle)
        itemView.setOnClickListener {
            val vm = getViewModel<LayoutViewModel<ModelTest>>()
            //修改Model数据
            vm?.model?.title = "测试更新${Random.nextInt(10000)}"
            //用Adapter更新数据
            getAdapter<ListAdapter>()?.set(adapterPosition, vm)
        }
        onViewAttachedToWindow {
            firstAnimation()
        }
        onBindViewHolder {
            updateAnimation()
            val model = getModel<ModelTest>()
            titleText.text = model?.title
            subTitleText.text = model?.subTitle
        }
        onViewDetachedFromWindow {

        }
    }
}