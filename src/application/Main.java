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
            primaryStage.initStyle(StageStyle.UNDECORATED);//�趨�����ޱ߿�
            primaryStage.setScene(scene);
            primaryStage.setTitle("���ӵ���ϵͳ");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}