/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UsuarioDAO;
import fxVisao.MainFX;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import modelo.Usuario;
import util.Alertas;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class LoginFXController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    private Usuario user;
    List<Usuario> usuarios;

    public Usuario getUser() {
        return user;
    }

    Alertas alerta = new Alertas();

    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        System.exit(0);
    }

    public void validaLogin() throws IOException {
        if (existeUsuario()) {
            if (user.isAtivo()) {
                goToHome();
            } else {
                alerta.getAlert("Valida Login", "Usuário inativo", "Esse usuário está inativo!");
            }
        } else {
            alerta.getAlert("Valida Login", "Erro de Login", "O usuário e/ou senha inválido");
        }
    }

    private boolean existeUsuario() {
        UsuarioDAO dao = new UsuarioDAO();
        usuarios = dao.listar();
        boolean retorno = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getLogin().equals(username.getText()) && usuarios.get(i).getSenha().equals(password.getText())) {
                user = new Usuario();
                user.setAtivo(usuarios.get(i).isAtivo());
                user.setNome(usuarios.get(i).getNome());
                user.setId(usuarios.get(i).getId());
                user.setEmail(usuarios.get(i).getEmail());
                user.setLogin(usuarios.get(i).getSenha());
                retorno = true;
            }
        }
        return retorno;
    }

    private void goToHome() throws IOException {
        MainFX.setUser(user);
        new MainFX().start(new Stage());
        Platform.exit();
    }
}
