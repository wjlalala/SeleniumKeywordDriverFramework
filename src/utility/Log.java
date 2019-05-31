package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by junwang on 2019/5/29.
 */
public class Log {
    private static Logger Log = LogManager.getLogger(Log.class.getName());

    public static void  startTestCase(String sTestCaseName){
        Log.info("*******************************************************");
        Log.info("$$$$$$$$$          "+sTestCaseName+ "         $$$$$$$$$$");
        Log.info("*******************************************************");
    }

    public static void endTestCase(String sTestCaseName){
        Log.info("XXXXXXX            "+"-E---N---D-"+"          XXXXXXXXX");
        Log.info("X");
        Log.info("X");
    }

    public static void info(String message){
        Log.info(message);
    }

    public static void warn(String message){
        Log.warn(message);
    }

    public static void error(String message){
        Log.error(message);
    }

    public static void fatal(String message){
        Log.fatal(message);
    }

    public static void debug(String message){
        Log.debug(message);
    }

}
