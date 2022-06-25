package it.polimi.ingsw.controllers;

import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.client.ReducedAssistant;
import it.polimi.ingsw.client.ReducedCharacter;
import it.polimi.ingsw.client.ReducedPlayer;

import java.util.ArrayList;
import java.util.List;

public class ClientInputVerifier {

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
