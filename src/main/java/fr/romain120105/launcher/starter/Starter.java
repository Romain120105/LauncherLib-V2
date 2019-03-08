package fr.romain120105.launcher.starter;

import fr.romain120105.launcher.Game;
import fr.romain120105.launcher.auth.Credential;
import fr.romain120105.launcher.starter.command.StarterCommandBuilder;
import fr.romain120105.launcher.starter.helper.ProcessStarter;

import java.util.*;

public class Starter
{
    private Game game;
    private Ram ram;
    private Credential credential;
    
    public Starter(final Game game, final Ram ram, final Credential credential) {
        this.game = game;
        this.ram = ram;
        this.credential = credential;
    }
    
    public void launch() {
        final StarterCommandBuilder builder = new StarterCommandBuilder(this.game, this.ram, this.credential);
        final ArrayList<String> command = builder.build();
        final ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(this.game.getFolder().getGameFolder());
        processBuilder.redirectErrorStream(true);
        for(String c : command){
            System.out.print(c);
        }
        System.out.println();
        final ProcessStarter processStarter = new ProcessStarter(processBuilder);
        try {
            processStarter.launch();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
