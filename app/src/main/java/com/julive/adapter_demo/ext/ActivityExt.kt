package com.julive.adapter_demo.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable

inline fun <reified T : Any> Activity.startActivity(vararg params: Pair<String, Any?>){
    startActivity(this.intentFor<T>(*params))
}
inline fun <reified T : Any> Activity.intentFor(vararg params: Pair<String, Any?>): Intent {
    return Intent(this,T::class.java).apply {
        params.forEach {
            when (it.second) {
                is String -> {
                    putExtra(it.first,it.second.toString())
                }
                is Int -> {
                    putExtra(it.first, it.second as Int)
                }
                is Double -> {
                    putExtra(it.first, it.second as Double)
                }
                is Float -> {
                    putExtra(it.first, it.second as Float)
                }
                is Long -> {
                    putExtra(it.first, it.second as Long)
                }
                is CharSequence ->{
                    putExtra(it.first, it.second as CharSequence)
                }
                is Parcelable->{
                    putExtra(it.first, it.second as  Parcelable)
                }
                is Boolean -> {
                    putExtra(it.first, it.second as  Boolean)
                }
                is Array<*>->{
                    putExtra(it.first, it.second as? Array<Parcelable>)
                }
                is Byte->{
                    putExtra(it.first, it.second as Byte)
                }
                is Char->{
                    putExtra(it.first, it.second as Char)
                }
                is Short->{
                    putExtra(it.first, it.second as Short)
                }
            }
        }
    }
}

inline fun <reified T : Any> Context.launchActivity(
    flags: Int = 0,
    intentTransformer: Intent.() -> Unit = {}
) {
    val intent = Intent(this, T::class.java).apply {
        addFlags(flags)
        intentTransformer()
    }
    this.startActivity(intent)
}