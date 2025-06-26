package model;

import java.util.Date;

public abstract class Funcionario implements IPagamento {
    // Atributos básicos
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Date dataContratacao;
    private String cargo;
    private String departamento;

    // Atributos de autenticação
    private String login;
    private String senha;
    private String[] permissoes;

    // Construtor completo
    public Funcionario(int id, String nome, String cpf, String email,
                       String telefone, Date dataContratacao,
                       String cargo, String departamento,
                       String login, String senha, String[] permissoes) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataContratacao = dataContratacao;
        this.cargo = cargo;
        this.departamento = departamento;
        this.login = login;
        this.senha = senha;
        this.permissoes = permissoes;
    }

    // Implementação de IPagamento
    @Override
    public boolean processarPagamento(double valor) {
        double salarioDevido = this.calcularRemuneracao();
        return valor >= salarioDevido;
    }

    @Override
    public String gerarComprovante() {
        String comprovante = "COMPROVANTE DE PAGAMENTO\n";
        comprovante += "Funcionário: " + this.nome + " (ID: " + this.id + ")\n";
        comprovante += "Cargo: " + this.cargo + " | Depto: " + this.departamento + "\n";
        comprovante += "Valor: R$" + String.format("%.2f", this.calcularRemuneracao()) + "\n";
        comprovante += "Data: " + new Date().toString();
        return comprovante;
    }

    @Override
    public String getStatus() {
        return "Ativo";
    }

    // Método abstrato
    public abstract double calcularRemuneracao();

    // Getters e Setters para todos os atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String[] getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(String[] permissoes) {
        this.permissoes = permissoes;
    }

    // Método toString completo
    @Override
    public String toString() {
        String info = "Funcionário [ID: " + id + "]\n" +
                "Nome: " + nome + "\n" +
                "CPF: " + cpf + "\n" +
                "Email: " + email + "\n" +
                "Telefone: " + telefone + "\n" +
                "Cargo: " + cargo + " | Departamento: " + departamento + "\n" +
                "Contratado em: " + dataContratacao + "\n" +
                "Login: " + login + "\n" +
                "Permissões: ";

        if (permissoes != null) {
            for (String permissao : permissoes) {
                info += permissao + " ";
            }
        }

        return info;
    }
}