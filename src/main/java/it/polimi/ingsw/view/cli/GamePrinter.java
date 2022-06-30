package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.client.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to implement the graphic instances in the {@link it.polimi.ingsw.view.cli.Cli}
 */
public class GamePrinter {

    private final int[][] islandPositions = {{5, 12}, {31, 6}, {57, 2}, {83, 2}, {109, 2}, {135, 6}, {161, 12}, {135, 18}, {109, 22}, {83, 22}, {57, 22}, {31, 18}};
    private final int[][] cloudPositions = {{77, 12}, {95, 12}, {77, 17}, {95, 17}};
    private final int[][] dashboardPositions = {{49, 33}, {95, 33}, {3, 33}, {141, 33}};
    private final int[][] dashboardEntrancePositions = {{2, 1}, {6, 1}, {2, 2}, {6, 2}, {2, 3}, {6, 3}, {2, 4}, {6, 4}, {2, 5}, {6, 5}};
    private final int[][] characterStudentsPositions = {{2, 3}, {6, 3}, {2, 4}, {6, 4}, {2, 5}, {6, 5}};

    /**
     * This method prints in the cli the graphic interface of the game model
     *
     * @param clientGameModel the client's game model
     */
    public void print(ClientGameModel clientGameModel) { //this class is called by the Cli
        String[] canvas = generateSquare(189, 50);

        boolean motherNatureHere = false;
        int index = -1;
        int indexList = 0;
        for (ReducedIsland island : clientGameModel.getIslandList()) {
            motherNatureHere = clientGameModel.getMotherNaturePos() == indexList;
            index += island.getWeight();
            String[] generatedIsland = generateIsland(island, indexList + 1, motherNatureHere);
            mergeMatrix(canvas, islandPositions[index][0], islandPositions[index][1], generatedIsland);
            indexList++;
        }

        index = 0;
        for (ReducedCloud cloud : clientGameModel.getCloudList()) {
            String[] generatedCloud = generateCloud(cloud, index + 1);
            mergeMatrix(canvas, cloudPositions[index][0], cloudPositions[index][1], generatedCloud);
            index++;
        }

        index = 0;
        for (ReducedPlayer player : clientGameModel.getPlayersList()) {

            String teamPlayer = "";
            if (clientGameModel.getNumOfPlayers() == 4) {
                for (ReducedPlayer currP : clientGameModel.getPlayersList()) {
                    if (currP != player && currP.getTeam() == player.getTeam()) {
                        teamPlayer = currP.getName();
                        break;
                    }
                }
            }

            String[] generatedDashboard = generateDashboard(player, teamPlayer);
            mergeMatrix(canvas, dashboardPositions[index][0], dashboardPositions[index][1], generatedDashboard);
            index++;
        }

        writeAtPos(canvas, 10, 41, "My assistants");
        for (int i = 41; i < 47; i++) {
            writeAtPos(canvas, 7, i, "|");
        }

        int assistantPos = 10;
        for (ReducedAssistant myAssistant : clientGameModel.getAssistantList()) {
            String[] generatedAssistant = generateAssistant(myAssistant);
            mergeMatrix(canvas, assistantPos, 42, generatedAssistant);
            assistantPos += 10;
        }

        if (clientGameModel.isExpertMode()) {
            writeAtPos(canvas, 144, 48, "Write use_character [id] to use a character");

            writeAtPos(canvas, 150, 40, "Characters - Table money: " + clientGameModel.getTableMoney());
            for (int i = 40; i < 48; i++) {
                writeAtPos(canvas, 147, i, "|");
            }

            int characterPos = 150;
            for (ReducedCharacter character : clientGameModel.getCharactersList()) {
                String[] generatedCharacter = generateCharacter(character);
                mergeMatrix(canvas, characterPos, 41, generatedCharacter);
                characterPos += 13;
            }
        }
        printMatrix(canvas);
    }

    /**
     * This method generates an island in the cli
     */
    private String[] generateIsland(ReducedIsland island, int id, boolean motherNature) {
        String[] result = generateSquare(23, 9);
        writeAtPos(result, 7, 1, "Island " + id);

        Map<PawnColor, Integer> colorsMap = new HashMap<>();
        for (PawnColor p : island.getStudentsList()) {
            Integer existingNum = colorsMap.get(p);
            if (existingNum == null) {
                colorsMap.put(p, 1);
            } else {
                colorsMap.put(p, ++existingNum);
            }
        }

        //drawing students
        int startPos = 3;
        for (PawnColor p : PawnColor.values()) {
            if (colorsMap.get(p) != null) {
                writeAtPos(result, 2, startPos, getColor(p) + "█" + ColorCli.RESET + ": " + colorsMap.get(p));
                startPos++;
            }
        }

        //drawing weight
        writeAtPos(result, 18, 1, "(" + island.getWeight() + ")");

        //drawing towers
        if (island.getTowerColor() != null) {
            writeAtPos(result, 8, 3, "Domain: " + getColor(island.getTowerColor()) + island.getTowerColor() + ColorCli.RESET);
            writeAtPos(result, 8, 4, "█ Towers: " + island.getWeight());
        }

        //drawing forbid cards
        if (island.getForbidCards() != 0) {
            writeAtPos(result, 8, 5, "Forb.cards: " + island.getForbidCards());
        }

        //drawing mother nature
        if (motherNature) {
            writeAtPos(result, 11, 6, ColorCli.RED + "█  Mother" + ColorCli.RESET);
            writeAtPos(result, 10, 7, ColorCli.RED + "███ Nature" + ColorCli.RESET);
        }

        return result;
    }

