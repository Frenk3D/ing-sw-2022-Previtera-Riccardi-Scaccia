package it.polimi.ingsw.view.cli;

/**
 * This enum class contains all colors used in Cli.
 */
public enum ColorCli {
    //Color end string, color reset
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),

    // Regular Colors. Normal color, no bold, background color etc., for the islands etc...
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN  "\u001B[32m"
    BLUE("\033[0;34m"),     // BLUE

    // Bold for pawns and towers
    YELLOW_BOLD("\033[1;33m"), // YELLOW
    RED_BOLD("\033[1;31m"),
    GREEN_BOLD("\033[1;32m"),
    PINK_BOLD("\033[0;35m"),
    BLUE_BOLD("\033[1;34m"),

    //background colors
    YELLOW_BG("\033[43m"),
    RED_BG("\033[41m"),
    GREEN_BG("\033[42m"),
    PINK_BG("\033[45m"),
    BLUE_BG("\033[44m"),

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
