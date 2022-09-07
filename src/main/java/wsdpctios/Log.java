package wsdpctios;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Log {

    private static String string="";

    public static void addToString(String string) {
        Log.string=Log.string + string;
    }

    public static String getString() {
        return Log.string;
    }    
    
    public static void resetString() {
        Log.string = "";
    }  
    public static void log(String zprava) {
        Logger logger = Logger.getLogger("log"); 
        Handler handler = null;
        try {
            handler = new FileHandler("log.txt", true); 
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage() + " " + ex.getMessage());
        }

        handler.setFormatter(new SimpleFormatter()); 
        logger.addHandler(handler); 

        //logujeme
        logger.log(Level.INFO, zprava);

        handler.close(); 
    }
}
