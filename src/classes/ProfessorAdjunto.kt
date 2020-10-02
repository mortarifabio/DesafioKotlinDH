package classes

class ProfessorAdjunto (
    nome: String,
    sobrenome: String,
    codigo: String,
    var qtdHorasMonitoria: Int,
    contratacao: String? = null   // Para usar data de contratação retroativa
): Professor(nome, sobrenome, codigo, contratacao)