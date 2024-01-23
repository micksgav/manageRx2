/**
***********************************************
 @Name: Report
 @Author           : Gavin Micks
 @Creation Date    : January 18, 2024
 @Modified Date	   : January 22, 2024
   @Description    : Creates reports to print
   
************
**/
package utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import patientManagement.*;
import inventory.DrugStockLinkedList;
import inventory.DrugStock;

public class Report {
	static final String sep = System.getProperty("file.separator"); // Equivalent of "/" for OS
	
	/** Method Name: patientReport
	* @Author Gavin Micks
	* @Date December January 18, 2024
	* @Modified December 19, 2024
	* @Description creates a printable report about a patients info and prescriptions
	* @Parameters  Patient p - patient to scrape data from
	* @Returns void
	* Dependencies: BufferedWriter, FileWriter
	* Throws/Exceptions: N/A
    */
	public static void patientReport(Patient p) {
		
		PrescriptionList actList = p.getActivePrescriptions(); //active prescriptions
		PrescriptionList arcList = p.getArchivedPrescriptions(); //active prescriptions
		
		try {
			//writer for report of patient
			BufferedWriter bw = new BufferedWriter(new FileWriter("data" + sep + "reports" + sep + "patients" + sep + p.getName() + ".txt"));
			
			//write patient info
			bw.write("\nPatient Name: " + p.getName());
			bw.write("\nPatient Birthday: " + p.getBirthday());
			bw.write("\nPatient Phone Number: " + p.getPhoneNumber());
			bw.write("\nPatient eMail: " + p.getEmail());
			bw.write("\nPatient Address: " + p.getAddress());
			
			bw.write("\n\nActive Prescriptions:\n");
			
			//write all active prescription
			for (int i = 0; i < actList.length(); i++) {
				Prescription pTemp = actList.atIndex(i);
				bw.write("\nName: " + pTemp.getBrandName());
				bw.write("\nDIN: " + pTemp.getDIN());
				bw.write("\nDosage: " + pTemp.getDosage());
				bw.write("\nQuantity: " + pTemp.getQuantity());
				bw.write("\nDuration: " + pTemp.getDuration());
				bw.write("\nInstructions: " + pTemp.getInstructions());
				bw.write("\nPrescribing Doctor: " + pTemp.getDocName() + " " + pTemp.getDocPhone());
				bw.write("\n------------------------------------------\n");
			} //end for
			bw.write("\nArchived Prescriptions:\n");
			
			//write all archived prescriptions
			for (int i = 0; i < arcList.length(); i++) {
				Prescription pTemp2 = arcList.atIndex(i);
				bw.write("\nName: " + pTemp2.getBrandName());
				bw.write("\nDIN: " + pTemp2.getDIN());
				bw.write("\nDosage: " + pTemp2.getDosage());
				bw.write("\nQuantity: " + pTemp2.getQuantity());
				bw.write("\nDuration: " + pTemp2.getDuration());
				bw.write("\nInstructions: " + pTemp2.getInstructions());
				bw.write("\nPrescribing Doctor: " + pTemp2.getDocName() + " " + pTemp2.getDocPhone());
				bw.write("\n------------------------------------------\n");
			} //end for
			bw.close(); //close writer
			//from https://stackoverflow.com/questions/3487149/how-to-open-the-notepad-file-in-java
			//open in notepad (windows only)
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().edit(new File("data" + sep + "reports" + sep + "patients" + sep + p.getName() + ".txt"));
			    } //end if
		} catch (IOException e) {
			//error handling
			logErrors.log(e.getMessage() + " in patientReport in Report");
		} //end try-catch
	} //end patientReport
	
	/** Method Name: stockReport
	* @Author Gavin Micks
	* @Date December January 19, 2024
	* @Modified December 20, 2024
	* @Description creates a printable report about drug stocks
	* @Parameters  DrugStockLinkedList d - list to scrape data from
	* @Returns void
	* Dependencies: BufferedWriter, FileWriter
	* Throws/Exceptions: N/A
    */
	public static void stockReport(DrugStockLinkedList d) {
		
		try {
			//from https://www.javatpoint.com/java-get-current-date
			//formatted date for file name and header
			LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");

		    String formattedDate = myDateObj.format(myFormatObj);
		    //writer for report
			BufferedWriter bw = new BufferedWriter(new FileWriter("data" + sep + "reports" + sep + "stock" + sep + formattedDate + ".txt"));
			
			bw.write("\nDate: " + formattedDate + "\n");
			bw.write("\nNum of Drugs: " + d.countNodes());
			
			bw.write("\n\nActive Prescriptions:\n");
			DrugStock[] sl = d.getElements(); 
			
			//add each drug in stock and qty
			for (int i = 0; i < sl.length; i++) {
				bw.write("\nName: " + sl[i].getDrugName());
				bw.write("\nDIN: " + sl[i].getDrugDIN());
				bw.write("\nQuantity: " + sl[i].getNumInStock());
				bw.write("\nThreshold: " + sl[i].getStockThreshold());
				bw.write("\n------------------------------------------\n");
			} //end for
			bw.close();
			//https://stackoverflow.com/questions/3487149/how-to-open-the-notepad-file-in-java
			//open in notepad (only for windows)
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().edit(new File("data" + sep + "reports" + sep + "stock" + sep + formattedDate + ".txt"));
			} //end if
		} catch (IOException e) {
			//error handling
			logErrors.log(e.getMessage() + " in patientReport in Report");
		} //end try-catch
	} //end stockReport
} // end Report class
