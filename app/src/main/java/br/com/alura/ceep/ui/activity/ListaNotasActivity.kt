package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter

class ListaNotasActivity : AppCompatActivity() {

    private lateinit var adapter: ListaNotasAdapter
    private var todasNotas: MutableList<Nota> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)
        todasNotas = notasDeExemplo()
        configuraRecyclerView(todasNotas)

        val botaoInsereNota = findViewById<TextView>(R.id.lista_notas_insere_nota)
        botaoInsereNota.setOnClickListener {
            val iniciaFormularioNota =
                Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
            startActivity(iniciaFormularioNota)
        }
    }

    override fun onResume() {
        val dao = NotaDAO()
        todasNotas.clear()
        todasNotas.addAll(dao.todos())
        adapter.notifyDataSetChanged()
        super.onResume()
    }

    private fun notasDeExemplo(): MutableList<Nota> {
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
        this.adapter = ListaNotasAdapter(this@ListaNotasActivity, notas)
        listView.adapter = this.adapter
    }
}