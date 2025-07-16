package controller;

import exceptions.*;
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
    public boolean cadastrarProduto(Produto produto) throws ProdutoException, SistemaException {
        try {
            if (produto == null) {
                throw new ProdutoException("Produto não pode ser nulo");
            }
            if (produto.getPreco() <= 0) {
                throw new ProdutoException("Preço do produto deve ser maior que zero");
            }
            repProduto.adicionar(produto);
            return true;
        } catch (ProdutoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public Produto buscarProduto(int id) throws ProdutoNaoEncontradoException, SistemaException {
        try {
            Produto produto = repProduto.buscarPorId(id);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(id);
            }
            return produto;
        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    public Produto[] listarTodosProdutos() throws SistemaException {
        try {
            return repProduto.listarTodos();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public boolean atualizarProduto(Produto produto)
            throws ProdutoNaoEncontradoException, ProdutoException, SistemaException {
        try {
            // Validação do produto
            if (produto == null) {
                throw new ProdutoException("Produto não pode ser nulo");
            }

            // Tenta atualizar o produto
            boolean resultado = repProduto.atualizar(produto);
            if (!resultado) {
                throw new ProdutoNaoEncontradoException(produto.getId());
            }

            return resultado;
        } catch (ProdutoException e) { // Captura tanto ProdutoException quanto ProdutoNaoEncontradoException
            throw e; // Relança a exceção original
        } catch (Exception e) {
            throw new SistemaException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public boolean removerProduto(int id) throws ProdutoNaoEncontradoException, SistemaException {
        try {
            boolean resultado = repProduto.remover(id);
            if (!resultado) {
                throw new ProdutoNaoEncontradoException(id);
            }
            return resultado;
        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao remover produto: " + e.getMessage());
        }
    }

    // ---- MÉTODOS DE BUSCA ---- //
    public Produto[] buscarProdutosPorNome(String nome) throws DadosInvalidosException, SistemaException {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new DadosInvalidosException("Nome não pode ser vazio");
            }
            return repProduto.buscarPorNome(nome);
        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar produtos por nome: " + e.getMessage());
        }
    }

    public Produto[] buscarProdutosPorCategoria(String categoria) throws DadosInvalidosException, SistemaException {
        try {
            if (categoria == null || categoria.trim().isEmpty()) {
                throw new DadosInvalidosException("Categoria não pode ser vazia");
            }
            return repProduto.buscarPorCategoria(categoria);
        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar produtos por categoria: " + e.getMessage());
        }
    }

    public int getQuantidadeProdutos() throws SistemaException {
        try {
            return repProduto.getQuantidade();
        } catch (Exception e) {
            throw new SistemaException("Erro ao obter quantidade de produtos: " + e.getMessage());
        }
    }

    // ---- MÉTODOS DE CATEGORIA ---- //
    public boolean editarNomeCategoria(String nomeAntigo, String nomeNovo) throws CategoriaNaoEncontradaException, DadosInvalidosException, SistemaException {
        try {
            if (nomeAntigo == null || nomeNovo == null || nomeNovo.trim().isEmpty()) {
                throw new DadosInvalidosException("Nomes não podem ser nulos ou vazios");
            }

            Produto[] produtos = repProduto.buscarPorCategoria(nomeAntigo);
            if (produtos.length == 0) {
                throw new CategoriaNaoEncontradaException(nomeAntigo);
            }

            for (Produto p : produtos) {
                p.setCategoria(nomeNovo);
                repProduto.atualizar(p);
            }
            return true;
        } catch (CategoriaNaoEncontradaException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao editar nome da categoria: " + e.getMessage());
        }
    }

    public boolean podeRemoverCategoria(String nomeCategoria) throws DadosInvalidosException, SistemaException {
        try {
            if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
                throw new DadosInvalidosException("Nome da categoria não pode ser vazio");
            }
            return repProduto.buscarPorCategoria(nomeCategoria).length == 0;
        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao verificar categoria: " + e.getMessage());
        }
    }

    public String[] listarTodasCategorias() throws SistemaException {
        try {
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
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar categorias: " + e.getMessage());
        }
    }

    // ---- MÉTODOS DE IMAGENS ---- //
    public boolean adicionarImagem(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
                throw new DadosInvalidosException("Caminho da imagem não pode ser vazio");
            }
            boolean resultado = repProduto.adicionarImagemAoProduto(idProduto, caminhoImagem);
            if (!resultado) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }
            return resultado;
        } catch (ProdutoNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao adicionar imagem: " + e.getMessage());
        }
    }

    public boolean removerImagem(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
                throw new DadosInvalidosException("Caminho da imagem não pode ser vazio");
            }
            boolean resultado = repProduto.removerImagemDoProduto(idProduto, caminhoImagem);
            if (!resultado) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }
            return resultado;
        } catch (ProdutoNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao remover imagem: " + e.getMessage());
        }
    }

    public boolean definirImagemPrincipal(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
                throw new DadosInvalidosException("Caminho da imagem não pode ser vazio");
            }
            boolean resultado = repProduto.definirImagemPrincipal(idProduto, caminhoImagem);
            if (!resultado) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }
            return resultado;
        } catch (ProdutoNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao definir imagem principal: " + e.getMessage());
        }
    }

    public String[] listarImagens(int idProduto) throws ProdutoNaoEncontradoException, SistemaException {
        try {
            String[] imagens = repProduto.listarImagensDoProduto(idProduto);
            if (imagens == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }
            return imagens;
        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar imagens: " + e.getMessage());
        }
    }

    public String getImagemPrincipal(int idProduto) throws ProdutoNaoEncontradoException, SistemaException {
        try {
            String imagem = repProduto.getImagemPrincipalDoProduto(idProduto);
            if (imagem == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }
            return imagem;
        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao obter imagem principal: " + e.getMessage());
        }
    }

    // ---- MÉTODOS DE ESTOQUE ---- //
    public boolean registrarVenda(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, DadosInvalidosException, SistemaException {
        try {
            if (quantidade <= 0) {
                throw new DadosInvalidosException("Quantidade deve ser maior que zero");
            }

            Produto produto = repProduto.buscarPorId(idProduto);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new EstoqueInsuficienteException(idProduto, produto.getQuantidadeEstoque());
            }

            return repProduto.atualizarEstoque(idProduto, -quantidade);
        } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao registrar venda: " + e.getMessage());
        }
    }

    public boolean reporEstoque(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (quantidade <= 0) {
                throw new DadosInvalidosException("Quantidade deve ser maior que zero");
            }

            Produto produto = repProduto.buscarPorId(idProduto);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            return repProduto.atualizarEstoque(idProduto, quantidade);
        } catch (ProdutoNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao repor estoque: " + e.getMessage());
        }
    }

    public String verificarEstoque(int idProduto) throws ProdutoNaoEncontradoException, SistemaException {
        try {
            Produto p = repProduto.buscarPorId(idProduto);
            if (p == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            return "Estoque: " + p.getQuantidadeEstoque() +
                    " | Mínimo: " + p.getEstoqueMinimo() +
                    (p.precisaRepor() ? " (ESTOQUE BAIXO!)" : "");
        } catch (ProdutoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao verificar estoque: " + e.getMessage());
        }
    }

    public void emitirAlertasEstoque() throws SistemaException {
        try {
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
        } catch (Exception e) {
            throw new SistemaException("Erro ao emitir alertas de estoque: " + e.getMessage());
        }
    }
}