package controller.interfaces;

import exceptions.*;
import model.Promocao;

import java.util.Date;

public interface IControladorPromocoes {
    boolean criarPromocaoProduto(int idProduto, double valorDesconto, boolean isPercentual, Date dataInicio, Date dataFim, String descricao) throws PromocaoException, SistemaException;
    boolean criarCupom(String codigo, double valorDesconto, boolean isPercentual, Date dataInicio, Date dataFim, String descricao, int usosMaximos) throws PromocaoException, PromocaoJaExistenteException, SistemaException;
    double aplicarCupom(double valorOriginal, String codigoCupom) throws CupomInvalidoException, SistemaException;
    Promocao[] buscarPromocoesPorProduto(int idProduto) throws SistemaException;
    Promocao[] listarCuponsAtivos() throws SistemaException;
    boolean desativarPromocao(int idPromocao) throws PromocaoNaoEncontradaException, SistemaException;
    boolean validarCupom(String codigoCupom) throws SistemaException;
    Promocao[] listarTodasPromocoes() throws SistemaException;
    double verificarDescontoProduto(int idProduto) throws PromocaoException;
}
