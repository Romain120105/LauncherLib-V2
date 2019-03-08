package fr.romain120105.launcher.starter.utils;

public class JavaUtils
{
    public static String getJavaPath() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return "\"" + System.getProperty("java.home") + "\\bin\\java" + "\"";
        }
        return String.valueOf(System.getProperty("java.home")) + "/bin/java";
    }
}
