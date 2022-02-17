package br.com.alura.ceep.ui.dao

import br.com.alura.ceep.ui.model.Nota
import java.util.*

class NotaDAO {

    companion object {
        private val notas: ArrayList<Nota?> = ArrayList<Nota?>()
    }

    fun todos(): List<Nota> {
        return notas.clone() as List<Nota>
    }

    fun insere(vararg notas: Nota?) {
        Companion.notas.addAll(Arrays.asList(*notas))
    }

    fun altera(posicao: Int, nota: Nota?) {
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