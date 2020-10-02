package classes

class ProfessorTitular (
    nome: String,
    sobrenome: String,
    codigo: String,
    var especialidade: String,
    contratacao: String? = null   // Para usar data de contratação retroativa
): Professor(nome, sobrenome, codigo, contratacao)