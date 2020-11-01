package com.hyt.action

import android.view.View

/**
 * author : Hyt
 * time : 2020/11/01
 * version : 1.0
 *
 */
interface LabelViewClickListener {
    fun onItemClickListener(view: View, position:Int, text: String)
}