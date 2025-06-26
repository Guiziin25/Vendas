package repository;

import model.Funcionario;
import model.FuncionarioAssalariado;
import model.FuncionarioComissionado;
import model.FuncionarioHorista;
import repository.Interfaces.IRepFuncionario;

public class RepFuncionario implements IRepFuncionario {
    private static RepFuncionario instancia;  // Adicionando Singleton
    private Funcionario[] funcionarios;
    private int quantidade;
    private static final int TAMANHO_MAXIMO = 100;

    // Construtor privado para Singleton
    private RepFuncionario() {
        this.funcionarios = new Funcionario[TAMANHO_MAXIMO];
        this.quantidade = 0;
    }

    // Método Singleton (thread-safe)
    public static synchronized RepFuncionario getInstancia() {
        if (instancia == null) {
            instancia = new RepFuncionario();
        }
        return instancia;
    }

    @Override
    public boolean adicionar(Funcionario funcionario) {
        if (quantidade >= TAMANHO_MAXIMO) {
            System.out.println("ERRO: Limite máximo de funcionários atingido (100)");
            return false;
        }

        funcionarios[quantidade] = funcionario;
        quantidade++;
        return true;
    }

    @Override
    public Funcionario buscarPorId(int id) {
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getId() == id) {
                return funcionarios[i];
            }
        }
        return null;
    }

    @Override
    public Funcionario[] listarTodos() {
        Funcionario[] lista = new Funcionario[quantidade];
        for (int i = 0; i < quantidade; i++) {
            lista[i] = funcionarios[i];
        }
        return lista;
    }

    @Override
    public boolean atualizar(Funcionario funcionario) {
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getId() == funcionario.getId()) {
                funcionarios[i] = funcionario;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getId() == id) {
                // Substitui pelo último elemento
                funcionarios[i] = funcionarios[quantidade - 1];
                funcionarios[quantidade - 1] = null;
                quantidade--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Funcionario[] buscarPorNome(String nome) {
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getNome().equalsIgnoreCase(nome)) {
                contador++;
            }
        }

        Funcionario[] resultado = new Funcionario[contador];
        int index = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getNome().equalsIgnoreCase(nome)) {
                resultado[index++] = funcionarios[i];
            }
        }
        return resultado;
    }

    @Override
    public Funcionario[] buscarPorDepartamento(String departamento) {
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getDepartamento().equalsIgnoreCase(departamento)) {
                contador++;
            }
        }

        Funcionario[] resultado = new Funcionario[contador];
        int index = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i].getDepartamento().equalsIgnoreCase(departamento)) {
                resultado[index++] = funcionarios[i];
            }
        }
        return resultado;
    }

    @Override
    public FuncionarioAssalariado[] listarAssalariados() {
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioAssalariado) {
                contador++;
            }
        }

        FuncionarioAssalariado[] assalariados = new FuncionarioAssalariado[contador];
        int index = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioAssalariado) {
                assalariados[index++] = (FuncionarioAssalariado) funcionarios[i];
            }
        }
        return assalariados;
    }

    @Override
    public FuncionarioHorista[] listarHoristas() {
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioHorista) {
                contador++;
            }
        }

        FuncionarioHorista[] horistas = new FuncionarioHorista[contador];
        int index = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioHorista) {
                horistas[index++] = (FuncionarioHorista) funcionarios[i];
            }
        }
        return horistas;
    }

    @Override
    public FuncionarioComissionado[] listarComissionados() {
        int contador = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioComissionado) {
                contador++;
            }
        }

        FuncionarioComissionado[] comissionados = new FuncionarioComissionado[contador];
        int index = 0;
        for (int i = 0; i < quantidade; i++) {
            if (funcionarios[i] instanceof FuncionarioComissionado) {
                comissionados[index++] = (FuncionarioComissionado) funcionarios[i];
            }
        }
        return comissionados;
    }

    @Override
    public int getQuantidade() {
        return quantidade;
    }
}