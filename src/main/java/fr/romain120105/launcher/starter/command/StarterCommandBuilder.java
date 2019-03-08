package fr.romain120105.launcher.starter.command;

import fr.romain120105.launcher.Game;
import fr.romain120105.launcher.auth.Credential;
import fr.romain120105.launcher.starter.Ram;
import fr.romain120105.launcher.starter.utils.JavaUtils;
import fr.romain120105.launcher.utils.FileUtils;
import java.io.*;
import java.util.*;

public class StarterCommandBuilder
{
    private Game game;
    private Ram ram;
    private Credential credential;
    private ArrayList<String> builder;
    
    public StarterCommandBuilder(final Game game, final Ram ram, final Credential credential) {
        this.game = game;
        this.ram = ram;
        this.builder = new ArrayList<String>();
        this.credential = credential;
    }
    
    public ArrayList<String> build() {
        this.append(JavaUtils.getJavaPath());
        this.append("-XX:-UseAdaptiveSizePolicy");
        this.append("-XX:+UseConcMarkSweepGC");
        this.append("-XX:+CMSIncrementalMode");
        final ArrayList<String> vmArgs = new ArrayList<String>();
        vmArgs.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
        vmArgs.add("-Dfml.ignorePatchDiscrepancies=true");
        vmArgs.add("-Djava.library.path=" + this.game.getFolder().getNativesFolder().getAbsolutePath());
        vmArgs.add(this.ram.getRamArguments());
        this.builder.addAll(vmArgs);
        this.append("-cp");
        final ArrayList<File> files = new ArrayList<File>();
        for (final File file : FileUtils.listFilesForFolder(this.game.getFolder().getLibrariesFolder())) {
            if (!file.isDirectory()) {
                files.add(file);
            }
        }
        files.add(this.game.getFolder().getClientJarFile());
        String classPath = "";
        for (int i = 0; i < files.size(); ++i) {
            classPath = String.valueOf(classPath) + files.get(i).getAbsolutePath() + ((i + 1 == files.size()) ? "" : File.pathSeparator);
        }
        this.append(classPath);
        this.append(this.game.getMainClass());
        this.append("--username=" + this.credential.getSelectedProfile().getName());
        this.append("--accessToken");
        this.append(this.credential.getAccessToken());
        if (this.credential.getClientToken() != null) {
            this.append("--clientToken");
            this.append(this.credential.getClientToken());
        }
        this.append("--version");
        this.append(this.game.getVersion());
        this.append("--gameDir");
        this.append(this.game.getFolder().getGameFolder().getAbsolutePath());
        this.append("--assetsDir");
        this.append(this.game.getFolder().getAssetsFolder().getAbsolutePath());
        this.append("--assetIndex");
        this.append(this.game.getAssetIndex());
        this.append("--userProperties");
        this.append("{}");
        this.append("--uuid");
        this.append(this.credential.getSelectedProfile().getUUID().toString());
        this.append("--userType");
        this.append("legacy");
        System.out.println(this.game.getTweaker());
        if (this.game.getTweaker() != null) {
            this.append("--tweakClass");
            this.append(this.game.getTweaker());
        }
        return this.builder;
    }
    
    public void append(final String t) {
        this.builder.add(t);
    }
}
