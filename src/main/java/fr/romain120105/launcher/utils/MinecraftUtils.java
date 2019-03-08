package fr.romain120105.launcher.utils;

public class MinecraftUtils
{
    public static boolean isMinimum1_8(final String version) {
        final String[] v = version.split("\\.");
        if (v[1].length() >= 1 && isInteger(v[1])) {
            final int i = new Integer(v[1]);
            return i >= 8;
        }
        return false;
    }
    
    public static boolean isMaximum1_5_2(final String version) {
        final String[] v = version.split("\\.");
        if (v[1].length() >= 1 && isInteger(v[1])) {
            final int i = new Integer(v[1]);
            return i <= 5;
        }
        return false;
    }
    
    public static boolean is1_7_2_Lower(final String version) {
        final String[] v = version.split("\\.");
        if (v[1].length() < 1 || !isInteger(v[1])) {
            return false;
        }
        final int i = new Integer(v[1]);
        if (i == 6) {
            return true;
        }
        if (i > 7 && (i < 5 || i > 7)) {
            return false;
        }
        if (v.length == 3 && isInteger(v[2])) {
            final int e = new Integer(v[2]);
            return (i == 5 && e >= 3) || (i == 7 && e <= 2);
        }
        return v.length != 2 || i != 5;
    }
    
    public static boolean isInteger(final String s) {
        try {
            new Integer(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
