package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.List;

public class MessageCharacterParameters {
    private int characterIndex;

    private int islandIndex;
    private List<Integer> studentsIndexList;
    private List<Integer> studentsIndexEntranceList;
    private int studentIndex;
    private PawnColor selectedColor;
    private PawnColor selectedColor2;

    public int getCharacterIndex() {
        return characterIndex;
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

    public void setCharacterIndex(int characterIndex) {
        this.characterIndex = characterIndex;
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
