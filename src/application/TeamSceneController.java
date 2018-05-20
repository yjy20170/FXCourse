package application;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeamSceneController {
    @FXML
    private AnchorPane PANE_QUERY;
    @FXML
    private TextField TEXT_QUERY;
    @FXML
    private Button BTN_TEAM,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ADD,BTN_DEDUCT,BTN_GO_QUERY;
    @FXML
    private RadioButton RBTN_BY_TEAM, RBTN_BY_STD;
    private ToggleGroup QUERY_BY;
    @FXML
    private TableView<Team> TBVIEW_TEAM;
    @FXML
    private TableColumn<Team, Number> COL_TEAM_ID, COL_TEAM_SPKTIME, COL_TEAM_USCORE;
    @FXML
    private TableColumn<Team, String> COL_TEAM_STDSID, COL_TEAM_STDSNAME, COL_TEAM_CURCALL;

    private Manager manager;
    private Scene stdScene;
    private Stage stage;

    private boolean isShowQuery;
    private boolean isAllCalled = false;
    private boolean isContinueCall = false;

    public void init(Stage stage, Manager manager, Scene stdScene){
        this.manager = manager;
        this.stdScene = stdScene;
        this.stage = stage;

        TBVIEW_TEAM.setItems(manager.getTeams());
        COL_TEAM_ID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_TEAM_STDSID.setCellValueFactory(cellData -> cellData.getValue().stdsIDProperty());
        COL_TEAM_STDSNAME.setCellValueFactory(cellData -> cellData.getValue().stdsNameProperty());
        COL_TEAM_CURCALL.setCellValueFactory(cellData -> cellData.getValue().curCallProperty());
        COL_TEAM_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_TEAM_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
    }

    private void setButtonImage(Button btn, String filename, int width, int height){
        btn.setGraphic(new ImageView(
                new Image(
                        getClass().getClassLoader().getResourceAsStream("res/"+filename),
                        width, height, false, true
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
        setButtonImage(BTN_GO_QUERY,"go_query.png",33,33);

        setButtonTooltip(BTN_TEAM,"个人/小组模式");
        setButtonTooltip(BTN_QUERY,"查找");
        setButtonTooltip(BTN_NEXT,"顺序点名");
        setButtonTooltip(BTN_RANDOM,"随机点名");
        setButtonTooltip(BTN_ADD,"加分");
        setButtonTooltip(BTN_DEDUCT,"扣分");

        hideQuery();

        QUERY_BY = new ToggleGroup();
        RBTN_BY_TEAM.setToggleGroup(QUERY_BY);
        RBTN_BY_TEAM.setSelected(true);
        RBTN_BY_STD.setToggleGroup(QUERY_BY);
    }
    @FXML
    private void onClickBTN_TEAM(){
        stage.setScene(stdScene);
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
        ObservableList<Team> matchs = FXCollections.observableArrayList();
        for(Team team: manager.getTeams()){
            if(RBTN_BY_TEAM.isSelected()){
                if(Integer.toString(team.getTeamID()).indexOf(string) >= 0){
                    matchs.add(team);
                }
            } else {
                if(team.getStdsID().indexOf(string) >= 0
                        || team.getStdsName().indexOf(string) >= 0){
                    matchs.add(team);
                }
            }
        }
        if(matchs.size()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("查询结果");
            alert.setHeaderText("错误：没有找到符合条件的人");
            alert.showAndWait();
            return;
        }else{
            TBVIEW_TEAM.setItems(matchs);
        }
        TEXT_QUERY.clear();
    }

    private void showQuery(){
        isShowQuery = true;
        PANE_QUERY.setVisible(true);
        TBVIEW_TEAM.setPrefHeight(393);
        TBVIEW_TEAM.setLayoutY(141);
        BTN_QUERY.setStyle("-fx-background-color:#ffffff;");

        TEXT_QUERY.requestFocus();
    }
    private void hideQuery(){
        if(TBVIEW_TEAM.getItems().size()!=0
                && !TBVIEW_TEAM.getItems().equals(manager.getStudents())){
            TBVIEW_TEAM.setItems(manager.getTeams());
        }

        isShowQuery = false;
        PANE_QUERY.setVisible(false);
        TBVIEW_TEAM.setPrefHeight(474);
        TBVIEW_TEAM.setLayoutY(60);
        BTN_QUERY.setStyle("");
    }

    private void callNext(TableViewSelectionModel<Team> selects){
        int tbSize = TBVIEW_TEAM.getItems().size();
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
        alert.setContentText("已点完列表中所有小组，是否继续点名？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            isContinueCall = true;
            return;
        } else {
            return;
        }
    }
    private void action(int type){
        if(TBVIEW_TEAM.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无效操作");
            alert.setHeaderText("错误：请先在列表中选中一行");
            alert.showAndWait();
            return;
        }
        Team team = TBVIEW_TEAM.getSelectionModel().getSelectedItem();
        manager.teamAction(team,Manager.SPK);
        if(team.getCurCall().equals("否")) team.setCurCall();
        if(type == Manager.DED
                && (int)team.getUsualScore()==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("无效操作");
                alert.setHeaderText("错误：平时分不能低于0");
                alert.showAndWait();
                return;
            }
        manager.teamAction(team,type);
    }
    @FXML
    private void onClickBTN_NEXT(){
        if(isAllCalled && !isContinueCall) return;
        TableViewSelectionModel<Team> selects = TBVIEW_TEAM.getSelectionModel();
        int tbSize = TBVIEW_TEAM.getItems().size();
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
    }
    @FXML
    private void onClickBTN_RANDOM(){
        if(isAllCalled && !isContinueCall) return;
        int tbSize = TBVIEW_TEAM.getItems().size();
        TableViewSelectionModel<Team> selects = TBVIEW_TEAM.getSelectionModel();
        if(isAllCalled){
            int ran = new Random().nextInt(tbSize);
            selects.select(ran);
        }else{
            ArrayList<Team> teams = new ArrayList<Team>();
            for(Team team: TBVIEW_TEAM.getItems()){
                if(team.getCurCall().equals("否")) teams.add(team);
            }
            if(teams.size()==0){
                findAllCalled();
                if(isContinueCall){
                    int ran = new Random().nextInt(tbSize);
                    selects.select(ran);
                }else{
                    return;
                }
            }else{
                int ran = new Random().nextInt(teams.size());
                selects.select(teams.get(ran));
                selects.getSelectedItem().setCurCall();
            }
        }
    }
    @FXML
    private void onClickBTN_ADD(){
        action(Manager.ADD);
    }
    @FXML
    private void onClickBTN_DEDUCT(){
        action(Manager.DED);
    }
}
