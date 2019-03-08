package fr.romain120105.launcher;

import fr.romain120105.launcher.auth.AuthAgent;
import fr.romain120105.launcher.utils.MinecraftFolder;

public interface Launcher {

    AuthAgent getAuthAgent();

    MinecraftFolder getMinecraftFolder();

    class Builder{

        private AuthAgent authAgent;
        private String launcherName;
        private MinecraftFolder minecraftFolder = new DefaultMinecraftFolder();

        public Builder(AuthAgent authAgent){
            this.authAgent = authAgent;
        }

        public Builder setLauncherName(String launcherName){
            this.launcherName = launcherName;

            return this;
        }


        public AuthAgent getAuthAgent() {
            return authAgent;
        }

        public Builder setMinecraftFolder(MinecraftFolder minecraftFolder) {
            this.minecraftFolder = minecraftFolder;
            return this;
        }

        public MinecraftFolder getMinecraftFolder() {
            return minecraftFolder;
        }

        public Launcher build(){
            return new Launcher() {

                private AuthAgent authAgent;
                private MinecraftFolder minecraftFolder;


                public Launcher set(AuthAgent authAgent, MinecraftFolder minecraftFolder){
                    this.authAgent = authAgent;
                    this.minecraftFolder = minecraftFolder;
                    return this;
                }

                @Override
                public AuthAgent getAuthAgent() {
                    return this.authAgent;
                }

                @Override
                public MinecraftFolder getMinecraftFolder() {
                    return this.minecraftFolder;
                }
            }.set(this.authAgent, this.minecraftFolder);
        }

    }

}
