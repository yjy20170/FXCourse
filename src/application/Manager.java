package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Manager {
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Team> teams = FXCollections.observableArrayList();
    private DB db;

    public static final int CALL = 0, ABS = 1, ADD = 2, DED = 3, SPK = 4;  //用于统一处理时传递参数

    public Manager(){
        db = new DB();
        db.init(students, teams);
        //TODO
    }
    public ObservableList<Student> getStudents(){
        return students;
    }
    public ObservableList<Team> getTeams(){
        return teams;
    }
    public void studentAction(Student std, int type){
        String typeName = "";
        int newNumber = -1;
        switch(type){
        case CALL:
            typeName = "calltime";
            newNumber = (int)std.getCallTime()+1;
            std.setCallTime(newNumber);
            break;
        case ABS:
            typeName = "abstime";
            newNumber = (int)std.getAbsenceTime()+1;
            std.setAbsenceTime(newNumber);
            break;
        case ADD:
            typeName = "uscore";
            newNumber = (int)std.getUsualScore()+1;
            std.setUsualScore(newNumber);
            break;
        case DED:
            typeName = "uscore";
            newNumber = (int)std.getUsualScore()-1;
            std.setUsualScore(newNumber);
            break;
        case SPK:
            typeName = "spktime";
            newNumber = (int)std.getSpeakTime()+1;
            std.setSpeakTime(newNumber);
            break;
        }
        db.updateStd((int)std.getStdID(), typeName, newNumber);
    }
    public void teamAction(Team team, int type){
        String typeName = "";
        int newNumber = -1;
        switch(type){
        case ADD:
            typeName = "uscore";
            newNumber = (int)team.getUsualScore()+1;
            team.setUsualScore(newNumber);
            break;
        case DED:
            typeName = "uscore";
            newNumber = (int)team.getUsualScore()-1;
            team.setUsualScore(newNumber);
            break;
        case SPK:
            typeName = "spktime";
            newNumber = (int)team.getSpeakTime()+1;
            team.setSpeakTime(newNumber);
            break;
        }
        db.updateStd((int)team.getTeamID(), typeName, newNumber);
    }
}
