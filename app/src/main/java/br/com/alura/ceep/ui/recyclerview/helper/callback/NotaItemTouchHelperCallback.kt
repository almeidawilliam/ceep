package br.com.alura.ceep.ui.recyclerview.helper.callback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter

class NotaItemTouchHelperCallback(
    private val adapter: ListaNotasAdapter
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val marcacoesDeDeslize = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val marcacoesDeArrastar = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        NotaDAO().troca(viewHolder.adapterPosition, target.adapterPosition)
        adapter.troca(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val posicaoDaNotaDeslizada = viewHolder.adapterPosition
        NotaDAO().remove(posicaoDaNotaDeslizada)
        adapter.remove(posicaoDaNotaDeslizada)
    }

}
