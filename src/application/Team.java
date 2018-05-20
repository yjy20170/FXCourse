package application;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team {
    private final ObjectProperty<Number> teamID;
    private final StringProperty stdsID;
    private final StringProperty stdsName;
    private final StringProperty curCall;
    private final ObjectProperty<Number> speakTime;
    private final ObjectProperty<Number> usualScore;

    private ArrayList<Student> members = new ArrayList<Student>();

    public Team(int teamID, int speakTime, int usualScore){
        this.teamID = new SimpleObjectProperty<Number>(teamID);
        this.stdsID = new SimpleStringProperty("");
        this.stdsName = new SimpleStringProperty("");
        this.curCall = new SimpleStringProperty("·ñ");
        this.speakTime = new SimpleObjectProperty<Number>(speakTime);
        this.usualScore = new SimpleObjectProperty<Number>(usualScore);
    }

    public void joinMember(Student std){
        members.add(std);
        if(members.size()==1){
            setStdsID(Integer.toString(std.getStdID()));
            setStdsName(std.getName());
        } else {
            setStdsID(getStdsID()+","+std.getStdID());
            setStdsName(getStdsName()+","+std.getName());
        }
    }
    public void loseMember(Student std){
        members.remove(std);
        if(members.size()==0){
            setStdsID("");
            setStdsName("");
        } else {
            String ssID = "";
            String ssName = "";
            for(Student std2: members){
                ssID += std2.getStdID() + ",";
                ssName += std2.getName() + ",";
            }
            setStdsID(ssID.substring(0, ssID.length()-1));
            setStdsName(ssName.substring(0, ssName.length()-1));
        }
    }
    public boolean isEmpty(){
        return members.isEmpty();
    }

    public int getTeamID(){
        return (int)teamID.get();
    }
    public void setTeamID(int teamID){
        this.teamID.set(teamID);
    }
    public ObjectProperty<Number> teamIDProperty(){
        return teamID;
    }

    public String getStdsID(){
        return stdsID.get();
    }
    public void setStdsID(String stdsID){
        this.stdsID.set(stdsID);
    }
    public StringProperty stdsIDProperty(){
        return stdsID;
    }

    public String getStdsName(){
        return stdsName.get();
    }
    public void setStdsName(String name){
        this.stdsName.set(name);
    }
    public StringProperty stdsNameProperty(){
        return stdsName;
    }

    public String getCurCall(){
        return curCall.get();
    }
    public void setCurCall(){
        this.curCall.set("ÊÇ");
    }
    public StringProperty curCallProperty(){
        return curCall;
    }

    public int getSpeakTime(){
        return (int)speakTime.get();
    }
    public void setSpeakTime(int speakTime){
        this.speakTime.set(speakTime);
    }
    public ObjectProperty<Number> speakTimeProperty(){
        return speakTime;
    }

    public int getUsualScore(){
        return (int)usualScore.get();
    }
    public void setUsualScore(int usualScore){
        this.usualScore.set(usualScore);
    }
    public ObjectProperty<Number> usualScoreProperty(){
        return usualScore;
    }
}
