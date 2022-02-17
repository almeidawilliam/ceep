package br.com.alura.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.model.Nota

class ListaNotasAdapter(
    private val context: Context,
    private val listaNotas: List<Nota>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewCriada = LayoutInflater
            .from(context)
            .inflate(R.layout.item_nota, parent, false)

        return NotaViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nota = listaNotas[position]
        val tituloView = holder.itemView.findViewById<TextView>(R.id.item_nota_titulo)
        tituloView.text = nota.titulo

        val descricaoView = holder.itemView.findViewById<TextView>(R.id.item_nota_descricao)
        descricaoView.text = nota.descricao
    }

    override fun getItemCount(): Int =
        listaNotas.size
}