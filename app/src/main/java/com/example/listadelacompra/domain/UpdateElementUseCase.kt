package com.example.listadelacompra.domain

import com.example.listadelacompra.data.room.ElementsRepository
import com.example.listadelacompra.ui.model.ElementModel

class UpdateElementUseCase {

    private var repository = ElementsRepository()

    suspend operator fun invoke(elementModel: ElementModel){
        return repository.update(elementModel)
    }
}