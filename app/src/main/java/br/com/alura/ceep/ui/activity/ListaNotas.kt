package br.com.alura.ceep.ui.activity

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota

class ListaNotas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)

        val listView = findViewById<ListView>(R.id.listView)
        val dao = NotaDAO()
        dao.insere(
            Nota("First note", "First description")
        )
        val notes = dao.todos()
        listView.adapter = ListaNotasAdapter(this, notes)
    }
}