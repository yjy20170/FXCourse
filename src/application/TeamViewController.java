package application;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TeamViewController {
    @FXML
    private Button BTN_TEAM,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ADD,BTN_DEDUCT;
    @FXML
    private TableView<Student> TBVIEW_TEAM;
    @FXML
    private TableColumn<Student, Number> COL_TEAM_ID,
            COL_TEAM_STDID, COL_TEAM_SPKTIME, COL_TEAM_USCORE;
    @FXML
    private TableColumn<Student, String> COL_TEAM_STDNAME;

    private Manager manager;
    private Scene stdScene;
    private Stage stage;

    public void setManager(Manager manager,Stage stage,Scene stdScene){
        this.manager = manager;
        this.stdScene = stdScene;
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
        setButtonImage(BTN_TEAM,"student.png",42,42);
        setButtonImage(BTN_QUERY,"query.png",42,42);
        setButtonImage(BTN_NEXT,"next.png",42,42);
        setButtonImage(BTN_RANDOM,"random.png",42,42);
        setButtonImage(BTN_ADD,"add.png",42,42);
        setButtonImage(BTN_DEDUCT,"deduct.png",42,42);

        setButtonTooltip(BTN_TEAM,"个人/小组模式");
        setButtonTooltip(BTN_QUERY,"查找");
        setButtonTooltip(BTN_NEXT,"顺序点名");
        setButtonTooltip(BTN_RANDOM,"随机点名");
        setButtonTooltip(BTN_ADD,"加分");
        setButtonTooltip(BTN_DEDUCT,"扣分");

//        TBVIEW_TEAM.setItems(manager.getStudents());
//        COL_STD_NAME.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//        COL_STD_ID.setCellValueFactory(cellData -> cellData.getValue().stdIDProperty());
//        COL_STD_TEAMID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
//        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
//        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
//        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
    }
    @FXML
    private void onClickBTN_TEAM(){
        stage.setScene(stdScene);
    }
    int a;
    @FXML
    private void onClickBTN_QUERY(){
        System.out.println(a++);
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
