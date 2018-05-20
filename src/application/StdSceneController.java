package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StdSceneController {
    @FXML
    private AnchorPane PANE_MORE,PANE_QUERY, PANE_LARGE;
    @FXML
    private Label LABEL_NAME, LABEL_ID;
    @FXML
    private TextField TEXT_QUERY;
    @FXML
    private Button BTN_TEAM,BTN_QUERY,BTN_NEXT,BTN_RANDOM,
            BTN_ABSENCE,BTN_ADD,BTN_DEDUCT,BTN_MORE,BTN_VIEWMODE,
            BTN_MORE1, BTN_MORE2, BTN_MORE3, BTN_MORE4,
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

    ObservableList<Student> oneStdList = FXCollections.observableArrayList(); //只用于large模式

    public void init(Stage stage, Manager manager){
        this.stage = stage;
        this.manager = manager;

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
        setButtonImage(BTN_MORE1,"addStd.png",42,42);
        setButtonImage(BTN_MORE2,"rmvStd.png",42,42);
        setButtonImage(BTN_MORE3,"setPw.png",42,42);
        setButtonImage(BTN_MORE4,"about.png",42,42);


        setButtonTooltip(BTN_TEAM,"个人/小组模式");
        setButtonTooltip(BTN_QUERY,"查找");
        setButtonTooltip(BTN_NEXT,"顺序点名");
        setButtonTooltip(BTN_RANDOM,"随机点名");
        setButtonTooltip(BTN_ABSENCE,"缺勤");
        setButtonTooltip(BTN_ADD,"加分");
        setButtonTooltip(BTN_DEDUCT,"扣分");
        setButtonTooltip(BTN_MORE,"更多");
        setButtonTooltip(BTN_MORE1,"添加学生");
        setButtonTooltip(BTN_MORE2,"移除学生");
        setButtonTooltip(BTN_MORE3,"修改密码");
        setButtonTooltip(BTN_MORE4,"关于本软件");
        setButtonTooltip(BTN_VIEWMODE,"列表/聚焦视图");

        hideQuery();
        hideMore();
        PANE_LARGE.setVisible(false);

        TBVIEW_STD.setItems(manager.getStudents());
        COL_STD_NAME.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        COL_STD_ID.setCellValueFactory(cellData -> cellData.getValue().stdIDProperty());
        COL_STD_TEAMID.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_CURCALL.setCellValueFactory(cellData -> cellData.getValue().curCallProperty());
        COL_STD_CALLTIME.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
        TBVIEW_STD_LARGE.setItems(oneStdList);
        COL_STD_TEAMID_LARGE.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_CURCALL_LARGE.setCellValueFactory(cellData -> cellData.getValue().curCallProperty());
        COL_STD_CALLTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE_LARGE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
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
    private void onClickBTN_TEAM(){
        if(teamScene == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teamScene.fxml"));
            Parent conversionRoot;
            try {
                conversionRoot = loader.load();
                final Scene stdScene = stage.getScene();
                ((TeamSceneController)loader.getController()).init(stage, manager, stdScene);
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
            if(Integer.toString(std.getStdID()).indexOf(string) >= 0
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
        TEXT_QUERY.clear();
    }

    private void selectNext(){
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        int tbSize = TBVIEW_STD.getItems().size();
        if(selects.getSelectedIndex()==tbSize-1){
            selects.select(0);
        }else{
            selects.selectNext();
        }
    }
    private void findAllCalled(){
        isAllCalled = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("点名");
        alert.setContentText("已点完列表中所有同学，是否继续点名？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            isContinueCall = true;
        }
    }
    @FXML
    private void onClickBTN_NEXT(){
        if(isAllCalled && !isContinueCall) return;
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        int tbSize = TBVIEW_STD.getItems().size();
        if(isAllCalled){
            if(selects.getSelectedItem() == null) selects.select(0);
            else selectNext();
        }else{
            int test = 0;
            do{
                if(selects.getSelectedItem() == null) selects.select(0);
                if(selects.getSelectedItem().getCurCall().equals("否")){
                    break;
                }else if(test++==tbSize){  //表示遍历结束没有找到未点名的
                    break;
                }
                selectNext();
            }while(true);
            if(test>=tbSize){
                findAllCalled();
                if (isContinueCall == true) selectNext();
                else return;
            }else{
                selects.getSelectedItem().setCurCall();
            }
        }
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
    private void action(int type){  //把点名、加减分等实质为改变数值（而非删除、创建）的操作放在一起处理
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
            showStdInLarge();
        }
        if(type == Manager.DED
                && (int)std.getUsualScore()==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无效操作");
            alert.setHeaderText("错误：平时分不能低于0");
            alert.showAndWait();
            return;
        }
        if(type == Manager.ABS){
            if(std.getCurCall().equals("否")){
                std.setCurCall();
                action(Manager.CALL);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("无效操作");
                alert.setHeaderText("错误：只有每节课第一次点到时能记缺勤");
                alert.showAndWait();
                return;
            }
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
    }
    private void showStdInLarge(){
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        if(selects.getSelectedItem() == null) TBVIEW_STD.getSelectionModel().select(0);
        Student oneStd = selects.getSelectedItem();
        LABEL_NAME.setText(oneStd.getName());
        LABEL_ID.setText(Integer.toString(oneStd.getStdID()));
        oneStdList.clear();
        oneStdList.add(oneStd);
    }
    @FXML
    private void onClickBTN_VIEWMODE(){
        isLargeMode = !isLargeMode;
        if(isLargeMode){
            setButtonImage(BTN_VIEWMODE,"viewmode_small.png",42,42);
            TBVIEW_STD.setVisible(false);
            PANE_LARGE.setVisible(true);
            showStdInLarge();
        }else{
            setButtonImage(BTN_VIEWMODE,"viewmode_large.png",42,42);
            TBVIEW_STD.setVisible(true);
            PANE_LARGE.setVisible(false);
        }
    }
    @FXML
    private void onClickBTN_MORE1(){  //添加学生
     // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("添加学生");
        dialog.setHeaderText("请填写以下三项信息");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getClassLoader().getResource("res/student.png").toString()));

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idTF = new TextField();
        TextField nameTF = new TextField();
        TextField teamidTF = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(idTF, 1, 0);
        grid.add(new Label("姓名:"), 0, 1);
        grid.add(nameTF, 1, 1);
        grid.add(new Label("小组号:"), 0, 2);
        grid.add(teamidTF, 1, 2);
        Label info = new Label("");
        grid.add(info, 1, 3);

        // Enable/Disable login button depending on whether a username was entered.
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        ChangeListener<? super String> listener = (observable, oldValue, newValue) -> {
            if(idTF.getText().length() * nameTF.getText().length() * teamidTF.getText().length() == 0){
                info.setText("填写未完成");
                okButton.setDisable(true);
            } else if (!idTF.getText().matches("[0-9]*") || !teamidTF.getText().matches("[0-9]*")){
                info.setText("学号和小组号必须是数字");
                okButton.setDisable(true);
            } else {
                //必须是不存在的学号
                int id = Integer.parseInt(idTF.getText());
                for(Student std: manager.getStudents()){
                    if(std.getStdID()==id){
                        info.setText("学号不能出现重复");
                        okButton.setDisable(true);
                        return;
                    }
                }
                info.setText("");
                okButton.setDisable(false);
            }

        };
        // Do some validation (using the Java 8 lambda syntax).
        idTF.textProperty().addListener(listener);
        nameTF.textProperty().addListener(listener);
        teamidTF.textProperty().addListener(listener);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> idTF.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new String[]{idTF.getText(), nameTF.getText(),teamidTF.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(joinResult -> {
            manager.addStudent(Integer.parseInt(joinResult[0]), joinResult[1], Integer.parseInt(joinResult[2]));
        });
    }
    @FXML
    private void onClickBTN_MORE2(){
        if(TBVIEW_STD.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无效操作");
            alert.setHeaderText("错误：请先在列表中选中一行");
            alert.showAndWait();
            return;
        }
        if(manager.getStudents().size()==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("无效操作");
            alert.setHeaderText("错误：班级中至少要有一名学生");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("移除该学生");
        alert.setContentText("确定要执行移除操作？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Student curSelect = TBVIEW_STD.getSelectionModel().getSelectedItem();
            selectNext();
            manager.removeStudent(curSelect);
            showStdInLarge();
        }
    }
    @FXML
    private void onClickBTN_MORE3(){  //修改密码
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("修改密码");
        dialog.setHeaderText("请输入新密码");

        dialog.setGraphic(new ImageView(this.getClass().getClassLoader().getResource("res/"+"setPw.png").toString()));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField pw = new PasswordField();
        PasswordField pwAgain = new PasswordField();

        grid.add(new Label("新密码:"), 0, 0);
        grid.add(pw, 1, 0);
        grid.add(new Label("再次输入:"), 0, 1);
        grid.add(pwAgain, 1, 1);

        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        //仅当两次都输入且相等时可以点击确定
        pwAgain.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(!newValue.trim().equals(pw.getText()) || newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> pw.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return pwAgain.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(pwResult -> {
            manager.setPW(pwResult);
        });
    }
    @FXML
    private void onClickBTN_MORE4(){  //about
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("关于本软件");
        dialog.setHeaderText("   作者：\n      15141019,\n      15141010,\n      15141007,\n      150810??");
        dialog.setGraphic(new ImageView(this.getClass().getClassLoader().getResource("res/"+"acclaim.png").toString()));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(30);
        grid.setPadding(new Insets(20, 100, 0, 20));
        grid.add(new ImageView(
                new Image(
                        getClass().getClassLoader().getResourceAsStream("res/"+"github.png"),
                        40,40,false,true
                )
        ), 0, 0);
        Hyperlink hyp = new Hyperlink("看看源代码");
        hyp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.github.com/yjy20170/FXCourse"));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        grid.add(hyp, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            return null;
        });

        dialog.showAndWait();
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
