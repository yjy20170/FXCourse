package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final ObjectProperty<Number> stdID;
    private final StringProperty name;
    private final ObjectProperty<Number> teamID;
    private final StringProperty curCall;
    private final ObjectProperty<Number> callTime;
    private final ObjectProperty<Number> absenceTime;
    private final ObjectProperty<Number> speakTime;
    private final ObjectProperty<Number> usualScore;

    public Student(int stdID, String name, int teamID, int callTime, int absenceTime, int speakTime, int usualScore){
        this.stdID = new SimpleObjectProperty<Number>(stdID);
        this.name = new SimpleStringProperty(name);
        this.teamID = new SimpleObjectProperty<Number>(teamID);
        this.curCall = new SimpleStringProperty("·ñ");
        this.callTime = new SimpleObjectProperty<Number>(callTime);
        this.absenceTime = new SimpleObjectProperty<Number>(absenceTime);
        this.speakTime = new SimpleObjectProperty<Number>(speakTime);
        this.usualScore = new SimpleObjectProperty<Number>(usualScore);
    }


    public int getStdID(){
        return (int)stdID.get();
    }
    public void setStdID(int stdID){
        this.stdID.set(stdID);
    }
    public ObjectProperty<Number> stdIDProperty(){
        return stdID;
    }

    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public StringProperty nameProperty(){
        return name;
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

    public String getCurCall(){
        return curCall.get();
    }
    public void setCurCall(){
        this.curCall.set("ÊÇ");
    }
    public StringProperty curCallProperty(){
        return curCall;
    }

    public int getCallTime(){
        return (int)callTime.get();
    }
    public void setCallTime(int callTime){
        this.callTime.set(callTime);
    }
    public ObjectProperty<Number> callTimeProperty(){
        return callTime;
    }

    public int getAbsenceTime(){
        return (int)absenceTime.get();
    }
    public void setAbsenceTime(int absenceTime){
        this.absenceTime.set(absenceTime);
    }
    public ObjectProperty<Number> absenceTimeProperty(){
        return absenceTime;
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
