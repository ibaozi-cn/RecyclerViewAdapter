# RecyclerViewAdapter

重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

# 框架设计核心思想

- 摒弃notifyDataSetChanged无脑操作，利用ObservableList自动匹配数据，并实现局部刷新
- 真正通用的ViewHolder抽象，从此只关注Layout XML布局
- 真正通用的Adapter，从此不再写Adapter子类
- ItemViewType自动匹配对象Layout XML，不再关心它的细节
- 科学的分包处理，真正做到框架的各取所需（一般列表只需引用Adapter-core核心库即可）
- 扩展Anko Layout版本，体验Anko Layout的魅力，并能获取高于XML加载至少3倍以上的效率提升
- 像堆积木一样，将页面的每个模块都做到了复用，跟Fragment可以说再见

设计图
![Image](https://upload-images.jianshu.io/upload_images/2413316-122eacfd4d5240f8.png)

# 规划

- ~~核心库Core的完善~~
- ~~Anko扩展~~
- FlexboxLayout 扩展
- SortedList 扩展
- paging 3 扩展
- DiffUtil 扩展
- DataBinding 扩展
- 等等.. 未来有好的想法继续扩展

# 环境需要

- Kotlin
- JAVA
- AndroidX
抱歉目前按照最新的AndroidX适配的，如有其他需要请私聊我。

# 怎么用

## ArrayListAdapter

#### step1

创建xml布局

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="5dp"
    android:layout_margin="5dp">

    <LinearLayout
        android:background="?attr/selectableItemBackground"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="5dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@color/colorPrimary"
            android:textSize="22sp" />

        <TextView
            android:id="@+id/tv_subTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@color/colorAccent"
            android:textSize="18sp" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

#### step2

定义ViewModel及Model

```
/**
 * Model
 */
data class ModelTest(var title: String, var subTitle: String)

/**
 * ViewModel
 */
class ArrayViewModelTest : ArrayItemViewModel<ModelTest>() {

    var index = 0

    override fun onBindView(adapter: ArrayListAdapter?) {
        viewHolder.itemView.apply {
            tv_title.text = model.title
            tv_subTitle.text = model.subTitle
            cardItem.setOnClickListener {
                model.title = "${index++}"
                reBindView()
            }
        }
    }

    override fun getLayoutRes() = R.layout.item_test

}
```

#### step3

Activity 中增删改

```
/**
 * Activity
 */
class ArrayListActivity : AppCompatActivity() {

    private val mArrayListAdapter by lazy {
        ArrayListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_list)
        rv_list.bindListAdapter(mArrayListAdapter)


        // 新增一个
        new_add.setText("新增").setOnClickListener {
            mArrayListAdapter.add(ArrayViewModelTest().apply {
                model = ModelTest("标题", "副标题")
            })
        }


        // 删除第一个
        delete.setText("删除").setOnClickListener {
            if (mArrayListAdapter.size > 0)
                mArrayListAdapter.removeAt(0)
            else
                toast("请添加新用例后再试")
        }


        // 随机更新
        var updateSize = 0
        update.setText("更新").setOnClickListener {
            updateSize++
            if (mArrayListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mArrayListAdapter.size)
                mArrayListAdapter.set(randomInt, ArrayViewModelTest().apply {
                    model = ModelTest("标题$updateSize", "副标题$updateSize")
                })
            } else {
                toast("请添加新用例后再试")
            }

        }

    }
}
```

## AnkoListAdapter

#### step1

定义AnkoLayout

```
/**
 * AnkoItemView
 */
class AnkoItemView() : AnkoComponent<ViewGroup> {

    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null

    @SuppressLint("ResourceType")
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        cardView {

            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                margin = dip(5)
            }

            verticalLayout {

                val typedValue = TypedValue()
                context.theme
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                val attribute = intArrayOf(android.R.attr.selectableItemBackground)
                val typedArray =
                    context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)

                background = typedArray.getDrawable(0)

                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    padding = dip(10)
                }

                tvTitle = textView {
                    textSize = px2dip(60)
                    textColorResource = R.color.colorPrimary
                }.lparams(matchParent, wrapContent)

                tvSubTitle = textView {
                    textSize = px2dip(45)
                    textColorResource = R.color.colorAccent
                }.lparams(matchParent, wrapContent)

            }

        }

    }
}

```

#### step2

定义ViewModel，Model

```
/**
 * Model
 */
data class ModelTest(var title: String, var subTitle: String)

/**
 * ViewModel
 */
class AnkoViewModelTest : AnkoItemViewModel<ModelTest, AnkoItemView>() {

    var index = 0

    override fun onBindView(adapter: AnkoListAdapter) {

        ankoView.tvTitle?.text = model.title
        ankoView.tvSubTitle?.text = model.subTitle

        viewHolder.itemView.setOnClickListener {
            model.title = "${index++}"
            reBindView()
        }

    }

    override fun onCreateView(): AnkoItemView {
        return AnkoItemView()
    }
}
```

#### step3

Activity 中增删改

```
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
```

# 博客

一个资深的Android是不是应该学会自己做一个超级的RecyclerView.Adapter
https://juejin.im/post/5ee640116fb9a047967349c7


# 开发者

* i校长
  * [Jetpack.net.cn](http://jetpack.net.cn)
  * [简书](https://www.jianshu.com/u/77699cd41b28)
  * [掘金](https://juejin.im/user/5d6fb3a65188251a875b1d52/posts)

# License

    Copyright 2020 Mike Penz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.