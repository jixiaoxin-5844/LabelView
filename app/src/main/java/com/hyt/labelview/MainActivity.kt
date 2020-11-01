package com.hyt.labelview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {

        val list = mutableListOf("标签0","标签1标签1","标签2标签2标签2","标签3标签3标签3标签3","标签4标签4标签4","标签5标签5","标签6")


        //设置数据
        main_labelView.setSearchStringList(list)

        //添加监听
        main_labelView.setOnItemClickListener { view, position, text ->

            Toast.makeText(this,"点击了第${position}个item,它的文本是$text",Toast.LENGTH_SHORT).show()

        }



    }
}