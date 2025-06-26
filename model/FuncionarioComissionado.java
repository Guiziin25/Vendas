package model;

import java.util.Date;

public class FuncionarioComissionado extends Funcionario {
    private double vendasMes;
    private double taxaComissao;

    // Construtor atualizado
    public FuncionarioComissionado(int id, String nome, String cpf, String email,
                                   String telefone, Date dataContratacao,
                                   String cargo, String departamento,
                                   String login, String senha, String[] permissoes,
                                   double vendasMes, double taxaComissao) {
        super(id, nome, cpf, email, telefone, dataContratacao, cargo, departamento,
                login, senha, permissoes);
        this.vendasMes = vendasMes;
        this.taxaComissao = taxaComissao;
    }

    @Override
    public double calcularRemuneracao() {
        return vendasMes * taxaComissao;
    }

    // Getters específicos
    public double getVendasMes() {
        return vendasMes;
    }

    public double getTaxaComissao() {
        return taxaComissao;
    }
}