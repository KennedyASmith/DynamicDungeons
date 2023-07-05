package com.kennedysmithjava.dynamicdungeons.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    public static String get(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> get(List<String> strings){
        List<String> newList = new ArrayList<>();
        strings.forEach(s -> newList.add(Color.get(s)));
        return newList;
    }

    public static String getGradient(String message, String color1, String color2) {
        int length = message.length();
        int colorCount = length - 1;

        int r1 = Integer.parseInt(color1.substring(0, 2), 16);
        int g1 = Integer.parseInt(color1.substring(2, 4), 16);
        int b1 = Integer.parseInt(color1.substring(4, 6), 16);

        int r2 = Integer.parseInt(color2.substring(0, 2), 16);
        int g2 = Integer.parseInt(color2.substring(2, 4), 16);
        int b2 = Integer.parseInt(color2.substring(4, 6), 16);

        StringBuilder coloredMessage = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = message.charAt(i);

            if (i < colorCount) {
                double progress = (double) i / colorCount;

                int r = (int) (r1 + (r2 - r1) * progress);
                int g = (int) (g1 + (g2 - g1) * progress);
                int b = (int) (b1 + (b2 - b1) * progress);

                String colorCode = String.format("#%02X%02X%02X", r, g, b);
                coloredMessage.append(net.md_5.bungee.api.ChatColor.of(colorCode));
            }

            coloredMessage.append(c);
        }

        return coloredMessage.toString();
    }

    public static String strip(String msg){
        return ChatColor.stripColor(Color.get(msg));
    }

    private static final char COLOR_CHAR = '&';
    private static final Pattern START_WITH_COLORS = Pattern.compile("(?i)^(" + COLOR_CHAR + "[0-9A-FK-ORX])+");
    private static final Pattern COLOR = Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-ORX]");

    /**
     * Breaks a raw string up into a series of lines. Words are wrapped using
     * spaces as decimeters and the newline character is respected.
     *
     * @param rawString The raw string to break.
     * @param maxLineLength The length of a line of text.
     * @return A list of word-wrapped lines.
     */
    public static List<String> wordWrap(String rawString, int maxLineLength) {
        return wordWrap(rawString, maxLineLength, maxLineLength);
    }

    /**
     * Breaks a raw string up into a series of lines. Words are wrapped using
     * spaces as decimeters and the newline character is respected.
     *
     * @param rawString The raw string to break.
     * @param maxLineLength The length of a line of text, not counting color/format characters.
     * @param criticalLineLength The maximum length of a line of text, counting color/format characters.
     * @return A list of word-wrapped lines.
     */
    public static List<String> wordWrap(String rawString, int maxLineLength, int criticalLineLength) {
        if (maxLineLength > criticalLineLength) maxLineLength = criticalLineLength;
        // A null string is a single line
        if (rawString == null) {
            return Arrays.asList("");
        }

        // A string shorter than the lineWidth is a single line
        if (rawString.length() <= maxLineLength && !rawString.contains("\n")) {
            return Arrays.asList(rawString);
        }

        char[] rawChars = (rawString + ' ').toCharArray(); // add a trailing space to trigger pagination
        StringBuilder word = new StringBuilder();
        StringBuilder line = new StringBuilder(maxLineLength);
        String colorsWord = "";
        String colors = "";
        boolean first = false;
        List<String> lines = new LinkedList<>();
        int wordLength = 0;
        int lineLength = 0;

        for (int i = 0; i < rawChars.length; i++) {
            char c = rawChars[i];

            // skip chat color modifiers
            if (c == COLOR_CHAR) {
                Matcher matcher = START_WITH_COLORS.matcher(rawString.substring(i));
                if (matcher.find()) {
                    String rawColors = matcher.group();
                    String oldColors = colors;
                    colors = appendRawColorString(colors, rawColors);
                    String toAdd = getColorDifference(oldColors, colors);

                    if (colors.length() >= criticalLineLength) { // weird case : the formatting and word is longer than a line
                        colors = "";
                    }else {
                        word.append(toAdd);
                    }
                    i += rawColors.length() - 1; // eat all color characters minus 1 (first § already processed)
                }else {
                    word.append(c); // unknown color code: we copy it
                }
            }else if (c == ' ' || c == '\n') {
                if (lineLength == 0 && (wordLength > maxLineLength || word.length() > criticalLineLength)) { // special case: extremely long word begins a line
                    lines.addAll(splitColoredString(word.toString(), wordLength, maxLineLength, criticalLineLength, colorsWord));
                }else {
                    int totalLength = (lineLength == 0 ? 0 : lineLength + 1) + wordLength;
                    int totalLengthC = (lineLength == 0 ? 0 : line.length() + 1) + word.length();
                    if ((totalLength == maxLineLength && totalLengthC <= criticalLineLength) || (totalLengthC == criticalLineLength && totalLength <= maxLineLength)) { // Line exactly the correct length...newline
                        if (lineLength > 0) line.append(' ');
                        line.append(word);
                        lines.add(line.toString());
                        line = new StringBuilder(maxLineLength);
                        lineLength = 0;
                        first = true;
                    }else if (totalLength > maxLineLength || totalLengthC > criticalLineLength) { // Line too long...break the line
                        lines.add(line.toString());
                        if (word.length() >= maxLineLength || word.length() >= criticalLineLength) {
                            lines.addAll(splitColoredString(word.toString(), wordLength, maxLineLength, criticalLineLength, colorsWord));
                            lineLength = 0;
                            line = new StringBuilder(maxLineLength);
                        }else {
                            int textIndex = getFirstTextIndex(word.toString());
                            line = new StringBuilder(maxLineLength); // start the line with the word
                            line.append(word.substring(textIndex));
                            lineLength = wordLength;
                            line.insert(0, appendRawColorString(colorsWord, word.substring(0, textIndex)));
                        }
                    }else {
                        if (line.length() > 0) {
                            if (!first) {
                                line.append(' ');
                                lineLength++;
                            }
                        }
                        first = false;
                        line.append(word);
                        lineLength += wordLength;
                    }
                }
                colorsWord = colors;
                word = lineLength == 0 ? new StringBuilder(colors) : new StringBuilder();
                wordLength = 0;

                if (c == '\n') { // Newline forces the line to flush
                    lines.add(line.toString());
                    line = new StringBuilder(maxLineLength);
                    lineLength = 0;
                    word.append(colors);
                    first = true;
                }
            }else {
                word.append(c);
                wordLength++;
            }
        }

        if (line.length() > 0) { // Only add the last line if there is anything to add
            lines.add(line.toString());
        }

        return lines;
    }

    private static String appendRawColorString(String original, String appended) {
        StringBuilder builder = new StringBuilder(original);
        StringBuilder hexBuilder = null;
        for (int colorIndex = 1; colorIndex < appended.length(); colorIndex += 2) {
            char cc = appended.charAt(colorIndex);
            if (hexBuilder != null) {
                hexBuilder.append('§').append(cc);
                if (hexBuilder.length() == 14) { // end of the color
                    builder = hexBuilder; // as it is a color, previous formatting is lost
                    hexBuilder = null;
                }
            }else if (cc == 'x') {
                if (colorIndex + 2 * 6 < appended.length()) {
                    hexBuilder = new StringBuilder("§x");
                }
            }else {
                ChatColor sub = ChatColor.getByChar(cc);
                if (sub != null) {
                    if (sub == ChatColor.RESET) {
                        builder.setLength(0); // reset -> empty previous format and do not copy the reset code
                        continue;
                    }else if (sub.isColor()) {
                        builder.setLength(0); // color -> empty previous format and copy color
                    }
                    builder.append(sub.toString());
                }else builder.append('§').append(cc); // unknown color character
            }
        }
        return builder.toString();
    }

    private static String getColorDifference(String oldColors, String newColors) {
        return newColors.startsWith(oldColors) ? newColors.substring(oldColors.length()) : newColors;
    }

    private static List<String> splitColoredString(String string, int stringLength, int maxLength, int criticalLength, String startColors) {
        List<String> split = new ArrayList<>();
        while (stringLength >= maxLength && string.length() >= criticalLength) {
            int colorIndex = string.indexOf('§');
            if (colorIndex > maxLength) { // before color -> only text
                split.add(string.substring(0, maxLength));
                string = startColors + string.substring(maxLength);
            }else {
                int length = 0;
                int previousIndex = -2;
                String colors = startColors;
                Matcher matcher = COLOR.matcher(string);
                while (matcher.find()) {
                    length += matcher.start() - previousIndex - 2;
                    if (length > maxLength || matcher.start() > criticalLength) {
                        split.add(string.substring(0, previousIndex));
                        string = colors + string.substring(previousIndex);
                        startColors = colors;
                    }
                    colors = appendRawColorString(colors, matcher.group());
                    previousIndex = matcher.start();
                    stringLength -= length;
                }
            }
        }
        split.add(string);
        return split;
    }

    private static int getFirstTextIndex(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (i % 2 == 0) {
                if (c != COLOR_CHAR) return i;
            }else {
                if (net.md_5.bungee.api.ChatColor.ALL_CODES.indexOf(c) == -1) return i - 1;
            }
        }
        return string.length();
    }

}
