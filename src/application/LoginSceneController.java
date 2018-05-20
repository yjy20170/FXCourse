package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginSceneController {
    @FXML
    ImageView IMG_LOGIN_BKG, IMG_LOGIN_USER;
    @FXML
    Button BTN_EXIT, BTN_LOGIN;
    @FXML
    PasswordField TEXT_PW;
    @FXML
    Label LABEL_TIP;

    private Stage stage;
    private Manager manager;
    public void init(Stage stage){
        this.stage = stage;
        manager = new Manager();
        TEXT_PW.requestFocus();
    }
    @FXML
    private void onClickBTN_EXIT(){
        Platform.exit();
    }
    @FXML
    private void onClickBTN_LOGIN(){
        if(!TEXT_PW.getText().equals(manager.getPW())){
            LABEL_TIP.setText("密码错误！");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("stdScene.fxml"));
        try {
            Parent root = loader.load();
            Stage newStage = new Stage();
            ((StdSceneController)loader.getController()).init(newStage, manager);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            newStage.setScene(scene);
            newStage.setTitle("电子点名系统");
            newStage.show();
            stage.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
