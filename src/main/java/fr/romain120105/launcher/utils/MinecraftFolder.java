package fr.romain120105.launcher.utils;

import java.io.*;
import java.net.*;

public class MinecraftFolder
{
    private File gameFolder;
    private File modsFolder;
    private File librariesFolder;
    private File nativesFolder;
    private File assetsFolder;
    private File clientJarFile;
    private String clientJarSHA1;
    private URL clientJarURL;
    
    public MinecraftFolder(final File gameFolder, final File assetsFolder, final File librariesFolder, final File nativesFolder, final File clientJarFile, final URL clientJarURL, final String clientJarSHA1) {
        this.gameFolder = gameFolder;
        this.librariesFolder = librariesFolder;
        this.nativesFolder = nativesFolder;
        this.assetsFolder = assetsFolder;
        this.clientJarFile = clientJarFile;
        this.clientJarURL = clientJarURL;
        this.clientJarSHA1 = clientJarSHA1;
    }

    public MinecraftFolder(){

    }
    
    public File getGameFolder() {
        return this.gameFolder;
    }
    
    public void setGameFolder(final File gameFolder) {
        this.gameFolder = gameFolder;
    }
    
    public File getModsFolder() {
        return this.modsFolder;
    }
    
    public void setModsFolder(final File modsFolder) {
        this.modsFolder = modsFolder;
    }
    
    public File getLibrariesFolder() {
        return this.librariesFolder;
    }
    
    public void setLibrariesFolder(final File librariesFolder) {
        this.librariesFolder = librariesFolder;
    }
    
    public File getNativesFolder() {
        return this.nativesFolder;
    }
    
    public void setNativesFolder(final File nativesFolder) {
        this.nativesFolder = nativesFolder;
    }
    
    public File getAssetsFolder() {
        return this.assetsFolder;
    }
    
    public void setAssetsFolder(final File assetsFolder) {
        this.assetsFolder = assetsFolder;
    }
    
    public File getClientJarFile() {
        return this.clientJarFile;
    }
    
    public void setClientJarFile(final File clientJarFile) {
        this.clientJarFile = clientJarFile;
    }
    
    public String getClientJarSHA1() {
        return this.clientJarSHA1;
    }
    
    public void setClientJarSHA1(final String clientJarSHA1) {
        this.clientJarSHA1 = clientJarSHA1;
    }
    
    public URL getClientJarURL() {
        return this.clientJarURL;
    }
    
    public void setClientJarURL(final URL clientJarURL) {
        this.clientJarURL = clientJarURL;
    }
}
