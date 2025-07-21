package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControladorLogin {
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private Button botaoEntrar;

    // Simulação de um banco de dados de usuários
    private final Map<String, String> usuarios = new HashMap<>();

    public ControladorLogin() {
        // Usuários pré-definidos (usuário -> senha)
        usuarios.put("cliente", "123");
        usuarios.put("funcionario", "456");
        usuarios.put("admin", "789");
    }

    public void initialize(URL location, ResourceBundle resources) {
        exibirMensagem("Bem-vindo!", "Faça login para continuar.");
    }

    @FXML
    private void entrar() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(senha)) {
            exibirMensagem("Login bem-sucedido!", "Bem-vindo, " + usuario + "!");
            // Aqui você pode redirecionar para outra tela
        } else {
            exibirMensagem("Erro de autenticação", "Usuário ou senha inválidos.");
        }
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}