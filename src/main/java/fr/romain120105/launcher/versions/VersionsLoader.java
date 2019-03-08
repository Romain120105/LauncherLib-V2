package fr.romain120105.launcher.versions;


import com.google.gson.*;
import fr.romain120105.launcher.LightVersion;
import fr.romain120105.launcher.utils.JsonManager;
import fr.romain120105.launcher.utils.HttpUtils;
import fr.romain120105.launcher.Version;

import java.io.*;
import java.net.*;
import java.util.*;

public class VersionsLoader
{
    private Gson gson;
    private URL manifestURL;
    private URL forgeManifestURL;
    private ArrayList<String> loadedGlobalVersion;
    private ArrayList<LightVersion> versions = new ArrayList<>();
    private ArrayList<ForgeVersion> forgeVersions = new ArrayList<>();
    private ArrayList<ForgeVersion> loadedForgeVersions = new ArrayList<>();

    
    public VersionsLoader() throws MalformedURLException {
        if (JsonManager.getGson() == null) {
            JsonManager.setGson(new Gson());
            JsonManager.init();
        }
        this.gson = JsonManager.getGson();
        this.manifestURL = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        this.forgeManifestURL = new URL("https://v1.meta.multimc.org/net.minecraftforge/index.json");
        this.loadedGlobalVersion = new ArrayList<String>();
    }

    public void loadOfficialVersions() {
        try {
            final RawVersionList versionList = (RawVersionList)this.gson.fromJson(this.getContent(this.manifestURL), (Class)RawVersionList.class);
            for (final LightVersion version : versionList.getVersions()) {
                version.setType(Version.VersionType.VANILLA);
                this.versions.add(version);
            }
        }
        catch (JsonSyntaxException | IOException ex2) {
            ex2.printStackTrace();
        }
    }

    public Version getVersion(String name){
        for(LightVersion version : versions){
            if(version.getId().equals(name)){
                return version;
            }
        }

        return null;
    }


    public void loadAllForgeVersion() {
        try {
            final RawForgeVersionList versionList = (RawForgeVersionList)this.gson.fromJson(this.getContent(this.forgeManifestURL), (Class)RawForgeVersionList.class);
            for (final ForgeVersion version : versionList.getVersions()) {
                final String[] v = version.getMinecraftVersion().split("\\.");
                final int i = new Integer(v[1]);
                if (i >= 6) {
                    if (this.loadedGlobalVersion.contains(version.getMinecraftVersion())) {
                        version.setLatest(false);
                    }
                    else {
                        this.loadedGlobalVersion.add(version.getMinecraftVersion());
                        version.setLatest(true);
                    }
                    this.forgeVersions.add(version);
                }
            }
        }
        catch (JsonSyntaxException | IOException ex2) {
            ex2.printStackTrace();
        }
    }

    public boolean containsForgeVersion(String id){

        for(ForgeVersion version: forgeVersions) {

            if(version.getForgeVersionName().equals(id)){
                return  true;
            }
        }
        return false;
    }


    public ForgeVersion getLoadedForgeVersion(String id){
        for(ForgeVersion version: loadedForgeVersions) {
            if(version.getForgeVersionName().equals(id)){
                return  version;
            }
        }

        return loadForgeVersion(id);
    }

    public ForgeVersion getForgeVersion(String id){
        for(ForgeVersion version: forgeVersions) {
            if(version.getForgeVersionName().equals(id)){
                return  version;
            }
        }

        return null;
    }
    
    public ForgeVersion loadForgeVersion(final String id) {

            if (this.containsForgeVersion(id)) {
                ForgeVersion version = this.getForgeVersion(id);
                try {
                    final URL url = new URL("https://v1.meta.multimc.org/net.minecraftforge/" + version.getForgeVersionName() + ".json");
                    version = (ForgeVersion)this.gson.fromJson(this.getContent(url), (Class)ForgeVersion.class);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (JsonSyntaxException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
                version.setType(Version.VersionType.FORGE);
               this.loadedForgeVersions.add(version);
                return version;
            }
            System.err.println("Unknow forge version: " + id);
            return null;

    }

    public String getContent(final URL url) throws IOException {
        return HttpUtils.performGet(url, Proxy.NO_PROXY);
    }
    
    private class RawVersionList
    {
        private List<LightVersion> versions;
        
        private RawVersionList() {
            this.versions = new ArrayList<LightVersion>();
        }
        
        public List<LightVersion> getVersions() {
            return this.versions;
        }
    }


    private class RawForgeVersionList
    {
        private List<ForgeVersion> versions;
        
        private RawForgeVersionList() {
            this.versions = new ArrayList<ForgeVersion>();
        }
        
        public List<ForgeVersion> getVersions() {
            return this.versions;
        }
    }

}
