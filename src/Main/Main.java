package Main;

import java.io.IOException;
import java.util.LinkedList;

import PatientManagement.*;
import inventory.*;
import utilities.*;
import mainUI.*;

public class Main {

	public static void main(String[] args) throws IOException {

		SQLHelper helper = new SQLHelper(); // for connecting with sql

		PatientList patients = helper.getAllPatients(); // all patients
		DrugStockLinkedList drugStock = helper.getAllDrugStock(); // complete drug stock
		AllStock stock = new AllStock(drugStock); // complete stock
		int[] containerStock = helper.getAllContainerStock(); // stock of all container sizes
		int numSmall = containerStock[0];
		int numMed = containerStock[1];
		int numLarge = containerStock[2];
		stock.setNumSmall(numSmall);
		stock.setNumMedium(numMed);
		stock.setNumLarge(numLarge);
		PrescriptionList allScripts = helper.getAllPrescriptions(); // all prescriptions
		LinkedList<Insurance> allInsurance = helper.getAllInsurance(); // all insurance
		String[] usernames = helper.getAllUsernames();
		String[] passwords = helper.getAllPasswords();

		// import prescriptions and place in correct patients
		WaitDialog.showWait("Importing Prescription Information");
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
		//WaitDialog.disposeWait();
		WaitDialog.showWait("Importing Insurance Information");
		for (int i = 0; i < allInsurance.size(); i++) {
			for (int j = 0; j < patients.numRecs(); j++) {
				if (patients.returnData(j).getId() == allInsurance.get(i).getPatientID()) {
					patients.returnData(j).addNewInsuranceInfo(allInsurance.get(i));
				} // end if
			} // end for
		} // end for

		WaitDialog.disposeWait();
		
		Drug drug1 = drugFinder.getDrug("00548359");
		stock.updateStock(100, drug1.getDIN(), drug1.getDrugClass(), "100");
		stock.updateStock(400, drug1.getDIN(), drug1.getDrugClass(), "");

		loginUI oui = new loginUI("ManageRx", patients, stock, usernames, passwords);

		oui.setVisible(true);
	}
}
