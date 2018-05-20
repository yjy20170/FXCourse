package application;


import java.util.HashMap;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Manager {
    private DB db;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Team> teams = FXCollections.observableArrayList();
    private HashMap<Integer,Team> teamMap = new HashMap<Integer,Team>();  //用于快速获取teamID对应的Team对象

    public static final int CALL = 0, ABS = 1, ADD = 2, DED = 3, SPK = 4;  //用于统一处理时传递参数

    public Manager(){
        db = new DB();
        db.init(students, teams);  //只负责加载数据库中的数据

        for(Team team: teams){
            teamMap.put(team.getTeamID(), team);
        }
        //数据库、ObservableList、Map保持同步
        int teamid;
        for(Student std: students){
            teamid = std.getTeamID();
            if(!teamMap.containsKey(teamid)){
                Team newTeam = new Team(teamid, 0, 0);
                teamMap.put(teamid, newTeam);
                teams.add(newTeam);
                db.teamInsert(teamid, 0, 0);
            }
            teamMap.get(teamid).joinMember(std);
        }
        Iterator<Team> iter = teams.iterator();
        while(iter.hasNext()){
            Team team = iter.next();
            if(team.isEmpty()){
                teamMap.remove(team);
                teams.remove(team);
                db.teamDelete(team.getTeamID());
            }
        }
    }
    public String getPW(){
        return db.getConf("pw");
    }
    public void setPW(String pw){
        db.setConf("pw",pw);
    }
    public ObservableList<Student> getStudents(){
        return students;
    }
    public ObservableList<Team> getTeams(){
        return teams;
    }
    public void addStudent(int id, String name, int teamid){
        Student std = new Student(id,name,teamid,0,0,0,0);
        students.add(std);
        db.stdInsert(id,name,teamid,0,0,0,0);

        if(!teamMap.containsKey(teamid)){
            Team newTeam = new Team(teamid, 0, 0);
            teamMap.put(teamid, newTeam);
            teams.add(newTeam);
            db.teamInsert(teamid, 0, 0);
        }
        teamMap.get(teamid).joinMember(std);
    }
    public void removeStudent(Student std){
        students.remove(std);
        db.stdDelete(std.getStdID());
        Team team = teamMap.get(std.getTeamID());
        team.loseMember(std);
        if(team.isEmpty()){
            teamMap.remove(team);
            teams.remove(team);
            db.teamDelete(team.getTeamID());
        }
    }
    public void studentAction(Student std, int type){
        String typeName = "";
        int newNumber = -1;
        switch(type){
        case CALL:
            typeName = "calltime";
            newNumber = std.getCallTime()+1;
            std.setCallTime(newNumber);
            break;
        case ABS:
            typeName = "abstime";
            newNumber = std.getAbsenceTime()+1;
            std.setAbsenceTime(newNumber);
            break;
        case ADD:
            typeName = "uscore";
            newNumber = std.getUsualScore()+1;
            std.setUsualScore(newNumber);
            break;
        case DED:
            typeName = "uscore";
            newNumber = std.getUsualScore()-1;
            std.setUsualScore(newNumber);
            break;
        case SPK:
            typeName = "spktime";
            newNumber = std.getSpeakTime()+1;
            std.setSpeakTime(newNumber);
            break;
        }
        db.stdUpdate(std.getStdID(), typeName, newNumber);
    }
    public void teamAction(Team team, int type){
        String typeName = "";
        int newNumber = -1;
        switch(type){
        case ADD:
            typeName = "uscore";
            newNumber = team.getUsualScore()+1;
            team.setUsualScore(newNumber);
            break;
        case DED:
            typeName = "uscore";
            newNumber = team.getUsualScore()-1;
            team.setUsualScore(newNumber);
            break;
        case SPK:
            typeName = "spktime";
            newNumber = team.getSpeakTime()+1;
            team.setSpeakTime(newNumber);
            break;
        }
        db.teamUpdate(team.getTeamID(), typeName, newNumber);
    }
}
