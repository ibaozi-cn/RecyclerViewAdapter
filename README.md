## RecyclerViewAdapter
It has been written **100% in Kotlin**. ❤️
重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

设计图

![设计图](https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306)
看不见图？
点该链接
https://user-gold-cdn.xitu.io/2020/7/4/173187052f0c9ddf?w=1432&h=1098&f=png&s=132306

实现图

![实现图](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d7c6aa9fb9ba4356865c9f1087ba8116~tplv-k3u1fbpfcp-zoom-1.image)
看不见图？
点该链接
https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d7c6aa9fb9ba4356865c9f1087ba8116~tplv-k3u1fbpfcp-zoom-1.image

## 规划

- ~~核心库Core的完善~~
- ~~Anko扩展~~
- ~~FlexboxLayout 扩展~~
- ~~SortedList 扩展~~
- ~~Kotlin DSL扩展支持~~
- ~~paging 3 扩展~~
- ~~DiffUtil 扩展~~
- ~~DataBinding 扩展~~
- ~~可选择Selectable 扩展~~
- ~~可展开expandable 扩展~~
- ~~UI扩展：空布局~~、滚动底部或头部回调、获取可见项等
- 等等.. 未来有好的想法继续扩展

## 库大小

|  名字   | release aar size  | 其他 |
|  ----  | ----  | ----  | 
| Core | 28kb | 核心库目前包含ListAdapter的实现，最基础且最实用的扩展 |
| Anko | 13kb | 适用本项目所有Adapter扩展 |
| DataBinding | 20kb | 适配DataBinding布局，适用本项目所有Adapter扩展 |
| Sorted | 10kb | SortedListAdapter扩展实现 |
| Paging | 13kb | PagingListAdapter扩展适配 |
| Diff | 6kb | 适配DiffUtil，目前适用ListAdapter |
| FlexBox | 9kb | 适配FlexBox布局 |
| Selectable | 8kb | 动态扩展单选、多选、最大可选项功能 |
| Expandable | 8kb | 动态扩展可展开功能，支持仅单展开或多展开配置 |
| UI | 17kb | 扩展空布局 |

## 各个Adapter的优势在哪，如何选择？

|  名字   | 优势 | 劣势 | 适合做什么 |
|  ----  | ----  | ----  | ----  | 
|  ListAdapter | 简单实用，易扩展，ViewHolder复用率高，DSL支持写法优美 | 对于需要排序的列表处理麻烦性能低 | 不考虑排序的一般列表 | 
|  SortedListAdapter | 排序超级容易 | 侵入性高，需要继承SortedModel实现Model层，目前优化为接口，增加了可使用范围 | 任何需要排序的列表 | 
|  PagingListAdapter | 自带加载状态，后台计算完成后通知刷新，加载效率高 | 侵入性高，需要Model层继承实现，学习成本高，掌握难度高 | 适合自动加载分页的列表 | 

## 各个ViewModel

适用范围
|  名字   | ListAdapter | SortedListAdapter | PagingListAdapter | 
|  ----  | ----  | ----  | ----  | 
|  LayoutViewModel  | 支持  | 支持  | 支持  | 
|  AnkoViewModel  | 支持  | 支持  | 支持  | 
|  BindingViewModel  | 支持  | 支持  | 支持  | 

如何选择
|  名字   | 功能 | 优势 | 其他 | 
|  ----  | ----  | ----  | ----  | 
|  LayoutViewModel  | 加载XML布局  | 灵活，易懂，易用 |  | 
|  AnkoViewModel  | 加载Anko Layout  | 免去XML加载，布局加载效率提升明显 | 目前官方不维护，需要自己扩展 | 
|  BindingViewModel  | 加载DataBinding布局  | 省去绑定View的过程，代码逻辑简洁 |  | 

## 如何依赖？

```
allprojects {
    repositories {
        // 首先项目根目录的build.gradle文件中加入这一行 
        maven { url 'https://jitpack.io' }
    }
}

def adapterVersion = 'v1.2.0'

//核心库
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-core:$adapterVersion"

//下面都是可选项

//anko layout 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-anko:$adapterVersion"
//diffutil 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-diff:$adapterVersion"
//data binding扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-binding:$adapterVersion"
// paging3 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-paging:$adapterVersion"
// sortedlist 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-sorted:$adapterVersion"
// flexbox 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-flex:$adapterVersion"
// UI 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-ui:$adapterVersion"
// Selectable 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-selectable:$adapterVersion"
// Expandable 扩展
implementation "com.github.ibaozi-cn.RecyclerViewAdapter:adapter-expandable:$adapterVersion"

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
        supportActionBar?.title = "ListAdapter DSL"
        setContentView(R.layout.activity_adapter_dsl)
        listAdapter {
            addAll(createViewModelList(3))
            addAll(createAnkoViewModelList(3))
            addAll(createBindingViewModelList(3))
            // 绑定 RecyclerView
            into(rv_list_dsl)
        }
    }
}
fun createViewModelList(max: Int = 10) = (0..max).map { _ ->
    layoutViewModelDsl(R.layout.item_test, ModelTest("title", "subTitle")) {
        onBindViewHolder {
            val model = getModel<ModelTest>()
            getView<TextView>(R.id.tv_title).text = model?.title
            getView<TextView>(R.id.tv_subTitle).text = model?.subTitle
        }
        itemView.setOnClickListener {
            val vm = getViewModel<LayoutViewModel<ModelTest>>()
            //修改Model数据
            vm?.model?.title = "测试更新${Random.nextInt(10000)}"
            //用Adapter更新数据
            getAdapter<ListAdapter>()?.set(adapterPosition, vm)
        }
    }
}

fun createAnkoViewModelList(max: Int = 10) = (0..max).map { _ ->
    //AnkoViewModel对象
    ankoViewModelDsl(
        ModelTest("title", "ankoViewModelDsl"),
        { AnkoItemView() }
    ) {
        onBindViewHolder { _ ->
            val model = getModel<ModelTest>()
            val ankoView = getAnkoView<AnkoItemView>()
            ankoView?.tvTitle?.text = model?.title
            ankoView?.tvSubTitle?.text = model?.subTitle
        }
        itemView.setOnClickListener {
            val viewModel = getViewModel<AnkoViewModel<ModelTest, AnkoItemView>>()
            viewModel?.model?.title = "点击更新${Random.nextInt(10000)}"
            getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
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
    }
}
```

#### 博客相关介绍

- 最新 [后现在化RecyclerView Adapter稳定版本终于来了](https://juejin.im/post/6856283564649283597)

- [重学RecyclerView Adapter封装的深度思考和实现](https://juejin.im/post/5f09cbdce51d45349d6c1384)

- [一个全新的RecyclerView Adapter框架源码开源](https://juejin.im/post/5f001c6b5188252e703ab676)

- [一个资深的Android是不是应该学会自己做一个超级的RecyclerView.Adapter](https://juejin.im/post/5ee640116fb9a047967349c7)

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
