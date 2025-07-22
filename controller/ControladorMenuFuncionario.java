package controller;

import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import controller.ControladorProduto;
import model.Produto;
import model.Cliente;
import repository.RepCliente;

public class ControladorMenuFuncionario {

    @FXML
    private Button btnListarProdutos;
    @FXML
    private Button btnAdicionarProduto;
    @FXML
    private Button btnRemoverProduto;

    private final ControladorProduto controladorProduto = ControladorProduto.getInstancia();

    @FXML
    private void listarProdutos() {
        try {
            Produto[] produtos = controladorProduto.listarTodosProdutos();
            StringBuilder sb = new StringBuilder();
            for (Produto p : produtos) {
                sb.append("ID: ").append(p.getId())
                        .append("\nNome: ").append(p.getNome())
                        .append("\nDescrição: ").append(p.getDescricao())
                        .append("\nPreço: R$ ").append(String.format("%.2f", p.getPreco()))
                        .append("\nCategoria: ").append(p.getCategoria())
                        .append("\nEstoque: ").append(p.getQuantidadeEstoque())
                        .append("\n-----------------------------\n");
            }
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Lista de Produtos");
            alerta.setHeaderText(null);

            javafx.scene.control.TextArea area = new javafx.scene.control.TextArea(sb.length() > 0 ? sb.toString() : "Nenhum produto cadastrado.");
            area.setEditable(false);
            area.setWrapText(true);
            area.setPrefWidth(350);
            area.setPrefHeight(400);

            alerta.getDialogPane().setContent(area);
            alerta.showAndWait();
        } catch (SistemaException e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao listar produtos: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void adicionarProduto() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Adicionar Produto");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do produto:");
            java.util.Optional<String> resultId = dialogId.showAndWait();
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            javafx.scene.control.TextInputDialog dialogNome = new javafx.scene.control.TextInputDialog();
            dialogNome.setTitle("Adicionar Produto");
            dialogNome.setHeaderText(null);
            dialogNome.setContentText("Informe o nome:");
            java.util.Optional<String> resultNome = dialogNome.showAndWait();
            if (!resultNome.isPresent()) return;
            String nome = resultNome.get();

            javafx.scene.control.TextInputDialog dialogDescricao = new javafx.scene.control.TextInputDialog();
            dialogDescricao.setTitle("Adicionar Produto");
            dialogDescricao.setHeaderText(null);
            dialogDescricao.setContentText("Informe a descrição:");
            java.util.Optional<String> resultDescricao = dialogDescricao.showAndWait();
            if (!resultDescricao.isPresent()) return;
            String descricao = resultDescricao.get();

            javafx.scene.control.TextInputDialog dialogPreco = new javafx.scene.control.TextInputDialog();
            dialogPreco.setTitle("Adicionar Produto");
            dialogPreco.setHeaderText(null);
            dialogPreco.setContentText("Informe o preço:");
            java.util.Optional<String> resultPreco = dialogPreco.showAndWait();
            if (!resultPreco.isPresent()) return;
            double preco = Double.parseDouble(resultPreco.get());

            javafx.scene.control.TextInputDialog dialogCategoria = new javafx.scene.control.TextInputDialog();
            dialogCategoria.setTitle("Adicionar Produto");
            dialogCategoria.setHeaderText(null);
            dialogCategoria.setContentText("Informe a categoria:");
            java.util.Optional<String> resultCategoria = dialogCategoria.showAndWait();
            if (!resultCategoria.isPresent()) return;
            String categoria = resultCategoria.get();

            javafx.scene.control.TextInputDialog dialogEstoque = new javafx.scene.control.TextInputDialog();
            dialogEstoque.setTitle("Adicionar Produto");
            dialogEstoque.setHeaderText(null);
            dialogEstoque.setContentText("Informe a quantidade em estoque:");
            java.util.Optional<String> resultEstoque = dialogEstoque.showAndWait();
            if (!resultEstoque.isPresent()) return;
            int estoque = Integer.parseInt(resultEstoque.get());

            Produto novo = new Produto(id, nome, descricao, preco, categoria, estoque, 0, "");
            boolean sucesso = controladorProduto.cadastrarProduto(novo);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Adicionar Produto");
            alerta.setHeaderText(null);
            alerta.setContentText(sucesso ? "Produto adicionado!" : "Falha ao adicionar produto.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao adicionar produto: " + e.getMessage());
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
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            boolean sucesso = controladorProduto.removerProduto(id);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Remover Produto");
            alerta.setHeaderText(null);
            alerta.setContentText(sucesso ? "Produto removido!" : "Produto não encontrado.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao remover produto: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void editarProduto() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Editar Produto");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do produto:");
            java.util.Optional<String> resultId = dialogId.showAndWait();
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            Produto produto = controladorProduto.buscarProduto(id);
            if (produto != null) {
                javafx.scene.control.TextInputDialog dialogPreco = new javafx.scene.control.TextInputDialog(String.valueOf(produto.getPreco()));
                dialogPreco.setTitle("Editar Produto");
                dialogPreco.setHeaderText(null);
                dialogPreco.setContentText("Novo preço:");
                java.util.Optional<String> resultPreco = dialogPreco.showAndWait();
                if (!resultPreco.isPresent()) return;
                produto.setPreco(Double.parseDouble(resultPreco.get()));

                boolean sucesso = controladorProduto.atualizarProduto(produto);

                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alerta.setTitle("Editar Produto");
                alerta.setHeaderText(null);
                alerta.setContentText(sucesso ? "Produto editado!" : "Falha ao editar produto.");
                alerta.showAndWait();
            } else {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setTitle("Editar Produto");
                alerta.setHeaderText(null);
                alerta.setContentText("Produto não encontrado.");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao editar produto: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void listarCategorias() {
        try {
            String[] categorias = controladorProduto.listarTodasCategorias();
            StringBuilder sb = new StringBuilder();
            for (String c : categorias) {
                sb.append(c).append("\n");
            }
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Lista de Categorias");
            alerta.setHeaderText(null);
            alerta.setContentText(sb.length() > 0 ? sb.toString() : "Nenhuma categoria cadastrada.");
            alerta.showAndWait();
        } catch (SistemaException e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao listar categorias: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void editarCategoria() {
        try {
            javafx.scene.control.TextInputDialog dialogAtual = new javafx.scene.control.TextInputDialog();
            dialogAtual.setTitle("Editar Categoria");
            dialogAtual.setHeaderText(null);
            dialogAtual.setContentText("Informe o nome atual da categoria:");
            java.util.Optional<String> resultAtual = dialogAtual.showAndWait();
            if (!resultAtual.isPresent()) return;
            String nomeAtual = resultAtual.get();

            javafx.scene.control.TextInputDialog dialogNovo = new javafx.scene.control.TextInputDialog();
            dialogNovo.setTitle("Editar Categoria");
            dialogNovo.setHeaderText(null);
            dialogNovo.setContentText("Informe o novo nome da categoria:");
            java.util.Optional<String> resultNovo = dialogNovo.showAndWait();
            if (!resultNovo.isPresent()) return;
            String nomeNovo = resultNovo.get();

            boolean sucesso = controladorProduto.editarNomeCategoria(nomeAtual, nomeNovo);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Editar Categoria");
            alerta.setHeaderText(null);
            alerta.setContentText(sucesso ? "Categoria editada!" : "Falha ao editar categoria.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao editar categoria: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void excluirCategoria() {
        try {
            javafx.scene.control.TextInputDialog dialogNome = new javafx.scene.control.TextInputDialog();
            dialogNome.setTitle("Excluir Categoria");
            dialogNome.setHeaderText(null);
            dialogNome.setContentText("Informe o nome da categoria:");
            java.util.Optional<String> resultNome = dialogNome.showAndWait();
            if (!resultNome.isPresent()) return;
            String nome = resultNome.get();

            boolean podeRemover = controladorProduto.podeRemoverCategoria(nome);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Excluir Categoria");
            alerta.setHeaderText(null);
            if (podeRemover) {
                alerta.setContentText("Categoria removida!");
            } else {
                alerta.setContentText("Categoria possui produtos e não pode ser removida.");
            }
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao excluir categoria: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void adicionarCliente() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Adicionar Cliente");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do cliente:");
            java.util.Optional<String> resultId = dialogId.showAndWait();
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            javafx.scene.control.TextInputDialog dialogNome = new javafx.scene.control.TextInputDialog();
            dialogNome.setTitle("Adicionar Cliente");
            dialogNome.setHeaderText(null);
            dialogNome.setContentText("Informe o nome:");
            java.util.Optional<String> resultNome = dialogNome.showAndWait();
            if (!resultNome.isPresent()) return;
            String nome = resultNome.get();

            javafx.scene.control.TextInputDialog dialogEmail = new javafx.scene.control.TextInputDialog();
            dialogEmail.setTitle("Adicionar Cliente");
            dialogEmail.setHeaderText(null);
            dialogEmail.setContentText("Informe o email:");
            java.util.Optional<String> resultEmail = dialogEmail.showAndWait();
            if (!resultEmail.isPresent()) return;
            String email = resultEmail.get();

            javafx.scene.control.TextInputDialog dialogSenha = new javafx.scene.control.TextInputDialog();
            dialogSenha.setTitle("Adicionar Cliente");
            dialogSenha.setHeaderText(null);
            dialogSenha.setContentText("Informe a senha:");
            java.util.Optional<String> resultSenha = dialogSenha.showAndWait();
            if (!resultSenha.isPresent()) return;
            String senha = resultSenha.get();

            javafx.scene.control.TextInputDialog dialogEndereco = new javafx.scene.control.TextInputDialog();
            dialogEndereco.setTitle("Adicionar Cliente");
            dialogEndereco.setHeaderText(null);
            dialogEndereco.setContentText("Informe o endereço:");
            java.util.Optional<String> resultEndereco = dialogEndereco.showAndWait();
            if (!resultEndereco.isPresent()) return;
            String endereco = resultEndereco.get();

            javafx.scene.control.TextInputDialog dialogTelefone = new javafx.scene.control.TextInputDialog();
            dialogTelefone.setTitle("Adicionar Cliente");
            dialogTelefone.setHeaderText(null);
            dialogTelefone.setContentText("Informe o telefone:");
            java.util.Optional<String> resultTelefone = dialogTelefone.showAndWait();
            if (!resultTelefone.isPresent()) return;
            String telefone = resultTelefone.get();

            Cliente novo = new Cliente(id, nome, email, senha, endereco, telefone);
            boolean sucesso = ControladorFuncionario.getInstancia().atualizarCliente(novo);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Adicionar Cliente");
            alerta.setHeaderText(null);
            alerta.setContentText(sucesso ? "Cliente adicionado/atualizado!" : "Falha ao adicionar cliente.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao adicionar cliente: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void removerCliente() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Remover Cliente");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do cliente:");
            java.util.Optional<String> resultId = dialogId.showAndWait();
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            boolean sucesso = ControladorFuncionario.getInstancia().removerCliente(id);

            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Remover Cliente");
            alerta.setHeaderText(null);
            alerta.setContentText(sucesso ? "Cliente removido!" : "Cliente não encontrado.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao remover cliente: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void editarCliente() {
        try {
            javafx.scene.control.TextInputDialog dialogId = new javafx.scene.control.TextInputDialog();
            dialogId.setTitle("Editar Cliente");
            dialogId.setHeaderText(null);
            dialogId.setContentText("Informe o ID do cliente:");
            java.util.Optional<String> resultId = dialogId.showAndWait();
            if (!resultId.isPresent()) return;
            int id = Integer.parseInt(resultId.get());

            Cliente cliente = RepCliente.getInstancia().buscarPorId(id);
            if (cliente != null) {
                javafx.scene.control.TextInputDialog dialogNome = new javafx.scene.control.TextInputDialog(cliente.getNome());
                dialogNome.setTitle("Editar Cliente");
                dialogNome.setHeaderText(null);
                dialogNome.setContentText("Novo nome:");
                java.util.Optional<String> resultNome = dialogNome.showAndWait();
                if (!resultNome.isPresent()) return;
                cliente.setNome(resultNome.get());

                javafx.scene.control.TextInputDialog dialogEmail = new javafx.scene.control.TextInputDialog(cliente.getEmail());
                dialogEmail.setTitle("Editar Cliente");
                dialogEmail.setHeaderText(null);
                dialogEmail.setContentText("Novo email:");
                java.util.Optional<String> resultEmail = dialogEmail.showAndWait();
                if (!resultEmail.isPresent()) return;
                cliente.setEmail(resultEmail.get());

                javafx.scene.control.TextInputDialog dialogSenha = new javafx.scene.control.TextInputDialog(cliente.getSenha());
                dialogSenha.setTitle("Editar Cliente");
                dialogSenha.setHeaderText(null);
                dialogSenha.setContentText("Nova senha:");
                java.util.Optional<String> resultSenha = dialogSenha.showAndWait();
                if (!resultSenha.isPresent()) return;
                cliente.setSenha(resultSenha.get());

                javafx.scene.control.TextInputDialog dialogEndereco = new javafx.scene.control.TextInputDialog(cliente.getEndereco());
                dialogEndereco.setTitle("Editar Cliente");
                dialogEndereco.setHeaderText(null);
                dialogEndereco.setContentText("Novo endereço:");
                java.util.Optional<String> resultEndereco = dialogEndereco.showAndWait();
                if (!resultEndereco.isPresent()) return;
                cliente.setEndereco(resultEndereco.get());

                javafx.scene.control.TextInputDialog dialogTelefone = new javafx.scene.control.TextInputDialog(cliente.getTelefone());
                dialogTelefone.setTitle("Editar Cliente");
                dialogTelefone.setHeaderText(null);
                dialogTelefone.setContentText("Novo telefone:");
                java.util.Optional<String> resultTelefone = dialogTelefone.showAndWait();
                if (!resultTelefone.isPresent()) return;
                cliente.setTelefone(resultTelefone.get());

                boolean sucesso = ControladorFuncionario.getInstancia().atualizarCliente(cliente);

                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alerta.setTitle("Editar Cliente");
                alerta.setHeaderText(null);
                alerta.setContentText(sucesso ? "Cliente editado!" : "Falha ao editar cliente.");
                alerta.showAndWait();
            } else {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setTitle("Editar Cliente");
                alerta.setHeaderText(null);
                alerta.setContentText("Cliente não encontrado.");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao editar cliente: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void listarClientes() {
        try {
            Cliente[] clientes = RepCliente.getInstancia().listarTodos();
            StringBuilder sb = new StringBuilder();
            for (Cliente c : clientes) {
                sb.append(c.getId()).append(" - ").append(c.getNome()).append(" - ").append(c.getEmail()).append("\n");
            }
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alerta.setTitle("Lista de Clientes");
            alerta.setHeaderText(null);
            alerta.setContentText(sb.length() > 0 ? sb.toString() : "Nenhum cliente cadastrado.");
            alerta.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText(null);
            alerta.setContentText("Erro ao listar clientes: " + e.getMessage());
            alerta.showAndWait();
        }
    }
}