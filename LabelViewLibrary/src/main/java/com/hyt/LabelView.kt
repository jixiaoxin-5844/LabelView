package com.hyt

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hyt.action.LabelItemClickListener
import com.hyt.action.LabelViewClickListener

/**
 * author : Hyt
 * time : 2020/10/31
 * version : 1.0
 * 标签列表
 */
class LabelView : ConstraintLayout {

    private var marginTop = 12
    private var marginStart = 20
    private var viewLineWidth = 0     //此行子View宽度大于等于这个值，就放到下一行
   // private var labelItemBackGround = "#00000000"

    private lateinit var searchStringList: MutableList<String>

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.LabelView)

        marginTop = mTypeArray.getInt(R.styleable.LabelView_itemMarginTop, 0)
        marginStart = mTypeArray.getInt(R.styleable.LabelView_itemMarginStart, 20)

        mTypeArray.recycle()
    }

    //宽度请使用 match_parent
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var searchItemLine = 1   //默认一行
        var searchHeight = 0     //要显示的高度

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else {        //高度应该是  展示行 * 子View高度 +  marginTop * 展示行

            viewLineWidth = widthSize - marginStart * 2
            var nowWidth = 0  //当前宽度

            for (i in 0 until childCount) {
                val childView = getChildAt(i)
                // measureChild(childView,-20,0)

                nowWidth += (marginStart + childView.measuredWidth)

                if (nowWidth > viewLineWidth) { //换行
                    nowWidth = marginStart + childView.measuredWidth
                    searchItemLine += 1
                }
            }

            searchHeight += (getChildAt(0).measuredHeight + marginTop) * searchItemLine + marginTop

            when (heightMode) {
                MeasureSpec.AT_MOST -> { //wrap_content
                    setMeasuredDimension(widthSize, searchHeight)
                }
                MeasureSpec.EXACTLY -> { //match_parent
                    setMeasuredDimension(widthSize, heightSize)
                }
                else -> { //UNSPECIFIED
                    setMeasuredDimension(widthSize, heightSize)
                }
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = marginTop
        var right = r
        var bottom = b

        var width = marginStart * 2 //已经用掉的宽度

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            left += marginStart
            if (i == 0) {
                childView.layout(left, top, right, bottom)
                left += childView.measuredWidth
                width += childView.measuredWidth
            } else {  //先判断剩余空间是否能放下当前子View
                // Log.d("LabelView", "当前数值left:$left,top:$top,right:$right,bottom:$bottom")
                width += (childView.measuredWidth + marginStart)
                if (width <= viewLineWidth) {

                    setChildFrame(childView, left, top, childView.measuredWidth, childView.measuredHeight)

                    left += childView.measuredWidth
                } else {       //换行
                    width = marginStart + childView.measuredWidth
                    top += (childView.measuredHeight + marginTop)
                    // childView.layout(marginStart, top, right, bottom)
                    setChildFrame(childView, marginStart, top, childView.measuredWidth, childView.measuredHeight)
                    left = childView.measuredWidth + marginStart
                }
            }
        }
    }

    private fun setChildFrame(
        child: View,
        left: Int,
        top: Int,
        width: Int,
        height: Int
    ) {
        child.layout(left, top, left + width, top + height)
        Log.d(
            "LabelView",
            "当前数值left:$left,top:$top,left + width:${left + width},top + height:${top + height}"
        )
    }

    // ******************* 对外的方法 ***********************

    /**
     * 设置数据
     * */
    fun setSearchStringList(list: MutableList<String>) {
        this.searchStringList = list
        for (i in 0 until list.size) {

            // 创建Item  -> 在此定义样式 , 替换item
            val labelItem = LabelItem(context, list[i])

            labelItem.setOnItemClickListener(object : LabelItemClickListener {
                override fun onItemClickListener(view: View, text: String) {
                    if (this@LabelView::onItemClickListener.isInitialized) {
                        onItemClickListener.onItemClickListener(view, i, text)
                    }
                }
            })
            addView(labelItem)
        }
    }

    /**
     *  需要在 setSearchStringList 前调用  !!!
     *  设置LabelItem的背景色
     *  默认 #00000000
     * */
/*    fun setLabelItemBackGround(color: String) {
        this.labelItemBackGround = color
    }*/

    /**
     * 清除数据
     * */
    fun clearAllData() {
        removeAllViews()
    }

    private lateinit var onItemClickListener: LabelViewClickListener

/*  fun setOnItemClickListener(clickListener: OnLabelViewClickListener){
        this.onItemClickListener = clickListener
    }*/

    fun setOnItemClickListener(listener: (view: View, position: Int, text: String) -> Unit) {
        this.onItemClickListener = object : LabelViewClickListener {
            override fun onItemClickListener(view: View, position: Int, text: String) {
                listener.invoke(view, position, text)
            }
        }
    }

}