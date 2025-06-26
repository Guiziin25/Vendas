package model;

import java.util.Date;

public class FuncionarioAssalariado extends Funcionario {
    private double salarioBase;
    private double beneficios;

    // Construtor atualizado
    public FuncionarioAssalariado(int id, String nome, String cpf, String email,
                                  String telefone, Date dataContratacao,
                                  String cargo, String departamento,
                                  String login, String senha, String[] permissoes,
                                  double salarioBase, double beneficios) {
        super(id, nome, cpf, email, telefone, dataContratacao, cargo, departamento,
                login, senha, permissoes);
        this.salarioBase = salarioBase;
        this.beneficios = beneficios;
    }

    @Override
    public double calcularRemuneracao() {
        return salarioBase + beneficios;
    }

    // Getters específicos
    public double getSalarioBase() {
        return salarioBase;
    }

    public double getBeneficios() {
        return beneficios;
    }
}