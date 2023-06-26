package com.example.listadelacompra.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listadelacompra.R
import com.example.listadelacompra.ui.model.ElementModel

class ElementAdapter(private val elementModel: List<ElementModel>,
                     private var listener: OnClickListener
) :
    RecyclerView.Adapter<ElementViewHolder>() {

    //Devuelve el item al ViewHolder por cada objeto del listado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ElementViewHolder(layoutInflater.inflate(R.layout.item_element,parent,false))
    }

    //Por cada uno de los items llama a render
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val item = elementModel[position]
        holder.render(item,listener)
    }

    override fun getItemCount(): Int = elementModel.size

}