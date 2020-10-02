package classes

class DigitalHouseManager {
    private val cursos: MutableMap<String, Curso> = mutableMapOf()
    private val professores: MutableMap<String, Professor> = mutableMapOf()
    private val alunos: MutableMap<String, Aluno> = mutableMapOf()
    private val matriculas: MutableList<Matricula> = mutableListOf()

    init {
        println("Digital House Manager iniciado com sucesso.")
    }

    fun registrarCurso(nome: String, codigo: String, qtdMaxAlunos: Int): Boolean {
        return if(!cursos.containsKey(codigo)) {
            val curso = Curso(nome, codigo, qtdMaxAlunos)
            cursos[codigo] = curso
            println("Curso $curso registrado com sucesso.")
            true
        } else {
            println("Erro ao registrar: já existe um curso registrado com o código '$codigo'.")
            false
        }
    }

    fun excluirCurso(codigo: String) {
        if(cursos.containsKey(codigo)) {
            val curso = cursos[codigo]
            cursos.remove(codigo)
            matriculas.removeAll(matriculas.filter { it.curso == curso })
            println("Curso $curso excluído com sucesso.")
        } else {
            println("Erro ao excluir: não há curso registrado com o código '$codigo'.")
        }
    }

    fun registrarProfessorAdjunto(nome: String, sobrenome: String, codigo: String, qtdHorasMonitoria: Int, contratacao: String? = null): Boolean {
        return if(!professores.containsKey(codigo)) {
            val professor = ProfessorAdjunto(nome, sobrenome, codigo, qtdHorasMonitoria, contratacao)
            professores[codigo] = professor
            println("Professor(a) $professor registrado(a) com sucesso.")
            true
        } else {
            println("Erro ao registrar: já existe um(a) professor(a) registrado(a) com o código '$codigo'.")
            false
        }
    }

    fun registrarProfessorTitular(nome: String, sobrenome: String, codigo: String, especialidade: String, contratacao: String? = null): Boolean {
        return if(!professores.containsKey(codigo)) {
            val professor = ProfessorTitular(nome, sobrenome, codigo, especialidade, contratacao)
            professores[codigo] = professor
            println("Professor(a) $professor registrado(a) com sucesso.")
            true
        } else {
            println("Erro ao registrar: já existe um(a) professor(a) registrado(a) com o código '$codigo'.")
            false
        }
    }

    fun excluirProfessor(codigo: String) {
        if(professores.containsKey(codigo)) {
            val professor = professores[codigo]
            val alocado = cursos.filterValues { listOf(it.professorTitular, it.professorAdjunto).contains(professor) }
            if(alocado.isEmpty()){
                professores.remove(codigo)
                println("Professor(a) $professor excluído(a) com sucesso.")
            } else {
                println("Erro ao excluir: o(a) professor(a) $professor está alocado(a) no(s) curso(s) ${alocado.textualizar()}.")
            }
        } else {
            println("Erro ao excluir: não há professor(a) registrado(a) com o código '$codigo'.")
        }
    }

    fun alocarProfessores(codTitular: String, codAdjunto: String, codCurso: String) {
        when {
            !cursos.containsKey(codCurso) -> println("Erro ao alocar: não há curso registrado com o código '$codCurso'.")
            !professores.containsKey(codTitular) -> println("Erro ao alocar: não há professor(a) registrado(a) com o código '$codTitular'.")
            !professores.containsKey(codAdjunto) -> println("Erro ao alocar: não há professor(a) registrado(a) com o código '$codAdjunto'.")
            else -> {
                cursos[codCurso]?.alocarProfessores(professores[codTitular], professores[codAdjunto]) ?: println("Erro ao alocar: curso inválido.")
            }
        }
    }

