package fr.romain120105.test;

import fr.romain120105.launcher.Game;
import fr.romain120105.launcher.Launcher;
import fr.romain120105.launcher.Version;
import fr.romain120105.launcher.auth.Auth;
import fr.romain120105.launcher.auth.Credential;
import fr.romain120105.launcher.auth.UserIdentifiers;
import fr.romain120105.launcher.starter.Ram;


public class Test {

    private static Launcher launcher;

    public static void main(String... args){

        launcher = new Launcher.Builder(Auth.MINECRAFT_AUTH_AGENT).setLauncherName("Test").build();


        Credential credential = null;

        try {
            credential = Auth.autorize(launcher, new UserIdentifiers("email", "password"));

        }catch(Exception e){
            e.printStackTrace();
        }

        Auth.createAuthAgent("Custom" , "lienversleserveur")



        Version version = new Version.Builder("1.12.2").setForgeVersion("14.23.5.2814").setVersionType(Version.VersionType.FORGE).build();

        Game game = version.update(launcher);


        version.launch(game, credential);




    }

}
