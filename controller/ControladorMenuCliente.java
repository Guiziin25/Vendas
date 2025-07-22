package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Produto;
import model.SessaoUsuario;
import java.util.Date;

public class ControladorMenuCliente {

    @FXML
    private void listarProdutos() {
        try {
            ControladorProduto controladorProduto = ControladorProduto.getInstancia();
            Produto[] produtos = controladorProduto.listarTodosProdutos();

            StringBuilder sb = new StringBuilder("Produtos disponíveis:\n");
            for (Produto p : produtos) {
                sb.append(p.getId())
                        .append(" - ")
                        .append(p.getNome())
                        .append(" - R$")
                        .append(p.getPreco())
                        .append(" - Estoque: ")
                        .append(p.getQuantidadeEstoque())
                        .append("\n");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Produtos");
            alerta.setHeaderText(null);
            alerta.setContentText(sb.toString());
            alerta.showAndWait();
        } catch (exceptions.SistemaException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao listar produtos: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void adicionarProduto() {
        try {
            // Exemplo: solicitar ID e quantidade via diálogo simples
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Adicionar Produto");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do produto:");
            java.util.Optional<String> resultId = dialogId.showAndWait();

            if (resultId.isPresent()) {
                int idProduto = Integer.parseInt(resultId.get());

                javafx.scene.control.TextInputDialog dialogQtd = new javafx.scene.control.TextInputDialog();
                dialogQtd.setTitle("Adicionar Produto");
                dialogQtd.setHeaderText(null);
                dialogQtd.setContentText("Informe a quantidade:");
                java.util.Optional<String> resultQtd = dialogQtd.showAndWait();

                if (resultQtd.isPresent()) {
                    int quantidade = Integer.parseInt(resultQtd.get());

                    ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
                    controladorCarrinho.adicionarItem(idProduto, quantidade);

                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Sucesso");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Produto adicionado ao carrinho!");
                    alerta.showAndWait();
                }
            }
        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao adicionar produto: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void verCarrinho() {
        try {
            ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
            model.Produto[] itens = controladorCarrinho.listarItens();

            java.util.Map<Integer, model.Produto> produtosMap = new java.util.HashMap<>();
            java.util.Map<Integer, Integer> quantidadeMap = new java.util.HashMap<>();
            for (model.Produto p : itens) {
                produtosMap.put(p.getId(), p);
                quantidadeMap.put(p.getId(), quantidadeMap.getOrDefault(p.getId(), 0) + 1);
            }

            StringBuilder sb = new StringBuilder("Itens no carrinho:\n");
            for (Integer id : produtosMap.keySet()) {
                model.Produto p = produtosMap.get(id);
                int quantidade = quantidadeMap.get(id);
                sb.append(p.getId())
                        .append(" - ")
                        .append(p.getNome())
                        .append(" - R$")
                        .append(p.getPreco())
                        .append(" - Quantidade: ")
                        .append(quantidade)
                        .append("\n");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Carrinho");
            alerta.setHeaderText(null);
            alerta.setContentText(sb.toString());
            alerta.showAndWait();
        } catch (exceptions.CarrinhoVazioException e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Carrinho");
            alerta.setHeaderText(null);
            alerta.setContentText("O carrinho está vazio.");
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao exibir carrinho: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void removerProduto() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Remover Produto");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do produto:");
            java.util.Optional<String> resultId = dialogId.showAndWait();

            if (resultId.isPresent()) {
                int idProduto = Integer.parseInt(resultId.get());

                javafx.scene.control.TextInputDialog dialogQtd = new javafx.scene.control.TextInputDialog();
                dialogQtd.setTitle("Remover Produto");
                dialogQtd.setHeaderText(null);
                dialogQtd.setContentText("Informe a quantidade a remover:");
                java.util.Optional<String> resultQtd = dialogQtd.showAndWait();

                if (resultQtd.isPresent()) {
                    int quantidade = Integer.parseInt(resultQtd.get());

                    ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
                    controladorCarrinho.removerItem(idProduto, quantidade);

                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Sucesso");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Produto removido do carrinho!");
                    alerta.showAndWait();
                }
            }
        } catch (exceptions.ProdutoNaoEncontradoException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Produto não encontrado no carrinho.");
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao remover produto: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void finalizarCompra() {
        try {
            ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
            double total = controladorCarrinho.calcularTotal();
            double frete = controladorCarrinho.calcularFrete();
            double valorFinal = total + frete;

            int idPedido = 0; // Defina conforme sua lógica
            int idCliente = SessaoUsuario.getInstancia().getIdClienteLogado();
            Date dataEmissao = new Date();
            Date dataVencimento = new Date(System.currentTimeMillis() + 86400000 * 7); // 7 dias
            String descricao = "Fatura do pedido " + idPedido;

            model.Fatura fatura = new model.Fatura(0, idPedido, idCliente, valorFinal, dataEmissao, dataVencimento, descricao);

            javafx.scene.control.ChoiceDialog<String> dialog = new javafx.scene.control.ChoiceDialog<>("credito", java.util.Arrays.asList("credito", "debito", "pix", "paypal"));
            dialog.setTitle("Pagamento");
            dialog.setHeaderText(null);
            dialog.setContentText("Escolha o método de pagamento:");
            java.util.Optional<String> resultMetodo = dialog.showAndWait();

            if (resultMetodo.isPresent()) {
                String metodo = resultMetodo.get();
                model.Pagamento pagamento = new model.Pagamento(metodo, 0, valorFinal, metodo, new java.util.Date());

                ControladorPagamento controladorPagamento = ControladorPagamento.getInstancia();
                boolean sucesso = controladorPagamento.processarPagamento(pagamento, fatura);

                if (sucesso) {
                    controladorCarrinho.limparCarrinho();
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Compra Finalizada");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Compra finalizada com sucesso!\n" + pagamento.gerarComprovante());
                    alerta.showAndWait();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Pagamento");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Falha ao processar pagamento: " + pagamento.getStatus());
                    alerta.showAndWait();
                }
            }
        } catch (exceptions.CarrinhoVazioException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Carrinho");
            alerta.setHeaderText(null);
            alerta.setContentText("O carrinho está vazio.");
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao finalizar compra: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void sair() {
        // Implemente lógica para fechar ou voltar à tela inicial
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Sair");
        alerta.setHeaderText(null);
        alerta.setContentText("Saindo do menu cliente.");
        alerta.showAndWait();
    }
}