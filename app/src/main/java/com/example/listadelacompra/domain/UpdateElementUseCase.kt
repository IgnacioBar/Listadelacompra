package com.example.listadelacompra.domain

import com.example.listadelacompra.data.ElementsRepository
import com.example.listadelacompra.ui.model.ElementModel

/*****
 * Proyect: Listadelacompra
 * Package: com.example.listadelacompra.domain
 *
 * Created by Rafael Barbeyto Torrellas on 03/07/2023 at 11:24
 * More info: https://www.linkedin.com/in/rafa-barbeyto/
 *
 * All rights reserved 2023.
 *****/
class UpdateElementUseCase {
    private var repository = ElementsRepository()

    suspend operator fun invoke(elementModel: ElementModel){
        return repository.update(elementModel)
    }
}