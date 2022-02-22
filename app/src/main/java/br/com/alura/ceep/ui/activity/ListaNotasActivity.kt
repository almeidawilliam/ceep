package br.com.alura.ceep.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback

class ListaNotasActivity : AppCompatActivity() {

    companion object {
        private const val TITULO_APPBAR = "Notas"
    }

    private lateinit var adapter: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)
        title = (TITULO_APPBAR)
        val todasNotas = //notasDeExemplo()
        configuraRecyclerView(todasNotas())
        val startForResult = registerForActivityResultInsere()
        configuraBotaoInsereNota(startForResult)
    }

    private fun configuraRecyclerView(notas: MutableList<Nota>) {
        val listView = findViewById<RecyclerView>(R.id.lista_notas_recyclerview)
        configuraAdapter(listView, notas)
        configuraItemTouchHelper(listView)
    }

    private fun configuraItemTouchHelper(listView: RecyclerView?) {
        val itemTouchHelper = ItemTouchHelper(NotaItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(listView)
    }

    private fun todasNotas() : MutableList<Nota> {
        val dao = NotaDAO()
//        for (i in 1..10) {
//            dao.insere(
//                Nota("Título $i", "Descrição $i")
//            )
//        }
        return dao.todos()
    }

    private fun configuraBotaoInsereNota(startForResult: ActivityResultLauncher<Intent>) {
        val botaoInsereNota = findViewById<TextView>(R.id.lista_notas_insere_nota)
        botaoInsereNota.setOnClickListener {
            vaiParaFormularioNotaActivityInsere(startForResult)
        }
    }

    private fun vaiParaFormularioNotaActivityInsere(startForResult: ActivityResultLauncher<Intent>) {
        val iniciaFormularioNota =
            Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
        startForResult.launch(iniciaFormularioNota)
    }

    private fun registerForActivityResultInsere(): ActivityResultLauncher<Intent> {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK
                    && result.data != null
                    && result.data!!.hasExtra(CHAVE_NOTA)
                ) {
                    val notaRecebida = result.data!!.getSerializableExtra(CHAVE_NOTA) as Nota
                    NotaDAO().insere(notaRecebida)
                    adapter.adiciona(notaRecebida)
//                } else if (result.resultCode == Activity.RESULT_CANCELED){
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

    private fun configuraAdapter(
        listView: RecyclerView,
        notas: MutableList<Nota>
    ) {
        val registerForActivityResultAltera = registerForActivityResultAltera()
        this.adapter = ListaNotasAdapter(this@ListaNotasActivity, notas)
        this.adapter.onItemClickListener =
            object : OnItemClickListener {
                override fun onItemClick(nota: Nota, posicao: Int) {
                    vaiParaFormularioNotaActivityAltera(
                        nota,
                        posicao,
                        registerForActivityResultAltera
                    )
                }
            }
        listView.adapter = this.adapter
    }

    private fun vaiParaFormularioNotaActivityAltera(
        nota: Nota,
        posicao: Int,
        registerForActivityResult: ActivityResultLauncher<Intent>
    ) {
        val intent = Intent(this@ListaNotasActivity, FormularioNotaActivity::class.java)
        intent.putExtra(CHAVE_NOTA, nota)
        intent.putExtra(CHAVE_POSICAO, posicao)
        registerForActivityResult.launch(intent)
    }

    private fun registerForActivityResultAltera(): ActivityResultLauncher<Intent> {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK
                    && result.data != null
                    && result.data!!.hasExtra(CHAVE_NOTA)
                ) {
                    result.data
                    val notaRecebida = result.data!!.getSerializableExtra(CHAVE_NOTA) as Nota
                    val posicaoRecebida = result.data!!.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA)

                    if (posicaoRecebida > POSICAO_INVALIDA) {
                        NotaDAO().altera(posicaoRecebida, notaRecebida)
                        adapter.altera(posicaoRecebida, notaRecebida)
//                    } else {
//                        Toast.makeText(
//                            this,
//                            "Ocorreu um problema na alteração da nota",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
                }
            }
        return startForResult
    }
}