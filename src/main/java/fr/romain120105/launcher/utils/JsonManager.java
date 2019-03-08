package fr.romain120105.launcher.utils;

import com.google.gson.*;

public class JsonManager
{
    private static Gson gson;
    
    public static Gson init() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    }
    
    public JsonManager() {
        JsonManager.gson = init();
    }
    
    public static Gson getGson() {
        return JsonManager.gson;
    }
    
    public static void setGson(final Gson gson) {
        JsonManager.gson = gson;
    }
}
