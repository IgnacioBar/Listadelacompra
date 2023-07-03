package com.example.listadelacompra.ui.adapter

import com.example.listadelacompra.ui.model.ElementModel

interface OnClickListener {

    fun onClickItem(elementModel: ElementModel)
    fun onDeleteElement(elementModel: ElementModel)
    fun onCompleteElement(elementModel: ElementModel)
}