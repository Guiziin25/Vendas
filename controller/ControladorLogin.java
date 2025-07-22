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
import model.Cliente;
import model.Funcionario;
import repository.RepCliente;
import repository.RepFuncionario;
import controller.ControladorAcesso;
import exceptions.SistemaException;
import exceptions.AcessoNegadoException;

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
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        try {
            if ("Cliente".equalsIgnoreCase(tipoUsuario)) {
                RepCliente repCliente = RepCliente.getInstancia();
                Cliente clienteEncontrado = null;
                for (Cliente c : repCliente.listarTodos()) {
                    if (c.getEmail().equalsIgnoreCase(usuario) && c.getSenha().equals(senha)) {
                        clienteEncontrado = c;
                        break;
                    }
                }
                if (clienteEncontrado != null) {
                    abrirMenu("/view/MenuCliente.fxml", "Menu Cliente");
                } else {
                    mostrarErro("Usuário ou senha inválidos!");
                }
            } else if ("Admin".equalsIgnoreCase(tipoUsuario)) {
                RepFuncionario repFuncionario = RepFuncionario.getInstancia();
                Funcionario adminEncontrado = null;
                for (Funcionario f : repFuncionario.listarTodos()) {
                    if (f.getLogin().equalsIgnoreCase(usuario) && f.getSenha().equals(senha)) {
                        try {
                            if (ControladorAcesso.getInstancia().temPermissao(f, "ADMIN")) {
                                adminEncontrado = f;
                                break;
                            }
                        } catch (AcessoNegadoException e) {
                            // Ignora e continua procurando
                        } catch (SistemaException e) {
                            // Ignora e continua procurando
                        }
                    }
                }
                if (adminEncontrado != null) {
                    abrirMenu("/view/MenuAdmin.fxml", "Menu Admin");
                } else {
                    mostrarErro("Usuário ou senha inválidos ou sem permissão de administrador!");
                }
            } else if ("Funcionario".equalsIgnoreCase(tipoUsuario)) {
                RepFuncionario repFuncionario = RepFuncionario.getInstancia();
                Funcionario funcionarioEncontrado = null;
                for (Funcionario f : repFuncionario.listarTodos()) {
                    if (f.getLogin().equalsIgnoreCase(usuario) && f.getSenha().equals(senha)) {
                        funcionarioEncontrado = f;
                        break;
                    }
                }
                if (funcionarioEncontrado != null) {
                    abrirMenu("/view/MenuFuncionario.fxml", "Menu Funcionário");
                } else {
                    mostrarErro("Usuário ou senha inválidos!");
                }
            }
        } catch (Exception e) {
            mostrarErro("Erro ao autenticar: " + e.getMessage());
        }
    }

    private void abrirMenu(String fxml, String titulo) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();
        campoUsuario.getScene().getWindow().hide();
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Login");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}