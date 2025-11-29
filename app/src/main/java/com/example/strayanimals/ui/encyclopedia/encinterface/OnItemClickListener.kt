package com.example.strayanimals.ui.encyclopedia.encinterface

import android.view.View
//CallBack :https://blog.csdn.net/beibaokongming/article/details/90175192
interface OnItemClickListener {
    fun onItemClick(view: View?, position: Int,url:String)
}