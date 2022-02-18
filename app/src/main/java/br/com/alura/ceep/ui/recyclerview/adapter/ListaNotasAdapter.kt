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
    private val listaNotas: MutableList<Nota>
) : RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val viewCriada =
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_nota, parent, false)

        return NotaViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.vincula(nota)
    }

    override fun getItemCount(): Int =
        listaNotas.size

    fun adiciona(nota: Nota) {
        listaNotas.add(nota)
        notifyDataSetChanged()
    }

    inner class NotaViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val tituloView = itemView.findViewById<TextView>(R.id.item_nota_titulo)
        private val descricaoView = itemView.findViewById<TextView>(R.id.item_nota_descricao)

        init {
            itemView.setOnClickListener(
                View.OnClickListener {
                    view -> {
                    onItemClickListener.onItemClick()
                }
                }
            )
        }

        fun vincula(nota: Nota) {
            tituloView.text = nota.titulo
            descricaoView.text = nota.descricao
        }
    }
}