package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TableColumn<Student, String> COL_STD_NAME, COL_STD_STATE,
            COL_STD_STATE_LARGE;

    private Stage stage;
    private Scene teamScene = null;
    private Manager manager;

    private boolean isLargeMode = false;
    private boolean isShowMore = false;
    private boolean isShowQuery = false;

    private boolean isAllCalled = false;
    /*
     *  Student的“本节课状态”getState()初始为未点名，点过后变成出勤或缺勤
     * 设置isAllCalled的意义为，未点完时点击“点名”，会从所有状态为未点名的人中选择
     * 点完后，改为从出勤的人中选择，而不再选择缺勤的人
     *
     */
    private boolean isAllCalledInResult = false;  //在查询结果中点名时使用，不影响正常情况的点名
    private boolean isInResult = false;  //作用同上
    private boolean getCurAllCalled(){
        if(isInResult) return isAllCalledInResult;
        else return isAllCalled;
    }
    private void setCurAllCalled(boolean curIsAllCalled){
        if(isInResult) isAllCalledInResult = curIsAllCalled;
        else isAllCalled = curIsAllCalled;
    }
    private boolean isRandom = false;  //点名方式，默认为顺序点名

    private boolean isChangedByUser = true;  //由于tableview的changeListener无法区分鼠标点击与代码控制，以此区分

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
        COL_STD_STATE.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        COL_STD_CALLTIME.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());
        TBVIEW_STD_LARGE.setItems(oneStdList);
        COL_STD_TEAMID_LARGE.setCellValueFactory(cellData -> cellData.getValue().teamIDProperty());
        COL_STD_STATE_LARGE.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        COL_STD_CALLTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().callTimeProperty());
        COL_STD_ABSTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().absenceTimeProperty());
        COL_STD_SPKTIME_LARGE.setCellValueFactory(cellData -> cellData.getValue().speakTimeProperty());
        COL_STD_USCORE_LARGE.setCellValueFactory(cellData -> cellData.getValue().usualScoreProperty());

        TBVIEW_STD.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
            new ChangeListener<Student>() {
                @Override
                public void changed(
                        ObservableValue<? extends Student> observableValue,
                        Student oldItem, Student newItem) {
                    if(isChangedByUser){
                        //显示查询结果时也会出现oldItem == null，必须用isShowQuery进行判断
                        if(oldItem == null){
                            if(newItem.getState().equals(Student.UNK)) newItem.setState(Student.WAIT);
                        }
                        else if(!oldItem.equals(newItem)){
                            if(oldItem.getState().equals(Student.WAIT)) oldItem.setState(Student.UNK);
                            if(newItem != null && newItem.getState().equals(Student.UNK)) newItem.setState(Student.WAIT);
                        }
                    }
                }
            }
        );
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
            isInResult = false;
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
            SimpleDialog.alert("查询结果", "错误：没有找到符合条件的人");
            return;
        }else{
            TBVIEW_STD.setItems(matchs);
        }
        isInResult = true;  //搜出符合条件的才进入结果列表
        setCurAllCalled(false);  //每次新的查询结果都重置该变量
        TEXT_QUERY.clear();
        showStdInLarge();
    }

    private void action(int type){  //把点名、加减分等实质为改变数值（而非删除、创建）的操作放在一起处理
        if(type != Manager.CALL
                && TBVIEW_STD.getSelectionModel().getSelectedItem() == null){
            SimpleDialog.alert("无效操作","错误：请先在列表中选中一行");
            return;
        }
        Student std = TBVIEW_STD.getSelectionModel().getSelectedItem();
        switch(type){
        case Manager.DED:
            if((int)std.getUsualScore()==0){
                SimpleDialog.alert("无效操作","错误：平时分不能低于0");
                return;
            }
            //没有break
        case Manager.ADD:
            if(std.getState().equals(Student.ABS)){
                SimpleDialog.alert("无效操作", "错误：学生缺勤，不能给分");
                return;
            }else{
                std.setState(Student.PRE);
            }
            manager.studentAction(std,Manager.SPK);
            break;
        case Manager.CALL:
            showStdInLarge();
            if(std.getState().equals(Student.UNK)){
                std.setState(Student.WAIT);
            }
            break;
        case Manager.ABS:
            if(std.getState().equals(Student.WAIT)){
                std.setState(Student.ABS);
                action(Manager.CALL);
                if(isRandom){
                    onClickBTN_RANDOM();
                }else{
                    onClickBTN_NEXT();
                }
            } else {
                SimpleDialog.alert("无效操作", "错误：只有每节课第一次点到时能记缺勤");
                return;
            }
            break;
        }
        manager.studentAction(std,type);
    }

    private void selectNext(){
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        int tbSize = TBVIEW_STD.getItems().size();
        isChangedByUser = false;
        if(selects.getSelectedIndex()==tbSize-1){
            selects.select(0);
        }else{
            selects.selectNext();
        }
        isChangedByUser = true;
    }
    private boolean selectNext(String state){
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        isChangedByUser = false;
        if(selects.getSelectedItem() == null) selects.select(0);
        else selectNext();
        isChangedByUser = true;
        int tbSize = TBVIEW_STD.getItems().size();
        int test = 0;
        do{
            if(selects.getSelectedItem().getState().equals(state)){
                return true;
            }else if(test++==tbSize){  //表示遍历结束没有找到未点名的
                break;
            }
            selectNext();
        }while(true);
        return false;
    }
    private void findAllCalled(){
        setCurAllCalled(true);
        SimpleDialog.alert("点名", "已点完列表中所有同学，开始重复点名");
    }
    @FXML
    private void onClickBTN_NEXT(){
        isRandom = false;
//        if(isAllCalled && !isContinueCall) return;
        Student select = TBVIEW_STD.getSelectionModel().getSelectedItem();
        if(select != null
                && select.getState().equals(Student.WAIT)) {  //先前等待响应的学生
            select.setState(Student.PRE);
        }
        if(getCurAllCalled()){
            selectNext(Student.PRE);
        }else{
            if(!selectNext(Student.UNK)){  //优先选择未点过的，发现已点完
                findAllCalled();
//                if (!isContinueCall) return;  //不继续点名
            }
        }
        action(Manager.CALL);
    }
    private boolean selectRandom(String state){
        TableViewSelectionModel<Student> selects = TBVIEW_STD.getSelectionModel();
        ArrayList<Student> stds = new ArrayList<Student>();
        for(Student std: TBVIEW_STD.getItems()){
            if(std.getState().equals(state)) stds.add(std);
        }
        if(stds.size()==0){
            return false;
        }
        int ran = new Random().nextInt(stds.size());

        isChangedByUser = false;
        selects.select(stds.get(ran));
        isChangedByUser = true;
        return true;
    }
    @FXML
    private void onClickBTN_RANDOM(){
        isRandom = true;
//        if(isAllCalled && !isContinueCall) return;
        Student select = TBVIEW_STD.getSelectionModel().getSelectedItem();
        if(select != null
                && select.getState().equals(Student.WAIT)) {  //先前等待响应的学生
            select.setState(Student.PRE);
        }

        if(getCurAllCalled()){
            selectRandom(Student.PRE);
        }else{
            if(!selectRandom(Student.UNK)){
                findAllCalled();
//                if(!isContinueCall) return;
            }
        }
        action(Manager.CALL);
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
            SimpleDialog.alert("无效操作", "错误：请先在列表中选中一行");
            return;
        }
        if(manager.getStudents().size()==1){
            SimpleDialog.alert("无效操作", "错误：班级中至少要有一名学生");
            return;
        }
        Optional<ButtonType> result = SimpleDialog.ask("移除该学生", "确定要执行移除操作？");
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
        dialog.setHeaderText("   作者：\n      15141019,\n      15141010,\n      15141007,\n      15081017");
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
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.github.com/yjy20170/FXCourse"));
                } catch (Exception e) {
                    SimpleDialog.alert("错误", "无法打开网页");
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
