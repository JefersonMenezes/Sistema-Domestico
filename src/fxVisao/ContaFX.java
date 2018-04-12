/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxVisao;

import controller.ContaFXController;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 *
 * @author zion
 */
public class ContaFX extends Application {
    
    private static Usuario user;

    public static void setUser(Usuario usuario) {
        ContaFX.user = usuario;
    }

    public static Usuario getUser() {
        return ContaFX.user;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxVisao/ContaFX.fxml"));
        fxmlloader.setController(new ContaFXController(user));
        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }
    /*
    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/fxVisao/ContaFX.fxml"));
  
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
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
