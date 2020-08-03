package com.julive.adapter.core

interface Subscriber {
    fun onBindViewHolder(position: Int, payloads: List<Any>){}
    fun unBindViewHolder(position: Int){}
    fun onViewAttachedToWindow(position: Int) {}
    fun onViewDetachedFromWindow(position: Int) {}
}
