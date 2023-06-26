package com.example.listadelacompra.domain

import com.example.listadelacompra.data.ElementsRepository
import com.example.listadelacompra.ui.model.ElementModel

class AddElementUseCase {
    private var repository = ElementsRepository()

    suspend operator fun invoke(elementModel: ElementModel){
        return repository.add(elementModel)
    }
}