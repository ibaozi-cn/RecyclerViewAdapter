package com.julive.adapter_demo.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.julive.adapter_demo.R
import com.julive.adapter.anko.recyclerView
import com.julive.adapter.core.LayoutViewModel
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.bindListAdapter
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.include_button_bottom.view.*
import org.jetbrains.anko.*
import kotlin.random.Random

/**
 * Activity
 */
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
            new_add.setText("新增").setOnClickListener {
                arrayListAdapter.add(AnkoViewModelTest().apply {
                    model = ModelTest("标题${++index}", "副标题")
                })
            }
            // 删除第一个
            delete.setText("删除").setOnClickListener {
                if (arrayListAdapter.size > 0)
                    arrayListAdapter.removeAt(0)
                else
                    toast("请添加新用例后再试")
            }
            // 随机更新
            var updateSize = 0
            update.setText("更新").setOnClickListener {
                updateSize++
                if (arrayListAdapter.size > 0) {
                    val randomInt = Random.nextInt(0, arrayListAdapter.size)
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

/**
 * View
 *
 */
class AnkoLayoutComponent(private val ankoListAdapter: ListAdapter) :
    AnkoComponent<AnkoLayoutActivity> {

    override fun createView(ui: AnkoContext<AnkoLayoutActivity>) = with(ui) {

        verticalLayout {

            recyclerView {
                bindListAdapter(ankoListAdapter)
            }.lparams(matchParent) {
                weight = 1F
            }
            // Anko 兼容 xml布局的加载
            include<View>(R.layout.include_button_bottom)
        }
    }

}