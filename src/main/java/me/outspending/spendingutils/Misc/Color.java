package me.outspending.spendingutils.Misc;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    public static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String formatHexColorCodes(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            string = string.replace(matcher.group(), "" + ChatColor.getByChar(matcher.group()));
        }
        return string;
    }
}
