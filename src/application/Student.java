package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final ObjectProperty<Number> stdID;
    private final StringProperty name;
    private final ObjectProperty<Number> teamID;
    private final ObjectProperty<Number> absenceTime;
    private final ObjectProperty<Number> speakTime;
    private final ObjectProperty<Number> usualScore;

    public Student(int stdID, String name, int teamID, int absenceTime, int speakTime, int usualScore){
        this.stdID = new SimpleObjectProperty<Number>(stdID);
        this.name = new SimpleStringProperty(name);
        this.teamID = new SimpleObjectProperty<Number>(teamID);
        this.absenceTime = new SimpleObjectProperty<Number>(absenceTime);
        this.speakTime = new SimpleObjectProperty<Number>(speakTime);
        this.usualScore = new SimpleObjectProperty<Number>(usualScore);
    }


    public Number getStdID(){
        return stdID.get();
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

    public Number getTeamID(){
        return teamID.get();
    }
    public void setTeamID(int teamID){
        this.teamID.set(teamID);
    }
    public ObjectProperty<Number> teamIDProperty(){
        return teamID;
    }

    public Number getAbsenceTime(){
        return absenceTime.get();
    }
    public void setAbsenceTime(int absenceTime){
        this.absenceTime.set(absenceTime);
    }
    public ObjectProperty<Number> absenceTimeProperty(){
        return absenceTime;
    }

    public Number getSpeakTime(){
        return speakTime.get();
    }
    public void setSpeakTime(int speakTime){
        this.speakTime.set(speakTime);
    }
    public ObjectProperty<Number> speakTimeProperty(){
        return speakTime;
    }

    public Number getUsualScore(){
        return usualScore.get();
    }
    public void setUsualScore(int usualScore){
        this.usualScore.set(usualScore);
    }
    public ObjectProperty<Number> usualScoreProperty(){
        return usualScore;
    }
}
