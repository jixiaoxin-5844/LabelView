package com.hyt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.hyt.interfaceA.LabelItemClickListener
import kotlinx.android.synthetic.main.label_item.view.*

/**
 * author : Hyt
 * time : 2020/10/27
 * version : 1.0
 * 子标签
 */
internal class LabelItem  : ConstraintLayout {

    constructor(context: Context) : super(context){initView(context)}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){initView(context)}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    constructor(context: Context,text: String):super(context){
        initView(context)
        labelItem_btn0.text = text
    }

    /*
    * 需要的样式  线条粗细 ， 线条颜色 文字颜色 文字大小  图标 ... ->
    *   太麻烦懒得做了，自定义样式直接自己写一个LabelItem然后在创建的地方替换就好了
    * */

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.label_item, this, true)

        labelItem_btn0.setOnClickListener {
            if(::itemClickListener.isInitialized){
                itemClickListener.onItemClickListener(this@LabelItem, labelItem_btn0.text.toString())
            }
        }

    }

    private lateinit var itemClickListener: LabelItemClickListener

    fun setOnItemClickListener(clickListener: LabelItemClickListener){
        this.itemClickListener = clickListener
    }


}