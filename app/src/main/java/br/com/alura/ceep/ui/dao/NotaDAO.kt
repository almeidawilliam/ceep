package br.com.alura.ceep.ui.dao

import br.com.alura.ceep.ui.model.Nota
import java.util.*

class NotaDAO {

    companion object {
        private val notas: MutableList<Nota> = ArrayList()
    }

    fun todos(): MutableList<Nota> {
        return notas.toMutableList()
    }

    fun insere(vararg notas: Nota) {
        Companion.notas.addAll(listOf(*notas))
    }

    fun altera(posicao: Int, nota: Nota) {
        notas[posicao] = nota
    }

    fun remove(posicao: Int) {
        notas.removeAt(posicao)
    }

    fun troca(posicaoInicio: Int, posicaoFim: Int) {
        Collections.swap(notas, posicaoInicio, posicaoFim)
    }

    fun removeTodos() {
        notas.clear()
    }
}