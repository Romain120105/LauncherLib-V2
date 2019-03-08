package fr.romain120105.launcher;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class LoggerPrintStream extends PrintStream {
    public LoggerPrintStream(OutputStream out){
        super(out);
    }

    @Override
    public void println(String x) {
        Date date = new Date();
        String time = "[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "]";
        if(!x.startsWith(time)){
            super.println(time + " "+  x);
        }
        super.println( x);
    }
}
