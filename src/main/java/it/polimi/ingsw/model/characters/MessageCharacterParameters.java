package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains all the different parameters used by each character. It is used to communicate with the {@link it.polimi.ingsw.network.client}.
 */
public class MessageCharacterParameters implements Serializable {
    private int characterId;

    private int islandIndex;
    private List<Integer> studentsIndexList;
    private List<Integer> studentsIndexEntranceList;
    private int studentIndex;
    private PawnColor selectedColor;
    private PawnColor selectedColor2;

    //tableMoney?


    public MessageCharacterParameters() {
        characterId = -1;
        islandIndex = -1;
        studentsIndexList = null;
        studentsIndexEntranceList = null;
        studentIndex = -1;
        selectedColor = null;
        selectedColor2 = null;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getIslandIndex() {
        return islandIndex;
    }

    public List<Integer> getStudentsIndexList() {
        return studentsIndexList;
    }

    public List<Integer> getStudentsIndexEntranceList() {
        return studentsIndexEntranceList;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    public PawnColor getSelectedColor2() {
        return selectedColor2;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public void setIslandIndex(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    public void setStudentsIndexList(List<Integer> studentsIndexList) {
        this.studentsIndexList = studentsIndexList;
    }

    public void setStudentsIndexEntranceList(List<Integer> studentsIndexEntranceList) {
        this.studentsIndexEntranceList = studentsIndexEntranceList;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public void setSelectedColor(PawnColor selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedColor2(PawnColor selectedColor2) {
        this.selectedColor2 = selectedColor2;
    }
}
