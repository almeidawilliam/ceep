package br.com.alura.ceep.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA
import br.com.alura.ceep.ui.model.Nota

class FormularioNotaActivity : AppCompatActivity() {

    private var posicaoRecebida = POSICAO_INVALIDA
    private lateinit var tituloView: TextView
    private lateinit var descricaoView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)
        inicializaCampos()

        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            val nota = dadosRecebidos.getSerializableExtra(CHAVE_NOTA) as Nota
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA)
            preencheCampos(nota)
        }
    }

    private fun preencheCampos(nota: Nota) {
        tituloView.text = nota.titulo
        descricaoView.text = nota.descricao
    }

    private fun inicializaCampos() {
        tituloView = findViewById<TextView>(R.id.formulario_nota_titulo)
        descricaoView = findViewById<TextView>(R.id.formulario_nota_descricao)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota_salva, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (ehMenuSalvaNota(item)) {
            val notaCriada = criaNota()
            retornaNota(notaCriada)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun retornaNota(notaCriada: Nota) {
        val resultadoInsercao = Intent()
        resultadoInsercao.putExtra(CHAVE_NOTA, notaCriada)
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida)
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao)
    }

    private fun criaNota(): Nota {
        val titulo = tituloView
        val descricao = descricaoView

        return Nota(titulo.text.toString(), descricao.text.toString())
    }

    private fun ehMenuSalvaNota(item: MenuItem) =
        R.id.menu_formulario_nota_ic_salva == item.itemId
}