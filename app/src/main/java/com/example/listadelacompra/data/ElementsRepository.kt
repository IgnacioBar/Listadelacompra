package com.example.listadelacompra.data

import com.example.listadelacompra.ElementsApplication
import com.example.listadelacompra.ui.model.ElementModel

class ElementsRepository {

    private val dao = ElementsApplication.database.taskDao()

    //Primero recuperamos todas los elementos
    val elements: MutableList<ElementModel> = dao.getElements().map { entity ->
        ElementModel(entity.id, entity.element, entity.complete)
    }.toMutableList()

    //Ahora recuperamos todos los elementos completados
    val elementCompleted: MutableList<ElementModel> = dao.getElements().map { entity ->
        ElementModel(entity.id, entity.element, entity.complete)
    }.toMutableList()


    suspend fun add(elementModel: ElementModel) {
        dao.addTask(
            ElementsEntity(
                elementModel.id,
                elementModel.element,
                elementModel.complete
            )
        )
    }

    suspend fun delete(elementModel: ElementModel){
        dao.deleteElement(
            ElementsEntity(
                elementModel.id,
                elementModel.element,
                elementModel.complete
            )
        )
    }
}