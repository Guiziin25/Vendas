package model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private String categoria;
    private int quantidadeEstoque;
    private int estoqueMinimo;
    private String imagemPrincipal;

    // Construtor completo
    public Produto(int id, String nome, String descricao, double preco,
                   String categoria, int quantidadeEstoque, int estoqueMinimo,
                   String imagemPrincipal) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
        this.imagemPrincipal = imagemPrincipal;
    }

    // ---- Métodos de Gestão de Estoque ---- //
    public void reduzirEstoque(int quantidade) {
        if (quantidade > 0 && this.quantidadeEstoque >= quantidade) {
            this.quantidadeEstoque -= quantidade;
        }
    }

    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }

    public boolean precisaRepor() {
        return this.quantidadeEstoque <= this.estoqueMinimo;
    }

    // ---- Getters e Setters ---- //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    // ---- Métodos Auxiliares ---- //
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", categoria='" + categoria + '\'' +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", estoqueMinimo=" + estoqueMinimo +
                ", imagemPrincipal='" + imagemPrincipal + '\'' +
                '}';
    }
}