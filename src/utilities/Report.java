package utilities;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import PatientManagement.Patient;
import PatientManagement.Prescription;
import PatientManagement.PrescriptionList;

public class Report {
	static final String sep = System.getProperty("file.separator");
	
	public static void patientReport(Patient p) {
		PrescriptionList actList = p.getActivePrescriptions();
		PrescriptionList arcList = p.getArchivedPrescriptions();
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("data" + sep + "reports" + sep + "patients" + sep + p.getName() + ".txt"));
			
			bw.write("\nPatient Name: " + p.getName());
			bw.write("\nPatient Birthday: " + p.getBirthday());
			bw.write("\nPatient Phone Number: " + p.getPhoneNumber());
			bw.write("\nPatient eMail: " + p.getEmail());
			bw.write("\nPatient Address: " + p.getAddress());
			
			bw.write("\n\nActive Prescriptions:\n");
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
			}
			bw.write("\nArchived Prescriptions:\n");
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
			}
			bw.close();
			//https://stackoverflow.com/questions/3487149/how-to-open-the-notepad-file-in-java
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().edit(new File("data" + sep + "reports" + sep + "patients" + sep + p.getName() + ".txt"));
			} else {
// thing
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
