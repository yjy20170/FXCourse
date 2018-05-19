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
    public void init(ObservableList<Student> students){
        try {
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM students";
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
                System.out.println(name);
                students.add(new Student(id,name,teamid,calltime,abstime,spktime,uscore));
            }
            System.out.println("end");

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
