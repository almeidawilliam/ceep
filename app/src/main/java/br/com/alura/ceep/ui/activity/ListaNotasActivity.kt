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
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter

class ListaNotasActivity : AppCompatActivity() {

    private lateinit var adapter: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)
        val todasNotas: MutableList<Nota> = NotaDAO().todos()
        configuraRecyclerView(todasNotas)
        val startForResult = registerForActivityResult()
        configuraBotaoInsereNota(startForResult)
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
        listView.adapter = this.adapter
    }
}