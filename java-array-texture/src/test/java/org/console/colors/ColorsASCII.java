package org.console.colors;

import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColorsASCII {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BLACK = "\u001B[30m";

    public static final String ANSI_RED = "\u001B[31m";

    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_BLUE = "\u001B[34m";

    public static final String ANSI_MAGENTA = "\u001B[35m";

    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_GRAY = "\u001B[90m";

    public static final String ANSI_BRIGHT_RED = "\u001B[91m";

    public static final String ANSI_BRIGHT_GREEN = "\u001B[92m";

    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";

    public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";

    public static final String ANSI_BRIGHT_MAGENTA = "\u001B[95m";

    public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";

    public static final String ANSI_BRIGHT_WHITE = "\u001B[97m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static final String ANSI_MAGENTA_BACKGROUND = "\u001B[45m";

    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String ANSI_GRAY_BACKGROUND = "\u001B[100m";

    public static final String ANSI_BRIGHT_RED_BACKGROUND = "\u001B[101m";

    public static final String ANSI_BRIGHT_GREEN_BACKGROUND = "\u001B[102m";

    public static final String ANSI_BRIGHT_YELLOW_BACKGROUND = "\u001B[103m";

    public static final String ANSI_BRIGHT_BLUE_BACKGROUND = "\u001B[104m";

    public static final String ANSI_BRIGHT_MAGENTA_BACKGROUND = "\u001B[105m";

    public static final String ANSI_BRIGHT_CYAN_BACKGROUND = "\u001B[106m";

    public static final String ANSI_BRIGHT_WHITE_BACKGROUND = "\u001B[107m";

    public static final char ANSI_CHAR_BLOCK_0 = ' ';

    public static final char ANSI_CHAR_BLOCK_1 = '░';

    public static final char ANSI_CHAR_BLOCK_2 = '▒';

    public static final char ANSI_CHAR_BLOCK_3 = '▓';

    public static final char ANSI_CHAR_BLOCK_4 = '█';

    public static Vector3i stringToRGB(String c) {
        switch (c) {
            case ANSI_BLACK: case ANSI_BLACK_BACKGROUND: default:
                return new Vector3i(0, 0, 0);
            case ANSI_RED: case ANSI_RED_BACKGROUND:
                return new Vector3i(170, 0, 0);
            case ANSI_GREEN: case ANSI_GREEN_BACKGROUND:
                return new Vector3i(0, 170, 0);
            case ANSI_YELLOW: case ANSI_YELLOW_BACKGROUND:
                return new Vector3i(170, 85, 0);
            case ANSI_BLUE: case ANSI_BLUE_BACKGROUND:
                return new Vector3i(0, 0, 170);
            case ANSI_MAGENTA: case ANSI_MAGENTA_BACKGROUND:
                return new Vector3i(170, 0, 170);
            case ANSI_CYAN: case ANSI_CYAN_BACKGROUND:
                return new Vector3i(0, 170, 170);
            case ANSI_WHITE: case ANSI_WHITE_BACKGROUND:
                return new Vector3i(170, 170, 170);
            case ANSI_GRAY: case ANSI_GRAY_BACKGROUND:
                return new Vector3i(85, 85, 85);
            case ANSI_BRIGHT_RED: case ANSI_BRIGHT_RED_BACKGROUND:
                return new Vector3i(255, 85, 85);
            case ANSI_BRIGHT_GREEN: case ANSI_BRIGHT_GREEN_BACKGROUND:
                return new Vector3i(85, 255, 85);
            case ANSI_BRIGHT_YELLOW: case ANSI_BRIGHT_YELLOW_BACKGROUND:
                return new Vector3i(255, 255, 85);
            case ANSI_BRIGHT_BLUE: case ANSI_BRIGHT_BLUE_BACKGROUND:
                return new Vector3i(85, 85, 255);
            case ANSI_BRIGHT_MAGENTA: case ANSI_BRIGHT_MAGENTA_BACKGROUND:
                return new Vector3i(255, 85, 255);
            case ANSI_BRIGHT_CYAN: case ANSI_BRIGHT_CYAN_BACKGROUND:
                return new Vector3i(85, 255, 255);
            case ANSI_BRIGHT_WHITE: case ANSI_BRIGHT_WHITE_BACKGROUND:
                return new Vector3i(255);
        }
    }

    public static List<String> generateListColorsASCII() {
        List<String> l = new ArrayList<>();
        l.add(ANSI_BLACK);
        l.add(ANSI_RED);
        l.add(ANSI_GREEN);
        l.add(ANSI_YELLOW);
        l.add(ANSI_BLUE);
        l.add(ANSI_MAGENTA);
        l.add(ANSI_CYAN);
        l.add(ANSI_WHITE);
        l.add(ANSI_GRAY);
        l.add(ANSI_BRIGHT_RED);
        l.add(ANSI_BRIGHT_GREEN);
        l.add(ANSI_BRIGHT_YELLOW);
        l.add(ANSI_BRIGHT_BLUE);
        l.add(ANSI_BRIGHT_MAGENTA);
        l.add(ANSI_BRIGHT_CYAN);
        l.add(ANSI_BRIGHT_WHITE);
        return l;
    }

    public static List<String> generateListBackgroundASCII() {
        List<String> l = new ArrayList<>();
        l.add(ANSI_BLACK_BACKGROUND);
        l.add(ANSI_RED_BACKGROUND);
        l.add(ANSI_GREEN_BACKGROUND);
        l.add(ANSI_YELLOW_BACKGROUND);
        l.add(ANSI_BLUE_BACKGROUND);
        l.add(ANSI_MAGENTA_BACKGROUND);
        l.add(ANSI_CYAN_BACKGROUND);
        l.add(ANSI_WHITE_BACKGROUND);
        l.add(ANSI_GRAY_BACKGROUND);
        l.add(ANSI_BRIGHT_RED_BACKGROUND);
        l.add(ANSI_BRIGHT_GREEN_BACKGROUND);
        l.add(ANSI_BRIGHT_YELLOW_BACKGROUND);
        l.add(ANSI_BRIGHT_BLUE_BACKGROUND);
        l.add(ANSI_BRIGHT_MAGENTA_BACKGROUND);
        l.add(ANSI_BRIGHT_CYAN_BACKGROUND);
        l.add(ANSI_BRIGHT_WHITE_BACKGROUND);
        return l;
    }

    public static List<Character> generateListCharacters() {
        List<Character> l = new ArrayList<>();
        l.add(ANSI_CHAR_BLOCK_0);
        l.add(ANSI_CHAR_BLOCK_1);
        l.add(ANSI_CHAR_BLOCK_2);
        l.add(ANSI_CHAR_BLOCK_3);
        l.add(ANSI_CHAR_BLOCK_4);
        return l;
    }

    public static float charToAlpha(char c) {
        switch (c) {
            case ANSI_CHAR_BLOCK_0: default: // ' '
                return 0.0f;
            case ANSI_CHAR_BLOCK_1: // '░'
                return 0.25f;
            case ANSI_CHAR_BLOCK_2: // '▒'
                return 0.50f;
            case ANSI_CHAR_BLOCK_3: // '▓'
                return 0.75f;
            //case ANSI_CHAR_BLOCK_4: // '█'
            //    return 1.0f;
        }
    }

    public static Vector3i combineColors(String back, String front, char c) {
        Vector3i colorFront = stringToRGB(front);
        Vector3i colorBack = stringToRGB(back);
        float a = charToAlpha(c);
        if (a == 1.0f) {
            return colorFront;
        } else {
            int nr = colorBack.x - (int)((colorBack.x - colorFront.x) * a);
            int ng = colorBack.y - (int)((colorBack.y - colorFront.y) * a);
            int nb = colorBack.z - (int)((colorBack.z - colorFront.z) * a);
            return new Vector3i(nr, ng, nb);
        }
    }

    public static HashMap<Vector3i, String> generateAllColors() {
        HashMap<Vector3i, String> map = new HashMap<>();
        for (String background : generateListBackgroundASCII()) {
            for (String front : generateListColorsASCII()) {
                for (char c : generateListCharacters()) {
                    String color = background + front + c;
                    map.put(combineColors(background, front, c), color);
                }
            }
        }
        return map;
    }

    public static String castRGB(int in, HashMap<Vector3i, String> availableColors) {
        final int r = (in >> 16) & 0xFF;
        final int g = (in >> 8) & 0xFF;
        final int b = (in) & 0xFF;

        String color = "";
        double actualDistance = Double.MAX_VALUE;
        for (var e : availableColors.entrySet()) {
            var v = e.getKey();
            double dist = (r * v.x + g * v.y + b * v.z) / (Math.sqrt(r * r + g * g + b * b) * v.length());
            if (actualDistance > dist) {
                actualDistance = dist;
                color = e.getValue();
            }
        }

        return color;
    }

    public static void main(String[] args) {
        System.out.println(ANSI_BRIGHT_BLUE + ANSI_BRIGHT_RED_BACKGROUND + "░▒▓█\n" + ANSI_RESET);

        /*StringBuilder out = new StringBuilder();
        for (String background : generateListBackgroundASCII()) {
            for (String front : generateListColorsASCII()) {
                for (char c : generateListCharacters()) {
                    out.append(background).append(front).append(c);
                }
            }
            out.append('\n');
        }
        out.append(ANSI_RESET).append('\n');
        System.out.println(out);*/
        var colors = generateAllColors();
        System.out.printf("Número de colores disponibles: %d", colors.size());
    }

}
