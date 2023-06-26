package com.example.listadelacompra.domain

import com.example.listadelacompra.data.ElementsRepository
import com.example.listadelacompra.ui.model.ElementModel

class DeleteElementUseCase {

    private var repository = ElementsRepository()

    suspend operator fun invoke(taskModel: ElementModel){
        return repository.delete(taskModel)
    }
}