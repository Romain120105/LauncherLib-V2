package fr.romain120105.launcher.download;


import fr.romain120105.launcher.utils.MinecraftFolder;
import fr.romain120105.launcher.utils.FileUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DownloadManager
{

    private MinecraftFolder folder;
    private ArrayList<DownloadEntry> filesToDownload;
    private ArrayList<DownloadEntry> downloadedFile;
    private ArrayList<String> dontDelete;
    
    public DownloadManager(final MinecraftFolder folder) {
        this.filesToDownload = new ArrayList<DownloadEntry>();
        this.downloadedFile = new ArrayList<DownloadEntry>();
        this.dontDelete = new ArrayList<String>();
        this.folder = folder;
    }
    
    public DownloadEntry addDownloadableFile(final DownloadEntry file) {
        if (!this.filesToDownload.contains(file)) {
            this.dontDelete.add(file.getDestination().getAbsolutePath());
            if (file.needDownload()) {
                this.filesToDownload.add(file);
            }
        }
        return file;
    }
    
    public void startDownload() {
        if (this.filesToDownload.size() == 0) {
            System.out.println("Nothing to download!");
            return;
        }
        System.out.println(String.valueOf(this.filesToDownload.size()) + " files to download.");
        for (final DownloadEntry download : this.filesToDownload) {
            System.out.println("Download: " + download.getURL());
            download.download();
            this.downloadedFile.add(download);
        }
        System.out.println("Finished download !");
    }
    
    public int removeSurplus(final File folder) {
        final ArrayList<File> files = FileUtils.listFilesForFolder(folder);
        int count = 0;
        for (final File f : files) {
            if (f.isDirectory()) {
                boolean good = true;
                final ArrayList<File> files2 = FileUtils.listFilesForFolder(f);
                if (files2.isEmpty()) {
                    f.delete();
                }
                else {
                    for (final File f2 : files2) {
                        if (!f2.isDirectory()) {
                            good = true;
                            break;
                        }
                        good = false;
                    }
                    if (!good) {
                        f.delete();
                    }
                }
            }
            if (!this.dontDelete.contains(f.getAbsolutePath()) && !f.isDirectory()) {
                f.delete();
                if (folder == this.folder.getNativesFolder()) {
                    continue;
                }
                ++count;
            }
        }
        return count;
    }
    
    public void clearDownloads() {
        this.filesToDownload.clear();
        this.downloadedFile.clear();
        this.dontDelete.clear();
    }
    
    public float getDownloadPercentage() {
        final NumberFormat percentFormat = new DecimalFormat("0");
        percentFormat.setMaximumFractionDigits(this.filesToDownload.size());
        final float percentage = new Float(percentFormat.format(this.downloadedFile.size()));
        return percentage;
    }
    
    public boolean isDownloadFinished() {
        return this.filesToDownload.size() == 0;
    }
    
    public void check() {
        int count = 0;
        if (this.folder.getLibrariesFolder() != null && !this.folder.getLibrariesFolder().exists()) {
            this.folder.getLibrariesFolder().mkdirs();
        }
        if (this.folder.getNativesFolder() != null && !this.folder.getNativesFolder().exists()) {
            this.folder.getNativesFolder().mkdirs();
        }
        if (this.folder.getModsFolder() != null && this.folder.getModsFolder().exists()) {
            count += this.removeSurplus(this.folder.getModsFolder());
        }
        if (this.folder.getLibrariesFolder() != null) {
            count += this.removeSurplus(this.folder.getLibrariesFolder());
        }
        if (count != 0) {
            System.out.println("Deleted " + count + " files.");
        }
    }
    
    public ArrayList<String> getDontDownload() {
        return this.dontDelete;
    }

}
