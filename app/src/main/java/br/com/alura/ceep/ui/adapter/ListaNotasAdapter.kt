package br.com.alura.ceep.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.model.Nota

class ListaNotasAdapter(
    private val context: Context,
    private val notas: List<Nota>
) : BaseAdapter() {

    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(posicao: Int): Nota {
        return notas[posicao]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(posicao: Int, view: View?, viewGroup: ViewGroup?): View {
        val viewCriada: View =
            LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false)
        val nota: Nota = notas[posicao]

        val titulo: TextView = viewCriada.findViewById(R.id.item_nota_titulo)
        titulo.text = nota.titulo

        val descricao: TextView = viewCriada.findViewById(R.id.item_nota_descricao)
        descricao.text = nota.descricao

        return viewCriada
    }

}