package it.polimi.ingsw.controllers; //well connected to Game, need to Observ, with strategy or with checks

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters2and6and8and9;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.client.ReducedAssistant;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.network.server.Server.SERVERID;

/**
 * This is the main controller of the game rules in the server. It implements {@link Observer}
 */
public class Controller implements Observer {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not
    private Server server;
    private final Logger logger = Logger.getLogger(getClass().getName());

    //constructor
    public Controller(){
        game = new GameModel();
    }

    public void setServer(Server server){
        this.server = server;
    }

    public GameModel getGame(){
        return game;
    }

    //----------------functions for lobby display on server----------------------
    public int getNumOfPlayer(){
        return game.getNumOfPlayers();
    }

    public int getActualNumOfPlayers(){
        return game.getPlayersList().size();
    }

    public boolean getExpertMode(){
        return game.isExpertMode();
    }

    public boolean isOpen(){
        if(game.getGameState()==GameState.LOGIN_STATE){
            return true;
        }
        else {
            return false;
        }
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
                else {
                    sendError(message.getSenderId(), "It is not your turn");
                }
                break;
            default:
                logger.log(Level.SEVERE,"reached default in switch");
                sendError(message.getSenderId(), "The game is not active");
                break;
        }
    }

    private void settingState(Message receivedMessage){
        switch (receivedMessage.getMessageType()){
            case CHOOSE_TEAM:
                ChooseTeamMessage chooseTeamMessage = (ChooseTeamMessage) receivedMessage;
                chooseTeam(chooseTeamMessage.getSenderId(), chooseTeamMessage.getRequestedPlayerId());
                break;

            case CHOOSE_TOWER_COLOR:
                ChooseTowerColorMessage chooseTowerColorMessage = (ChooseTowerColorMessage) receivedMessage;
                chooseTowerColor(chooseTowerColorMessage.getSenderId(),chooseTowerColorMessage.getSelectedColor());
                break;

            case CHOOSE_WIZARD:
                ChooseWizardMessage chooseWizardMessage = (ChooseWizardMessage) receivedMessage;
                chooseWizard(chooseWizardMessage.getSenderId(),chooseWizardMessage.getSelectedWizard());
                break;

            default:
                logger.log(Level.SEVERE,"reached default in switch");
                sendError(receivedMessage.getSenderId(), "Forbidden command");
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
                parameters.setTableMoney(game.getTableMoney());

                useCharacter(messageParams.getCharacterId(),parameters);
                break;

            default:
                logger.log(Level.SEVERE,"reached default in switch");
                sendError(receivedMessage.getSenderId(), "Forbidden command");
                break;
        }
    }

    public void configure(int numOfPlayers, boolean expertMode){
        boolean result = game.setNumOfPlayers(numOfPlayers);
        if(!result){
            logger.log(Level.SEVERE,"wrong parameters");
            return;
        }
        game.setExpertMode(expertMode);
    }

    //LOGIN STATE
    public synchronized void addPlayer(Player player){
        if(getGameState() == GameState.LOGIN_STATE || game.getNumOfPlayers() == 0) {
            boolean result = game.addPlayer(player);
            if (!result) {
                logger.log(Level.SEVERE,"addPlayer: too many players");
                return;
            }
            if (game.getNumOfPlayers() != 4) {
                player.setTeam(player.getId());
            }
            if (game.getPlayersList().size() == game.getNumOfPlayers()){
                game.init();
                logger.log(Level.INFO,"GAME INITALIZED!");
            }
        }
        else {
            logger.log(Level.SEVERE,"not in login state or game not configured");
        }
    }

    //SETTING PHASE 1
    public synchronized void chooseTeam(int playerId, int requestedPlayerId){
        if(game.getNumOfPlayers() == 4 && game.getSettingState() == SettingState.CHOOSE_TEAM_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            Player requestedTeamPlayer = game.getPlayerById(requestedPlayerId);

            if(requestingPlayer==null || requestedTeamPlayer==null){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(playerId, "Wrong parameters");
            }
            else if(requestingPlayer.getTeam() != -1 || requestedTeamPlayer.getTeam()!=-1){
                logger.log(Level.SEVERE,"you already have a team or requested player already has a team");
                sendError(playerId, "You already have a team or requested player already has a team");
            }
            else {
                requestingPlayer.setTeam(requestingPlayer.getId());  //the teamId is the teamLeaderId
                requestedTeamPlayer.setTeam(requestingPlayer.getId());
                requestedTeamPlayer.setHasTower(false);
                sendOk(playerId);
                sendOk(requestedPlayerId); //we send ok to requested player to inform that he doesn't have to choose a team player

                for (Player p : game.getPlayersList()){ //check if all player choose team player
                    if(p.getTeam()==-1){
                        game.sendAvailableTeamPlayers(); //send to all clients the remaining people to choose
                        return;
                    }
                }
                game.setSettingState(SettingState.CHOOSE_TOWER_COLOR_STATE); //set and sends the current setting state
                game.sendAvailableTowerColors();
            }
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(playerId, "Forbidden command");
        }
    }

    //SETTING PHASE 2
    public synchronized void chooseTowerColor(int playerId, TowerColor selectedColor){
        if(game.getSettingState()==SettingState.CHOOSE_TOWER_COLOR_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            List<TowerColor> availableColors = game.getChooseTowerColorList();
            if (requestingPlayer == null) {
                logger.log(Level.SEVERE,"wrong parameters");
            } else if (!requestingPlayer.hasTower()) {
                logger.log(Level.SEVERE,"player isn't tower holder");
                sendError(playerId, "You aren't the tower holder");
            } else if (!availableColors.contains(selectedColor)) {
                logger.log(Level.SEVERE,"already chosen color");
                sendError(playerId, "Already chosen color");
            } else if(requestingPlayer.getTowerColor() != null){
                logger.log(Level.SEVERE,"player already choose color");
                sendError(playerId, "You already chosen color");
            }
            else {
                requestingPlayer.setPlayerTowerColor(selectedColor);
                availableColors.remove(selectedColor);
                sendOk(playerId);

                for (Player p : game.getPlayersList()){ //check if all players chose the color
                    if(p.getTowerColor() == null && p.hasTower()){
                        game.sendAvailableTowerColors();
                        return;
                    }
                }
                game.setSettingState(SettingState.CHOOSE_WIZARD_STATE);
                game.sendAvailableWizards();
            }
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(playerId, "Forbidden command");
        }
    }

    //SETTING PHASE 3
    public synchronized void chooseWizard(int playerId, Wizard selectedWizard){
        if(game.getSettingState()==SettingState.CHOOSE_WIZARD_STATE) {
            Player requestingPlayer = game.getPlayerById(playerId);
            List<Wizard> availableWizards = game.getWizardList();
            if (requestingPlayer == null) {
                logger.log(Level.SEVERE,"wrong parameters");
            } else if (!availableWizards.contains(selectedWizard)) {
                logger.log(Level.SEVERE,"already chosen wizard");
                sendError(playerId, "Already chosen wizard");
            } else if(requestingPlayer.getAssistantDeck().getWizard()!=null){
                logger.log(Level.SEVERE,"player already choose wizard");
                sendError(playerId, "You already choose wizard");
            }
            else {
                requestingPlayer.getAssistantDeck().setWizard(selectedWizard);
                availableWizards.remove(selectedWizard);
                sendOk(playerId);

                for (Player p : game.getPlayersList()){
                    if (p.getAssistantDeck().getWizard()==null){
                        game.sendAvailableWizards();
                        return;
                    }
                }
                game.setSettingState(SettingState.NOT_SETTING_STATE);
                game.start();
                logger.log(Level.INFO,"GAME STARTED!");
            }
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(playerId, "Forbidden command");
        }
    }



    //methods
    //ACTION PHASE 1
    public synchronized void moveStudentIsland(int entranceListIndex,int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.MOVE_STUDENT_STATE) {
            Player currPlayer = game.getCurrPlayer();
            if(currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex)==null||game.getIslandByIndex(islandIndex)==null){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(game.getCurrPlayer().getId(), "Wrong parameters");
                return;
            }

            Student studentToMove = currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex);
            game.getIslandByIndex(islandIndex).addStudent(studentToMove);
            currPlayer.getDashboard().getEntranceList().remove(studentToMove);
            game.getCurrRound().getCurrTurn().incrementMovedStudents(game.getNumOfPlayers());

            game.sendTable();
            game.sendDashboard();
            game.sendInGameState();
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }
    }

    //ACTION PHASE 1
    public synchronized void moveStudentDashboard(int entranceListIndex){
        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.MOVE_STUDENT_STATE){
            Player currPlayer = game.getCurrPlayer();
            if(currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex)==null){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(game.getCurrPlayer().getId(), "Wrong parameters");
                return;
            }

            Student studentToMove = currPlayer.getDashboard().getEntranceStudentByIndex(entranceListIndex);
            boolean result = currPlayer.getDashboard().addStudentHall(studentToMove,currPlayer,game.getTableMoney());
            if(!result){
                logger.log(Level.SEVERE,"too many students");
                sendError(game.getCurrPlayer().getId(), "Too many students");
                return;
            }

            currPlayer.getDashboard().getEntranceList().remove(studentToMove);

            if(game.isExpertMode() && game.getCurrRound().getCurrTurn().getUsedCharacter()!= null && game.getCurrRound().getCurrTurn().getUsedCharacter().getId()==2){
                ((Characters2and6and8and9)game.getCurrRound().getCurrTurn().getUsedCharacter()).modifiedUpdateProfessorsLists2(game.getPlayersList(), game.getCurrPlayer(), game.getTableProfessorsList());
            }
            else {
                int updatedPlayer = game.getCurrRound().getCurrTurn().updateProfessorsLists(game.getPlayersList(),game.getTableProfessorsList());
                if(updatedPlayer!=-1){
                    game.sendDashboard(updatedPlayer); //send the updated dashboard of other player
                }
            }

            game.getCurrRound().getCurrTurn().incrementMovedStudents(game.getNumOfPlayers());
            game.sendDashboard(); //update the dashboard in clients
            if(game.isExpertMode()){//update the money in players if expert mode
                game.sendCharacterTable();
            }
            game.sendInGameState();
        }

        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }
    }

    //ACTION PHASE 2
    public synchronized void moveMotherNature(int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_MOTHER_NATURE_STATE){

            int prevMotherNaturePos = game.getMotherNaturePos();

            if(game.getIslandByIndex(islandIndex)==null || islandIndex == prevMotherNaturePos){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(game.getCurrPlayer().getId(), "Wrong parameters");
                return;
            }
            else if((islandIndex - prevMotherNaturePos > 0) && (islandIndex - prevMotherNaturePos > game.getCurrPlayer().getSelectedAssistant().getMotherNaturePosShift())){
                logger.log(Level.SEVERE,"moved mother nature too far");
                sendError(game.getCurrPlayer().getId(), "Mother nature moved too far");
                return;
            }
            else if((islandIndex - prevMotherNaturePos < 0) && ((game.getIslandsList().size()-prevMotherNaturePos+islandIndex) > game.getCurrPlayer().getSelectedAssistant().getMotherNaturePosShift())){
                logger.log(Level.SEVERE,"moved mother nature too far");
                sendError(game.getCurrPlayer().getId(), "Mother nature moved too far");
                return;
            }

            game.setMotherNaturePosition(islandIndex);

            if(game.isExpertMode()) { //we apply the character effect if it's used, or we remove forbid card if present
                if( game.getCurrRound().getCurrTurn().getUsedCharacter()!=null){
                    int usedCharacterId = game.getCurrRound().getCurrTurn().getUsedCharacter().getId();
                    if(usedCharacterId==6||usedCharacterId==8||usedCharacterId==9) {
                    Characters2and6and8and9 character = (Characters2and6and8and9) game.getCurrRound().getCurrTurn().getUsedCharacter();
                    character.updateIslandDomainCharacter(game.getCurrPlayer(),game.getIslandByIndex(islandIndex),game.getPlayersList(),game.getForbidCharacter());
                    }
                    else { //simply remove forbid card if none of characters 6/8/9 was used in this turn (if there is)
                        game.getIslandByIndex(islandIndex).updateIslandDomainExpert(game.getPlayersList(), game.getForbidCharacter());
                    }
                }
                else{ //simply remove forbid card if no character was used in this turn (if there is)
                    game.getIslandByIndex(islandIndex).updateIslandDomainExpert(game.getPlayersList(), game.getForbidCharacter());
                }
                game.sendCharacterTable(); //update the number of forbid cards on the character
            }
            else {
                game.getIslandByIndex(islandIndex).updateIslandDomain(game.getPlayersList());
            }

            int newMotherNaturePos = game.getCurrRound().getCurrTurn().updateIslandList(game.getIslandsList());
            if(newMotherNaturePos != -1){
                game.setMotherNaturePosition(newMotherNaturePos);
            }


            game.sendAllDashboards();
            game.sendTable();
            game.getCurrRound().getCurrTurn().setStage(TurnState.CHOOSE_CLOUD_STATE);

            if(game.getCurrRound().isLastRound() && !game.getCurrRound().nextTurn()){ //if it is the last round because students in the bag finished we jump to next turn (without take from cloud), if turn is the last we find the winner
                game.sendWin(getWinner());
                new Thread(() -> server.deleteLobby(this)).start();
                return;
            }
            else if(checkWin(false)){
                game.sendWin(getWinner());
                new Thread(() -> server.deleteLobby(this)).start();
                return;
            }
            else {
                game.sendInGameState();
            }

        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }
    }

    //ACTION PHASE 3
    public synchronized void takeFromCloud(int cloudIndex){ //they go in the entranceList
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.CHOOSE_CLOUD_STATE){
            if(game.getCloudByIndex(cloudIndex)==null || game.getCloudByIndex(cloudIndex).getStudents().isEmpty()){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(game.getCurrPlayer().getId(), "Wrong parameters");
                return;
            }

            game.getCurrPlayer().getDashboard().getEntranceList().addAll(game.getCloudByIndex(cloudIndex).getStudents());
            game.getCloudByIndex(cloudIndex).getStudents().clear();
            game.sendDashboard();

            boolean result = game.getCurrRound().nextTurn();
            if(!result){ //the round is ended and we fill the clouds again
                if (checkWin(true)) {
                    game.sendWin(getWinner());
                    new Thread(() -> server.deleteLobby(this)).start();
                    return;
                }

                for (Player p : game.getPlayersList()){
                    p.setSelectedAssistant(null);
                }
                game.getCurrRound().fillClouds(game.getCloudsList(),game.getBag(),game.getNumOfPlayers());
            }
            game.sendTable();
            game.sendInGameState();
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }

    }

    public synchronized void selectAssistant(int assistantId){
        if(game.getCurrRound().getStage() == RoundState.PLANNING_STATE) {
            Round round = game.getCurrRound();
            Player player = round.getPlanningPhasePlayer(game.getPlayersList());
            if(player.getAssistantDeck().getAssistantById(assistantId)==null){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(player.getId(), "Assistant not available");
                return;
            }

            List<Integer> thrownAssistants = new ArrayList<>();
            List<Integer> myAssistants = new ArrayList<>();
            for (Player p : game.getPlayersList()){
                if(p.getSelectedAssistant()!=null) {
                    thrownAssistants.add(p.getSelectedAssistant().getId());
                }
            }
            for (Assistant a : player.getAssistantDeck().getAssistantsList()){
                myAssistants.add(a.getId());
            }

            if(thrownAssistants.contains(assistantId) && !thrownAssistants.containsAll(myAssistants)){ //my assistant was already thrown, and it is false that all my assistants were already thrown
                logger.log(Level.SEVERE,"same id of other players");
                sendError(player.getId(), "Assistant not available");
                return;
            }

            player.setSelectedAssistant(assistantId);
            player.getAssistantDeck().removeAssistantById(assistantId);

            sendAssistantsToClient(player.getId(), player.getAssistantDeck().getReducedAssistantsList()); //send the remaining assistants only to the current player
            game.sendSelectedAssistant(); //send selected assistant to all players before setting the next player of planning phase

            round.setNextPlayerPlanning(game.getNumOfPlayers());

            if (round.getNumOfAssistantThrows() == game.getNumOfPlayers()) {
                round.initRound(game.getPlayersList());
            }
            game.sendInGameState();
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }
    }


    public synchronized void useCharacter(int characterId, CharacterParameters parameters){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE) {
            Character usedCharacter = game.getCharacterById(characterId);
            if(usedCharacter==null){
                logger.log(Level.SEVERE,"wrong parameters");
                sendError(game.getCurrPlayer().getId(), "Wrong parameters");
                return;
            }

            //calculate true character cost
            int characterCost = usedCharacter.getInitialCost();
            if (usedCharacter.isUsed()) { //increment character cost if already used
                characterCost++;
            }
            if (game.getCurrPlayer().getMoney() >= characterCost) { //check if the player has enough money to pay the character
                boolean result = usedCharacter.applyEffect(parameters);
                Island prevIsl = game.getIslandByIndex(game.getMotherNaturePos());
                int prevIslListsize = game.getIslandsList().size();
                int newMotherNaturePos = game.getCurrRound().getCurrTurn().updateIslandList(game.getIslandsList());
                int diff = prevIslListsize- game.getIslandsList().size();
                if(newMotherNaturePos != -1 && (!game.getIslandsList().contains(prevIsl)  || parameters.getIsland() == prevIsl )){
                    game.setMotherNaturePosition(newMotherNaturePos);
                }  //we update islands because some characters calculate the influence
                else if(diff !=0 && newMotherNaturePos<game.getMotherNaturePos()){
                    game.setMotherNaturePosition((game.getMotherNaturePos()) - diff);
                }
                game.getCurrRound().getCurrTurn().updateProfessorsLists(game.getPlayersList(), game.getTableProfessorsList());
                game.getCurrRound().getCurrTurn().returnProfessorsToTable(game.getPlayersList(), game.getTableProfessorsList());

                if(!result) {
                    logger.log(Level.SEVERE,"error in character use");
                    sendError(game.getCurrPlayer().getId(), "Error in character use, no money was taken, \nRetry to use character or continue playing");
                    return;
                }
                game.getCurrPlayer().modifyMoney(-(characterCost), game.getTableMoney(), usedCharacter.isUsed());
                usedCharacter.setUsed();
                game.getCurrRound().getCurrTurn().setUsedCharacter(game.getCharacterById(characterId));
                game.sendCharacterTable(); //update the characters on the table
                game.sendTable();//update the table
                game.sendAllDashboards(); //update the dashboards of all users
                game.sendInGameState(); //orders the update of the views
                game.sendThrownCharacter(characterId);

                if(checkWin(false)){
                    game.sendWin(getWinner());
                    new Thread(() -> server.deleteLobby(this)).start();
                    return;
                }
            }
            else {
                logger.log(Level.SEVERE,"Not enough money");
                sendError(game.getCurrPlayer().getId(), "Not enough money");
            }
        }
        else {
            logger.log(Level.SEVERE,"forbidden move");
            sendError(game.getCurrPlayer().getId(), "Forbidden command");
        }
    }

    private GameState getGameState() {
        return game.getGameState();
    }

    public boolean checkWin(boolean finishedRound){
        if(finishedRound){
            for (Player p : game.getPlayersList()){ //one player finished assistants, the check on the bag is done in the method movemothernature
                if(p.getAssistantDeck().getAssistantsList().isEmpty()){
                    logger.log(Level.INFO,"win on finished assistants");
                    return true;
                }
            }
        }
        else{
            for(Player p : game.getPlayersList()) {
                if (p.hasTower() && p.getDashboard().getTowersList().isEmpty()) { //a player finished the towers
                    logger.log(Level.INFO, "win on finished towers");
                    return true;
                }
            }
            if (game.getIslandsList().size()<=3){//there are only three islands on the table
                logger.log(Level.INFO,"win on 3 island remained");
                return true;
            }
        }
        return false;
    }

    public int getWinner() {  //it was private, but we have to put it public for the tests
        int winnerId = -1;
        Player tmpPlayer = null ;
        for(int i = 0; i<game.getPlayersList().size(); i++){
            if(game.getPlayersList().get(i).hasTower()==true){ //it is impossible that tmpPlayer is not set here
                tmpPlayer = game.getPlayersList().get(i); //tmpPlayer is the current winner player,
                break;
            }
        }
        for (Player p : game.getPlayersList()) {
            if (p.hasTower()) {
                if (p.getDashboard().getTowersList().size() < tmpPlayer.getDashboard().getTowersList().size()) {
                    tmpPlayer = p;
                }
                else if(p.getDashboard().getTowersList().size() == tmpPlayer.getDashboard().getTowersList().size()){
                    if(p.getDashboard().getProfessorsList().size()> tmpPlayer.getDashboard().getProfessorsList().size()){ //if also professors are the same number it will win the first checked by the loop (pseudo-randomly)
                        tmpPlayer = p;
                    }
                }
            }
        }
        return tmpPlayer.getId();
    }

    private boolean checkUser(Message message){
        if((game.getCurrRound().getStage()==RoundState.ACTION_STATE && message.getSenderId()==game.getCurrPlayer().getId()) || (game.getCurrRound().getStage() == RoundState.PLANNING_STATE && message.getSenderId()==game.getCurrRound().getPlanningPhasePlayer(game.getPlayersList()).getId())){
            return true;
        }
        return false;
    }

    private void sendError(int playerId, String message){
        if(server != null) {
            RemoteView remoteView = server.getRemoteViewByPlayerId(playerId);
            remoteView.sendToClient(new StringMessage(MessageType.ERROR_REPLY, SERVERID, false, message));
        }
    }

    private void sendOk(int playerId){
        if(server != null) {
            RemoteView remoteView = server.getRemoteViewByPlayerId(playerId);
            remoteView.sendToClient(new GenericMessage(MessageType.OK_REPLY, SERVERID,false));
        }
    }

    private void sendAssistantsToClient(int playerId, List<ReducedAssistant> assistantList){
        if(server != null) {
            RemoteView remoteView = server.getRemoteViewByPlayerId(playerId);
            remoteView.sendToClient(new AssistantsSendMessage( SERVERID,assistantList));
        }
    }


    //ONLY TO TEST METHOD

    public Server getServer() {
        return server;
    }
}
