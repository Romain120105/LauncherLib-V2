package fr.romain120105.launcher.starter.helper;

import java.io.*;

public class ProcessStarter
{
    private ProcessBuilder pb;
    private Process p;
    
    public ProcessStarter(final ProcessBuilder processBuilder) {
        this.pb = processBuilder;
    }
    
    public void launch() throws Exception {
        this.p = this.pb.start();
        final ThreadPrinter localThreadPrinter1 = new ThreadPrinter(this.p.getInputStream());
        final ThreadPrinter localThreadPrinter2 = new ThreadPrinter(this.p.getErrorStream());
        new Thread(localThreadPrinter1).start();
        new Thread(localThreadPrinter2).start();
        this.p.waitFor();
    }
    
    public Process getProcess() {
        return this.p;
    }
    
    private class ThreadPrinter implements Runnable
    {
        private InputStream is;
        
        public ThreadPrinter(final InputStream paramInputStream) {
            this.is = paramInputStream;
        }
        
        private BufferedReader getBufferedReader(final InputStream paramInputStream) {
            return new BufferedReader(new InputStreamReader(paramInputStream));
        }
        
        public void run() {
            final BufferedReader localBufferedReader = this.getBufferedReader(this.is);
            String str = "";
            try {
                while ((str = localBufferedReader.readLine()) != null) {
                    if (!str.contains("Stopping")) {
                        System.out.println(str);
                    }
                    else {
                        System.out.println("Stopping minecraft with code: 0.");
                        System.exit(0);
                    }
                }
            }
            catch (Exception localException) {
                localException.printStackTrace();
            }
        }
    }
}
