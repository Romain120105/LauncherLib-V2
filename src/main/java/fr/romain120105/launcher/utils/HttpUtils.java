package fr.romain120105.launcher.utils;

import java.net.*;
import org.apache.commons.io.*;
import java.io.*;

public class HttpUtils
{
    public static String performGet(final URL url, final Proxy proxy) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(60000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT x.y; rv:10.0) Gecko/20100101 Firefox/10.0");
        final InputStream inputStream = connection.getInputStream();
        try {
            return IOUtils.toString(inputStream);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
