package it.polimi.ingsw.controllers;

import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.client.ReducedAssistant;
import it.polimi.ingsw.client.ReducedCharacter;
import it.polimi.ingsw.client.ReducedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to verify the correctness of the client's input
 */
public class ClientInputVerifier {
    /**
     * This method verifies if the input assistant is available or if the input is correct
     * @param clientGameModel the client game model
     * @param selectedAssistant the selected assistant
     * @return {@code true} if the assistant is available and the input is correct {@code false} otherwise
     */
    public static boolean verifyAssistant(ClientGameModel clientGameModel, int selectedAssistant) {
        List<Integer> thrownAssistants = new ArrayList<>();
        List<Integer> myAssistants = new ArrayList<>();
        for (ReducedPlayer p : clientGameModel.getPlayersList()) {
            if (p.getSelectedAssistant() != null) {
                thrownAssistants.add(p.getSelectedAssistant().getId());
            }
        }
        for (ReducedAssistant a : clientGameModel.getAssistantList()) {
            myAssistants.add(a.getId());
        }
        if (!myAssistants.contains(selectedAssistant)) {
            return false;
        } else if (thrownAssistants.contains(selectedAssistant) && !thrownAssistants.containsAll(myAssistants)) { //my assistant was thrown and
            return false;
        }
        return true;
    }

    /**
     * This method basically verifies if the dashboard's entrance list is empty
     * @param clientGameModel
     * @param selectedStudent
     * @return {@code true} if the entrance list is not empty and the selected student is not a negative number {@code false} otherwise
     */
    public static boolean verifyStudentDashboard(ClientGameModel clientGameModel, int selectedStudent) {
        if (selectedStudent < 0) {
            return false;
        } else if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getDashboard().getEntranceList().size() <= selectedStudent) {
            return false;
        }
        return true;
    }

    public static boolean verifyStudentIsland(ClientGameModel clientGameModel, int selectedStudent, int selectedIsland) {
        if (selectedStudent < 0 || selectedIsland < 0) {
            return false;
        } else if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getDashboard().getEntranceList().size() <= selectedStudent) {
            return false;
        } else if (clientGameModel.getIslandList().size() <= selectedIsland) {
            return false;
        }

        return true;
    }

    /**
     *This method verifies the correctness of the selected mother nature position
     * @param clientGameModel the client game model
     * @param selectedPos the selected mother nature position
     * @param usedCharacter the used character(in case it influences mother nature's movement)
     * @return {@code false} if the selected position is a negative number or if it's equals to the current mother nature position
     * @return {@code false} if the selected position is greater than the islands list's size
     * @return {@code false} if the movement is illegal based on the selected assistant
     * @return {@code true} otherwise
     */
    public static boolean verifyMotherNaturePos(ClientGameModel clientGameModel, int selectedPos, int usedCharacter) {
        ReducedAssistant assistant = clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getSelectedAssistant();
        if (usedCharacter != 4) {
            if (selectedPos < 0 || selectedPos == clientGameModel.getMotherNaturePos()) {
                return false;
            } else if (clientGameModel.getIslandList().size() <= selectedPos) {
                return false;
            } else if ((selectedPos - clientGameModel.getMotherNaturePos() > 0) && (selectedPos - clientGameModel.getMotherNaturePos() > assistant.getMotherNaturePosShift())) {
                return false;
            } else if ((selectedPos - clientGameModel.getMotherNaturePos() < 0) && ((clientGameModel.getIslandList().size() - clientGameModel.getMotherNaturePos() + selectedPos) > assistant.getMotherNaturePosShift())) {
                return false;
            }
        } else {
            if (selectedPos < 0 || selectedPos == clientGameModel.getMotherNaturePos()) {
                return false;
            } else if (clientGameModel.getIslandList().size() <= selectedPos) {
                return false;
            } else if ((selectedPos - clientGameModel.getMotherNaturePos() > 0) && (selectedPos - clientGameModel.getMotherNaturePos() > assistant.getMotherNaturePosShift() + 2)) {
                return false;
            } else if ((selectedPos - clientGameModel.getMotherNaturePos() < 0) && ((clientGameModel.getIslandList().size() - clientGameModel.getMotherNaturePos() + selectedPos) > assistant.getMotherNaturePosShift() + 2)) {
                return false;
            }

        }
        return true;
    }

    /**
     * This method verifies the selected cloud correctness
     * @param clientGameModel the client game model
     * @param selectedCloud the chosen cloud
     * @return {@code false} if the selected cloud is a negative number,is greater than the clouds list's size or the clouds list is empty
     * @return {@code true} otherwise
     */
    public static boolean verifyCloud(ClientGameModel clientGameModel, int selectedCloud) {
        if (selectedCloud < 0) {
            return false;
        } else if (clientGameModel.getCloudList().size() <= selectedCloud) {
            return false;
        } else if (clientGameModel.getCloudList().get(selectedCloud).getStudentsList().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * This method verifies the correctness of a selected character
     * @param clientGameModel the client game model
     * @param selectedCharacter the selected character
     * @return {@code false} if the character is null,or if the number of money of the player is inferior to the character's cost
     * @return {@code true} otherwise
     */
    public static boolean verifyCharacter(ClientGameModel clientGameModel, int selectedCharacter) {
        ReducedCharacter reducedCharacter = null;
        int cost = -1;
        for (ReducedCharacter rc : clientGameModel.getCharactersList()) {
            if (rc.getId() == selectedCharacter) {
                reducedCharacter = rc;
                break;
            }
        }
        if (reducedCharacter == null) {
            return false;
        } else {
            if (reducedCharacter.isUsed()) {
                cost = reducedCharacter.getInitialCost() + 1;
            } else {
                cost = reducedCharacter.getInitialCost();
            }
            if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getNumOfMoney() >= cost) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * This method verifies the correctness of character parameters
     * @param params the character parameters
     * @param clientGameModel the client game model
     * @return {@code false} if the character's id is not a number between 1 and 12,if the entrance list is null or a student within it is null
     * @return {@code true} if the params are correct
     */
    public static boolean verifyCharacterParams(MessageCharacterParameters params, ClientGameModel clientGameModel) {
        if (params.getCharacterId() < 1 || params.getCharacterId() > 12) {
            return false;
        }
        if(params.getStudentsIndexEntranceList()!=null) {
            for (Integer i : params.getStudentsIndexEntranceList()) {
                if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getDashboard().getEntranceList().get(i) == null) {
                    return false;
                }
            }
        }
        if(params.getIslandIndex()!=-1 && clientGameModel.getIslandList().get(params.getIslandIndex())==null){
            return false;
        }
        //if studentindexlist etc depends on characterId, and selected color check if available
        return true;
    }
}
