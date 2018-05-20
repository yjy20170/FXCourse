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

                Statement stmt2 = conn.createStatement();
                String sql2 = "SELECT * FROM students WHERE teamid = "+teamid+" ORDER BY id";
                ResultSet rs2 = stmt2.executeQuery(sql2);
                String stdsID = "", stdsName = "";
                while(rs2.next()){
                    //Retrieve by column name
                    stdsID += rs2.getInt("id")+", ";
                    stdsName += rs2.getString("name")+", ";
                }
                if(!stdsID.equals("")){
                    teams.add(new Team(teamid, stdsID.substring(0, stdsID.length()-2),
                            stdsName.substring(0, stdsName.length()-2), spktime, uscore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void updateStd(int stdID, String colName, int value){
        String sql = "update students set "+colName+"="+value+" where id="+stdID;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
