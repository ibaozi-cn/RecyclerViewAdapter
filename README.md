## RecyclerViewAdapter

重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

## 开源地址

[Github RecyclerViewAdapter](https://github.com/ibaozi-cn/RecyclerViewAdapter)

## 框架设计核心思想

- 摒弃notifyDataSetChanged无脑操作，利用ObservableList自动匹配数据，并实现局部刷新
- 真正通用的ViewHolder抽象，从此只关注Layout XML布局
- 真正通用的Adapter，从此不再写Adapter子类
- ItemViewType自动匹配对象Layout XML，不再关心它的细节
- 科学的分包处理，真正做到框架的各取所需（一般列表只需引用Adapter-core核心库即可）
- 扩展Anko Layout版本，体验Anko Layout的魅力，并能获取高于XML加载至少3倍以上的效率提升
- 像堆积木一样，将页面的每个模块都做到了复用，跟Fragment可以说再见

设计图
![Image](https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306)

## 规划

- ~~核心库Core的完善~~
- ~~Anko扩展~~
- ~~FlexboxLayout 扩展~~
- ~~SortedList 扩展~~
- paging 3 扩展
- DiffUtil 扩展
- DataBinding 扩展
- 等等.. 未来有好的想法继续扩展

现在有ArrayListAdapter，AnkoListAdapter，未来会有SortedListAdapter，PagingAdapter，为啥这么设计呢？

* 第一、原则上我根本不会去设计一款超级庞大的Adapter支持各种功能，单一职责需要贯穿始终
* 第二、保持各个Lib的特点，可以根据业务的不同自由选择，最大程度的减少包体积，真正体现了没有最好最全，只有最合适的思想
* 第三、保持可动态扩展的特性，你理解了原理，你也可以根据自己的需要定制，未来官方提供MergeAdapter组合使用方式，以及我们未来做一个WrapAdapter，你会发现更多组合额的可能性。

## 少了点什么？

你是不是觉得少了点什么功能？空布局，上拉加载，下拉加载，拖动，头布局，脚布局，展开折叠，分割线，动画等等，这些后期都会带着你们去实现，上面的规划更偏向于底层框架的封装，而这些功能更偏向业务组件，方向不同，不要着急哦，带着你们一步步完善，来体验封装Adapter中的乐趣

## 库大小

|  名字   | release aar size  | 其他   |
|  ----  | ----  | ----  | 
| Core | 28kb | 核心库目前包含ArrayListAdapter的实现 |
| Anko | 9kb | anko扩展库包含AnkoListAdapter |
| Sorted | 10kb | SortedList扩展实现 |
| .. | .. | 待实现 |

## 环境需要

- Kotlin
- JAVA
- AndroidX
抱歉目前按照最新的AndroidX适配的，如有其他需要请私聊我。

## 怎么用

### ArrayListAdapter

#### step1

创建xml布局,和之前一样的布局方式

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

定义ViewModel及Model，可以看到，逻辑简单明了，刷新自己的时候只需要更新Model，然后通过adapter更新ViewModel即可，刷新别人的话，也是需要通过Adapter去更新，复杂页面只需要再新建一个ArrayItemViewModel的子类即可，并创建一个新的XML布局，从这里的代码可以看出，同样一个ViewModel未来可以复用很多XML布局，完全做到了ViewModel、View、Model三个角色的任意复用。为业务多样化提供最底层的支持。

```
/**
 * Model
 */
data class ModelTest(var title: String, var subTitle: String)

class ArrayViewModelTest : ArrayItemViewModel<ModelTest, DefaultViewHolder>() {

    var index = 0

    override fun onBindView(adapter: ArrayListAdapter?) {
        getView<TextView>(R.id.tv_title)?.text = model.title
        getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
    }

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(view: View): DefaultViewHolder {
        return DefaultViewHolder(view).apply {
            itemView.setOnClickListener {
                val item = adapter.get(adapterPosition) as ArrayViewModelTest
                item.model.title = "${index++}"
                adapter.set(adapterPosition, item)
            }
        }
    }

}
```
目前这里做了优化，之前的写法确实发现了问题，在findViewById缓存这里，确实存在多次调用的情况，还有设置onClick监听也同样存在多次调用，因为onBindView是会根据ItemView的生命周期不断回调，为了优化这里，我将ViewHolder的创建下移到了业务层，只需要在ViewHolder里面设置监听，和查找View就行。


复用逻辑如下图：
![](https://user-gold-cdn.xitu.io/2020/7/4/173187f1b49efa88?w=960&h=824&f=png&s=337346)

#### step3

Activity 中增删改，增删改都是对ViewModel层的操作，简单实用。

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

### AnkoListAdapter

#### step1

定义AnkoLayout，跟之前写法相比，这种写法更合理，一方面不用再通过Map缓存View，一方面点击时间可以直接回调给使用方，都避免了重复find或者重复binding onClick的问题。

```
/**
 * AnkoItemView
 */
class AnkoItemView(val itemClick: () -> Unit) : AnkoComponent<ViewGroup> {

    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null
    var view: View? = null

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

                setOnClickListener {
                    itemClick()
                }

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

定义ViewModel，Model，这里有个细节需要说一下，在ArrayListAdapter的例子中我是在onBindView里设置的点击事件，这样就有个坏处就是导致每次重新onBindView都会导致设置点击事件，这样其实很不好，所以在Anko版本里我做了优化，在onCreateView处理点击事件，这里就做到了设置一次。

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
    }

    override fun onCreateView(): AnkoItemView {
        return AnkoItemView{
            model.title = "${index++}"
            reBindView()
        }
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

### SortedListAdapter

SortedListAdapter它跟上面两个用到了相同的数据结构，算法上有所不同，由于SortedList本身保持有序，所以再查找的时候用了二分查找算法，提高查找的效率，但它有个缺点是侵入性比较高，需要Model层实现SortedModel抽象类，因为它的排序离不开SortId，唯一性离不开UniqueId，当然你可以把这两个字段作为一个处理，但我的工作经验告诉我，分开更合理。需要适当理解才行，当然它也有好处，做到自动排序，有时候也会省下不少事。下面直接看使用例子

#### step1

直接使用之前ArrayListAdapter的layout 代码 我们直接第二步

#### step2

定义Model需要继承SortedModel，然后实现SortedItemViewModel，使用方法与ArrayListAdapter没有什么区别

```
/**
 * sortedId 排序用
 * title 作为uniqueId ，只要一样就会出现RecyclerView ItemView被替换的情况
 */
data class SortedModelTest(val sortedId: Int, var title: String, var subTitle: String) : SortedModel(sortedId, title)

class SortedItemViewModelTest : SortedItemViewModel<SortedModelTest,DefaultViewHolder>() {

    var index = 0

    override fun onBindView(adapter: SortedListAdapter) {
        viewHolder.getView<TextView>(R.id.tv_title)?.text = model.title
        viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
    }

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(view: View): DefaultViewHolder {
        return DefaultViewHolder(view).apply {
            itemView.setOnClickListener {
                val item = adapter.getItem(adapterPosition) as SortedItemViewModelTest
                item.model.subTitle = "刷新自己${index++}"
                adapter.updateItem(adapterPosition,item)
            }
        }
    }

}
```

#### step3

分别实现了，增删改，有个逻辑需要讲清楚

- 当使用Adapter add 函数的时候，仅当sortId和uniqueId一样时才会触发更新对应的Item，否则就是新增
- 当使用Adapter updateItem时， 只需要uniqueId一样就可以触发更新
- updateItem 时，uniqueId一样，sortId不一样，会触发更新并重新排序

所以使用中需要注意合适的地方使用合适的方法

```
class SortedActivity : AppCompatActivity() {

    private val mSortedListAdapter by lazy {
        SortedListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_list)

        rv_list.bindListAdapter(mSortedListAdapter)

        (0..10).map {
            mSortedListAdapter.add(SortedItemViewModelTest().apply {
                model = SortedModelTest(it, "标题$it", "副标题$it")
            })
        }

        var index = 100
        new_add.setText("新增").setOnClickListener {
            // 新增的时候 只有sortId 与 uniqueId 都一样才会替换
            val randomInt =
                Random.nextInt(0, if (mSortedListAdapter.size > 0) mSortedListAdapter.size else 10)
            mSortedListAdapter.add(SortedItemViewModelTest().apply {
                model = SortedModelTest(randomInt, "标题$randomInt", "sortId与uniqueId都一样，触发替换")
            })
            // 要想根据uniqueId更新数据，需要调用updateItem方法
            mSortedListAdapter.add(SortedItemViewModelTest().apply {
                model = SortedModelTest(index++, "标题$randomInt", "sortId不一样 uniqueId一样")
            })
        }

        delete.setText("删除").setOnClickListener {
            if (mSortedListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.removeItemAt(randomInt)
            }
        }

        update.setText("替换").setOnClickListener {
            // 根据uniqueId替换 如果sortId不一样就会触发排序
            if(mSortedListAdapter.size>0){
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.updateItem(randomInt, SortedItemViewModelTest().apply {
                    model = SortedModelTest(randomInt, "标题$randomInt", "根据uniqueId替换标题${index++}")
                })
            }
        }

    }
}
```

## 博客

[一个资深的Android是不是应该学会自己做一个超级的RecyclerView.Adapter](https://juejin.im/post/5ee640116fb9a047967349c7)


## 开发者

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
