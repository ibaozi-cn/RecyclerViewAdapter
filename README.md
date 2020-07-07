## RecyclerViewAdapter

重新定义RecyclerView Adapter的封装，追求既简单又实用，结合Kotlin的高级特性，优化代码书写方式，真正做到高内聚低耦合

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

现在有ArrayListAdapter，SortedListAdapter未来会有PagingAdapter，为啥这么设计呢？

* 第一、原则上我根本不会去设计一款超级庞大的Adapter支持各种功能，单一职责需要贯穿始终
* 第二、保持各个Lib的特点，可以根据业务的不同自由选择，最大程度的减少包体积，真正体现了没有最好最全，只有最合适的思想
* 第三、保持可动态扩展的特性，你理解了原理，你也可以根据自己的需要定制，未来官方提供MergeAdapter组合使用方式，以及我们未来做一个WrapAdapter，你会发现更多组合额的可能性。
* 第四、由于ViewHolder的复用，总会遇到一些问题，目前该框架已经可以完美解决复用导致UI重复问题。即保持良好的复用，又保持页面刷新的正确效果。

## 少了点什么？

你是不是觉得少了点什么功能？空布局，上拉加载，下拉加载，拖动，头布局，脚布局，展开折叠，分割线，动画等等，这些后期都会带着你们去实现，上面的规划更偏向于底层框架的封装，而这些功能更偏向业务组件，方向不同，不要着急哦，带着你们一步步完善，来体验封装Adapter中的乐趣

## 库大小

|  名字   | release aar size  | 其他   |
|  ----  | ----  | ----  | 
| Core | 28kb | 核心库目前包含ArrayListAdapter的实现 |
| Anko | 8kb | 同样是ArrayListAdapter,由于做了高度的抽象，所以目前没有了AnkoListAdapter |
| Sorted | 10kb | SortedList扩展实现 |
| .. | .. | 待实现 |

## 环境需要

- Kotlin
- JAVA
- AndroidX
抱歉目前按照最新的AndroidX适配的，如有其他需要请私聊我。

## 怎么用？请看下面博客

#### 博客
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
