package com.example.listadelacompra.core

sealed class Recyclers(val type: Int){
    object Superior: Recyclers(0)
    object Infieror: Recyclers(1)
}
