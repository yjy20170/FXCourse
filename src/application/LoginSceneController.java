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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginSceneController {
    @FXML
    ImageView IMG_LOGIN_USER;
    @FXML
    Button BTN_EXIT, BTN_LOGIN;
    @FXML
    PasswordField TEXT_PW;
    @FXML
    Label LABEL_TIP;

    private Stage stage;
    private Manager manager;

//    private void setButtonImage(Button btn, String filename, int width, int height){
//        btn.setGraphic(new ImageView(
//                new Image(
//                        getClass().getClassLoader().getResourceAsStream("res/"+filename),
//                        width,height,false,true
//                )
//        ));
//    }
    public void init(Stage stage){
        this.stage = stage;
        manager = new Manager();
        TEXT_PW.requestFocus();
//        setButtonImage(BTN_EXIT, "exit.png",15, 15);
        //setButtonImage(BTN_LOGIN,"login.png",60,60);
//        BTN_EXIT.setStyle("-fx-background-color: #ffffff00;");
        IMG_LOGIN_USER.setImage(new Image(
                        getClass().getClassLoader().getResourceAsStream("res/"+"avatar.png"),
                        240,240,false,false
                ));
        //TODO cool background
//        IMG_LOGIN_BKG.setImage(new Image(
//                getClass().getClassLoader().getResourceAsStream("res/"+"bkg.png"),
//                400,119,false,true
//        ));
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
