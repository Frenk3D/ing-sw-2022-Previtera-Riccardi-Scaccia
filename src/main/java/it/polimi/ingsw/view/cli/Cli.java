package it.polimi.ingsw.view.cli;



import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;



/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends ViewObservable implements View {

    private final PrintStream out;
    private Thread inputThread;

    private static final String STR_ROW = "Row: ";
    private static final String STR_COLUMN = "Column: ";
    private static final String STR_POSITION = "Position ";
    private static final String STR_INPUT_CANCELED = "User input canceled.";

    /**
     * Default constructor.
     */
    public Cli() {
        out = System.out;
    }


    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }



    /**
     * Clears the CLI terminal.
     */
    public void clearCli() {
        out.print(ColorCli.CLEAR);
        out.flush();
    }

    /**
     * Asks the user to choose a Nickname.
     */
    @Override
    public void askPlayerInfo() {

    }

    @Override
    public void askServerConfig() {

    }

    @Override
    public void askNewOrJoinGame() {

    }

    @Override
    public void showAvailableLobbies(List<Lobby> lobbiesList) {

    }

    @Override
    public void askRequestedLobby() {

    }

    @Override
    public void askTeam() {

    }

    @Override
    public void showAvailableTeams() {

    }

    @Override
    public void showTable() {

    }

    @Override
    public void showThrownAssistant() {

    }

    @Override
    public void showDashboards() {

    }

    @Override
    public void showCharacterTable() {

    }

    @Override
    public void askAssistantsList() {

    }

    @Override
    public void showAssistantsList() {

    }

    @Override
    public void askStudentToMoveIsland() {

    }

    @Override
    public void askStudentToMoveDashboard() {

    }

    @Override
    public void askMotherNatureMove() {

    }

    @Override
    public void askCloudExtraction() {

    }

    @Override
    public void askSelectedAssistant() {

    }

    @Override
    public void askUsedCharacter() {

    }

    /**
     * Shows a disconnection message.
     *
     * @param text                 a generic info text message.
     */
    @Override
    public void showDisconnectionMessage(String text) {

    }

    /**
     * Shows an error message.
     *
     * @param error the error message to be shown.
     */
    @Override
    public void showErrorAndExit(String error) {

    }

    @Override
    public void showMatchInfo() {

    }

    /**
     * Shows a win message.
     *
     * @param winner the nickname of the winner.
     */
    @Override
    public void showWinMessage(String winner) {

    }
}
