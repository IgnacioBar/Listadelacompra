package com.example.listadelacompra.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadelacompra.domain.AddElementUseCase
import com.example.listadelacompra.domain.DeleteElementUseCase
import com.example.listadelacompra.domain.GetElementsUseCase
import com.example.listadelacompra.domain.UpdateElementUseCase
import com.example.listadelacompra.ui.model.ElementModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ElementsViewModel : ViewModel() {

    private var _elements = MutableLiveData<MutableList<ElementModel>>()
    val elements: LiveData<MutableList<ElementModel>> = _elements

    private var _elementCompleted = MutableLiveData<MutableList<ElementModel>>()
    val elementsCompleted: LiveData<MutableList<ElementModel>> = _elementCompleted

    fun getAllElements() {
        viewModelScope.launch(Dispatchers.IO) {
            val elements = GetElementsUseCase().invoke().filter { elements ->
                !elements.complete
            }
            withContext(Dispatchers.Main) {
                _elements.value = elements.toMutableList()
            }

        }
    }

    fun getAllElementsComplete() {
        viewModelScope.launch(Dispatchers.IO) {
            val elements = GetElementsUseCase().invoke().filter { elements ->
                elements.complete
            }
            withContext(Dispatchers.Main) {
                _elementCompleted.value = elements.toMutableList()
            }
        }
    }

    fun addElement(elementModel: ElementModel) {
        viewModelScope.launch(Dispatchers.IO) {
            AddElementUseCase().invoke(elementModel)
            getAllElements()
        }
    }


    fun onDeleteElement(elementModel: ElementModel) {
        viewModelScope.launch(Dispatchers.IO) {
            DeleteElementUseCase().invoke(elementModel)
            getAllElements()
            getAllElementsComplete()
        }
    }

    fun onUpdateElement(elementModel: ElementModel) {
        viewModelScope.launch(Dispatchers.IO) {
            UpdateElementUseCase().invoke(elementModel)
            getAllElements()
            getAllElementsComplete()
        }
    }

}