package controller;

import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.ProdutoNaoEncontradoException;
import exceptions.SistemaException;
import model.Produto;
import repository.Interfaces.IRepProduto;
import repository.RepProduto;

public class ControladorCarrinho {
    private static ControladorCarrinho instancia;
    private IRepProduto repProduto;
    private Produto[] itens;
    private int quantidadeItens;
    private static final int CAPACIDADE_MAXIMA = 100;

    private ControladorCarrinho() {
        this.repProduto = RepProduto.getInstancia();
        this.itens = new Produto[CAPACIDADE_MAXIMA];
        this.quantidadeItens = 0;
    }

    public static synchronized ControladorCarrinho getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCarrinho();
        }
        return instancia;
    }

    /**
     * Adiciona um item ao carrinho de compras
     * @param idProduto ID do produto a ser adicionado
     * @param quantidade Quantidade do produto a ser adicionada
     * @throws ProdutoNaoEncontradoException Se o produto não for encontrado
     * @throws EstoqueInsuficienteException Se não houver estoque suficiente
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void adicionarItem(int idProduto, int quantidade)
            throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, SistemaException {
        if (quantidade <= 0) {
            throw new SistemaException("Quantidade deve ser maior que zero");
        }

        if (quantidadeItens >= CAPACIDADE_MAXIMA) {
            throw new SistemaException("Carrinho cheio - limite de " + CAPACIDADE_MAXIMA + " itens atingido");
        }

        try {
            Produto produto = repProduto.buscarPorId(idProduto);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new EstoqueInsuficienteException(idProduto, produto.getQuantidadeEstoque());
            }

            // Adiciona o produto ao carrinho (simplificado - na prática deveria agrupar itens iguais)
            for (int i = 0; i < quantidade; i++) {
                if (quantidadeItens < CAPACIDADE_MAXIMA) {
                    itens[quantidadeItens++] = produto;
                }
            }

        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException e) {
            throw e; // Re-lança exceções específicas
        } catch (Exception e) {
            throw new SistemaException("Erro ao adicionar item ao carrinho: " + e.getMessage());
        }
    }

    /**
     * Calcula o total do carrinho
     * @return Valor total dos itens no carrinho
     * @throws CarrinhoVazioException Se o carrinho estiver vazio
     */
    public double calcularTotal() throws CarrinhoVazioException {
        if (quantidadeItens == 0) {
            throw new CarrinhoVazioException();
        }

        double total = 0;
        for (int i = 0; i < quantidadeItens; i++) {
            total += itens[i].getPreco();
        }
        return total;
    }

    /**
     * Calcula o valor do frete
     * @return Valor do frete
     * @throws CarrinhoVazioException Se o carrinho estiver vazio
     */
    public double calcularFrete() throws CarrinhoVazioException {
        if (quantidadeItens == 0) {
            throw new CarrinhoVazioException();
        }

        // Lógica simplificada de cálculo de frete
        return 10.0;
    }

    /**
     * Remove um item do carrinho
     * @param idProduto ID do produto a ser removido
     * @param quantidade Quantidade a ser removida
     * @throws ProdutoNaoEncontradoException Se o produto não estiver no carrinho
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void removerItem(int idProduto, int quantidade)
            throws ProdutoNaoEncontradoException, SistemaException {
        if (quantidade <= 0) {
            throw new SistemaException("Quantidade deve ser maior que zero");
        }

        try {
            boolean produtoEncontrado = false;
            int itensRemovidos = 0;

            // Percorre o carrinho para remover os itens
            for (int i = 0; i < quantidadeItens && itensRemovidos < quantidade; i++) {
                if (itens[i].getId() == idProduto) {
                    // Substitui o item a ser removido pelo último item
                    itens[i] = itens[quantidadeItens - 1];
                    itens[quantidadeItens - 1] = null;
                    quantidadeItens--;
                    itensRemovidos++;
                    produtoEncontrado = true;
                    i--; // Reavalia a posição atual pois movemos um item para ela
                }
            }

            if (!produtoEncontrado) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao remover item do carrinho: " + e.getMessage());
        }
    }

    /**
     * Limpa todos os itens do carrinho
     */
    public void limparCarrinho() {
        this.itens = new Produto[CAPACIDADE_MAXIMA];
        this.quantidadeItens = 0;
    }

    /**
     * Retorna os itens do carrinho
     * @return Array de produtos no carrinho
     * @throws CarrinhoVazioException Se o carrinho estiver vazio
     */
    public Produto[] listarItens() throws CarrinhoVazioException {
        if (quantidadeItens == 0) {
            throw new CarrinhoVazioException();
        }

        Produto[] lista = new Produto[quantidadeItens];
        System.arraycopy(itens, 0, lista, 0, quantidadeItens);
        return lista;
    }
}