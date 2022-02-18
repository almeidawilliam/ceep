package br.com.alura.ceep.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.R
import br.com.alura.ceep.ui.dao.NotaDAO
import br.com.alura.ceep.ui.model.Nota

class FormularioNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota_salva, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_formulario_nota_ic_salva == item.itemId) {
            val titulo = findViewById<EditText>(R.id.formulario_nota_titulo)
            val descricao = findViewById<EditText>(R.id.formulario_nota_descricao)

            val notaCriada = Nota(titulo.text.toString(), descricao.text.toString())

            NotaDAO().insere(notaCriada)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}