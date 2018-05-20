package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loginScene.fxml"));
//            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("stdScene.fxml"));
            Parent root = loader.load();
            ((LoginSceneController)loader.getController()).init(primaryStage);
//            ((StdViewController)loader.getController()).setStage(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框
            primaryStage.setScene(scene);
            primaryStage.setTitle("电子点名系统");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}