package com.hyt.interfaceA

import android.view.View

/**
 * author : Hyt
 * time : 2020/10/27
 * version : 1.0
 *
 */
interface ClickListener {
}

interface LabelItemClickListener {
    fun onItemClickListener(view: View, text: String)
}

interface LabelViewClickListener {
    fun onItemClickListener(view: View, position:Int, text: String)
}