    fun desalocarProfessor(codProfessor: String, codCurso: String) {
        when {
            !cursos.containsKey(codCurso) -> println("Erro ao desalocar: não há curso registrado com o código '$codCurso'.")
            !professores.containsKey(codProfessor) -> println("Erro ao desalocar: não há professor(a) registrado(a) com o código '$codProfessor'.")
            else -> {
                cursos[codCurso]?.desalocarProfessor(professores[codProfessor]) ?: println("Erro ao desalocar: curso inválido.")
            }
        }
    }

    fun tempoDeCasa(codigo: String) {
        if(professores.containsKey(codigo)) {
            professores[codigo]?.tempoDeCasa()
        } else {
            println("Erro ao calcular tempo de casa: não há professor(a) registrado(a) com o código '$codigo'.")
        }
    }

    fun registrarAluno(nome: String, sobrenome: String, codigo: String): Boolean {
        return if(!alunos.containsKey(codigo)) {
            val aluno = Aluno(nome, sobrenome, codigo)
            alunos[codigo] = aluno
            println("Aluno(a) $aluno registrado(a) com sucesso.")
            true
        } else {
            println("Erro ao registrar: já existe um(a) aluno(a) registrado(a) com o código '$codigo'.")
            false
        }
    }

    fun excluirAluno(codigo: String) {
        if(alunos.containsKey(codigo)) {
            val aluno = alunos[codigo]
            if(matriculas.none { it.aluno == aluno }){
                alunos.remove(codigo)
                println("Aluno(a) $aluno excluído(a) com sucesso.")
            } else {
                val matriculado = cursos.filterValues { curso -> curso.alunosMatriculados.filterValues { it == aluno }.isNotEmpty() }
                println("Erro ao excluir: o aluno $aluno está matriculado no(s) curso(s) ${matriculado.textualizar()}.")
            }
        } else {
            println("Erro ao excluir: não há aluno(a) registrado(a) com o código '$codigo'.")
        }
    }

    fun matricularAluno(codAluno: String, codCurso: String) {
        when {
            !cursos.containsKey(codCurso) -> println("Erro ao matricular: não há curso registrado com o código '$codCurso'.")
            !alunos.containsKey(codAluno) -> println("Erro ao matricular: não há aluno(a) registrado(a) com o código '$codAluno'.")
            else -> {
                cursos[codCurso]?.let { curso ->
                    alunos[codAluno]?.let { aluno ->
                        if(curso.alunosMatriculados.size < curso.qtdMaxAlunos){
                            if(curso.adicionarAluno(aluno)) {
                                matriculas.add(Matricula(aluno, curso))
                                println("Aluno(a) $aluno matriculado(a) com sucesso no curso $curso.")
                            }
                        } else {
                            println("Erro ao matricular: não há vagas disponíveis para matricular o(a) aluno(a) $aluno no curso $curso")
                        }
                    } ?: println("Erro ao matricular: aluno inválido.")
                } ?: println("Erro ao matricular: curso inválido.")
            }
        }
    }

    fun desmatricularAluno(codAluno: String, codCurso: String) {
        when {
            !cursos.containsKey(codCurso) -> println("Erro ao desmatricular: não há curso registrado com o código '$codCurso'.")
            !alunos.containsKey(codAluno) -> println("Erro ao desmatricular: não há aluno(a) registrado(a) com o código '$codAluno'.")
            else -> {
                cursos[codCurso]?.let { curso ->
                    alunos[codAluno]?.let { aluno ->
                        if(curso.excluirAluno(aluno)) {
                            matriculas.removeAll(matriculas.filter { it.aluno == aluno && it.curso == curso })
                            println("Aluno(a) $aluno desmatriculado(a) com sucesso do curso $curso.")
                        }
                    } ?: println("Erro ao desmatricular: aluno inválido.")
                } ?: println("Erro ao desmatricular: curso inválido.")
            }
        }
    }

    private fun Map<String, Any>.textualizar(): String {
        var texto = ""
        this.values.forEachIndexed { k, it ->
            texto += when {
                k > 0 && k < this.size - 1 -> ", "
                k > 0 && k == this.size - 1 -> " e "
                else -> ""
            }
            texto += it
        }
        return texto
    }
}
