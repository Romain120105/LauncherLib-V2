package fr.romain120105.launcher.versions;

import com.google.gson.annotations.*;
import fr.romain120105.launcher.LightVersion;
import fr.romain120105.launcher.versions.CompleteVersion;
import fr.romain120105.launcher.versions.Downloads;
import fr.romain120105.launcher.versions.Library;

import java.util.*;

public class ForgeVersion extends LightVersion
{
    @SerializedName("+tweakers")
    private String[] tweakers;
    private List<Library> libraries;
    @SerializedName("mainClass")
    private String mainClass;
    private Requires[] requires;
    private String version;
    private JarMods[] jarMods;
    private boolean latest;
    
    public String getMinecraftVersion() {
        return (this.requires != null) ? this.requires[0].equals : null;
    }
    
    public List<Library> getLibraries() {
        return this.libraries;
    }
    
    public void setLibraries(final List<Library> libraries) {
        this.libraries = libraries;
    }
    
    public String getTweakers() {
        return (this.tweakers != null && this.tweakers.length != 0) ? this.tweakers[0] : null;
    }
    
    public void setTweakers(final String[] tweakers) {
        this.tweakers = tweakers;
    }
    
    public String getMainClass() {
        return this.mainClass;
    }
    
    public void setMainClass(final String mainClass) {
        this.mainClass = mainClass;
    }
    
    public Requires[] getRequires() {
        return this.requires;
    }
    
    public void setRequires(final Requires[] requires) {
        this.requires = requires;
    }
    
    public String getForgeVersionName() {
        return this.version;
    }
    
    public void setForgeVersion(final String version) {
        this.version = version;
    }
    
    public JarMods[] getJarMods() {
        return this.jarMods;
    }
    
    public boolean isLatest() {
        return this.latest;
    }
    
    public void setLatest(final boolean latest) {
        this.latest = latest;
    }
    
    @Override
    public String getId() {
        return this.getForgeVersionName();
    }
    
    public class Requires
    {
        private String uid;
        private String equals;
        
        public String getUID() {
            return this.uid;
        }
        
        public String getEquals() {
            return this.equals;
        }
    }
    
    public class JarMods
    {
        private String name;
        private Downloads downloads;
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public Downloads getDownloads() {
            return this.downloads;
        }
        
        public void setDownloads(final Downloads downloads) {
            this.downloads = downloads;
        }
        
        public String getArtifactURL() {
            return this.downloads.getArtifact().getArtifactURL(this.name);
        }
    }
}
