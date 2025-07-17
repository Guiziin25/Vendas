package controller.interfaces;

import exceptions.ClienteNaoEncontradoException;
import exceptions.DadosInvalidosException;
import exceptions.SistemaException;

import java.util.Date;

public interface IControladorRelatorio {
    String gerarRelatorioVendas(Date inicio, Date fim) throws DadosInvalidosException, SistemaException;
    String gerarRelatorioVendasPorCliente(int idCliente) throws ClienteNaoEncontradoException, SistemaException;
    String gerarRelatorioMensal(int mes, int ano) throws DadosInvalidosException, SistemaException;
}
