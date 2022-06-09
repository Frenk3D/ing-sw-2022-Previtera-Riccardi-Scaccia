package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.client.ReducedAssistant;
import it.polimi.ingsw.model.client.ReducedPlayer;

import java.util.ArrayList;
import java.util.List;

public class ClientInputVerifier {

    public static boolean verifyAssistant(ClientGameModel clientGameModel, int selectedAssistant){
        List<Integer> thrownAssistants = new ArrayList<>();
        List<Integer> myAssistants = new ArrayList<>();
        for (ReducedPlayer p : clientGameModel.getPlayersList()) {
            if (p.getSelectedAssistant() != null) {
                thrownAssistants.add(p.getSelectedAssistant().getId());
            }
        }
        for (ReducedAssistant a : clientGameModel.getAssistantList()){
            myAssistants.add(a.getId());
        }
        if(!myAssistants.contains(selectedAssistant)){
            return false;
        }
        else if(thrownAssistants.contains(selectedAssistant) && !thrownAssistants.containsAll(myAssistants)) { //my assistant was thrown and
            return false;
        }
        return true;
    }

    public static boolean verifyStudentDashboard(ClientGameModel clientGameModel, int selectedStudent){
        if(selectedStudent<0){
            return false;
        }
        else if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getDashboard().getEntranceList().size() <= selectedStudent) {
            return false;
        }
        return true;
    }

    public static boolean verifyStudentIsland(ClientGameModel clientGameModel, int selectedStudent, int selectedIsland){
        if(selectedStudent<0||selectedIsland<0){
            return false;
        }
        else if (clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getDashboard().getEntranceList().size() <= selectedStudent) {
            return false;
        }
        else if (clientGameModel.getIslandList().size() <= selectedIsland) {
            return false;
        }

        return true;
    }

    public static boolean verifyMotherNaturePos(ClientGameModel clientGameModel, int selectedPos){
        ReducedAssistant assistant = clientGameModel.findPlayerById(clientGameModel.getMyPlayerId()).getSelectedAssistant();
        if(selectedPos<0 || selectedPos == clientGameModel.getMotherNaturePos()){
            return false;
        }
        else if (clientGameModel.getIslandList().size() <= selectedPos) {
            return false;
        }
        else if((selectedPos - clientGameModel.getMotherNaturePos() > 0) && (selectedPos - clientGameModel.getMotherNaturePos() > assistant.getMotherNaturePosShift())){
            return false;
        }
        else if((selectedPos - clientGameModel.getMotherNaturePos() < 0) && ((clientGameModel.getIslandList().size()-clientGameModel.getMotherNaturePos()+selectedPos) > assistant.getMotherNaturePosShift())){
            return false;
        }
        return true;
    }

    public static boolean verifyCloud(ClientGameModel clientGameModel, int selectedCloud){
        if(selectedCloud<0){
            return false;
        }
        else if (clientGameModel.getCloudList().size() <= selectedCloud) {
            return false;
        }
        else if(clientGameModel.getCloudList().get(selectedCloud).getStudentsList().isEmpty()){
            return false;
        }
        return true;
    }
}
