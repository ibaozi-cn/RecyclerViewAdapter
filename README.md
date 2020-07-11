## RecyclerViewAdapter

重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

设计图
![Image](https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306)

## 规划

- ~~核心库Core的完善~~
- ~~Anko扩展~~
- ~~FlexboxLayout 扩展~~
- ~~SortedList 扩展~~
- ~~Kotlin DSL扩展支持~~
- ~~paging 3 扩展~~
- ~~DiffUtil 扩展~~
- DataBinding 扩展
- 等等.. 未来有好的想法继续扩展

现在有ArrayListAdapter，SortedListAdapter、PagingAdapter，为啥这么设计呢？

* 第一、原则上我根本不会去设计一款超级庞大的Adapter支持各种功能，单一职责需要贯穿始终
* 第二、保持各个Lib的特点，可以根据业务的不同自由选择，最大程度的减少包体积，真正体现了没有最好最全，只有最合适的思想
* 第三、保持可动态扩展的特性，你理解了原理，你也可以根据自己的需要定制，未来官方提供MergeAdapter组合使用方式，以及我们未来做一个WrapAdapter，你会发现更多组合额的可能性。
* 第四、由于ViewHolder的复用，总会遇到一些问题，目前该框架已经可以完美解决复用导致UI重复问题。即保持良好的复用，又保持页面刷新的正确效果。

## 少了点什么？

你是不是觉得少了点什么功能？空布局，上拉加载，下拉加载，拖动，头布局，脚布局，展开折叠，分割线，动画等等，这些后期都会带着你们去实现，上面的规划更偏向于底层框架的封装，而这些功能更偏向业务组件，方向不同，不要着急哦，带着你们一步步完善，来体验封装Adapter中的乐趣

## 库大小

|  名字   | release aar size  | 其他 |
|  ----  | ----  | ----  | 
| Core | 35kb | 核心库目前包含ArrayListAdapter的实现，最基础且最实用的扩展 |
| Anko | 9kb | 同样是ArrayListAdapter,由于做了高度的抽象，所以目前剔除AnkoListAdapter，用ArrayListAdapter代替 |
| Sorted | 11kb | SortedListAdapter扩展实现 |
| Paging | 14kb | PagingListAdapter扩展适配 |

## 各个Adapter的优势在哪，如何选择？

|  名字   | 优势 | 劣势 | 适合做什么 |
|  ----  | ----  | ----  | ----  | 
|  ArrayListAdapter | 简单实用，易扩展，ViewHolder复用率高，DSL支持写法优美 | 对于需要排序的列表处理麻烦性能低 | 不考虑排序的一般列表 | 
|  SortedListAdapter | 排序超级容易 | 侵入性高，需要Model层继承实现，目前优化为接口，增加了可使用范围 | 任何需要排序的列表 | 
|  PagingListAdapter | 自带加载状态，后台计算完成后通知刷新，加载效率高 | 侵入性高，需要Model层继承实现，学习成本高，掌握难度高 | 适合自动加载分页的列表 | 


## 环境需要

- Kotlin
- JAVA
- AndroidX
抱歉目前按照最新的AndroidX适配的，如有其他需要请私聊我。

## 怎么用？

### 看一个最简单的例子 DSL的支持

```
class AdapterDslActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "ArrayListAdapter DSL"

        setContentView(R.layout.activity_adapter_dsl)

        arrayListAdapter {
            //循环添加ItemViewModel
            (0..10).map {
                add(
                    // ItemViewModel 对象 函数中传入布局IdRes
                    arrayItemViewModelDsl<ModelTest>(if (it % 2 == 0) R.layout.item_test else R.layout.item_test_2) {
                        // Model 数据模型
                        model = ModelTest("title$it", "subTitle$it")
                        // 绑定数据
                        onBindViewHolder { viewHolder ->
                            viewHolder.getView<TextView>(R.id.tv_title)?.text = model?.title
                            viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                        }
                        // 点击处理
                        onItemClick { vm, vh ->
                            //这里需要注意，为什么直接从该对象获取的Model是不正确的？因为ViewHolder的复用
                            //导致click事件其实是在另外一个VM里触发的
                            Log.d("arrayItemViewModel", "不正确的model${model}")
                            Log.d("arrayItemViewModel", "正确的model${vm.model}")
                            Log.d("arrayItemViewModel", "adapter$adapter")
                            Log.d("arrayItemViewModel", "viewHolder${vh.adapterPosition}")
                            //修改Model数据
                            vm.model?.title = "测试更新"
                            //用Adapter更新数据
                            adapter?.set(vh.adapterPosition, vm)
                        }
                    }
                )
            }
            // 绑定 RecyclerView
            into(rv_list_dsl)
        }
    }
}
```

#### 博客相关介绍
[一个全新的RecyclerView Adapter框架源码开源](https://juejin.im/post/5f001c6b5188252e703ab676)
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
