package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener

class ListaNotasActivity : AppCompatActivity() {

    private lateinit var adapter: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)
        val todasNotas = notasDeExemplo()
        configuraRecyclerView(notasDeExemplo())
        val startForResult = registerForActivityResult()
        configuraBotaoInsereNota(startForResult)
    }

    fun notasDeExemplo(): MutableList<Nota> {
        val dao = NotaDAO()
        for (i in 1..10) {
            dao.insere(
                Nota("Título $i", "Descrição $i")
            )
        }
        return dao.todos()
    }

    private fun configuraBotaoInsereNota(startForResult: ActivityResultLauncher<Intent>) {
        val botaoInsereNota = findViewById<TextView>(R.id.lista_notas_insere_nota)
        botaoInsereNota.setOnClickListener {
            vaiParaFormularioNotaActivity(startForResult)
        }
    }

    private fun vaiParaFormularioNotaActivity(startForResult: ActivityResultLauncher<Intent>) {
        val iniciaFormularioNota =
            Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
        startForResult.launch(iniciaFormularioNota)
        //            startActivityForResult(iniciaFormularioNota, 1)
    }

    private fun registerForActivityResult(): ActivityResultLauncher<Intent> {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                if (result.resultCode == CODIGO_RESULTADO_NOTA_CRIADA
                    && result.data != null
                    && result.data!!.hasExtra(CHAVE_NOTA)
                ) {
                    val notaRecebida = result.data!!.getSerializableExtra(CHAVE_NOTA) as Nota
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

    private fun configuraRecyclerView(notas: MutableList<Nota>) {
        val listView = findViewById<RecyclerView>(R.id.lista_notas_recyclerview)
        configuraAdapter(listView, notas)
    }

    private fun configuraAdapter(
        listView: RecyclerView,
        notas: MutableList<Nota>
    ) {
        this.adapter = ListaNotasAdapter(this@ListaNotasActivity, notas)
        this.adapter.onItemClickListener =
            object : OnItemClickListener {
                override fun onItemClick(nota: Nota) {
                    Toast.makeText(this@ListaNotasActivity, nota.titulo, Toast.LENGTH_SHORT).show()
                }
            }
        listView.adapter = this.adapter
    }
}