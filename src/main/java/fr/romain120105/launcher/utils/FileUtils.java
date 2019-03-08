package fr.romain120105.launcher.utils;

import java.security.*;
import javax.xml.bind.annotation.adapters.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;

public class FileUtils
{
    public static String getFileExtension(final File file) {
        final String fileName = file.getName();
        final int dotIndex = fileName.lastIndexOf(46);
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    
    public static String removeExtension(final String fileName) {
        if (fileName == null) {
            return "";
        }
        if (!getFileExtension(new File(fileName)).isEmpty()) {
            return fileName.substring(0, fileName.lastIndexOf(46));
        }
        return fileName;
    }
    
    public static String getSHA1(final File file) {
        try {
            final InputStream input = new FileInputStream(file);
            try {
                final MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
                final byte[] buffer = new byte[8192];
                for (int len = input.read(buffer); len != -1; len = input.read(buffer)) {
                    sha1.update(buffer, 0, len);
                }
                return new HexBinaryAdapter().marshal(sha1.digest()).toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<File> listFilesForFolder(final File folder) {
        final ArrayList<File> files = new ArrayList<File>();
        File[] listFiles;
        for (int length = (listFiles = folder.listFiles()).length, i = 0; i < length; ++i) {
            final File fileEntry = listFiles[i];
            if (fileEntry.isDirectory()) {
                files.addAll(listFilesForFolder(fileEntry));
                files.add(fileEntry);
            }
            else {
                files.add(fileEntry);
            }
        }
        return files;
    }
    
    public static void unzip(final File zipfile, final File folder) throws FileNotFoundException, IOException {
        final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipfile.getCanonicalFile())));
        ZipEntry ze = null;
        try {
            while ((ze = zis.getNextEntry()) != null) {
                final File f = new File(folder.getCanonicalPath(), ze.getName());
                if (ze.isDirectory()) {
                    f.mkdirs();
                }
                else {
                    f.getParentFile().mkdirs();
                    final OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
                    try {
                        try {
                            final byte[] buf = new byte[8192];
                            int bytesRead;
                            while (-1 != (bytesRead = zis.read(buf))) {
                                fos.write(buf, 0, bytesRead);
                            }
                        }
                        finally {
                            fos.close();
                        }
                        fos.close();
                    }
                    catch (IOException ioe) {
                        f.delete();
                        throw ioe;
                    }
                }
            }
        }
        finally {
            zis.close();
        }
        zis.close();
    }
    
    public static void removeFolder(final File folder) {
        if (folder.exists() && folder.isDirectory()) {
            final ArrayList<File> files = listFilesForFolder(folder);
            if (files.isEmpty()) {
                folder.delete();
                return;
            }
            for (final File f : files) {
                f.delete();
            }
            folder.delete();
        }
    }
}
