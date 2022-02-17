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

        val listView = findViewById<RecyclerView>(R.id.lista_notas_recyclerview)
        val dao = NotaDAO()

        for (i in 1..1000) {
            dao.insere(
                Nota("Note $i", "description")
            )
        }

        listView.adapter = ListaNotasAdapter(this@ListaNotas, dao.todos())
        val layoutManager = LinearLayoutManager(this@ListaNotas)
        listView.layoutManager = layoutManager
    }
}