/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxVisao;

import controller.MainFXController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 *
 * @author zion
 */
public class MainFX extends Application {

    private static Usuario user;

    public static void setUser(Usuario usuario) {
        MainFX.user = usuario;
    }

    public static Usuario getUser() {
        return MainFX.user;
    }
    
     @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxVisao/MainFX.fxml"));
        fxmlloader.setController(new MainFXController(user));

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    /*
    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/fxVisao/MainFX.fxml"));
  
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
    }
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
