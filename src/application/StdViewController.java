package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StdViewController {
    @FXML
    private AnchorPane PANE_MORE,PANE_QUERY, PANE_LARGE;
    @FXML
    private Label LABEL_NAME, LABEL_ID;
    @FXML
    private TextField TEXT_QUERY;
    @FXML
    private Button BTN_TEAM,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ABSENCE,BTN_ADD,BTN_DEDUCT,BTN_MORE,BTN_VIEWMODE,
            BTN_MORE1, BTN_MORE2, BTN_MORE3,
            BTN_GO_QUERY;
    @FXML
    private TableView<Student> TBVIEW_STD, TBVIEW_STD_LARGE;
    @FXML
    private TableColumn<Student, Number> COL_STD_ID,
            COL_STD_TEAMID, COL_STD_CALLTIME, COL_STD_ABSTIME, COL_STD_SPKTIME, COL_STD_USCORE,
            COL_STD_TEAMID_LARGE, COL_STD_CALLTIME_LARGE,
            COL_STD_ABSTIME_LARGE, COL_STD_SPKTIME_LARGE, COL_STD_USCORE_LARGE;
    @FXML
    private TableColumn<Student, String> COL_STD_NAME, COL_STD_CURCALL,
            COL_STD_CURCALL_LARGE;

    private Stage stage;
    private Scene teamScene = null;
    private Manager manager;

    private boolean isLargeMode = false;
    private boolean isShowMore = false;
    private boolean isShowQuery = false;

    private boolean isAllCalled = false;  //点完所有人
    private boolean isContinueCall = false;  //在点完所有人之后是否继续点名(true蕴含已经点完所有人)

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
        PANE_LARGE.setVisible(false);

        manager = new Manager();
        TBVIEW_STD.setItems(manager.getStudents());
        COL_STD_NAME.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        COL_STD_ID.setCellValueFactory(cellData -> cellData.getValue().stdIDProperty());
        COL_STD_TEAMID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_CURCALL.setCellValueFactory(cellData -> cellData.getValue().curCallProperty());
        COL_STD_CALLTIME.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
        TBVIEW_STD_LARGE.setItems(FXCollections.observableArrayList());
        COL_STD_TEAMID_LARGE.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_CURCALL_LARGE.setCellValueFactory(cellData -> cellData.getValue().curCallProperty());
        COL_STD_CALLTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE_LARGE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
    }
    @FXML
    private void onClickBTN_TEAM(){
        if(teamScene == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teamScene.fxml"));
            Parent conversionRoot;
            try {
                conversionRoot = loader.load();
                final Scene stdScene = stage.getScene();
                ((TeamViewController)loader.getController()).setParam(manager,stage,stdScene);
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
    }
    @FXML
    private void onClickBTN_GO_QUERY(){
        String string = TEXT_QUERY.getText();
        ObservableList<Student> matchs = FXCollections.observableArrayList();
        for(Student std: manager.getStudents()){
            if(std.getStdID().toString().indexOf(string) >= 0
                    || std.getName().indexOf(string) >= 0){
                matchs.add(std);
            }
        }
        if(matchs.size()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("查询结果");
            alert.setHeaderText("错误：没有找到符合条件的人");
            alert.showAndWait();
            return;
        }else{
            TBVIEW_STD.setItems(matchs);
        }
    }

    private void callNext(TableViewSelectionModel<Student> selects){
        int tbSize = TBVIEW_STD.getItems().size();
        if(selects.getSelectedIndex()==tbSize-1){
            selects.select(0);
        }else{
            selects.selectNext();
        }
    }
    private void findAllCalled(){
        isAllCalled = true;
        //弹出提示
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("点名");
        alert.setContentText("已点完列表中所有同学，是否继续点名？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            isContinueCall = true;
            return;
        } else {
            return;
        }
    }
    @FXML
    private void onClickBTN_NEXT(){
        if(isAllCalled && !isContinueCall) return;
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        int tbSize = TBVIEW_STD.getItems().size();
        if(isAllCalled){
            if(selects.getSelectedItem() == null) selects.select(0);
            else callNext(selects);
        }else{
            int test = 0;
            do{
                if(selects.getSelectedItem() == null) selects.select(0);
                if(selects.getSelectedItem().getCurCall().equals("否")){
                    break;
                }else if(test++==tbSize){  //表示遍历结束没有找到未点名的
                    break;
                }
                callNext(selects);
            }while(true);
            if(test>=tbSize){
                findAllCalled();
                if (isContinueCall == true) callNext(selects);
                else return;
            }else{
                selects.getSelectedItem().setCurCall();
            }
        }
        System.out.println(selects.getSelectedItem().getName()+" called");
        action(Manager.CALL);
    }
    @FXML
    private void onClickBTN_RANDOM(){
        if(isAllCalled && !isContinueCall) return;
        int tbSize = TBVIEW_STD.getItems().size();
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        if(isAllCalled){
            int ran = new Random().nextInt(tbSize);
            selects.select(ran);
        }else{
            ArrayList<Student> stds = new ArrayList<Student>();
            for(Student std: TBVIEW_STD.getItems()){
                if(std.getCurCall().equals("否")) stds.add(std);
            }
            if(stds.size()==0){
                findAllCalled();
                if(isContinueCall){
                    int ran = new Random().nextInt(tbSize);
                    selects.select(ran);
                }else{
                    return;
                }
            }else{
                int ran = new Random().nextInt(stds.size());
                selects.select(stds.get(ran));
                selects.getSelectedItem().setCurCall();
            }
        }
        action(Manager.CALL);
    }
    private void action(int type){
        if(type != Manager.CALL
                && TBVIEW_STD.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无效操作");
            alert.setHeaderText("错误：请先在列表中选中一行");
            alert.showAndWait();
            return;
        }
        Student std = TBVIEW_STD.getSelectionModel().getSelectedItem();
        if(type == Manager.ADD || type == Manager.DED){
            manager.studentAction(std,Manager.SPK);
        }
        if(type == Manager.CALL){
            LABEL_NAME.setText(std.getName());
            LABEL_ID.setText(std.getStdID().toString());
        }
        if(type == Manager.DED
                && (int)std.getUsualScore()==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("无效操作");
                alert.setHeaderText("错误：平时分不能低于0");
                alert.showAndWait();
                return;
            }
        manager.studentAction(std,type);
    }
    @FXML
    private void onClickBTN_ABSENCE(){
        action(Manager.ABS);
    }
    @FXML
    private void onClickBTN_ADD(){
        action(Manager.ADD);
    }
    @FXML
    private void onClickBTN_DEDUCT(){
        action(Manager.DED);
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
            TBVIEW_STD.setVisible(false);
            PANE_LARGE.setVisible(true);
            TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
            if(selects.getSelectedItem() == null) selects.select(0);
            Student oneStd = selects.getSelectedItem();
            ObservableList<Student> oneStdList = FXCollections.observableArrayList();
            oneStdList.add(oneStd);
            LABEL_NAME.setText(oneStd.getName());
            LABEL_ID.setText(oneStd.getStdID().toString());
            TBVIEW_STD_LARGE.setItems(oneStdList);
            TBVIEW_STD_LARGE.getSelectionModel().select(0);
        }else{
            setButtonImage(BTN_VIEWMODE,"viewmode_large.png",42,42);
            TBVIEW_STD.setVisible(true);
            PANE_LARGE.setVisible(false);
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
        PANE_LARGE.setPrefHeight(393);
        TBVIEW_STD.setLayoutY(141);
        PANE_LARGE.setLayoutY(141);
        BTN_QUERY.setStyle("-fx-background-color:#ffffff;");

        TEXT_QUERY.requestFocus();
    }
    private void hideQuery(){
        if(TBVIEW_STD.getItems().size()!=0
                && !TBVIEW_STD.getItems().equals(manager.getStudents())){
            TBVIEW_STD.setItems(manager.getStudents());
        }

        isShowQuery = false;
        PANE_QUERY.setVisible(false);
        TBVIEW_STD.setPrefHeight(474);
        PANE_LARGE.setPrefHeight(474);
        TBVIEW_STD.setLayoutY(60);
        PANE_LARGE.setLayoutY(60);
        BTN_QUERY.setStyle("");
    }
    private void showMore(){
        if(isShowQuery) hideQuery();
        isShowMore = true;
        PANE_MORE.setVisible(true);
        TBVIEW_STD.setPrefHeight(393);
        PANE_LARGE.setPrefHeight(393);
        TBVIEW_STD.setLayoutY(141);
        PANE_LARGE.setLayoutY(141);
        BTN_MORE.setStyle("-fx-background-color:#ffffff;");
    }
    private void hideMore(){
        isShowMore = false;
        PANE_MORE.setVisible(false);
        TBVIEW_STD.setPrefHeight(474);
        PANE_LARGE.setPrefHeight(474);
        TBVIEW_STD.setLayoutY(60);
        PANE_LARGE.setLayoutY(60);
        BTN_MORE.setStyle("");
    }
}
