package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.model.enumerations.*;

import java.util.Map;

//controller is the view observer and the view is the view observable
/*(it has fewer prototypes of ModelObserver but with more parameters to say to the controller witch message send)*/

/**
 * Custom observer interface for views. It supports different types of notification. From the different views to {@link it.polimi.ingsw.controllers.ClientController}
 */
public interface ViewObserver {

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onAskServerInfo(Map<String, String> serverInfo) ;

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    void onSendLoginRequest(String nickname);

//    void onAskCreateOrJoin(String input);

    /**
     * Sends a message to the server with the new lobby name,the number of players and the game mode
     * @param input the lobby's name
     * @param numOfPlayers the lobby's number of players
     * @param expertMode the lobby's game mode
     */
    void onSendNewLobbyRequest(String input,int numOfPlayers,boolean expertMode);

    /**
     * Sends a message to the server to showcase the existing lobbies list
     */
    void onSendLobbiesRequest();

    /**
     * Sends a message to the server with the chosen lobby from the existing ones
     * @param chosenLobby the lobby chosen by the player
     */
    void onSendChooseLobby(String chosenLobby);

    /**
     * Sends a message to the server with the chosen team player's id
     * @param chosenTeamPlayerId the chosen team player's id
     */
    void onSendChooseTeam(int chosenTeamPlayerId);

    /**
     * Sends a message to the server with the chosen tower color
     * @param color the chosen tower color
     */
    void onSendChooseTowerColor(TowerColor color);

    /**
     * Sends a message to the server with the chosen wizard
     * @param wizard the chosen wizard
     */
    void onSendChooseWizard(Wizard wizard);

    /**
     * Sends a message to the server with the selected assistant's id
     * @param selectedAssistantId
     */
    void onSendSelectAssistant(int selectedAssistantId);

    /**
     * Sends a message to the server with the index of the student to be moved and the index of the island on which it moves
     * @param selectedStudentIndex the index of the student
     * @param selectedIslandIndex the index of the island
     */
    void onSendMoveAStudentIsland(int selectedStudentIndex, int selectedIslandIndex);

    /**
     * Sends a message to the server with the index of the student to be moved in the hall
     * @param selectedStudentIndex the index of the student
     */
    void onSendMoveAStudentDashboard(int selectedStudentIndex);

    /**
     * Sends a message to the server with index of the island on which mother nature has to be moved
     * @param selectedIslandIndex the index of the island
     */
    void onSendMoveMotherNature(int selectedIslandIndex);

    /**
     * Sends a message to the Server with the index of the selected cloud
     * @param selectedCloudIndex the index of the cloud
     */
    void onSendChooseCloud(int selectedCloudIndex);

    /**
     * Sends a message to the server with the id of the selected character
     * @param characterId
     */
    void onAskCharacter(int characterId);
    /**
     * Sends a message to the server with the used character parameters
     * @param params the character's parameters
     */
    void onSendUseCharacter(MessageCharacterParameters params);


//    /**
//     * Handles a disconnection wanted by the user.
//     */
//    void onAskDisconnection(); from the view we can choose to close the game ?

}
