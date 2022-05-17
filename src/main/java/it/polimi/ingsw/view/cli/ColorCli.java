package it.polimi.ingsw.view.cli;

/**
 * This class contains all colors used in Cli.
 */
public enum ColorCli {
    //Color end string, color reset
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),

    // Regular Colors. Normal color, no bold, background color etc.
    RED("\033[0;31m"),      // RED
    GREEN("\033[38;5;28m"),    // GREEN  "\u001B[32m"
    BLUE("\033[0;34m"),     // BLUE

    // Bold
    YELLOW_BOLD("\033[1;33m"), // YELLOW
    RED_BOLD("\033[1;31m"),
    GREEN_BOLD("\033[1;32m"),
    PINK_BOLD("\033[35;1m"),
    BLUE_BOLD("\033[1;34m"),
    BLACK_BOLD("\033[1;30m"),
    WHITE_BOLD("\033[37;1m"),
    GRAY_BOLD("\033[30;1m");  //bright black


    private final String code;

    ColorCli(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}