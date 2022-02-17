package br.com.alura.ceep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter

class ListaNotas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)
        val notasDeExemplo = notasDeExemplo()
        configuraRecyclerView(notasDeExemplo)
    }

    private fun notasDeExemplo(): List<Nota> {
        val dao = NotaDAO()

        dao.insere(
            Nota("Nota 1", "descrição 1"),
            Nota("Nota 2", "descrição 2"),
        )
        return dao.todos()
    }

    private fun configuraRecyclerView(notas: List<Nota>) {
        val listView = findViewById<RecyclerView>(R.id.lista_notas_recyclerview)
        configuraAdapter(listView, notas)
    }

    private fun configuraAdapter(
        listView: RecyclerView,
        notas: List<Nota>
    ) {
        listView.adapter = ListaNotasAdapter(this@ListaNotas, notas)
    }
}