package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class DB {
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB_NAME = "test";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"+DB_NAME+"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private Connection conn;

    public DB(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void init(ObservableList<Student> students, ObservableList<Team> teams){
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students ORDER BY id";
            ResultSet rs = stmt.executeQuery(sql);
            int id,teamid,calltime,abstime,spktime,uscore;
            String name;
            while(rs.next()){
                //Retrieve by column name
                id  = rs.getInt("id");
                name = rs.getString("name");
                teamid = rs.getInt("teamid");
                calltime = rs.getInt("calltime");
                abstime = rs.getInt("abstime");
                spktime = rs.getInt("spktime");
                uscore = rs.getInt("uscore");
                students.add(new Student(id,name,teamid,calltime,abstime,spktime,uscore));
            }
        } catch (Exception e) {
            e.printStackTrace();  //TODO
            return;
        }
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM teams ORDER BY teamid";
            ResultSet rs = stmt.executeQuery(sql);
            int teamid,spktime,uscore;
            while(rs.next()){
                teamid = rs.getInt("teamid");
                spktime = rs.getInt("spktime");
                uscore = rs.getInt("uscore");

                teams.add(new Team(teamid, spktime, uscore));
            }
        } catch (Exception e) {
            e.printStackTrace();  //TODO
            return;
        }
    }
    public void stdInsert(int ID, String name, int teamid, int callTime, int absTime, int spkTime, int uscore){
        String sql = "insert into students values("
                + ID+",'"+name+"',"+teamid+","  //字符串注意两侧的单引号
                +callTime+","+absTime+","
                +spkTime+","+uscore+");";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void stdDelete(int stdID){
        String sql = "DELETE FROM students where id = " + stdID + ";";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void stdUpdate(int stdID, String colName, int value){
        String sql = "update students set "+colName+"="+value+" where id="+stdID;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void teamInsert(int teamid, int spkTime, int uscore){
        String sql = "insert into teams values("
                + teamid+","+spkTime+","+uscore+");";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void teamDelete(int teamID){
        String sql = "DELETE FROM teams where teamid = " + teamID + ";";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void teamUpdate(int teamID, String colName, int value){
        String sql = "update teams set "+colName+"="+value+" where teamid="+teamID;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setConf(String colName, String value){
        String sql = "update conf set "+colName+"='"+value+"' where 1";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getConf(String colName){
        String sql = "select "+colName+" from conf";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getString(colName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
    }
}
