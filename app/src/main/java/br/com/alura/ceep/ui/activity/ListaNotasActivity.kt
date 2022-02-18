package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

        val startForResult = registerForActivityResult()

        val botaoInsereNota = findViewById<TextView>(R.id.lista_notas_insere_nota)
        botaoInsereNota.setOnClickListener {
            val iniciaFormularioNota =
                Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
            startForResult.launch(iniciaFormularioNota)
//            startActivityForResult(iniciaFormularioNota, 1)
        }
    }

    private fun registerForActivityResult(): ActivityResultLauncher<Intent> {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                if (result.resultCode == 2 && result.data != null && result.data!!.hasExtra("nota")) {
                    val notaRecebida = result.data!!.getSerializableExtra("nota") as Nota
                    NotaDAO().insere(notaRecebida)
                    adapter.adiciona(notaRecebida)
                }
            }
        return startForResult
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 1 && resultCode == 2 && data?.hasExtra("nota") == true) {
//            val notaRecebida = data.getSerializableExtra("nota") as Nota
//            NotaDAO().insere(notaRecebida)
//            adapter.adiciona(notaRecebida)
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun notasDeExemplo(): MutableList<Nota> {
        val dao = NotaDAO()

        dao.insere(
            Nota("Nota 1", "descrição 1"),
            Nota("Nota 2", "descrição 2"),
        )
        return dao.todos()
    }

    private fun configuraRecyclerView(notas: MutableList<Nota>) {
        val listView = findViewById<RecyclerView>(R.id.lista_notas_recyclerview)
        configuraAdapter(listView, notas)
    }

    private fun configuraAdapter(
        listView: RecyclerView,
        notas: MutableList<Nota>
    ) {
        this.adapter = ListaNotasAdapter(this@ListaNotasActivity, notas)
        listView.adapter = this.adapter
    }
}