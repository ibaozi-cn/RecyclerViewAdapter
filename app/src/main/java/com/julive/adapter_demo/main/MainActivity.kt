package com.julive.adapter_demo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.AnkoLayoutActivity
import com.julive.adapter_demo.core.ArrayListActivity
import com.julive.adapter_demo.ext.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_anko.setText("AnkoListAdapter").setOnClickListener {
            startActivity<AnkoLayoutActivity>()
        }

        btn_array.setText("ArrayListAdapter").setOnClickListener {
            startActivity<ArrayListActivity>()
        }

    }

}