package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private List<Student> studentsList;

    public Bag(){
        studentsList = new ArrayList<Student>();
    }

    public void fillBag(List<Student> studentsList) {

    }
    public List<Student> extractStudents (int num) {
        if(studentsList.size()<num) return null;

        List<Student> result = new ArrayList<Student>();
        int remainingExtraction=num;
        while (remainingExtraction>0){
            int randomInt = (int)(Math.random() * (studentsList.size() + 1));
            result.add(studentsList.get(randomInt));
            studentsList.remove(randomInt);
            remainingExtraction--;
        }
        return result;
    }
    public void addAllStudents() {

    }


}
