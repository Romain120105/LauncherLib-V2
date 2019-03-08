package fr.romain120105.launcher;

import fr.romain120105.launcher.utils.MinecraftFolder;

import java.io.File;

public class DefaultMinecraftFolder extends MinecraftFolder {

    public DefaultMinecraftFolder(){
        File gameFolder = new File(System.getenv("APPDATA"), ".Test");

        this.setGameFolder(gameFolder);
        this.setAssetsFolder(new File(gameFolder, "assets"));
        this.setClientJarFile(new File(gameFolder, "minecraft.jar"));
        this.setNativesFolder(new File(gameFolder, "natives"));
        this.setLibrariesFolder(new File(gameFolder, "libraries"));
        this.setModsFolder(new File(gameFolder, "mods"));

    }

}
