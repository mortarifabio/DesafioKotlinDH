package classes

class Aluno (
    val nome: String,
    val sobrenome: String,
    val codigo: String
) {
    override fun equals(other: Any?): Boolean {
        return (other as? Aluno)?.let {
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "$nome $sobrenome (Cod. $codigo)"
    }
}