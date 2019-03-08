package fr.romain120105.launcher;

import fr.romain120105.launcher.utils.JsonManager;
import fr.romain120105.launcher.utils.MinecraftFolder;
import fr.romain120105.launcher.download.DownloadEntry;
import fr.romain120105.launcher.download.DownloadManager;
import fr.romain120105.launcher.utils.FileUtils;
import fr.romain120105.launcher.versions.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class VersionDownloader {

    private Launcher launcher;
    private MinecraftFolder folder;
    private DownloadManager manager;
    private ArrayList<File> natives;

    public VersionDownloader(Launcher launcher){
        this.launcher = launcher;
        this.folder = launcher.getMinecraftFolder();
        this.manager = new DownloadManager(folder);
        this.natives = new ArrayList<>();
    }

    public Game downloadVersion(LightVersion version, final CompleteVersion complete) {

        final String index = this.downloadResources(complete, this.folder.getAssetsFolder());
        this.downloadClient(complete);

        Game game = new Game(complete.getID(), null, null, index, this.folder, Version.VersionType.VANILLA);
        System.out.println(version.getForgeVersion());
        if(version.getForgeVersion() != null){
            this.downloadForgeLibraries(version.getForgeVersion());

            game.setVersionType(Version.VersionType.FORGE);
            game.setTweaker(version.getForgeVersion().getTweakers());
        }

        this.downloadLibraries(complete);


        if (version.getForgeVersion() != null && this.folder.getModsFolder() != null && !this.folder.getModsFolder().exists()) {
            this.folder.getModsFolder().mkdirs();
        }


        return game;
    }

    private void downloadForgeLibraries(final ForgeVersion version) {
        if (version.getLibraries() == null || version.getLibraries().isEmpty()) {
            final String v = version.getMinecraftVersion();
            if (!v.equals("1.1") && !v.startsWith("1.2.") && !v.equals("1.3.2")) {
                if (!v.startsWith("1.4.")) {
                    try {

                        final File l = new File(this.folder.getLibrariesFolder(), version.getJarMods()[0].getArtifactURL());
                        this.manager.addDownloadableFile(new DownloadEntry(new URL(version.getJarMods()[0].getDownloads().getArtifact().getUrl()), new File(FileUtils.removeExtension(l.getAbsolutePath()) + ".jar"), version.getJarMods()[0].getDownloads().getArtifact().getSha1(), this.launcher));
                        this.folder.setClientJarSHA1(null);
                    }
                    catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            try {
                this.folder.setClientJarURL(new URL(version.getJarMods()[0].getDownloads().getArtifact().getUrl()));
                this.folder.setClientJarSHA1(version.getJarMods()[0].getDownloads().getArtifact().getSha1());
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return;
        }
        for (final Library library : version.getLibraries()) {
            try {
                if (library.getURL().contains("typesafe") || (version.getMinecraftVersion().equals("1.7.10") && library.getName().contains("guava") && library.getName().contains("15"))) {
                    continue;
                }
                if (version.getMinecraftVersion().equals("1.7.2") && library.getName().contains("launchwrapper")) {
                    this.manager.addDownloadableFile(new DownloadEntry(new URL("https://libraries.minecraft.net/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar"), new File(this.folder.getLibrariesFolder(), library.getArtifactURL()), null, this.launcher));
                }
                else {
                    this.manager.addDownloadableFile(new DownloadEntry(new URL(library.getURL()), new File(this.folder.getLibrariesFolder(), library.getArtifactURL()), null, this.launcher));
                }
            }
            catch (MalformedURLException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void downloadLibraries(final CompleteVersion complete) {
        final ArrayList<File> natives = new ArrayList<File>();
        for (final Library library : complete.getLibraries()) {
            if (!complete.getID().equals("1.7.10") || !library.getName().contains("guava") || !complete.getVersionType().equals(Version.VersionType.FORGE)) {
                String file = "";
                String classifier = null;
                File local = new File(this.folder.getLibrariesFolder(), String.valueOf(library.getArtifactBaseDir()) + ".jar");
                String sha1 = null;
                if (library.getNative() != null) {
                    classifier = library.getNative();
                    if (classifier != null) {
                        file = library.getArtifactPath(classifier);
                        local = new File(this.folder.getNativesFolder(), library.getArtifactFilename(classifier));
                        natives.add(local);
                        this.manager.getDontDownload().add(local.getAbsolutePath());
                        if (library.getClassifiers() != null) {
                            if (classifier.contains("windows")) {
                                sha1 = library.getClassifiers().getWindows().getSha1();
                            }
                            if (classifier.contains("linux")) {
                                sha1 = library.getClassifiers().getLinux().getSha1();
                            }
                            if (classifier.contains("macos")) {
                                sha1 = library.getClassifiers().getMacos().getSha1();
                            }
                        }
                        else if (library.getDownloads().getArtifact() != null) {
                            sha1 = library.getDownloads().getArtifact().getSha1();
                        }
                    }
                }
                else {
                    file = library.getArtifactPath();
                }
                try {
                    final URL url = new URL("https://libraries.minecraft.net/" + file);
                    if (library.getDownloads().getArtifact() != null) {
                        sha1 = library.getDownloads().getArtifact().getSha1();
                    }
                    this.manager.addDownloadableFile(new DownloadEntry(url, local, sha1, this.launcher));
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        this.natives.addAll(natives);
    }

    private String downloadResources(final CompleteVersion complete, final File assets) {
        InputStream stream = null;
        final File objectsFolder = new File(assets, "objects");
        final File indexesFolder = new File(assets, "indexes");
        if (!objectsFolder.exists()) {
            objectsFolder.mkdirs();
        }
        final AssetIndexInfo indexInfo = complete.getAssetIndex();
        final File indexFile = new File(indexesFolder, String.valueOf(indexInfo.getId()) + ".json");
        try {
            final URL indexUrl = indexInfo.getURL();
            stream = indexUrl.openConnection().getInputStream();
            final String json = IOUtils.toString(stream);
            org.apache.commons.io.FileUtils.writeStringToFile(indexFile, json);
            final AssetIndex index = (AssetIndex) JsonManager.getGson().fromJson(json, (Class)AssetIndex.class);
            for (final Map.Entry<AssetIndex.AssetObject, String> entry : index.getUniqueObjects().entrySet()) {
                final AssetIndex.AssetObject object = entry.getKey();
                final String filename = String.valueOf(object.getHash().substring(0, 2)) + "/" + object.getHash();
                final File file = new File(objectsFolder, filename);
                if (!file.isFile() || org.apache.commons.io.FileUtils.sizeOf(file) != object.getSize()) {
                    final AssetDownloadable downloadable = new AssetDownloadable(entry.getValue(), object, "http://resources.download.minecraft.net/", objectsFolder, this.launcher);
                    this.manager.addDownloadableFile(downloadable);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return indexInfo.getId();
        }
        finally {
            IOUtils.closeQuietly(stream);
        }
        IOUtils.closeQuietly(stream);
        return indexInfo.getId();
    }

    public void unzipNatives() {
        for (final File file : this.natives) {
            if (file.exists()) {
                try {
                    FileUtils.unzip(file, this.folder.getNativesFolder());
                    this.manager.getDontDownload().add(file.getAbsolutePath());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        final File meta = new File(this.folder.getNativesFolder(), "META-INF");
        final File md = new File(meta, "MANIFEST.MF");
        if (meta.exists()) {
            FileUtils.removeFolder(meta);
            org.apache.commons.io.FileUtils.deleteQuietly(meta);
        }
        if (md.exists()) {
            md.delete();
        }
    }

    private void downloadClient(final CompleteVersion complete) {
        try {
            if (this.folder.getClientJarURL() != null) {
                this.manager.addDownloadableFile(new DownloadEntry(this.folder.getClientJarURL(), this.folder.getClientJarFile(), complete.getDownloads().getClient().getSha1(), this.launcher));
            }
            else {
                this.manager.addDownloadableFile(new DownloadEntry(new URL(complete.getDownloads().getClient().getUrl()), this.folder.getClientJarFile(), complete.getDownloads().getClient().getSha1(), this.launcher));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DownloadManager getDownloadManager() {
        return manager;
    }
}
