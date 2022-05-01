package it.polimi.ingsw.controller; //well connected to Game, need to Observ, with strategy or with checks

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters2and6and8and9;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.LoginRequestMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class Controller implements Observer {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not

    //constructor
    public Controller(){
        game = new GameModel();
    }

    public GameModel getGame(){
        return game;
    }

    public void onMessageReceived(Message message){
        switch (getGameState()){
            case LOGIN_STATE:
                loginState(message);
                break;
            case SETTING_STATE:
                settingState(message);
                break;
            case INGAME_STATE:
                if(checkUser(message)){
                    inGameState(message);
                }
                break;
            default:
                System.out.println("Errore");
                break;
        }
    }

    private void loginState(Message message) {
        switch (message.getMessageType()){
            case ADD_PLAYER_REQUEST:
                break;
            default:
                System.out.println("Errore");
                break;
        }
    }


    private void settingState(Message message){
        switch (message.getMessageType()){
            case CHOOSE_TEAM:
                break;
            case CHOOSE_TOWER_COLOR:
                break;
            case CHOOSE_WIZARD:
                break;
            default:
                System.out.println("Errore");
                break;
        }
    }

    private void inGameState(Message message){
        switch (message.getMessageType()){
            case SELECT_ASSISTANT:
                break;
            case MOVE_STUDENT_ISLAND:
                break;
            case MOVE_STUDENT_DASHBOARD:
                break;
            case MOVE_MOTHER_NATURE:
                break;
            case TAKE_FROM_CLOUD:
                break;
            case USE_CHARACTER:
                break;
            default:
                System.out.println("Errore");
                break;
        }
    }

    //LOGIN STATE
    public void addPlayer(Player player){
        boolean result = game.addPlayer(player);
        if (!result){
            System.out.println("addPlayer: too many players");
        }
        if(game.getNumOfPlayers()!=4){
            player.setTeam(player.getId());
        }
    }

    //SETTING PHASE 1
    public void chooseTeam(int playerId, int requestedPlayerId){
        if(game.getNumOfPlayers()==4) {
            Player requestingPlayer = game.getPlayerById(playerId);
            Player requestedTeamPlayer = game.getPlayerById(requestedPlayerId);
            if(requestingPlayer==null || requestedTeamPlayer==null){
                System.out.println("chooseTeam: wrong parameters");
            }
            else if(requestingPlayer.getTeam()!=-1|| requestedTeamPlayer.getTeam()!=-1){
                System.out.println("chooseTeam: you already have a team or requested player already has a team");
            }
            else {
                requestingPlayer.setTeam(requestingPlayer.getId());
                requestedTeamPlayer.setTeam(requestingPlayer.getId());
                requestedTeamPlayer.setHasTower(false);
            }
        }
        else {
            System.out.println("chooseTeam: you are not in a 4 player game");
        }
    }

    //SETTING PHASE 2
    public void chooseTowerColor(int playerId, TowerColor selectedColor){
        Player requestingPlayer = game.getPlayerById(playerId);
        List<TowerColor> availableColors = game.getChooseTowerColorList();
        if(requestingPlayer==null){
            System.out.println("chooseTowerColor: wrong parameters");
        }
        else if(!requestingPlayer.hasTower()){
            System.out.println("chooseTowerColor: player doesnt have tower");
        }
        else if(!availableColors.contains(selectedColor)){
            System.out.println("chooseTowerColor: already choosen color");
        }
        else {
            requestingPlayer.setPlayerTowerColor(selectedColor);
            availableColors.remove(selectedColor);
        }
    }

    public void chooseWizard(int playerId, Wizard selectedWizard){
        Player requestingPlayer = game.getPlayerById(playerId);
        List<Wizard> availableWizards = game.getWizardList();
        if(requestingPlayer==null){
            System.out.println("chooseWizard: wrong parameters");
        }
        else if(!availableWizards.contains(selectedWizard)){
            System.out.println("chooseWizard: already choosen wizard");
        }
        else {
            requestingPlayer.getAssistantDeck().setWizard(selectedWizard);
            availableWizards.remove(selectedWizard);

        }
    }



    //methods
    //ACTION PHASE 1
    public void moveStudentIsland(int entranceListIndex,int islandIndex){
        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_STUDENT_STATE) {
            Player currPlayer = game.getCurrPlayer();
            if(currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex)==null||game.getIslandByIndex(islandIndex)==null){
                System.out.println("move student island: wrong parameters");
                return;
            }

            Student studentToMove = currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex);
            game.getIslandByIndex(islandIndex).addStudent(studentToMove);
            currPlayer.getDashboard().getEntranceList().remove(studentToMove);
            game.getCurrRound().getCurrTurn().incrementMovedStudents();
        }
        else {
            System.out.println("forbidden move");
        }
    }

    //ACTION PHASE 1
    public void moveStudentDashboard(int entranceListIndex){
        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.MOVE_STUDENT_STATE){
            Player currPlayer = game.getCurrPlayer();
            if(currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex)==null){
                System.out.println("move student dashboard: wrong parameters");
                return;
            }

            Student studentToMove = currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex);
            currPlayer.getDashboard().addStudentHall(studentToMove,currPlayer,game.getTableMoney());
            currPlayer.getDashboard().getEntranceList().remove(studentToMove);
            if(game.isExpertMode() && game.getCurrRound().getCurrTurn().getUsedCharacter().getId()==2){
                ((Characters2and6and8and9)game.getCurrRound().getCurrTurn().getUsedCharacter()).modifiedUpdateProfessorsLists2(game.getPlayersList(), game.getCurrPlayer(), game.getTableProfessorsList());
            }
            else {
                game.getCurrRound().getCurrTurn().updateProfessorsLists(game.getPlayersList(),game.getTableProfessorsList());
            }

            game.getCurrRound().getCurrTurn().incrementMovedStudents();

        }
        else {
            System.out.println("forbidden move");
        }
    }

    //ACTION PHASE 2
    public void moveMotherNature(int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_MOTHER_NATURE_STATE){
            if(game.getIslandByIndex(islandIndex)==null){
                System.out.println("move mother nature: wrong parameters");
                return;
            }

            game.setMotherNaturePosition(islandIndex);

            if(game.isExpertMode()){//we apply the character effect if it's used or we remove forbid card if present
                int usedCharacterId = game.getCurrRound().getCurrTurn().getUsedCharacter().getId();

                if(usedCharacterId==6||usedCharacterId==8||usedCharacterId==9) {
                    Characters2and6and8and9 character = (Characters2and6and8and9) game.getCurrRound().getCurrTurn().getUsedCharacter();
                    character.updateIslandDomainCharacter(game.getCurrPlayer(),game.getIslandByIndex(islandIndex),game.getPlayersList(),game.getForbidCharacter());
                }
                else {
                    game.getIslandByIndex(islandIndex).updateIslandDomainExpert(game.getPlayersList(), game.getForbidCharacter());
                }
            }

            else {
                game.getIslandByIndex(islandIndex).updateIslandDomain(game.getPlayersList());
            }
            game.getCurrRound().getCurrTurn().setStage(TurnState.CHOOSE_CLOUD_STATE);
        }
        else {
            System.out.println("forbidden move");
        }
    }

    //ACTION PHASE 3
    public void takeFromCloud(int cloudIndex){ //they go in the entranceList
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.CHOOSE_CLOUD_STATE){
            if(game.getCloudByIndex(cloudIndex)==null || game.getCloudByIndex(cloudIndex).getStudents().isEmpty()){
                System.out.println("take from cloud: wrong parameters");
                return;
            }

            game.getCurrPlayer().getDashboard().getEntranceList().addAll(game.getCloudByIndex(cloudIndex).getStudents());
            game.getCloudByIndex(cloudIndex).getStudents().clear();
            game.getCurrRound().nextTurn();
        }
        else {
            System.out.println("forbidden move");
        }

    }

    public void selectAssistant(int assistantId){
        if(game.getCurrRound().getStage() == RoundState.PLANNING_STATE) {
            Round round = game.getCurrRound();
            Player player = round.getPlanningPhasePlayer(game.getPlayersList());
            if(player.getAssistantDeck().getAssistantById(assistantId)==null){
                System.out.println("select assistant: wrong parameters");
                return;
            }

            player.setSelectedAssistant(assistantId);
            player.getAssistantDeck().removeAssistantById(assistantId);
            round.setNextPlayerPlanning(game.getNumOfPlayers());

            if (round.getNumOfAssistantThrows() == game.getNumOfPlayers()) {
                round.initRound(game.getPlayersList(), game.getCloudsList(), game.getBag());
            }
        }
        else {
            System.out.println("forbidden move");
        }
    }


    public void useCharacter(int characterIndex, CharacterParameters parameters){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE) {
            Character usedCharacter = game.getCharacterByIndex(characterIndex);
            if(usedCharacter==null){
                System.out.println("use character: wrong parameters");
                return;
            }

            int characterCost = usedCharacter.getInitialCost();
            if (usedCharacter.isUsed()) { //increment character cost if already used
                characterCost++;
            }

            if (game.getCurrPlayer().getMoney() >= characterCost) { //check if the player has enough money to pay the character
                boolean result = usedCharacter.applyEffect(parameters);
                if(!result) {
                    System.out.println("use character: error in character use");
                    return;
                }
                usedCharacter.setUsed();
                game.getCurrPlayer().modifyMoney(-(characterCost - 1), game.getTableMoney(), usedCharacter.isUsed());
                game.getCurrRound().getCurrTurn().setUsedCharacter(game.getCharacterByIndex(characterIndex));
            }
            else {
                System.out.println("Not enough money");
            }
        }
        else {
            System.out.println("forbidden move");
        }
    }

    private GameState getGameState() {
        return game.getGameState();
    }

    private boolean checkUser(Message message){
        if((game.getCurrRound().getStage()==RoundState.ACTION_STATE && message.getSenderId()==game.getCurrPlayer().getId()) || (game.getCurrRound().getStage() == RoundState.PLANNING_STATE && message.getSenderId()==game.getCurrRound().getPlanningPhasePlayer(game.getPlayersList()).getId())){
            return true;
        }
        return false;
    }

    @Override
    public void update(Message message) {

    }
}
