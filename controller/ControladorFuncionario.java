package controller;

import model.Funcionario;
import model.FuncionarioAssalariado;
import model.FuncionarioComissionado;
import model.FuncionarioHorista;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;

public class ControladorFuncionario {
    private static ControladorFuncionario instancia;
    private IRepFuncionario repFuncionario;

    // Construtor privado para Singleton
    private ControladorFuncionario() {
        this.repFuncionario = RepFuncionario.getInstancia();
    }

    // Método Singleton para obter a instância
    public static synchronized ControladorFuncionario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorFuncionario();
        }
        return instancia;
    }

    // Método para contratar funcionário
    public boolean contratarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Erro: Funcionário não pode ser nulo");
            return false;
        }

        boolean resultado = repFuncionario.adicionar(funcionario);
        if (resultado) {
            System.out.println("Funcionário " + funcionario.getNome() + " contratado com sucesso!");
        } else {
            System.out.println("Erro ao contratar funcionário! Limite máximo atingido.");
        }
        return resultado;
    }

    // Método para buscar funcionário por ID
    public Funcionario buscarFuncionario(int id) {
        Funcionario funcionario = repFuncionario.buscarPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário com ID " + id + " não encontrado!");
        }
        return funcionario;
    }

    // Método para listar todos os funcionários
    public void listarTodosFuncionarios() {
        Funcionario[] funcionarios = repFuncionario.listarTodos();
        if (funcionarios.length == 0) {
            System.out.println("Nenhum funcionário cadastrado!");
            return;
        }

        System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===");
        for (int i = 0; i < funcionarios.length; i++) {
            Funcionario func = funcionarios[i];
            System.out.println("ID: " + func.getId() +
                    " | Nome: " + func.getNome() +
                    " | Departamento: " + func.getDepartamento() +
                    " | Tipo: " + func.getClass().getSimpleName());
        }
    }

    // Método para atualizar dados do funcionário
    public boolean atualizarFuncionario(Funcionario funcionario) {
        boolean resultado = repFuncionario.atualizar(funcionario);
        if (resultado) {
            System.out.println("Dados do funcionário atualizados com sucesso!");
        } else {
            System.out.println("Erro ao atualizar funcionário!");
        }
        return resultado;
    }

    // Método para demitir funcionário
    public boolean demitirFuncionario(int id) {
        boolean resultado = repFuncionario.remover(id);
        if (resultado) {
            System.out.println("Funcionário demitido com sucesso!");
        } else {
            System.out.println("Erro ao demitir funcionário!");
        }
        return resultado;
    }

    // Método para listar funcionários por departamento
    public void listarPorDepartamento(String departamento) {
        Funcionario[] funcionarios = repFuncionario.buscarPorDepartamento(departamento);
        if (funcionarios.length == 0) {
            System.out.println("Nenhum funcionário no departamento " + departamento);
            return;
        }

        System.out.println("\n=== FUNCIONÁRIOS DO DEPARTAMENTO " + departamento.toUpperCase() + " ===");
        for (int i = 0; i < funcionarios.length; i++) {
            Funcionario func = funcionarios[i];
            System.out.println("ID: " + func.getId() +
                    " | Nome: " + func.getNome() +
                    " | Tipo: " + func.getClass().getSimpleName());
        }
    }

    // Método para calcular salário de um funcionário
    public double calcularSalarioFuncionario(int idFuncionario) {
        Funcionario funcionario = repFuncionario.buscarPorId(idFuncionario);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return 0.0;
        }

        if (funcionario instanceof FuncionarioAssalariado) {
            FuncionarioAssalariado assalariado = (FuncionarioAssalariado) funcionario;
            return assalariado.getSalarioBase() + assalariado.getBeneficios();
        }
        else if (funcionario instanceof FuncionarioHorista) {
            FuncionarioHorista horista = (FuncionarioHorista) funcionario;
            double salario = horista.getValorHora() * horista.getHorasTrabalhadas();
            if (horista.isRecebeAdicionalNoturno()) {
                salario *= 1.2; // Adicional de 20% para noturno
            }
            return salario;
        }
        else if (funcionario instanceof FuncionarioComissionado) {
            FuncionarioComissionado comissionado = (FuncionarioComissionado) funcionario;
            return comissionado.getVendasMes() * comissionado.getTaxaComissao();
        }

        System.out.println("Tipo de funcionário não reconhecido");
        return 0.0;
    }
}