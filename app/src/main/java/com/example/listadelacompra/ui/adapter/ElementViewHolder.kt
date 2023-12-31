package com.example.listadelacompra.ui.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.listadelacompra.databinding.ItemElementBinding
import com.example.listadelacompra.ui.model.ElementModel

class ElementViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val mBinding = ItemElementBinding.bind(view)

    fun render(elementModel: ElementModel, listener: OnClickListener) {
        //Incoporamos los datos a cada uno de los Items.
        // Repetir para todos lo elementos del modelo y del item
        mBinding.tvElement.text = elementModel.element
        mBinding.cbCompleted.isChecked = elementModel.complete

        mBinding.cbCompleted.setOnClickListener {
            elementModel.complete = !elementModel.complete
            listener.onCompleteElement(elementModel = elementModel)
            Log.i("DEVELOPRAFA",elementModel.toString())
        }
        //Si quieres click sobre toda la celda
        itemView.setOnClickListener { listener.onClickItem(elementModel) }
        //Si quieres un click largo sobre toda la celda
        itemView.setOnLongClickListener {
            listener.onDeleteElement(elementModel = elementModel)
            true
        }




    }
}