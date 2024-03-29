package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * As the name suggests,this class implements characters 10 and 12
 */
public class Characters10and12 extends Character {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Bag bag;

    //constructor
    public Characters10and12(int id, int initialCost) {
        this.id = id;
        this.initialCost = initialCost;
    }
    //methods

    /**
     * This method implements the 10th character effect,which is a student swap
     *
     * @param cardPlayer                the player who plays the card
     * @param studentsIndexEntranceList the indexes of the students in the entrance chosen
     * @param hallStudentColor1         the hall chosen
     * @param hallStudentColor2         the second hall chosen
     * @param tableMoney                the variable table money
     * @return false if there aren't available students to get or if an exception is detected
     * @return true otherwise
     */
    private boolean swapStudents10(Player cardPlayer, List<Integer> studentsIndexEntranceList, PawnColor hallStudentColor1, PawnColor hallStudentColor2, AtomicInteger tableMoney) {
        //if we swap students of the same colors, the ref of the students swapped are not right, but the colors yes, it's ok
        try {
            int[] checkArray=new int[10];
            //check if selected students exist
            for (Integer i : studentsIndexEntranceList) {
                if (cardPlayer.getDashboard().getEntranceStudentByIndex(i) == null) {
                    logger.log(Level.SEVERE, "selected entrance students does not exist");
                    return false;
                }
                checkArray[i]++;
                if(checkArray[i]>1){
                    logger.log(Level.SEVERE, "repeated entrance student");
                    return false;
                }
            }

            if (cardPlayer.getDashboard().getNumOfHallStudents(hallStudentColor1) == 0 || (hallStudentColor2 != null && cardPlayer.getDashboard().getNumOfHallStudents(hallStudentColor2) == 0) || (hallStudentColor1 == hallStudentColor2) && cardPlayer.getDashboard().getNumOfHallStudents(hallStudentColor1) == 1) {
                logger.log(Level.SEVERE, "selected hall color not enough");
                return false;
            }

            //add selected students to the hall
            List<Student> studentsToRemove = new ArrayList<>();
            for (Integer i : studentsIndexEntranceList) { //the ref of the students are different, but they are the same colors
                cardPlayer.getDashboard().addStudentHall(cardPlayer.getDashboard().getEntranceList().get(i), cardPlayer, tableMoney);
                studentsToRemove.add(cardPlayer.getDashboard().getEntranceList().get(i));
            }
            //remove selected students from the entrance
            for (Student s : studentsToRemove) {
                cardPlayer.getDashboard().getEntranceList().remove(s);
            }
            //add selected students to the entrance and remove from the hall
            cardPlayer.getDashboard().getEntranceList().add(cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor1).remove(0));
            if (studentsIndexEntranceList.size() == 2) {
                cardPlayer.getDashboard().getEntranceList().add(cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor2).remove(0)); //we add students at the end of the list
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Error in the swap");
            return false;
        }
    }

    /**
     * This method implements the 12th character effect,which is adding students to the bag from the hall
     *
     * @param hallColor   the hall to choose
     * @param playersList the list of players
     * @return false if an exception is detected
     * @return true otherwise
     */
    private boolean fillBagFromHall12(PawnColor hallColor, List<Player> playersList) {
        try {
            for (Player p : playersList) {
                for (int i = 0; i < 3; i++) {
                    try {
                        int size = p.getDashboard().getNumOfHallStudents(hallColor);
                        if (size != 0) {
                            Student s = p.getDashboard().getHallStudentsListByColor(hallColor).remove(size - 1);
                            bag.getStudentsList().add(s);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        logger.log(Level.SEVERE, "No more students of this color");
                    }

                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean applyEffect(CharacterParameters params) {
        switch (id) {
            case 10:
                return swapStudents10(params.getPlayer(), params.getStudentsIndexEntranceList(), params.getSelectedColor(), params.getSelectedColor2(), params.getTableMoney());
            case 12:
                return fillBagFromHall12(params.getSelectedColor(), params.getPlayersList());
            default:
                return false;
        }
    }

    @Override
    public void initCharacter(CharacterParameters params) {
        bag = params.getBag();
    }

    //for tests purposes
    public Bag getBag() {
        return bag;
    }
}
