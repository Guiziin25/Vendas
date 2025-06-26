package repository;

import model.Produto;
import repository.Interfaces.IRepProduto;

public class RepProduto implements IRepProduto {
    private static RepProduto instancia;
    private Produto[] produtos;
    private String[][] imagensProdutos; // Array paralelo para imagens
    private int tamanhoAtual;
    private static final int TAMANHO_MAX = 100;

    private RepProduto() {
        produtos = new Produto[TAMANHO_MAX];
        imagensProdutos = new String[TAMANHO_MAX][10]; // Máximo 10 imagens por produto
        tamanhoAtual = 0;
    }

    public static synchronized RepProduto getInstancia() {
        if (instancia == null) {
            instancia = new RepProduto();
        }
        return instancia;
    }

    // ---- MÉTODOS BÁSICOS ---- //
    @Override
    public void adicionar(Produto produto) {
        if (tamanhoAtual < TAMANHO_MAX) {
            produtos[tamanhoAtual] = produto;
            tamanhoAtual++;
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == id) {
                return produtos[i];
            }
        }
        return null;
    }

    @Override
    public Produto[] listarTodos() {
        Produto[] lista = new Produto[tamanhoAtual];
        System.arraycopy(produtos, 0, lista, 0, tamanhoAtual);
        return lista;
    }

    @Override
    public boolean atualizar(Produto produto) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == produto.getId()) {
                produtos[i] = produto;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == id) {
                // Substitui o produto a ser removido pelo último da lista
                produtos[i] = produtos[tamanhoAtual - 1];
                imagensProdutos[i] = imagensProdutos[tamanhoAtual - 1];
                // Limpa a última posição
                produtos[tamanhoAtual - 1] = null;
                imagensProdutos[tamanhoAtual - 1] = new String[10];
                tamanhoAtual--;
                return true;
            }
        }
        return false;
    }

    // ---- MÉTODOS DE BUSCA ---- //
    @Override
    public Produto[] buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new Produto[0];
        }

        int count = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getNome().equalsIgnoreCase(nome)) {
                count++;
            }
        }

        Produto[] resultado = new Produto[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getNome().equalsIgnoreCase(nome)) {
                resultado[index++] = produtos[i];
            }
        }
        return resultado;
    }

    @Override
    public Produto[] buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return new Produto[0];
        }

        int count = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getCategoria().equalsIgnoreCase(categoria)) {
                count++;
            }
        }

        Produto[] resultado = new Produto[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getCategoria().equalsIgnoreCase(categoria)) {
                resultado[index++] = produtos[i];
            }
        }
        return resultado;
    }

    @Override
    public int getQuantidade() {
        return tamanhoAtual;
    }

    // ---- MÉTODOS DE IMAGENS ---- //
    @Override
    public boolean adicionarImagemAoProduto(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == idProduto) {
                for (int j = 0; j < imagensProdutos[i].length; j++) {
                    if (imagensProdutos[i][j] == null) {
                        imagensProdutos[i][j] = caminhoImagem;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean removerImagemDoProduto(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == idProduto) {
                if (produtos[i].getImagemPrincipal().equals(caminhoImagem)) {
                    return false; // Não permite remover imagem principal
                }

                for (int j = 0; j < imagensProdutos[i].length; j++) {
                    if (caminhoImagem.equals(imagensProdutos[i][j])) {
                        imagensProdutos[i][j] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean definirImagemPrincipal(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == idProduto) {
                for (int j = 0; j < imagensProdutos[i].length; j++) {
                    if (caminhoImagem.equals(imagensProdutos[i][j])) {
                        produtos[i].setImagemPrincipal(caminhoImagem);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String[] listarImagensDoProduto(int idProduto) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].getId() == idProduto) {
                int count = 0;
                for (int j = 0; j < imagensProdutos[i].length; j++) {
                    if (imagensProdutos[i][j] != null) {
                        count++;
                    }
                }

                String[] imagens = new String[count];
                int index = 0;
                for (int j = 0; j < imagensProdutos[i].length; j++) {
                    if (imagensProdutos[i][j] != null) {
                        imagens[index++] = imagensProdutos[i][j];
                    }
                }
                return imagens;
            }
        }
        return new String[0];
    }

    @Override
    public String getImagemPrincipalDoProduto(int idProduto) {
        Produto p = buscarPorId(idProduto);
        return (p != null) ? p.getImagemPrincipal() : null;
    }

    // ---- MÉTODOS DE ESTOQUE ---- //
    @Override
    public boolean atualizarEstoque(int idProduto, int quantidade) {
        Produto produto = buscarPorId(idProduto);
        if (produto != null) {
            if (quantidade > 0) {
                produto.aumentarEstoque(quantidade);
            } else {
                produto.reduzirEstoque(-quantidade);
            }
            return true;
        }
        return false;
    }

    @Override
    public Produto[] listarProdutosComEstoqueBaixo() {
        int count = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].precisaRepor()) {
                count++;
            }
        }

        Produto[] produtosComAlerta = new Produto[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (produtos[i].precisaRepor()) {
                produtosComAlerta[index++] = produtos[i];
            }
        }
        return produtosComAlerta;
    }
}