package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StdViewController {
    @FXML
    private AnchorPane PANE_MORE,PANE_QUERY;
    @FXML
    private TextField TEXT_QUERY;
    @FXML
    private Button BTN_TEAM,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ABSENCE,BTN_ADD,BTN_DEDUCT,BTN_MORE,BTN_VIEWMODE,
            BTN_MORE1, BTN_MORE2, BTN_MORE3,
            BTN_GO_QUERY;
    @FXML
    private TableView<Student> TBVIEW_STD;
    @FXML
    private TableColumn<Student, Number> COL_STD_ID,
            COL_STD_TEAMID, COL_STD_CALLTIME, COL_STD_ABSTIME, COL_STD_SPKTIME, COL_STD_USCORE;
    @FXML
    private TableColumn<Student, String> COL_STD_NAME;

    private Stage stage;
    private Scene teamScene = null;
    private Manager manager;

    private boolean isLargeMode = false;
    private boolean isShowMore = false;
    private boolean isShowQuery = false;

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
        setButtonImage(BTN_TEAM,"team.png",42,42);
        setButtonImage(BTN_QUERY,"query.png",42,42);
        setButtonImage(BTN_NEXT,"next.png",42,42);
        setButtonImage(BTN_RANDOM,"random.png",42,42);
        setButtonImage(BTN_ABSENCE,"absence.png",42,42);
        setButtonImage(BTN_ADD,"add.png",42,42);
        setButtonImage(BTN_DEDUCT,"deduct.png",42,42);
        setButtonImage(BTN_MORE,"more.png",42,42);
        setButtonImage(BTN_VIEWMODE,"viewmode_large.png",42,42);
        setButtonImage(BTN_GO_QUERY,"go_query.png",33,33);

        setButtonTooltip(BTN_TEAM,"个人/小组模式");
        setButtonTooltip(BTN_QUERY,"查找");
        setButtonTooltip(BTN_NEXT,"顺序点名");
        setButtonTooltip(BTN_RANDOM,"随机点名");
        setButtonTooltip(BTN_ABSENCE,"缺勤");
        setButtonTooltip(BTN_ADD,"加分");
        setButtonTooltip(BTN_DEDUCT,"扣分");
        setButtonTooltip(BTN_MORE,"更多");
        setButtonTooltip(BTN_VIEWMODE,"列表/聚焦视图");

        hideQuery();
        hideMore();

        manager = new Manager();
        TBVIEW_STD.setItems(manager.getStudents());
        COL_STD_NAME.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        COL_STD_ID.setCellValueFactory(cellData -> cellData.getValue().stdIDProperty());
        COL_STD_TEAMID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_CALLTIME.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
    }
    @FXML
    private void onClickBTN_TEAM(){
        if(teamScene == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teamScene.fxml"));
            Parent conversionRoot;
            try {
                conversionRoot = loader.load();
                final Scene stdScene = stage.getScene();
                ((TeamViewController)loader.getController()).setManager(manager,stage,stdScene);
                Scene scene = new Scene(conversionRoot);
                scene.getStylesheets().add("style.css");
                stage.setScene(scene);

                teamScene = scene;  //保存，下次直接使用减少延迟
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//已存在
            stage.setScene(teamScene);
        }
    }
    @FXML
    private void onClickBTN_QUERY(){
        if(!isShowQuery){
            showQuery();
        }else{
            hideQuery();
        }
        //TODO
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
        if(!isShowMore){
            showMore();
        }else{
            hideMore();
        }
        //TODO
    }
    @FXML
    private void onClickBTN_VIEWMODE(){
        isLargeMode = !isLargeMode;
        //TODO
        if(isLargeMode){
            setButtonImage(BTN_VIEWMODE,"viewmode_small.png",42,42);
        }else{
            setButtonImage(BTN_VIEWMODE,"viewmode_large.png",42,42);
        }
    }
    @FXML
    private void onClickBTN_MORE1(){

    }
    @FXML
    private void onClickBTN_MORE2(){

    }
    @FXML
    private void onClickBTN_MORE3(){

    }

    private void showQuery(){
        if(isShowMore) hideMore();
        isShowQuery = true;
        PANE_QUERY.setVisible(true);
        TBVIEW_STD.setPrefHeight(393);
        TBVIEW_STD.setLayoutY(141);
        BTN_QUERY.setStyle("-fx-background-color:#ffffff;");

        TEXT_QUERY.requestFocus();
    }
    private void hideQuery(){
        isShowQuery = false;
        PANE_QUERY.setVisible(false);
        TBVIEW_STD.setPrefHeight(474);
        TBVIEW_STD.setLayoutY(60);
        BTN_QUERY.setStyle("");
    }
    private void showMore(){
        if(isShowQuery) hideQuery();
        isShowMore = true;
        PANE_MORE.setVisible(true);
        TBVIEW_STD.setPrefHeight(393);
        TBVIEW_STD.setLayoutY(141);
        BTN_MORE.setStyle("-fx-background-color:#ffffff;");
    }
    private void hideMore(){
        isShowMore = false;
        PANE_MORE.setVisible(false);
        TBVIEW_STD.setPrefHeight(474);
        TBVIEW_STD.setLayoutY(60);
        BTN_MORE.setStyle("");
    }
}
