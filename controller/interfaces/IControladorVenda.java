package controller.interfaces;

import exceptions.*;
import model.Venda;
import java.util.Date;

public interface IControladorVenda {
    void registrarVenda(Venda venda) throws VendaException, SistemaException;
    double gerarRelatorioVendas(Date inicio, Date fim) throws DadosInvalidosException, SistemaException;
    Venda buscarVenda(int id) throws VendaNaoEncontradaException, SistemaException;
    Venda[] listarVendasPorCliente(int idCliente) throws ClienteNaoEncontradoException, SistemaException;
    double calcularTotalVendas() throws SistemaException;
}
