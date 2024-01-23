/**
***********************************************
 @Name: logErrors
 @Author           : Gavin Micks
 @Creation Date    : December 22, 2023
 @Modified Date	   : December 23, 2023
   @Description    : logs errors
   
************
**/
package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class logErrors {
    static final String sep = System.getProperty("file.separator"); //Equivalent "/" for OS

    
	/** Method Name: log
	* @Author Gavin Micks
	* @Date December 22, 2023
	* @Modified December 23, 2024
	* @Description logs errors in log file
	* @Parameters  String message - error message
	* @Returns void
	* Dependencies: LocalDateTime, DateTimeFormatter, BufferedWriter, FileWriter
	* Throws/Exceptions: N/A
    */
    public static void log(String message){
    	try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data" + sep + "log.txt", true));
            //from https://www.javatpoint.com/java-get-current-date
            //date and time of error
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            writer.write(dtf.format(now) + " " + message + "\n"); //write error to log file
            writer.close(); //save log file
    	} catch (Exception e) {
    		//do not save error if not working
    	}
    } //end log
} //end logErrors
