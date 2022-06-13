package it.polimi.ingsw.model;
//if we want more matches we have to use bag like a normal class and not like a singleton
import it.polimi.ingsw.model.enumerations.PawnColor;
//singleton is not good for multiple matches
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Bag {
    //attributes
    private List<Student> studentsList;

    //Methods
    //constructor
     /** Constructor Bag creates a new Bag instance. */
    public Bag(){
        studentsList = new ArrayList<>(); //removed argument from <> as Intellij suggests
    }

    //getter
    public List<Student> getStudentsList() {
        return studentsList;
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
    /**
   * Method extractStudents is called when someone have to extract students from the bag.
   * A check on the number of the students in the bag returns null if there aren't enough
   *
   * @param num of the students to extract
   * @throws /
   * @return the students extracted (type List).
   */
    public List<Student> extractStudents (int num) {
        if(studentsList.size()<num){
            System.out.println("Not enough students in bag");
            return null;
        }

        List<Student> result = new ArrayList<>(); //..
        int remainingExtraction=num;
        while (remainingExtraction>0){
            // for testing, to check, System.out.println(remainingExtraction);
            int randomInt = (int)(Math.random() * (studentsList.size()));
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

    public int getRemainingStudents(){
        return studentsList.size();
    }


    //for test purposes
  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bag)) return false;
        Bag bag = (Bag) o;
        return getStudentsList().equals(bag.getStudentsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentsList());
    }

   */
}
