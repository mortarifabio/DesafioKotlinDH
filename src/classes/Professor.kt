package classes

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

abstract class Professor (
    val nome: String,
    val sobrenome: String,
    val codigo: String,
    private val contratacao: String? = null   // Possibilitando usar data de contratação retroativa
) {
    private var dataContratacao: LocalDate = LocalDate.now()

    init {
        contratacao?.let {
            setDataContratacao(contratacao)
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Professor)?.let {
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "$nome $sobrenome (Cod. $codigo)"
    }

    private fun setDataContratacao(data: String){
        val infoData = data.split("/")
        if(infoData.size == 3) {
            try {
                dataContratacao = LocalDate.of(infoData[2].toInt(), infoData[1].toInt(), infoData[0].toInt())
            } catch (exception: NumberFormatException) {
                println("Formato de data inválido, utilize o formato 'dd/mm/aaaa'.")
            }
        } else {
            println("Formato de data inválido, utilize o formato 'dd/mm/aaaa'.")
        }
    }

    fun tempoDeCasa() {
        val periodo = Period.between(dataContratacao, LocalDate.now())
        val anos = periodo.get(ChronoUnit.YEARS)
        val meses = periodo.get(ChronoUnit.MONTHS)
        val dias = periodo.get(ChronoUnit.DAYS)

        if(anos == 0L && meses == 0L && dias == 0L) {
            println("$this acabou de ser contratado(a).")
        } else {
            print("$this é professor(a) da Digital House há")
            if(anos == 1L) print(" $anos ano")
            if(anos > 1L) print(" $anos anos")
            if(anos > 0L && meses > 0L && dias > 0L) print(",")
            if(anos > 0L && meses > 0L && dias == 0L) print(" e")
            if(meses == 1L) print(" $meses mês")
            if(meses > 1L) print(" $meses meses")
            if((anos > 0L || meses > 0L) && dias > 0L) print(" e")
            if(dias == 1L) print(" $dias dia")
            if(dias > 1L) print(" $dias dias")
            println(".")
        }
    }
}