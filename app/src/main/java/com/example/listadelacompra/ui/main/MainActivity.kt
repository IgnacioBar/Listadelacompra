package com.example.listadelacompra.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadelacompra.R
import com.example.listadelacompra.databinding.ActivityMainBinding
import com.example.listadelacompra.databinding.NewElementBinding
import com.example.listadelacompra.ui.adapter.ElementAdapter
import com.example.listadelacompra.ui.adapter.OnClickListener
import com.example.listadelacompra.ui.model.ElementModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: ElementAdapter

    private lateinit var mLinearLayout: RecyclerView.LayoutManager

    private val mainViewModel by viewModels<ElementsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // Inicializar el recyclerView
        initRecyclerView()

        //Establezco la configuración de observadores del ViewModel
        initViewModel()

        // Manejar el clic en el botón de agregar tarea
        mBinding.btnAddElement.setOnClickListener {
            showDialog()
        }

    }

    private fun initRecyclerView() {
        //una unica columna
        mAdapter = ElementAdapter(mutableListOf(), this)
        mLinearLayout = LinearLayoutManager(this)
        //Grid
        // mGridLayout = GridLayoutManager(this, spanCount)
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLinearLayout //mGridLayout
            adapter = mAdapter
        }
    }

    private fun initViewModel() {
        mainViewModel.getAllElements()

        mainViewModel.elements.observe(this) { elementsList ->
            //Si el listado de tareas cambia, se actualiza el RV
            mAdapter = ElementAdapter(elementsList, this)
            mBinding.recyclerView.adapter = mAdapter
            mBinding.recyclerView.adapter?.notifyItemChanged(elementsList.size - 1)
        }
    }

    override fun onClickItem(elementModel: ElementModel) {
        Toast.makeText(this, "Has pulsado el elemento: ${elementModel.element}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteElement(elementModel: ElementModel) {
        // Eliminar la tarea de la lista
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.text_title_alert_delete))
            .setMessage(getString(R.string.text_explain_delete).plus("${elementModel.element}?"))
            .setNegativeButton("Descartar") { _, _ ->
                // Respond to neutral button press
            }
            .setPositiveButton("Eliminar") { _, _ ->
                mainViewModel.onDeleteElement(elementModel)
                Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showDialog() {

        val dialogBinding = NewElementBinding.inflate(LayoutInflater.from(this))
        val dialogView: View = dialogBinding.root

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Añadir Elemento")
            .setCancelable(false)

        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnAddElementDialog.setOnClickListener {

            val element = dialogBinding.etNameElementDialog.text.toString()

            if (element.isNotEmpty()) {
                // Agregar la tarea a la lista
                mainViewModel.addElement(
                    ElementModel(
                        element = element.trim(),
                        complete = false
                    )
                )
                dialog.dismiss()
            }
        }
    }
}