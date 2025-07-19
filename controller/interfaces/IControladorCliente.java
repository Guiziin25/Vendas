package controller.interfaces;

import exceptions.*;
import model.Cliente;

public interface IControladorCliente {
    void cadastrarCliente(Cliente cliente) throws ClienteJaCadastradoException, DadosInvalidosException, SistemaException;
    Cliente autenticarCliente(int id, String senha) throws ClienteNaoEncontradoException, DadosInvalidosException, SistemaException;
    Cliente buscarCliente(int id) throws ClienteNaoEncontradoException, SistemaException;
    Cliente buscarClientePorEmail(String email) throws ClienteNaoEncontradoException, DadosInvalidosException, SistemaException;
    Cliente buscarClientePorToken(String token) throws TokenInvalidoException, SistemaException;
    boolean solicitarRecuperacaoSenha(String email) throws ClienteNaoEncontradoException, SistemaException;
    boolean redefinirSenha(String token, String novaSenha) throws TokenInvalidoException, DadosInvalidosException, SistemaException;
    String gerarTokenRecuperacao();
    void enviarEmailRecuperacao(String email, String token);
    Cliente obterCliente(int id) throws ClienteNaoEncontradoException, SistemaException;
    Cliente[] listarClientes() throws SistemaException;
}