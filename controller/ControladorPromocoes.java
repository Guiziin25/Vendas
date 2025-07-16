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

    /**
     * Cria uma promoção para um produto específico
     * @param idProduto ID do produto
     * @param valorDesconto Valor do desconto
     * @param isPercentual Indica se o desconto é percentual
     * @param dataInicio Data de início da promoção
     * @param dataFim Data de fim da promoção
     * @param descricao Descrição da promoção
     * @return true se a promoção foi criada com sucesso
     * @throws PromocaoException Se os dados da promoção forem inválidos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean criarPromocaoProduto(int idProduto, double valorDesconto, boolean isPercentual,
                                        Date dataInicio, Date dataFim, String descricao)
            throws PromocaoException, SistemaException {
        try {
            if (valorDesconto <= 0) {
                throw new PromocaoException("Valor do desconto deve ser maior que zero");
            }
            if (dataInicio == null || dataFim == null || dataInicio.after(dataFim)) {
                throw new PromocaoException("Datas de início e fim inválidas");
            }

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
        } catch (PromocaoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao criar promoção: " + e.getMessage());
        }
    }

    /**
     * Cria um cupom de desconto geral
     * @param codigo Código do cupom
     * @param valorDesconto Valor do desconto
     * @param isPercentual Indica se o desconto é percentual
     * @param dataInicio Data de início do cupom
     * @param dataFim Data de fim do cupom
     * @param descricao Descrição do cupom
     * @param usosMaximos Número máximo de usos
     * @return true se o cupom foi criado com sucesso
     * @throws PromocaoException Se os dados do cupom forem inválidos
     * @throws PromocaoJaExistenteException Se já existir um cupom com o mesmo código
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean criarCupom(String codigo, double valorDesconto, boolean isPercentual,
                              Date dataInicio, Date dataFim, String descricao, int usosMaximos)
            throws PromocaoException, PromocaoJaExistenteException, SistemaException {
        try {
            if (codigo == null || codigo.trim().isEmpty()) {
                throw new PromocaoException("Código do cupom não pode ser vazio");
            }
            if (valorDesconto <= 0) {
                throw new PromocaoException("Valor do desconto deve ser maior que zero");
            }
            if (usosMaximos <= 0) {
                throw new PromocaoException("Número de usos deve ser maior que zero");
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
            return repPromocao.adicionar(cupom);
        } catch (PromocaoException e) { // Agora captura tanto a superclasse quanto a subclasse
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao criar cupom: " + e.getMessage());
        }
    }
    /**
     * Aplica um cupom de desconto
     * @param valorOriginal Valor original
     * @param codigoCupom Código do cupom
     * @return Valor com desconto aplicado
     * @throws CupomInvalidoException Se o cupom for inválido ou expirado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public double aplicarCupom(double valorOriginal, String codigoCupom)
            throws CupomInvalidoException, SistemaException {
        try {
            if (valorOriginal <= 0) {
                throw new SistemaException("Valor original deve ser maior que zero");
            }

            Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
            if (cupom == null || !cupom.isCupom() || !cupom.podeSerUsada()) {
                throw new CupomInvalidoException(codigoCupom);
            }

            return cupom.aplicarDesconto(valorOriginal);
        } catch (CupomInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao aplicar cupom: " + e.getMessage());
        }
    }

    /**
     * Busca promoções ativas para um produto
     * @param idProduto ID do produto
     * @return Array de promoções encontradas
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Promocao[] buscarPromocoesPorProduto(int idProduto) throws SistemaException {
        try {
            return repPromocao.buscarPorProduto(idProduto);
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar promoções por produto: " + e.getMessage());
        }
    }

    /**
     * Busca cupons ativos
     * @return Array de cupons ativos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Promocao[] listarCuponsAtivos() throws SistemaException {
        try {
            return repPromocao.buscarCuponsAtivos();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar cupons ativos: " + e.getMessage());
        }
    }

    /**
     * Desativa uma promoção/cupom
     * @param idPromocao ID da promoção
     * @return true se a promoção foi desativada com sucesso
     * @throws PromocaoNaoEncontradaException Se a promoção não for encontrada
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean desativarPromocao(int idPromocao)
            throws PromocaoNaoEncontradaException, SistemaException {
        try {
            Promocao promocao = repPromocao.buscarPorId(idPromocao);
            if (promocao == null) {
                throw new PromocaoNaoEncontradaException(idPromocao);
            }

            promocao.setAtivo(false);
            return repPromocao.atualizar(promocao);
        } catch (PromocaoNaoEncontradaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao desativar promoção: " + e.getMessage());
        }
    }

    /**
     * Verifica se um cupom é válido
     * @param codigoCupom Código do cupom
     * @return true se o cupom for válido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean validarCupom(String codigoCupom) throws SistemaException {
        try {
            Promocao cupom = repPromocao.buscarPorCodigo(codigoCupom);
            return cupom != null && cupom.isCupom() && cupom.estaAtiva();
        } catch (Exception e) {
            throw new SistemaException("Erro ao validar cupom: " + e.getMessage());
        }
    }

    /**
     * Obtém todas as promoções (para relatórios)
     * @return Array com todas as promoções
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Promocao[] listarTodasPromocoes() throws SistemaException {
        try {
            return repPromocao.listarTodos();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar promoções: " + e.getMessage());
        }
    }
}