    /**
     * This methods generates a player's dashboard in the cli
     *
     * @param player the player
     * @return the dashboard string
     */
    private String[] generateDashboard(ReducedPlayer player, String teamPlayer) {
        ReducedDashboard dashboard = player.getDashboard();
        String playerName = player.getName();
        TowerColor towerColor = player.getPlayerTowerColor();
        ReducedAssistant assistant = player.getSelectedAssistant();

        String[] result = generateSquare(45, 7);

        if (player.getNumOfMoney() != -1) {
            writeAtPos(result, 2, 0, " " + playerName + " (" + player.getWizard().toString() + " " + teamPlayer + ") Money: " + player.getNumOfMoney() + " ");
        } else {
            writeAtPos(result, 2, 0, " " + playerName + " (" + player.getWizard().toString() + ") ");

        }

        int pos = 0;
        int i;

        for (PawnColor p : dashboard.getEntranceList()) {
            writeAtPos(result, dashboardEntrancePositions[pos][0], dashboardEntrancePositions[pos][1], getColorBg(p) + " " + (pos + 1) + " " + ColorCli.RESET);
            pos++;
        }

        //first separator
        for (i = 1; i < 6; i++) {
            writeAtPos(result, 10, i, "|");
        }

        pos = 1;
        for (PawnColor p : PawnColor.values()) {
            writeAtPos(result, 12, pos, getColor(p) + "█" + ColorCli.RESET + ": " + dashboard.getStudentsHall().get(p));

            if (dashboard.getProfessorsList().contains(p)) {
                writeAtPos(result, 20, pos, getColor(p) + "██" + ColorCli.RESET);
            }
            pos++;
        }

        //second separator
        for (i = 1; i < 6; i++) {
            writeAtPos(result, 24, i, "|");
        }
        if (towerColor != null) {
            writeAtPos(result, 26, 2, "Towers:");
            writeAtPos(result, 27, 3, getColor(towerColor) + towerColor + ColorCli.RESET);
            writeAtPos(result, 29, 4, getColor(towerColor) + dashboard.getTowerNumber() + ColorCli.RESET);
        }

        //third separator
        for (i = 1; i < 6; i++) {
            writeAtPos(result, 34, i, "|");
        }

        writeAtPos(result, 37, 2, "Card:");

        if (assistant != null && assistant.getId() != 0) {
            writeAtPos(result, 37, 3, ColorCli.BLUE_BOLD + "ID: " + assistant.getId() + ColorCli.RESET);
            writeAtPos(result, 36, 4, "POS: +" + assistant.getMotherNaturePosShift());
        } else {
            writeAtPos(result, 38, 4, "...");
        }

        return result;
    }

    /**
     * This method generates a cloud in th cli
     *
     * @param cloud the generated cloud
     * @param id    the cloud's id
     * @return the generated cloud string
     */
    private String[] generateCloud(ReducedCloud cloud, int id) {
        String[] result = generateSquare(16, 5);
        writeAtPos(result, 4, 1, "Cloud " + id);
        int startPos = 3;

        for (PawnColor p : cloud.getStudentsList()) {
            writeAtPos(result, startPos, 3, getColor(p) + "█" + ColorCli.RESET);
            startPos = startPos + 3;
        }
        return result;
    }

    /**
     * This method generates an assistant in the cli
     *
     * @param assistant the generated assistant
     * @return the generated assistant string
     */
    private String[] generateAssistant(ReducedAssistant assistant) {
        String[] result = generateSquare(8, 5);
        writeAtPos(result, 1, 1, ColorCli.BLUE_BOLD + "" + assistant.getId() + "" + ColorCli.RESET);
        writeAtPos(result, 2, 2, "POS:");
        writeAtPos(result, 3, 3, "+" + assistant.getMotherNaturePosShift());
        return result;
    }

    /**
     * This method generates a character in the cli
     *
     * @param character the generated character
     * @return the generated character string
     */
    private String[] generateCharacter(ReducedCharacter character) {
        String[] result = generateSquare(11, 7);
        writeAtPos(result, 1, 1, ColorCli.GREEN_BOLD + "" + character.getId() + "" + ColorCli.RESET);
        writeAtPos(result, 2, 2, "COST: " + character.getInitialCost());

        if (character.isUsed()) {
            writeAtPos(result, 8, 1, ColorCli.YELLOW_BOLD + "█" + ColorCli.RESET);
        }

        if (character.getId() == 1 || character.getId() == 7 || character.getId() == 11) {
            int pos = 0;
            for (PawnColor p : character.getCardStudentsList()) {
                writeAtPos(result, characterStudentsPositions[pos][0], characterStudentsPositions[pos][1], getColorBg(p) + " " + (pos + 1) + " " + ColorCli.RESET);
                pos++;
            }
        } else if (character.getId() == 5) {
            writeAtPos(result, 2, 4, "Forbid");
            writeAtPos(result, 2, 5, "Cards:" + character.getNumOfForbidCards());
        }

        return result;
    }

