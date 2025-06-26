package model;

import java.util.Date;

public class FuncionarioHorista extends Funcionario {
    private double valorHora;
    private double horasTrabalhadas;
    private boolean recebeAdicionalNoturno;

    // Construtor atualizado
    public FuncionarioHorista(int id, String nome, String cpf, String email,
                              String telefone, Date dataContratacao,
                              String cargo, String departamento,
                              String login, String senha, String[] permissoes,
                              double valorHora, double horasTrabalhadas,
                              boolean recebeAdicionalNoturno) {
        super(id, nome, cpf, email, telefone, dataContratacao, cargo, departamento,
                login, senha, permissoes);
        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
        this.recebeAdicionalNoturno = recebeAdicionalNoturno;
    }

    @Override
    public double calcularRemuneracao() {
        double salario = valorHora * horasTrabalhadas;
        return recebeAdicionalNoturno ? salario * 1.2 : salario;
    }

    // Getters específicos
    public double getValorHora() {
        return valorHora;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public boolean isRecebeAdicionalNoturno() {
        return recebeAdicionalNoturno;
    }
}