package controller;

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

    // Cria uma promoção para um produto específico
    public boolean criarPromocaoProduto(int idProduto, double valorDesconto, boolean isPercentual,
                                        Date dataInicio, Date dataFim, String descricao) {
        Promocao promocao = new Promocao(
                repPromocao.getQuantidade() + 1,
                idProduto,
                valorDesconto,
                isPercentual,
                dataInicio,
                dataFim,
                descricao
        );
        return repPromocao.adicionar(promocao);
    }

    // Cria um cupom de desconto geral
    public boolean criarCupom(String codigo, double valorDesconto, boolean isPercentual,
                              Date dataInicio, Date dataFim, String descricao, int usosMaximos) {
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
        return repPromocao.adicionar(cupom);
    }

    // Aplica um cupom de desconto
    public double aplicarCupom(double valorOriginal, String codigoCupom) {
        Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
        if (cupom != null && cupom.isCupom()) {
            return cupom.aplicarDesconto(valorOriginal);
        }
        return valorOriginal;
    }

    // Busca promoções ativas para um produto
    public Promocao[] buscarPromocoesPorProduto(int idProduto) {
        return repPromocao.buscarPorProduto(idProduto);
    }

    // Busca cupons ativos
    public Promocao[] listarCuponsAtivos() {
        return repPromocao.buscarCuponsAtivos();
    }

    // Desativa uma promoção/cupom
    public boolean desativarPromocao(int idPromocao) {
        Promocao promocao = repPromocao.buscarPorId(idPromocao);
        if (promocao != null) {
            promocao.setAtivo(false);
            return repPromocao.atualizar(promocao);
        }
        return false;
    }

    // Verifica se um cupom é válido
    public boolean validarCupom(String codigoCupom) {
        Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
        return cupom != null && cupom.isCupom() && cupom.estaAtiva();
    }

    // Obtém todas as promoções (para relatórios)
    public Promocao[] listarTodasPromocoes() {
        return repPromocao.listarTodos();
    }
}