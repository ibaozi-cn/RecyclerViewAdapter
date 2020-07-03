package com.julive.adapter_demo.ext

import android.app.Activity
import org.jetbrains.anko.intentFor

inline fun <reified T : Any> Activity.startActivity(vararg params: Pair<String, Any?>){
    startActivity(intentFor<T>(*params))
}