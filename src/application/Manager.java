package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Manager {
    private ObservableList<Student> students = FXCollections.observableArrayList();
    public Manager(){
        students.add(new Student(15141048,"abc",1,2,3,4));
        students.add(new Student(15141019,"yjy",3,23123,34234235,0));
        students.add(new Student(15141060,"hfgdferdvrew",1,2,3,5));
    }
    public ObservableList<Student> getStudents(){
        return students;
    }
}
