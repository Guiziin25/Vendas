package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControladorLogin {
    private String tipoUsuario;

    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;

    public void setTipoUsuario(String tipo) {
        this.tipoUsuario = tipo;
    }

    @FXML
    private void entrar() {
        // Autenticação (simples)
        if ("Cliente".equalsIgnoreCase(tipoUsuario)) {
            String usuario = campoUsuario.getText();
            String senha = campoSenha.getText();

            // Busca cliente pelo e-mail
            repository.RepCliente repCliente = repository.RepCliente.getInstancia();
            model.Cliente clienteEncontrado = null;
            for (model.Cliente c : repCliente.listarTodos()) {
                if (c.getEmail().equalsIgnoreCase(usuario)) {
                    clienteEncontrado = c;
                    break;
                }
            }

            if (clienteEncontrado != null && clienteEncontrado.getSenha().equals(senha)) {
                // Login OK, abre MenuCliente.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuCliente.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Menu Cliente");
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Fecha a janela de login
                    campoUsuario.getScene().getWindow().hide();
                } catch (Exception e) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Erro");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Falha ao abrir menu do cliente: " + e.getMessage());
                    alerta.showAndWait();
                }
            } else {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Login");
                alerta.setHeaderText(null);
                alerta.setContentText("Usuário ou senha inválidos!");
                alerta.showAndWait();
            }
        }
    }
}
