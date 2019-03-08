package fr.romain120105.launcher.versions;

import java.util.*;
import java.net.*;
import fr.romain120105.launcher.Version.VersionType;

public class Version
{
    private String id;
    private Date time;
    private Date releaseTime;
    private URL url;
    private VersionType type;
    
    public String getId() {
        return this.id;
    }
    
    public Date getUpdatedTime() {
        return this.time;
    }
    
    public void setUpdatedTime(final Date time) {
        this.time = time;
    }
    
    public Date getReleaseTime() {
        return this.releaseTime;
    }
    
    public void setReleaseTime(final Date time) {
        this.releaseTime = time;
    }
    
    public URL getUrl() {
        return this.url;
    }
    
    public void setUrl(final URL url) {
        this.url = url;
    }
    
    public void setID(final String id) {
        this.id = id;
    }
    
    public VersionType getType() {
        return this.type;
    }
    
    public void setType(final VersionType type) {
        this.type = type;
    }
}
