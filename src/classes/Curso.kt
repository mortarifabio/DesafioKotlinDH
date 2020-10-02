package classes

class Curso (
    val nome: String,
    val codigo: String,
    var qtdMaxAlunos: Int,
) {
    val alunosMatriculados: MutableMap<String, Aluno> = mutableMapOf()
    var professorTitular: ProfessorTitular? = null
    var professorAdjunto: ProfessorAdjunto? = null

    override fun equals(other: Any?): Boolean {
        return (other as? Curso)?.let {
            codigo == it.codigo
        } ?: false
    }

    override fun hashCode(): Int {
        return codigo.hashCode()
    }

    override fun toString(): String {
        return "$nome (Cod. $codigo)"
    }

    fun adicionarAluno(aluno: Aluno): Boolean {
        return (aluno as? Aluno)?.let {
            if(!alunosMatriculados.containsKey(it.codigo)){
                alunosMatriculados[it.codigo] = it
                true
            } else {
                println("Já há um aluno matriculado com o código '${it.codigo}'.")
                false
            }
        } ?: run {
            println("Não foi possível adicionar o aluno.")
            false
        }
    }

    fun excluirAluno(aluno: Any): Boolean {
        val codigo = when(aluno) {
            is String -> aluno
            is Aluno -> aluno.codigo
            else -> null
        }
        return codigo?.let {
            if(alunosMatriculados.containsKey(it)) {
                alunosMatriculados.remove(it)
                true
            } else {
                println("Erro ao desmatricular: o(a) aluno(a) não está matriculado(a) nesse curso.")
                false
            }
        } ?: run {
            println("Erro ao desmatricular: aluno(a) inválido(a).")
            false
        }
    }

    fun alocarProfessores(profTitular: Professor?, profAdjunto: Professor?) {
        (profTitular as? ProfessorTitular)?.let { titular ->
            (profAdjunto as? ProfessorAdjunto)?.let { adjunto ->
                this.professorTitular = titular
                this.professorAdjunto = adjunto
                println("Professores(as) $titular [titular] e $adjunto [adjunto] alocados(as) com sucesso no curso $this.")
            } ?: println("Erro ao alocar: professor adjunto inválido.")
        } ?: println("Erro ao alocar: professor titular inválido.")
    }

    fun desalocarProfessor(professor: Professor?) {
        when {
            professor is ProfessorTitular && this.professorTitular == professor -> {
                this.professorTitular = null
                println("Professor(a) titular $professor desalocado(a) com sucesso no curso $this.")
            }
            professor is ProfessorAdjunto && this.professorAdjunto == professor -> {
                this.professorAdjunto = null
                println("Professor(a) adjunto $professor desalocado(a) com sucesso no curso $this.")
            }
            else -> {
                println("Erro ao desalocar: professor inválido.")
            }
        }
    }

}