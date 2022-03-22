package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;


public class Bag {
     private List<Student> studentsList;

    public Bag(){
        studentsList = new ArrayList<>(); //removed argument from <> as Intellij suggests
    }

    public void initialBagFill() { //fills the bag with two students of each color
        for(int i=0;i<2;i++){
        studentsList.add(new Student(PawnColor.BLUE));
        studentsList.add(new Student(PawnColor.RED));
        studentsList.add(new Student(PawnColor.GREEN));
        studentsList.add(new Student(PawnColor.PINK));
        studentsList.add(new Student(PawnColor.YELLOW));
    }
}
    public List<Student> extractStudents (int num) {
        if(studentsList.size()<num) return null;

        List<Student> result = new ArrayList<>(); //..
        int remainingExtraction=num;
        while (remainingExtraction>0){
            int randomInt = (int)(Math.random() * (studentsList.size() + 1));
            result.add(studentsList.get(randomInt));
            studentsList.remove(randomInt);
            remainingExtraction--;
        }
        return result;
    }
    public void addAllStudents() { //fills the bag with remaining students
        for(int i=0;i<24;i++){
            studentsList.add(new Student(PawnColor.BLUE));
            studentsList.add(new Student(PawnColor.RED));
            studentsList.add(new Student(PawnColor.GREEN));
            studentsList.add(new Student(PawnColor.PINK));
            studentsList.add(new Student(PawnColor.YELLOW));
        }
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }
}