    //------------------------------------------------------------------------------------------------------

    /**
     * This method merges a greater matrix with a smaller one
     *
     * @param matrixToAdd greater matrix
     * @param x           position in the x axis
     * @param y           position in the y axis
     * @param matrix2     smaller matrix
     */
    private void mergeMatrix(String[] matrixToAdd, int x, int y, String[] matrix2) {
        for (String s : matrix2) {
            writeAtPos(matrixToAdd, x, y, s);
            y++;
        }
    }

    /**
     * Writes text at a given position in a matrix
     *
     * @param matrix the matrix
     * @param x      the position in the x axis
     * @param y      the position in the y axis
     * @param text   the text to be written
     */
    private void writeAtPos(String[] matrix, int x, int y, String text) {
        String[] result = matrix;
        int ansiLengthText = 0;
        int ansiLenghtMatrix = 0;
        Pattern pattern = Pattern.compile("\u001B\\[.*?m");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String s = matcher.group(0);
            ansiLengthText += s.length();
        }

        matcher = pattern.matcher(matrix[y]);
        while (matcher.find()) {
            int len = matcher.group(0).length();
            if (matcher.start() < x + len + ansiLenghtMatrix) {
                ansiLenghtMatrix += len;
            }
        }

        StringBuilder stringBuilder = new StringBuilder(result[y]);
        // replace substring from index 5 to index 9
        stringBuilder.replace(x + ansiLenghtMatrix, x + ansiLenghtMatrix + text.length() - ansiLengthText, text);
        result[y] = stringBuilder.toString();
    }

    /**
     * This method generates a square in the cli
     *
     * @param x the position in the x axis
     * @param y the position in the y axis
     * @return the generated square string
     */
    private String[] generateSquare(int x, int y) { //x number of columns, y number of rows
        String[] result = new String[y];
        int i, j;
        for (i = 0; i < y; i++) {
            result[i] = "";
            for (j = 0; j < x; j++) {
                if (i == 0) {
                    if (j == 0) {
                        result[i] += "┌";
                    } else if (j == x - 1) {
                        result[i] += "┐";
                    } else {
                        result[i] += "─";
                    }
                } else if (i == y - 1) {
                    if (j == 0) {
                        result[i] += "└";
                    } else if (j == x - 1) {
                        result[i] += "┘";
                    } else {
                        result[i] += "─";
                    }
                } else {
                    if (j == 0 || j == x - 1) {
                        result[i] += "|";
                    } else {
                        result[i] += " ";
                    }
                }
            }
        }
        return result;
    }

    /**
     * Prints a matrix in the cli
     *
     * @param matrix the printed matrix
     */
    private void printMatrix(String[] matrix) {
        for (String s : matrix) {
            if (s != null) {
                System.out.println(s);
            }
        }
    }

    /**
     * @param p the pawn color
     * @return the pawn color string
     */
    private String getColor(PawnColor p) {
        String color = "";
        switch (p) {
            case RED:
                color = ColorCli.RED_BOLD.toString();
                break;
            case GREEN:
                color = ColorCli.GREEN_BOLD.toString();
                break;
            case BLUE:
                color = ColorCli.BLUE_BOLD.toString();
                break;
            case PINK:
                color = ColorCli.PINK_BOLD.toString();
                break;
            case YELLOW:
                color = ColorCli.YELLOW_BOLD.toString();
                break;
        }
        return color;
    }

    /**
     * @param t the tower color
     * @return the tower color string
     */
    private String getColor(TowerColor t) {
        String color = "";
        if (t != null) {
            switch (t) {
                case WHITE:
                    color = ColorCli.WHITE_BOLD.toString();
                    break;
                case BLACK:
                    color = ColorCli.BLACK_BOLD.toString();
                    break;
                case GRAY:
                    color = ColorCli.GRAY_BOLD.toString();
                    break;
            }
        }
        return color;
    }

    /**
     * @param p the pawn color
     * @return the pawn color string obtained from the background
     */
    private String getColorBg(PawnColor p) {
        String color = "";
        switch (p) {
            case RED:
                color = ColorCli.RED_BG.toString();
                break;
            case GREEN:
                color = ColorCli.GREEN_BG.toString();
                break;
            case BLUE:
                color = ColorCli.BLUE_BG.toString();
                break;
            case PINK:
                color = ColorCli.PINK_BG.toString();
                break;
            case YELLOW:
                color = ColorCli.YELLOW_BG.toString();
                break;
        }
        return color;
    }
}
