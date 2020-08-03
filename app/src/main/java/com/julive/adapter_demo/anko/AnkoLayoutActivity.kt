package com.julive.adapter_demo.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.julive.adapter_demo.R
import com.julive.adapter.anko.recyclerView
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.into
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.include_button_bottom.view.*
import org.jetbrains.anko.*
import kotlin.random.Random

class AnkoLayoutActivity : AppCompatActivity() {

    private val arrayListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter"
        var index = 0
        AnkoLayoutComponent(arrayListAdapter).setContentView(this).apply {
            // 新增一个
            btn_left.setText("新增").setOnClickListener {
                arrayListAdapter.add(AnkoViewModelTest().apply {
                    model = ModelTest("标题${++index}", "副标题")
                })
            }
            // 删除第一个
            btn_middle.setText("删除").setOnClickListener {
                if (arrayListAdapter.itemCount > 0)
                    arrayListAdapter.removeAt(0)
                else
                    toast("请添加新用例后再试")
            }
            // 随机更新
            var updateSize = 0
            btn_right.setText("更新").setOnClickListener {
                updateSize++
                if (arrayListAdapter.itemCount > 0) {
                    val randomInt = Random.nextInt(0, arrayListAdapter.itemCount)
                    arrayListAdapter.set(randomInt, arrayListAdapter.getItem(randomInt).apply {
                        model.also {
                            it as ModelTest
                            it.title = "$updateSize"
                        }
                    })
                } else {
                    toast("请添加新用例后再试")
                }
            }
        }
    }
}

class AnkoLayoutComponent(private val ankoListAdapter: ListAdapter) : AnkoComponent<AnkoLayoutActivity> {

    override fun createView(ui: AnkoContext<AnkoLayoutActivity>) = with(ui) {

        verticalLayout {

            recyclerView {
                itemAnimator = DefaultItemAnimator()
            }.lparams(matchParent) {
                weight = 1F
            }.also {
                ankoListAdapter.into(it)
            }
            // Anko 兼容 xml布局的加载
            include<View>(R.layout.include_button_bottom)
        }
    }

}