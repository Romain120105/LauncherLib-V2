package fr.romain120105.launcher;

import com.google.gson.JsonSyntaxException;
import fr.romain120105.launcher.auth.Credential;
import fr.romain120105.launcher.starter.Ram;
import fr.romain120105.launcher.utils.JsonManager;
import fr.romain120105.launcher.utils.HttpUtils;
import fr.romain120105.launcher.versions.CompleteVersion;
import fr.romain120105.launcher.versions.VersionsLoader;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public interface Version {

    Game update(Launcher launcher);

    void launch(Game game, Ram ram, Credential credential);

    void launch(Game game, Credential credential);


    URL getURL();

    class Builder{

        private String name;

        private VersionType versionType;

        private String forgeVersion;

        public Builder(String name){
            this.name = name;

        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVersionType(VersionType versionType){
            this.versionType = versionType;
            return this;
        }

        public String getForgeVersion() {
            return forgeVersion;
        }

        public Builder setForgeVersion(String forgeVersion) {
            this.forgeVersion = forgeVersion;
            return this;
        }

        public Version build(){
            try {
                VersionsLoader versionsLoader = new VersionsLoader();

                versionsLoader.loadOfficialVersions();

                LightVersion version = (LightVersion)versionsLoader.getVersion(this.getName());

                if(this.versionType.equals(VersionType.FORGE)){

                    versionsLoader.loadAllForgeVersion();

                    if(this.forgeVersion != null && versionsLoader.containsForgeVersion(this.forgeVersion)){
                        version.setForgeVersion( versionsLoader.getLoadedForgeVersion(this.forgeVersion));
                    }
                }
                CompleteVersion complete = null;

                try {
                    complete = (CompleteVersion) JsonManager.getGson().fromJson(HttpUtils.performGet(version.getURL(), Proxy.NO_PROXY), (Class) CompleteVersion.class);
                } catch (JsonSyntaxException | IOException ex2) {
                    ex2.printStackTrace();
                }
                version.setCompleteVersion(complete);


                return version;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    enum VersionType{
        VANILLA,
        FORGE,
        OPTIFINE,
        MCP
    }

}
