package br.com.vhaporfiro.gerenciamentodetarefas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/vhaporfiro/gerenciamentodetarefas/MainView.fxml"));
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("Gerenciamento de Tarefas");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
