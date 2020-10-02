import classes.DigitalHouseManager

fun main() {
    println("\n\n:: CARREGANDO A APLICAÇÃO\n")
    val dhm = DigitalHouseManager()

    println("\n\n:: REGISTRANDO CURSO\n")
    dhm.registrarCurso("Santander Coders Mobile Android", "CM001", 3)
    dhm.registrarCurso("Santander Coders Mobile iOS", "CM002", 3)
    dhm.registrarCurso("Desenvolvedor Full-Stack", "CF001", 3)

    println("\n\n:: REGISTRANDO PROFESSORES\n")
    dhm.registrarProfessorTitular("Cesar", "Nascimento", "PT001", "Kotlin")
    dhm.registrarProfessorAdjunto("Eduardo", "Misina", "PA002", 60, "15/01/2018")  // Professor registrado com data de contratação retroativa
    dhm.registrarProfessorTitular("Lívia", "Guimarães", "PT003", "iOS")
    dhm.registrarProfessorAdjunto("Alessandra", "Ramos", "PA002", 40)  // Tentando registrar com código duplicado
    dhm.registrarProfessorAdjunto("Alessandra", "Ramos", "PA004", 40)

    println("\n\n:: REGISTRANDO ALUNOS\n")
    dhm.registrarAluno("Fabio", "Mortari", "AL001")
    dhm.registrarAluno("Caio", "Pontes", "AL002")
    dhm.registrarAluno("Ana", "Martins", "AL003")
    dhm.registrarAluno("Sandra", "Cardoso", "AL002")  // Tentando registrar com código duplicado
    dhm.registrarAluno("Sandra", "Cardoso", "AL004")
    dhm.registrarAluno("Paulo", "Machado", "AL005")

    println("\n\n:: MATRICULANDO ALUNOS\n")
    dhm.matricularAluno("AL001", "CM001")
    dhm.matricularAluno("AL002", "CM001")
    dhm.matricularAluno("AL003", "CM001")
    dhm.matricularAluno("PT001", "CM001")  // Tentando matricular aluno com código de um professor (inexistente para alunos)
    dhm.matricularAluno("AL004", "CM001")  // Tentando matricular em curso sem vagas disponíveis
    dhm.matricularAluno("AL004", "CM003")  // Tentando matricular em curso inexistente
    dhm.matricularAluno("AL004", "CM002")

    println("\n\n:: ALOCANDO PROFESSORES\n")
    dhm.alocarProfessores("PT001", "PA002", "CM001")
    dhm.alocarProfessores("PT001", "PA002", "CM003")  // Tentando alocar em curso inexistente
    dhm.alocarProfessores("PA004", "PT003", "CM002")  // Tentando alocar professores invertidos (adjunto como titular)
    dhm.alocarProfessores("PT003", "PA004", "CM002")

    println("\n\n:: VERIFICANDO TEMPO DE CASA\n")
    dhm.tempoDeCasa("PT001")
    dhm.tempoDeCasa("PA002")

    println("\n\n:: VALIDAÇÕES ADICIONAIS\n")
    dhm.excluirCurso("CF001")                              // Excluindo curso
    println("")
    dhm.excluirAluno("PA002")                              // Tentando excluir aluno com código de um professor (inexistente para alunos)
    dhm.excluirAluno("AL002")                              // Tentando excluir aluno matriculado em curso
    dhm.desmatricularAluno("AL002", "CM002")     // Tentando desmatricular o aluno do curso errado
    dhm.desmatricularAluno("AL002", "CM001")     // Desmatriculando o aluno do curso
    dhm.excluirAluno("AL002")                              // Excluindo aluno após desmatricular
    println("")
    dhm.excluirProfessor("PA004")                          // Tentando excluir professor ajdunto alocado em curso
    dhm.desalocarProfessor("PA004", "CM002")  // Tentando excluir professor alocado em múltiplos cursos
    dhm.excluirProfessor("PA004")                          // Tentando excluir professor ajdunto alocado em curso
}