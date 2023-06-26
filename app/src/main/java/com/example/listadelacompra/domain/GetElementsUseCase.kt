package com.example.listadelacompra.domain

import com.example.listadelacompra.data.ElementsRepository
import com.example.listadelacompra.ui.model.ElementModel

class GetElementsUseCase {

    private var repository = ElementsRepository()

    operator fun invoke(): MutableList<ElementModel>{
        return repository.elements
    }
}