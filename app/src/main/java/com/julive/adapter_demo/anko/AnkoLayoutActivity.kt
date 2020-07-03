package com.julive.adapter_demo.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.julive.adapter.anko.AnkoListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter.anko.recyclerView
import com.julive.adapter.core.bindListAdapter
import kotlinx.android.synthetic.main.include_button_bottom.view.*
import org.jetbrains.anko.*
import kotlin.random.Random

/**
 * Activity
 */
class AnkoLayoutActivity : AppCompatActivity() {

    private val mAnkoListAdapter by lazy {
        AnkoListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnkoLayoutComponent(mAnkoListAdapter).setContentView(this).apply {

            // 新增一个
            new_add.setText("新增").setOnClickListener {
                mAnkoListAdapter.add(AnkoViewModelTest().apply {
                    model = ModelTest("标题", "副标题")
                })
            }


            // 删除第一个
            delete.setText("删除").setOnClickListener {
                if (mAnkoListAdapter.size > 0)
                    mAnkoListAdapter.removeAt(0)
                else
                    toast("请添加新用例后再试")
            }


            // 随机更新
            var updateSize = 0
            update.setText("更新").setOnClickListener {
                updateSize++
                if (mAnkoListAdapter.size > 0) {
                    val randomInt = Random.nextInt(0, mAnkoListAdapter.size)
                    mAnkoListAdapter.set(randomInt, mAnkoListAdapter.getItem(randomInt).apply {
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
class AnkoLayoutComponent(private val ankoListAdapter: AnkoListAdapter) : AnkoComponent<AnkoLayoutActivity> {

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