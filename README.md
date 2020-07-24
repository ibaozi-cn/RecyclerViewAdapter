## RecyclerViewAdapter

重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

设计图

![设计图](https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306)
看不见图？
点该链接
https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306

实现图

![实现图](https://user-gold-cdn.xitu.io/2020/7/24/1737e393a1218f05?w=960&h=720&f=png&s=201077)
看不见图？
点该链接
https://user-gold-cdn.xitu.io/2020/7/24/1737e393a1218f05?w=960&h=720&f=png&s=201077

## 规划

- ~~核心库Core的完善~~
- ~~Anko扩展~~
- ~~FlexboxLayout 扩展~~
- ~~SortedList 扩展~~
- ~~Kotlin DSL扩展支持~~
- ~~paging 3 扩展~~
- ~~DiffUtil 扩展~~
- ~~DataBinding 扩展~~
- 等等.. 未来有好的想法继续扩展

## 库大小

|  名字   | release aar size  | 其他 |
|  ----  | ----  | ----  | 
| Core | 25kb | 核心库目前包含ListAdapter的实现，最基础且最实用的扩展 |
| Anko | 8kb | 适用本项目所有Adapter扩展 |
| DataBinding | 19kb | 适配DataBinding布局，适用本项目所有Adapter扩展 |
| Sorted | 10kb | SortedListAdapter扩展实现 |
| Paging | 13kb | PagingListAdapter扩展适配 |
| Diff | 6kb | 适配DiffUtil，目前适用ListAdapter |
| FlexBox | 9kb | 适配FlexBox布局 |

## 各个Adapter的优势在哪，如何选择？

|  名字   | 优势 | 劣势 | 适合做什么 |
|  ----  | ----  | ----  | ----  | 
|  ListAdapter | 简单实用，易扩展，ViewHolder复用率高，DSL支持写法优美 | 对于需要排序的列表处理麻烦性能低 | 不考虑排序的一般列表 | 
|  SortedListAdapter | 排序超级容易 | 侵入性高，需要继承SortedModel实现Model层，目前优化为接口，增加了可使用范围 | 任何需要排序的列表 | 
|  PagingListAdapter | 自带加载状态，后台计算完成后通知刷新，加载效率高 | 侵入性高，需要Model层继承实现，学习成本高，掌握难度高 | 适合自动加载分页的列表 | 

## 如何依赖？

```
allprojects {
    repositories {
        // 首先项目根目录的build.gradle文件中加入这一行 
        maven { url 'https://jitpack.io' }
    }
}

//核心库
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-core:V1.0.2'

//下面都是可选项

//anko layout 扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-anko:V1.0.2'
//diffutil 扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-diff:V1.0.2'
//data binding扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-binding:V1.0.2'
// paging3 扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-paging:V1.0.2'
// sortedlist 扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-sorted:V1.0.2'
// flexbox 扩展
implementation 'com.github.ibaozi-cn.RecyclerViewAdapter:adapter-flex:V1.0.2'
```

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

                listAdapter {

                     add(
                         // LayoutViewModel 对象 函数中传入布局IdRes
                         layoutViewModelDsl<ModelTest>(R.layout.item_test_2) {
                             // Model 数据模型
                             model = ModelTest("title", "layoutViewModelDsl")
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
                     add(
                         //AnkoViewModel对象
                         ankoViewModelDsl<ModelTest, AnkoItemView> {
                             model = ModelTest("title", "ankoViewModelDsl")
                             onCreateView {
                                 AnkoItemView()
                             }
                             onBindViewHolder { viewHolder ->
                                 val ankoView = getAnkoView(viewHolder)
                                 Log.d("AnkoViewModelTest", "ankoView=${ankoView}")
                                 ankoView.tvTitle?.text = model?.title
                                 ankoView.tvSubTitle?.text = model?.subTitle
                             }
                             onItemClick { viewModel, viewHolder ->
                                 viewModel.model?.title = "点击更新"
                                 adapter?.set(viewHolder.adapterPosition, viewModel)
                             }
                         }
                     )
                     add(
                         bindingViewModelDsl<ModelTest>(R.layout.item_binding_layout, BR.model) {
                             model = ModelTest("title", "bindingViewModelDsl")
                             onItemClick { viewModel, viewHolder ->
                                 val adapterPosition = viewHolder.adapterPosition
                                 viewModel.model?.title = "${Random().nextInt(100)}"
                                 adapter?.set(adapterPosition, viewModel)
                             }
                         }
                     )
                     // 绑定 RecyclerView
                     into(rv_list_dsl)
                 }
    }
}
```

#### 博客相关介绍

[一个全新的RecyclerView Adapter框架源码开源](https://juejin.im/post/5f001c6b5188252e703ab676)

[一个资深的Android是不是应该学会自己做一个超级的RecyclerView.Adapter](https://juejin.im/post/5ee640116fb9a047967349c7)

[重学RecyclerView Adapter封装的深度思考和实现](https://juejin.im/post/5f09cbdce51d45349d6c1384)

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
