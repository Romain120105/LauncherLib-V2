package fr.romain120105.launcher;

import fr.romain120105.launcher.auth.Credential;
import fr.romain120105.launcher.starter.Ram;
import fr.romain120105.launcher.starter.Starter;
import fr.romain120105.launcher.versions.CompleteVersion;
import fr.romain120105.launcher.versions.ForgeVersion;
import javafx.scene.chart.StackedAreaChart;

import java.net.URL;

public class LightVersion implements Version{

    private String id;
    private URL url;
    private VersionType type;
    private CompleteVersion completeVersion;
    private ForgeVersion forgeVersion;



    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setType(VersionType type) {
        this.type = type;
    }

    public void setForgeVersion(ForgeVersion forgeVersion) {
        this.forgeVersion = forgeVersion;
    }

    public ForgeVersion getForgeVersion() {
        return forgeVersion;
    }

    @Override
    public URL getURL() {
        return this.url;
    }

    public String getId() {
        return id;
    }

    public VersionType getVersionType() {
        return type;
    }

    public CompleteVersion getCompleteVersion() {
        return completeVersion;
    }

    public void setCompleteVersion(CompleteVersion completeVersion) {
        this.completeVersion = completeVersion;
    }

    @Override
    public Game update(Launcher launcher) {
        VersionDownloader downloader = new VersionDownloader(launcher);
        Game game  = downloader.downloadVersion(this, this.getCompleteVersion());
        downloader.getDownloadManager().check();
        downloader.getDownloadManager().startDownload();
        downloader.unzipNatives();
        downloader.getDownloadManager().check();

        return game;
    }

    @Override
    public void launch(Game game, Ram ram, Credential credential) {
        Starter starter = new Starter(game, ram, credential);
        starter.launch();

    }

    @Override
    public void launch(Game game, Credential credential) {
        short a = 1;
        this.launch(game, new Ram(a, "G"), credential);
    }
}
