package application;

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

    public Team(int teamID, String stdsID, String stdsName, int speakTime, int usualScore){
        this.teamID = new SimpleObjectProperty<Number>(teamID);
        this.stdsID = new SimpleStringProperty(stdsID);
        this.stdsName = new SimpleStringProperty(stdsName);
        this.curCall = new SimpleStringProperty("·ñ");
        this.speakTime = new SimpleObjectProperty<Number>(speakTime);
        this.usualScore = new SimpleObjectProperty<Number>(usualScore);
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
