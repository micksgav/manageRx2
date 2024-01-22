/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : January 21, 2024
 * @Last Modified: January 22, 2024
 * @Description: Main driver for ManageRx. Initializes all objects that are required for the program by pulling from sql database.
 ***********************************************
 */

package Main;

import java.util.LinkedList;

import PatientManagement.*;
import inventory.*;
import utilities.*;
import mainUI.*;

public class Main {

	public static void main(String[] args) {

		SQLHelper helper = new SQLHelper(); // for connecting with sql

		// import from SQL
		WaitDialog.showWait("Importing Patient Information");
		PatientList patients = helper.getAllPatients(); // all patients
		WaitDialog.showWait("Importing Stock Information");
		DrugStockLinkedList drugStock = helper.getAllDrugStock(); // complete drug stock
		AllStock stock = new AllStock(drugStock); // complete stock
		int[] containerStock = helper.getAllContainerStock(); // stock of all container sizes
		int numSmall = containerStock[0]; // number of small containers
		int numMed = containerStock[1]; // number of medium containers
		int numLarge = containerStock[2]; // number of large containers
		stock.setNumSmall(numSmall);
		stock.setNumMedium(numMed);
		stock.setNumLarge(numLarge);
		WaitDialog.showWait("Importing Prescription Information");
		PrescriptionList allScripts = helper.getAllPrescriptions(); // all prescriptions
		LinkedList<Insurance> allInsurance = helper.getAllInsurance(); // all insurance
		String[] usernames = helper.getAllUsernames(); // usernames
		String[] passwords = helper.getAllPasswords(); // passwords

		// import prescriptions and place in correct patients
		for (int i = 0; i < allScripts.length(); i++) {
			for (int j = 0; j < patients.numRecs(); j++) {
				if (patients.returnData(j).getId() == allScripts.atIndex(i).getPatientID()) {
					if (allScripts.atIndex(i).getCurrent()) {
						patients.returnData(j).addActivePrescription(allScripts.atIndex(i));
					} // end if
					else {
						patients.returnData(j).addArchivedPrescription(allScripts.atIndex(i));
					} // end else
				} // end if
			} // end for
		} // end for
		
		// import insurance information and place in correct patients
		WaitDialog.showWait("Importing Insurance Information");
		for (int i = 0; i < allInsurance.size(); i++) {
			for (int j = 0; j < patients.numRecs(); j++) {
				if (patients.returnData(j).getId() == allInsurance.get(i).getPatientID()) {
					patients.returnData(j).addNewInsuranceInfo(allInsurance.get(i));
				} // end if
			} // end for
		} // end for

		loginUI oui = new loginUI("ManageRx", patients, stock, usernames, passwords); // open UI

		oui.setVisible(true);
	} // end main
} // end Main
