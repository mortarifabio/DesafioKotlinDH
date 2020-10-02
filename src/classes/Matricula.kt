package classes

import java.time.LocalDate

class Matricula (
    val aluno: Aluno,
    val curso: Curso
){
    var dataMatricula: LocalDate = LocalDate.now()
}