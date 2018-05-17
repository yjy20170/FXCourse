package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StdViewController {
    @FXML
    private Button BTN_GROUP,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ABSENCE,BTN_ADD,BTN_DEDUCT,BTN_MORE,BTN_VIEWMODE;
    @FXML
    private TableView<Student> TBVIEW_STD;
    @FXML
    private TableColumn<Student, Number> COL_STD_ID,
            COL_STD_TEAMID, COL_STD_ABSTIME, COL_STD_SPKTIME, COL_STD_USCORE;
    @FXML
    private TableColumn<Student, String> COL_STD_NAME;

    private Stage stage;
    private Manager manager;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    private void setButtonImage(Button btn, String filename, int width, int height){
        btn.setGraphic(new ImageView(
                new Image(
                        getClass().getClassLoader().getResourceAsStream("res/"+filename),
                        width,height,false,true
                )
        ));
    }

    private void setButtonTooltip(Button btn, String string){
        Tooltip tooltip = new Tooltip(string);
        tooltip.setStyle("-fx-font-size: 15");
        btn.setTooltip(tooltip);
    }

    @FXML
    private void initialize(){
        setButtonImage(BTN_GROUP,"team.png",40,40);
        setButtonImage(BTN_QUERY,"query.png",40,40);
        setButtonImage(BTN_NEXT,"next.png",36,36);
        setButtonImage(BTN_RANDOM,"random.png",40,40);
        setButtonImage(BTN_ABSENCE,"absence.png",40,40);
        setButtonImage(BTN_ADD,"add.png",42,42);
        setButtonImage(BTN_DEDUCT,"deduct.png",42,42);
        setButtonImage(BTN_MORE,"more.png",40,40);
        setButtonImage(BTN_VIEWMODE,"viewmode_large.png",38,38);

        setButtonTooltip(BTN_GROUP,"����/С��ģʽ");
        setButtonTooltip(BTN_QUERY,"����");
        setButtonTooltip(BTN_NEXT,"˳�����");
        setButtonTooltip(BTN_RANDOM,"�������");
        setButtonTooltip(BTN_ABSENCE,"ȱ��");
        setButtonTooltip(BTN_ADD,"�ӷ�");
        setButtonTooltip(BTN_DEDUCT,"�۷�");
        setButtonTooltip(BTN_MORE,"����");
        setButtonTooltip(BTN_VIEWMODE,"�б�/�۽���ͼ");

        manager = new Manager();
        TBVIEW_STD.setItems(manager.getStudents());
        COL_STD_NAME.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        COL_STD_ID.setCellValueFactory(cellData -> cellData.getValue().stdIDProperty());
        COL_STD_TEAMID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
    }
    @FXML
    private void onClickBTN_GROUP(){

        // ͨ�����ڻ�ȡ����ֵ
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/teamScene.fxml"));
        Parent conversionRoot;
        try {
            conversionRoot = loader.load();
            final Scene oldScene = stage.getScene();
            ((TeamViewController)loader.getController()).setManager(manager,stage,oldScene);
            stage.setScene(new Scene(conversionRoot));
//            stage.showAndWait();
//            int temp = -1;
//            if (!conversionStage.isFocused()) {
//                temp = converController.getMessage();
//                conversionStage.close();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onClickBTN_QUERY(){

    }
    @FXML
    private void onClickBTN_NEXT(){

    }
    @FXML
    private void onClickBTN_RANDOM(){

    }
    @FXML
    private void onClickBTN_ABSENCE(){

    }
    @FXML
    private void onClickBTN_ADD(){

    }
    @FXML
    private void onClickBTN_DEDUCT(){

    }
    @FXML
    private void onClickBTN_MORE(){

    }
    @FXML
    private void onClickBTN_VIEWMODE(){

    }
}
