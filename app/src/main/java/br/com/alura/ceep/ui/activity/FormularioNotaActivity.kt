package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA
import br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA
import br.com.alura.ceep.ui.model.Nota

class FormularioNotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)

        val dadosRecebidos = intent
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            val nota = dadosRecebidos.getSerializableExtra(CHAVE_NOTA) as Nota

            val tituloView = findViewById<TextView>(R.id.formulario_nota_titulo)
            val descricaoView = findViewById<TextView>(R.id.formulario_nota_descricao)

            tituloView.text = nota.titulo
            descricaoView.text = nota.descricao
        }
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
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoInsercao)
    }

    private fun criaNota(): Nota {
        val titulo = findViewById<EditText>(R.id.formulario_nota_titulo)
        val descricao = findViewById<EditText>(R.id.formulario_nota_descricao)

        return Nota(titulo.text.toString(), descricao.text.toString())
    }

    private fun ehMenuSalvaNota(item: MenuItem) =
        R.id.menu_formulario_nota_ic_salva == item.itemId
}