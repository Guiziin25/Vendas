package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControladorInit {
    @FXML
    private void entrarComoCliente() throws Exception {
        abrirLogin("Cliente");
    }
    @FXML
    private void entrarComoAdmin() throws Exception {
        abrirLogin("Admin");
    }
    @FXML
    private void entrarComoFuncionario() throws Exception {
        abrirLogin("Funcionario");
    }

    private void abrirLogin(String tipo) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        ControladorLogin Controladorlogin = loader.getController();
        Controladorlogin.setTipoUsuario(tipo);
        Stage stage = new Stage();
        stage.setTitle("Login " + tipo);
        stage.setScene(new Scene(root));
        stage.show();
    }
}