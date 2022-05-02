package it.polimi.ingsw.controller; //well connected to Game, need to Observ, with strategy or with checks

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters2and6and8and9;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class Controller implements Observer {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not
    private String name;

    //constructor
    public Controller(String name){
        game = new GameModel();
        this.name = name;
    }

    public GameModel getGame(){
        return game;
    }

    public String getName(){
        return name;
    }

    @Override
    public void update(Message message){ //the controller observes the view
        switch (getGameState()){
            case SETTING_STATE:
                settingState(message);
                break;
            case INGAME_STATE:
                if(checkUser(message)){ //check if the user is the right sender
                    inGameState(message);
                }
                break;
            default:
                System.out.println("Errore");
                break;
        }
    }

    private void settingState(Message receivedMessage){
        switch (receivedMessage.getMessageType()){
            case CHOOSE_TEAM:
                ChooseTeamMessage chooseTeamMessage = (ChooseTeamMessage) receivedMessage;
                chooseTeam(chooseTeamMessage.getPlayerId(), chooseTeamMessage.getRequestedPlayerId());
                break;

            case CHOOSE_TOWER_COLOR:
                ChooseTowerColorMessage chooseTowerColorMessage = (ChooseTowerColorMessage) receivedMessage;
                chooseTowerColor(chooseTowerColorMessage.getPlayerId(),chooseTowerColorMessage.getSelectedColor());
                break;

            case CHOOSE_WIZARD:
                ChooseWizardMessage chooseWizardMessage = (ChooseWizardMessage) receivedMessage;
                chooseWizard(chooseWizardMessage.getPlayerId(),chooseWizardMessage.getSelectedWizard());
                break;

            default:
                System.out.println("Errore");
                break;
        }
    }

    private void inGameState(Message receivedMessage){
        switch (receivedMessage.getMessageType()){
            case SELECT_ASSISTANT:
                SelectAssistantMessage selectAssistantMessage = (SelectAssistantMessage)receivedMessage;
                selectAssistant(selectAssistantMessage.getSelectedAssistant());
                break;

            case MOVE_STUDENT_ISLAND:
                MoveStudentIslandMessage moveStudentIslandMessage = (MoveStudentIslandMessage)receivedMessage;
                moveStudentIsland(moveStudentIslandMessage.getEntranceListIndex(),moveStudentIslandMessage.getIslandIndex());
                break;

            case MOVE_STUDENT_DASHBOARD:
                MoveStudentDashboardMessage moveStudentDashboardMessage = (MoveStudentDashboardMessage) receivedMessage;
                moveStudentDashboard(moveStudentDashboardMessage.getEntranceListIndex());
                break;

            case MOVE_MOTHER_NATURE:
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) receivedMessage;
                moveMotherNature(moveMotherNatureMessage.getIslandIndex());
                break;

            case TAKE_FROM_CLOUD:
                TakeFromCloudMessage takeFromCloudMessage = (TakeFromCloudMessage) receivedMessage;
                takeFromCloud(takeFromCloudMessage.getCloudIndex());
                break;

            case USE_CHARACTER:
                UseCharacterMessage useCharacterMessage = (UseCharacterMessage) receivedMessage;
                MessageCharacterParameters messageParams = useCharacterMessage.getCharacterParameters();

                CharacterParameters parameters = new CharacterParameters();
                parameters.setPlayer(game.getCurrPlayer());
                parameters.setPlayersList(game.getPlayersList());
                parameters.setTableProfessorsList(game.getTableProfessorsList());
                parameters.setForbidCharacter(game.getForbidCharacter());
                parameters.setBag(game.getBag());
                parameters.setIsland(game.getIslandByIndex(messageParams.getIslandIndex()));
                parameters.setStudentsIndexList(messageParams.getStudentsIndexList());
                parameters.setStudentsIndexEntranceList(messageParams.getStudentsIndexEntranceList());
                parameters.setStudentIndex(messageParams.getStudentIndex());
                parameters.setSelectedColor(messageParams.getSelectedColor());
                parameters.setSelectedColor2(messageParams.getSelectedColor2());

                useCharacter(messageParams.getCharacterIndex(),parameters);
                break;

            default:
                System.out.println("Errore");
                break;
        }
    }

    public void configure(int numOfPlayers, boolean expertMode){
        boolean result = game.setNumOfPlayers(numOfPlayers);
        if(!result){
            System.out.println("configure: wrong parameters");
            return;
        }
        game.setExpertMode(expertMode);
    }

    //LOGIN STATE
    public void addPlayer(Player player){
        if(getGameState() == GameState.LOGIN_STATE || game.getNumOfPlayers() == 0) {
            boolean result = game.addPlayer(player);
            if (!result) {
                System.out.println("addPlayer: too many players");
            }
            if (game.getNumOfPlayers() != 4) {
                player.setTeam(player.getId());
            }
            if (game.getPlayersList().size() == game.getNumOfPlayers()){
                game.init();
                System.out.println("GAME INITALIZED!");
            }
        }
        else {
            System.out.println("add player: not in login state or game not configured");
        }
    }

    //SETTING PHASE 1
    public void chooseTeam(int playerId, int requestedPlayerId){
        if(game.getNumOfPlayers() == 4 && game.getSettingState() == SettingState.CHOOSE_TEAM_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            Player requestedTeamPlayer = game.getPlayerById(requestedPlayerId);
            if(requestingPlayer==null || requestedTeamPlayer==null){
                System.out.println("chooseTeam: wrong parameters");
            }
            else if(requestingPlayer.getTeam() != -1 || requestedTeamPlayer.getTeam()!=-1){
                System.out.println("chooseTeam: you already have a team or requested player already has a team");
            }
            else {
                requestingPlayer.setTeam(requestingPlayer.getId());
                requestedTeamPlayer.setTeam(requestingPlayer.getId());
                requestedTeamPlayer.setHasTower(false);

                for (Player p : game.getPlayersList()){ //check if all player choose team player
                    if(p.getTeam()==-1){
                        return;
                    }
                }
                game.setSettingState(SettingState.CHOOSE_TOWER_COLOR_STATE);

            }
        }
        else {
            System.out.println("chooseTeam: forbidden move");
        }
    }

    //SETTING PHASE 2
    public void chooseTowerColor(int playerId, TowerColor selectedColor){
        if(game.getSettingState()==SettingState.CHOOSE_TOWER_COLOR_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            List<TowerColor> availableColors = game.getChooseTowerColorList();
            if (requestingPlayer == null) {
                System.out.println("chooseTowerColor: wrong parameters");
            } else if (!requestingPlayer.hasTower()) {
                System.out.println("chooseTowerColor: player doesnt have tower");
            } else if (!availableColors.contains(selectedColor)) {
                System.out.println("chooseTowerColor: already choosen color");
            } else {
                requestingPlayer.setPlayerTowerColor(selectedColor);
                availableColors.remove(selectedColor);

                for (Player p : game.getPlayersList()){ //check if all player choose the color
                    if(p.getTowerColor() == null && p.hasTower()){
                        return;
                    }
                }
                game.setSettingState(SettingState.CHOOSE_WIZARD_STATE);
            }
        }
        else {
            System.out.println("chooseTowerColor: Forbidden move");
        }
    }

    //SETTING PHASE 3
    public void chooseWizard(int playerId, Wizard selectedWizard){
        if(game.getSettingState()==SettingState.CHOOSE_WIZARD_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            List<Wizard> availableWizards = game.getWizardList();
            if (requestingPlayer == null) {
                System.out.println("chooseWizard: wrong parameters");
            } else if (!availableWizards.contains(selectedWizard)) {
                System.out.println("chooseWizard: already choosen wizard");
            } else {
                requestingPlayer.getAssistantDeck().setWizard(selectedWizard);
                availableWizards.remove(selectedWizard);

                for (Player p : game.getPlayersList()){
                    if (p.getAssistantDeck().getWizard()==null){
                        return;
                    }
                }
                game.setSettingState(SettingState.NOT_SETTING_STATE);
                game.start();
                System.out.println("GAME STARTED!");
            }
        }
        else {
            System.out.println("chooseWizard: Forbidden move");
        }
    }



    //methods
    //ACTION PHASE 1
    public void moveStudentIsland(int entranceListIndex,int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.MOVE_STUDENT_STATE) {
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
            System.out.println("move student island: forbidden move");
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
            boolean result = currPlayer.getDashboard().addStudentHall(studentToMove,currPlayer,game.getTableMoney());
            if(!result){
                System.out.println("move student dashboard: too many students");
                return;
            }

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
            System.out.println("move student dashboard: forbidden move");
        }
    }

    //ACTION PHASE 2
    public void moveMotherNature(int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_MOTHER_NATURE_STATE){

            int prevMotherNaturePos = game.getMotherNaturePos();
            if(islandIndex - prevMotherNaturePos > game.getCurrPlayer().getSelectedAssistant().getValue() || islandIndex - prevMotherNaturePos < 0){
                System.out.println("move mother nature: too far");
                return;
            }

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
            System.out.println("move mother nature: forbidden move");
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

            boolean result = game.getCurrRound().nextTurn();
            if(!result){ //the round is ended and we fill the clouds again
                game.getCurrRound().fillClouds(game.getCloudsList(),game.getBag(),game.getNumOfPlayers());
            }

        }
        else {
            System.out.println("take from cloud: forbidden move");
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
                round.initRound(game.getPlayersList());
            }
        }
        else {
            System.out.println("select assistant: forbidden move");
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
                System.out.println("use character: Not enough money");
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
}
