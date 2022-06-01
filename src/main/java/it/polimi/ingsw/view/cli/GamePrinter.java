package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.client.ReducedCloud;
import it.polimi.ingsw.model.client.ReducedDashboard;
import it.polimi.ingsw.model.client.ReducedIsland;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePrinter {
    private ClientGameModel clientGameModel;
    private final int[][] islandPositions = {{5,12},{31,6},{57,2},{83,2},{109,2},{135,6},{161,12},{135,18},{109,22},{83,22},{57,22},{31,18}};
    private final int[][] cloudPositions = {{80,12},{95,12},{87,17}};
    private final int[][] dashboardPositions = {{50,33},{100,33},{5,33},{150,33}};
    private final int[][] dashboardEntrancePositions = {{2,1},{6,1},{2,2},{6,2},{2,3},{6,3},{2,4},{6,4},{2,5},{6,5}};
    private final int[][] thrownAssistantPositions = {{50,33},{100,33},{5,33},{150,33}};

    public void print(ClientGameModel clientGameModel){ //this class is called by the Cli
        //String[] canvas = generateCanvas(130,20," ");
        String[] canvas = generateSquare(189,50);

        /*String[] matrix1 = generateSquare(15,4);
        writeAtPos(matrix1,4,2,ColorCli.RED_BOLD+"HEY!"+ColorCli.RESET);
        printMatrix(matrix1);

        String[] matrix2 = generateSquare(29,6);
        writeAtPos(matrix2,3,1,ColorCli.RED_BOLD+"ciao questa è una prova"+ColorCli.RESET);
        writeAtPos(matrix2,3,3,"sembra funzionare tutto!");

        String[] matrix3 = generateSquare(50,4);
        writeAtPos(matrix3,2,1,ColorCli.BLUE+"Sono diventato pazzo "+ColorCli.PINK_BOLD+"con questi colori!!"+ColorCli.RESET);

        mergeMatrix(canvas, 4,5, matrix1);
        mergeMatrix(canvas, 30, 7, matrix2);
        mergeMatrix(canvas, 70,7, matrix3);

        printMatrix(canvas);*/

        Island island = new Island();
        List<Student> studentsList= island.getStudentsList();
        studentsList.add(new Student(PawnColor.RED));
        studentsList.add(new Student(PawnColor.RED));
        studentsList.add(new Student(PawnColor.BLUE));
        studentsList.add(new Student(PawnColor.GREEN));
        //studentsList.add(new Student(PawnColor.PINK));
        studentsList.add(new Student(PawnColor.YELLOW));
        island.setWeight(2);
        island.getTowersList().add(new Tower(TowerColor.BLACK));
        //island.setForbidCards(4);


        ReducedIsland reducedIsland = new ReducedIsland(island);
        boolean mn;
        int i;
        for (i = 0; i<12;i++){
            if(i == 1) {mn =true;}
            else {mn=false;}
            String[] generatedIsland = generateIsland(reducedIsland,i+1,mn);
            mergeMatrix(canvas,islandPositions[i][0],islandPositions[i][1],generatedIsland);
        }

        Cloud cloud = new Cloud();
        List<Student> studentList = cloud.getStudents();
        studentList.add(new Student(PawnColor.RED));
        studentList.add(new Student(PawnColor.BLUE));
        studentList.add(new Student(PawnColor.YELLOW));

        for (i = 0; i<3;i++){
            String[] generatedCloud = generateCloud(new ReducedCloud(cloud),i+1);
            mergeMatrix(canvas,cloudPositions[i][0],cloudPositions[i][1],generatedCloud);
        }

        Dashboard dashboard = new Dashboard();
        List<Student> redList = dashboard.getHallStudentsListByColor(PawnColor.RED);
        List<Student> greenList = dashboard.getHallStudentsListByColor(PawnColor.GREEN);
        List<Student> blueList = dashboard.getHallStudentsListByColor(PawnColor.BLUE);

        redList.add(new Student(PawnColor.RED));
        redList.add(new Student(PawnColor.RED));
        greenList.add(new Student(PawnColor.GREEN));
        blueList.add(new Student(PawnColor.BLUE));

        List<Student> entranceList = dashboard.getEntranceList();
        entranceList.add(new Student(PawnColor.RED));
        entranceList.add(new Student(PawnColor.YELLOW));
        entranceList.add(new Student(PawnColor.GREEN));
        entranceList.add(new Student(PawnColor.BLUE));
        entranceList.add(new Student(PawnColor.YELLOW));

        List<Professor> professorList = dashboard.getProfessorsList();
        professorList.add(new Professor(PawnColor.BLUE));
        professorList.add(new Professor(PawnColor.RED));

        List<Tower> towerList = dashboard.getTowersList();
        towerList.add(new Tower(TowerColor.BLACK));
        towerList.add(new Tower(TowerColor.BLACK));

        for (i = 0; i<4;i++){
            String[] generatedDashboard = generateDashboard(new ReducedDashboard(dashboard),"Marco",TowerColor.BLACK);
            mergeMatrix(canvas,dashboardPositions[i][0],dashboardPositions[i][1],generatedDashboard);
        }

        printMatrix(canvas);
    }

    private String[] generateIsland(ReducedIsland island, int id, boolean motherNature){
        String[] result = generateSquare(23,9);
        writeAtPos(result,7,1,"Island "+id);

        Map<PawnColor,Integer> colorsMap = new HashMap<>();
        for (PawnColor p : island.getStudentsList()){
            Integer existingNum = colorsMap.get(p);
            if(existingNum == null){
                colorsMap.put(p,1);
            }
            else {
                colorsMap.put(p,++existingNum);
            }
        }

        //drawing students
        int startPos = 3;
        for (PawnColor p : PawnColor.values()){
            if(colorsMap.get(p)!=null) {
                writeAtPos(result, 2, startPos, getColor(p) + "█" + ColorCli.RESET + ": " + colorsMap.get(p));
                startPos++;
            }
        }

        //drawing weight
        writeAtPos(result,18 , 1, "("+island.getWeight()+")");

        //drawing towers
        if (island.getTowerColor()!=null){
            writeAtPos(result, 8, 3,"Domain: " +getColor(island.getTowerColor()) + island.getTowerColor() + ColorCli.RESET);
            writeAtPos(result, 8, 4,"█ Towers: " + island.getWeight());
        }

        //drawing forbid cards
        if(island.getForbidCards() != 0){
            writeAtPos(result, 8, 5,"Forb.cards: " + island.getForbidCards());
        }

        //drawing mother nature
        if(motherNature){
            writeAtPos(result, 11, 6,ColorCli.RED + "█  Mother"+ ColorCli.RESET);
            writeAtPos(result, 10, 7,ColorCli.RED + "███ Nature"+ ColorCli.RESET);
        }

        return result;
    }

    private String[] generateDashboard(ReducedDashboard dashboard, String playerName, TowerColor towerColor){
        String[] result = generateSquare(35,7);
        writeAtPos(result,2,0," "+ playerName+ " ");

        int pos = 0;
        int i;

        for (PawnColor p : dashboard.getEntranceList()){
            writeAtPos(result, dashboardEntrancePositions[pos][0], dashboardEntrancePositions[pos][1], getColorBg(p)+" " +(pos+1)+" " + ColorCli.RESET);
            pos++;
        }

        //first separator
        for (i = 1; i<6; i++){
            writeAtPos(result, 10, i, "|");
        }

        pos = 1;
        for (PawnColor p : PawnColor.values()){
            writeAtPos(result, 12, pos, getColor(p) + "█" + ColorCli.RESET + ": " + dashboard.getStudentsHall().get(p));

            if(dashboard.getProfessorsList().contains(p)){
                writeAtPos(result, 20, pos, getColor(p) + "██" + ColorCli.RESET);
            }
            pos++;
        }

        //second separator
        for (i = 1; i<6; i++){
            writeAtPos(result, 24, i, "|");
        }

        writeAtPos(result, 26, 2, "Towers:");
        writeAtPos(result, 27, 3, getColor(towerColor) + towerColor.toString() + ColorCli.RESET);
        writeAtPos(result, 29, 4, getColor(towerColor) + dashboard.getTowerNumber() + ColorCli.RESET);

        return result;
    }

    private String[] generateCloud(ReducedCloud cloud,int id){
        String[] result = generateSquare(13,5);
        writeAtPos(result,3,1,"Cloud "+id);
        int startPos = 3;

        for (PawnColor p : cloud.getStudentsList()) {
            writeAtPos(result, startPos, 3, getColor(p) + "█" + ColorCli.RESET);
            startPos = startPos+3;
        }
        return result;
    }

    private void mergeMatrix(String[] matrixToAdd, int x, int y, String[] matrix2){
        for (String s : matrix2){
            writeAtPos(matrixToAdd,x,y,s);
            y++;
        }
    }

    private void writeAtPos(String[] matrix, int x, int y, String text){
        String result[] = matrix;
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
            if(matcher.start() < x+len+ansiLenghtMatrix){
                ansiLenghtMatrix += len;
            }
        }

        StringBuilder stringBuilder = new StringBuilder(result[y]);
        // replace substring from index 5 to index 9
        stringBuilder.replace(x+ansiLenghtMatrix,x+ansiLenghtMatrix+text.length()-ansiLengthText,text);
        result[y] = stringBuilder.toString();
    }

    private String[] generateSquare(int x, int y){ //x number of columns, y number of rows
        String result[] = new String[y];
        int i,j;
        for (i=0; i<y; i++){
            result[i]="";
            for (j=0;j<x; j++){
                if(i==0){
                    if(j==0){
                        result[i]+="┌";
                    }
                    else if(j==x-1){
                        result[i]+="┐";
                    }
                    else {
                        result[i]+="─";
                    }
                }

                else if(i==y-1){
                    if(j==0){
                        result[i]+="└";
                    }
                    else if(j==x-1){
                        result[i]+="┘";
                    }
                    else {
                        result[i]+="─";
                    }
                }

                else {
                    if(j==0 || j==x-1){
                        result[i]+="|";
                    }
                    else {
                        result[i]+=" ";
                    }
                }
            }
        }
        return result;
    }

    private String[] generateCanvas(int x, int y, String filler){
        String result[] = new String[y];
        int i,j;
        for (i=0; i<y; i++){
            result[i]="";
            for (j=0;j<x;j++){
            result[i]+=filler;
            }
        }
        return result;
    }

    private void printMatrix(String[] matrix){
        for (String s : matrix){
            if(s!=null){
                System.out.println(s);
            }
        }
    }

    private String getColor(PawnColor p){
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

    private String getColor(TowerColor t){
        String color = "";
        switch (t){
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
        return color;
    }

    private String getColorBg(PawnColor p){
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
