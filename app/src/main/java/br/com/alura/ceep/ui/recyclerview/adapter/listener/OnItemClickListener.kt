package br.com.alura.ceep.ui.recyclerview.adapter.listener

import br.com.alura.ceep.ui.model.Nota

interface OnItemClickListener {

    fun onItemClick(nota: Nota, posicao: Int)
}