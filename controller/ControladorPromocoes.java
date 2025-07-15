package controller;

import exceptions.*;
import model.Promocao;
import repository.Interfaces.IRepPromocao;
import repository.RepPromocao;

import java.util.Date;

public class ControladorPromocoes {
    private static ControladorPromocoes instancia;
    private IRepPromocao repPromocao;

    private ControladorPromocoes() {
        this.repPromocao = RepPromocao.getInstancia();
    }

    public static synchronized ControladorPromocoes getInstancia() {
        if (instancia == null) {
            instancia = new ControladorPromocoes();
        }
        return instancia;
    }

    public boolean criarPromocaoProduto(int idProduto, double valorDesconto, boolean isPercentual,
                                        Date dataInicio, Date dataFim, String descricao)
            throws PromocaoException {
        validarParametrosPromocao(valorDesconto, dataInicio, dataFim, descricao);

        Promocao promocao = new Promocao(
                repPromocao.getQuantidade() + 1,
                idProduto,
                valorDesconto,
                isPercentual,
                dataInicio,
                dataFim,
                descricao
        );

        if (!repPromocao.adicionar(promocao)) {
            throw new PromocaoException("Falha ao adicionar promoção");
        }
        return true;
    }

    public boolean criarCupom(String codigo, double valorDesconto, boolean isPercentual,
                              Date dataInicio, Date dataFim, String descricao, int usosMaximos)
            throws PromocaoException {
        try {
            validarParametrosPromocao(valorDesconto, dataInicio, dataFim, descricao);

            if (codigo == null || codigo.isEmpty()) {
                throw new CampoObrigatorioException("Código do cupom");
            }

            if (repPromocao.buscarPorCodigo(codigo) != null) {
                throw new PromocaoJaExistenteException(codigo);
            }

            Promocao cupom = new Promocao(
                    repPromocao.getQuantidade() + 1,
                    codigo,
                    valorDesconto,
                    isPercentual,
                    dataInicio,
                    dataFim,
                    descricao,
                    usosMaximos
            );

            if (!repPromocao.adicionar(cupom)) {
                throw new PromocaoException("Falha ao adicionar cupom");
            }
            return true;
        } catch (CampoObrigatorioException e) {
            throw new PromocaoException(e.getMessage());
        }
    }

    public double aplicarCupom(double valorOriginal, String codigoCupom)
            throws CupomInvalidoException {
        if (valorOriginal <= 0) {
            throw new CupomInvalidoException("Valor original deve ser positivo");
        }

        if (codigoCupom == null || codigoCupom.isEmpty()) {
            throw new CupomInvalidoException("Código do cupom é obrigatório");
        }

        Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
        if (cupom == null || !cupom.isCupom() || !cupom.podeSerUsada()) {
            throw new CupomInvalidoException(codigoCupom);
        }

        return cupom.aplicarDesconto(valorOriginal);
    }

    public Promocao[] buscarPromocoesPorProduto(int idProduto) throws PromocaoNaoEncontradaException {
        Promocao[] promocoes = repPromocao.buscarPorProduto(idProduto);
        if (promocoes.length == 0) {
            throw new PromocaoNaoEncontradaException("Nenhuma promoção encontrada para o produto " + idProduto);
        }
        return promocoes;
    }

    public boolean validarCupom(String codigoCupom) throws CupomInvalidoException {
        if (codigoCupom == null || codigoCupom.isEmpty()) {
            throw new CupomInvalidoException("Código do cupom é obrigatório");
        }

        Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
        return cupom != null && cupom.isCupom() && cupom.podeSerUsada();
    }

    private void validarParametrosPromocao(double valorDesconto, Date dataInicio, Date dataFim, String descricao)
            throws PromocaoException {
        try {
            if (valorDesconto <= 0) {
                throw new PromocaoException("Valor do desconto deve ser positivo");
            }

            if (dataInicio == null || dataFim == null) {
                throw new CampoObrigatorioException("Datas de início e fim");
            }

            if (dataFim.before(dataInicio)) {
                throw new PromocaoException("Data final deve ser após data inicial");
            }

            if (descricao == null || descricao.isEmpty()) {
                throw new CampoObrigatorioException("Descrição");
            }
        } catch (CampoObrigatorioException e) {
            throw new PromocaoException(e.getMessage());
        }
    }
}