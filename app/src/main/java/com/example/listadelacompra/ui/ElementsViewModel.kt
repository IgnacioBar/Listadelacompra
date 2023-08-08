package com.example.listadelacompra.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadelacompra.domain.AddElementUseCase
import com.example.listadelacompra.domain.DeleteElementUseCase
import com.example.listadelacompra.domain.GetElementsUseCase
import com.example.listadelacompra.domain.UpdateElementUseCase
import com.example.listadelacompra.ui.model.ElementModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ElementsViewModel : ViewModel() {

    private var _elements = MutableLiveData<MutableList<ElementModel>>()
    val elements: LiveData<MutableList<ElementModel>> = _elements

    private var _elementCompleted = MutableLiveData<MutableList<ElementModel>>()
    val elementsCompleted: LiveData<MutableList<ElementModel>> = _elementCompleted

    private var _versionCode = MutableLiveData<Long>()
    val versionCode: LiveData<Long> = _versionCode

    private var _versionName = MutableLiveData<String>()
    val versionName: LiveData<String> = _versionName

    private var database = FirebaseDatabase.getInstance()

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

    fun checkVersion(){

        //crear un private var database = FirebaseDatabase.getInstance()

        var databaseReference = database.getReference("VERSION")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (childSnapshot in snapshot.children){
                        val key = childSnapshot.key
                        val value = childSnapshot.value
                        when(key){
                            "versionCode" -> {
                                _versionCode.value = value as Long
                            }
                            "versionName" -> {
                                _versionName.value = value.toString()
                            }
                        }
                    }
                }else {
                    Log.i("DEVELOPRAFA", "No hay datos disponibles en el DataBase")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("DEVELOPRAFA", "Error: ${error.message}")
            }

        })
    }

}