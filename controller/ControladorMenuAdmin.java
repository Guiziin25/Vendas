package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import model.Cliente;
import model.Produto;
import model.Venda;

public class ControladorMenuAdmin {
    @FXML
    private TextArea areaExibicao;

    @FXML
    private void listarClientes() {
        try {
            Cliente[] clientes = ControladorCliente.getInstancia().listarClientes();
            StringBuilder sb = new StringBuilder("Clientes cadastrados:\n");
            for (Cliente c : clientes) {
                sb.append(c.getId()).append(" - ").append(c.getNome()).append("\n");
            }
            areaExibicao.setText(sb.toString());
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro de sistema");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void listarVendas() {
        try {
            Venda[] vendas = ControladorVenda.getInstancia().listarVendas();
            StringBuilder sb = new StringBuilder("Vendas realizadas:\n");
            for (Venda v : vendas) {
                sb.append("Venda #").append(v.getId())
                        .append(" - Cliente: ").append(v.getIdCliente())
                        .append(" - Valor: R$").append(v.getValorTotal())
                        .append(" - Status: ").append(v.getStatus()).append("\n");
            }
            areaExibicao.setText(sb.toString());
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro de sistema");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void listarProdutos() {
        try {
            Produto[] produtos = ControladorProduto.getInstancia().listarTodosProdutos();
            StringBuilder sb = new StringBuilder("Produtos disponíveis:\n");
            for (Produto p : produtos) {
                sb.append(p.getId()).append(" - ").append(p.getNome())
                        .append(" - R$").append(p.getPreco())
                        .append(" | Estoque: ").append(p.getQuantidadeEstoque()).append("\n");
            }
            areaExibicao.setText(sb.toString());
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro de sistema");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void adicionarProduto() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Adicionar Produto");
            dialog.setContentText("Nome:");
            String nome = dialog.showAndWait().orElse("");
            if (nome.isEmpty()) return;

            dialog.setContentText("Descrição:");
            String descricao = dialog.showAndWait().orElse("");
            if (descricao.isEmpty()) return;

            dialog.setContentText("Preço:");
            double preco = Double.parseDouble(dialog.showAndWait().orElse("0"));

            dialog.setContentText("Categoria:");
            String categoria = dialog.showAndWait().orElse("");
            if (categoria.isEmpty()) return;

            dialog.setContentText("Quantidade em estoque:");
            int quantidadeEstoque = Integer.parseInt(dialog.showAndWait().orElse("0"));

            dialog.setContentText("Estoque mínimo:");
            int estoqueMinimo = Integer.parseInt(dialog.showAndWait().orElse("0"));

            dialog.setContentText("Imagem principal:");
            String imagemPrincipal = dialog.showAndWait().orElse("");

            Produto novoProduto = new Produto(0, nome, descricao, preco, categoria, quantidadeEstoque, estoqueMinimo, imagemPrincipal);
            ControladorProduto.getInstancia().cadastrarProduto(novoProduto);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("Produto adicionado!");
            alerta.showAndWait();
            listarProdutos();
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro de sistema");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro ao adicionar produto");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void removerProduto() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Remover Produto");
            dialog.setContentText("ID do produto:");
            int idRemover = Integer.parseInt(dialog.showAndWait().orElse("0"));
            ControladorProduto.getInstancia().removerProduto(idRemover);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("Produto removido!");
            alerta.showAndWait();
            listarProdutos();
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro de sistema");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro ao remover produto");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}