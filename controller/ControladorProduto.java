package controller;

import model.Produto;
import repository.Interfaces.IRepProduto;
import repository.RepProduto;

public class ControladorProduto {
    private static ControladorProduto instancia;
    private IRepProduto repProduto;

    private ControladorProduto() {
        this.repProduto = RepProduto.getInstancia();
    }

    public static synchronized ControladorProduto getInstancia() {
        if (instancia == null) {
            instancia = new ControladorProduto();
        }
        return instancia;
    }

    // ---- MÉTODOS BÁSICOS (CRUD) ---- //
    public boolean cadastrarProduto(Produto produto) {
        if (produto != null && produto.getPreco() > 0) {
            repProduto.adicionar(produto);
            return true;
        }
        return false;
    }

    public Produto buscarProduto(int id) {
        return repProduto.buscarPorId(id);
    }

    public Produto[] listarTodosProdutos() {
        return repProduto.listarTodos();
    }

    public boolean atualizarProduto(Produto produto) {
        return produto != null && repProduto.atualizar(produto);
    }

    public boolean removerProduto(int id) {
        return repProduto.remover(id);
    }

    // ---- MÉTODOS DE BUSCA ---- //
    public Produto[] buscarProdutosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new Produto[0];
        }
        return repProduto.buscarPorNome(nome);
    }

    public Produto[] buscarProdutosPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return new Produto[0];
        }
        return repProduto.buscarPorCategoria(categoria);
    }

    public int getQuantidadeProdutos() {
        return repProduto.getQuantidade();
    }

    // ---- MÉTODOS DE CATEGORIA ---- //
    public boolean editarNomeCategoria(String nomeAntigo, String nomeNovo) {
        if (nomeAntigo == null || nomeNovo == null || nomeNovo.trim().isEmpty()) {
            return false;
        }

        Produto[] produtos = repProduto.buscarPorCategoria(nomeAntigo);
        if (produtos.length == 0) {
            return false;
        }

        for (Produto p : produtos) {
            p.setCategoria(nomeNovo);
            repProduto.atualizar(p);
        }
        return true;
    }

    public boolean podeRemoverCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            return false;
        }
        return repProduto.buscarPorCategoria(nomeCategoria).length == 0;
    }

    public String[] listarTodasCategorias() {
        Produto[] todosProdutos = repProduto.listarTodos();
        String[] categorias = new String[todosProdutos.length];
        int count = 0;

        for (Produto p : todosProdutos) {
            boolean categoriaJaExiste = false;
            for (int i = 0; i < count; i++) {
                if (categorias[i].equalsIgnoreCase(p.getCategoria())) {
                    categoriaJaExiste = true;
                    break;
                }
            }
            if (!categoriaJaExiste) {
                categorias[count++] = p.getCategoria();
            }
        }

        String[] resultado = new String[count];
        System.arraycopy(categorias, 0, resultado, 0, count);
        return resultado;
    }

    // ---- MÉTODOS DE IMAGENS ---- //
    public boolean adicionarImagem(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }
        return repProduto.adicionarImagemAoProduto(idProduto, caminhoImagem);
    }

    public boolean removerImagem(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }
        return repProduto.removerImagemDoProduto(idProduto, caminhoImagem);
    }

    public boolean definirImagemPrincipal(int idProduto, String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return false;
        }
        return repProduto.definirImagemPrincipal(idProduto, caminhoImagem);
    }

    public String[] listarImagens(int idProduto) {
        return repProduto.listarImagensDoProduto(idProduto);
    }

    public String getImagemPrincipal(int idProduto) {
        return repProduto.getImagemPrincipalDoProduto(idProduto);
    }

    // ---- MÉTODOS DE ESTOQUE ---- //
    public boolean registrarVenda(int idProduto, int quantidade) {
        if (quantidade <= 0) return false;
        return repProduto.atualizarEstoque(idProduto, -quantidade);
    }

    public boolean reporEstoque(int idProduto, int quantidade) {
        if (quantidade <= 0) return false;
        return repProduto.atualizarEstoque(idProduto, quantidade);
    }

    public String verificarEstoque(int idProduto) {
        Produto p = repProduto.buscarPorId(idProduto);
        if (p == null) return "Produto não encontrado";

        return "Estoque: " + p.getQuantidadeEstoque() +
                " | Mínimo: " + p.getEstoqueMinimo() +
                (p.precisaRepor() ? " (ESTOQUE BAIXO!)" : "");
    }

    public void emitirAlertasEstoque() {
        Produto[] produtos = repProduto.listarProdutosComEstoqueBaixo();
        if (produtos.length == 0) {
            System.out.println("Nenhum produto com estoque baixo");
            return;
        }

        System.out.println("==== ALERTAS DE ESTOQUE ====");
        for (Produto p : produtos) {
            System.out.println(
                    p.getNome() + " - Estoque: " + p.getQuantidadeEstoque() +
                            " (Mínimo: " + p.getEstoqueMinimo() + ")"
            );
        }
    }
}