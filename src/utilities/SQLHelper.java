package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import patientManagement.*;
import inventory.AllStock;
import inventory.DrugStock;
import inventory.DrugStockLinkedList;

public class SQLHelper {
	ExecutorService service; // for multithreading
	boolean status; // for background processes - stores status
	int IDnum; // for background processes - stores ID
	Statement statement; // SQL statement
	Connection connect = null; // SQL connection

	public SQLHelper() {
		service = Executors.newFixedThreadPool(1); // create new thread pool
		// connect to SQL
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// login info
			connect = DriverManager.getConnection(
					"jdbc:mysql://managerx.cxa8k0i6mls8.ca-central-1.rds.amazonaws.com:3306/manageRx", "manageRxAdmin",
					"manageRxSQL*!");
			statement = connect.createStatement();
			connect.setAutoCommit(true);
		} catch (Exception e) {
			// Error Handling
			logErrors.log(e.getMessage() + " during connecting in SQLHelper");
		} // end try-catch
	} // end SQLHelper constructor

	/*
	 * Method Name: updateBG Author: Gavin Micks Creation Date: January 15, 2024
	 * Modified Date: January 15, 2024 Description: generic sql update in background
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed thing
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void updateBG(String table, String column, Object obj, int ID) {
		// runs update method on another thread
		service.submit(new Runnable() {
			public void run() {
				status = update(table, column, obj, ID);
			}
		});
	} // end updateBG

	/*
	 * Method Name: updateInsuranceBG Author: Gavin Micks Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: updates insurance in sql in
	 * the background
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed insurance
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void updateInsuranceBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
			public void run() {
				status = updateInsurance(table, column, obj, ID);
			}
		});
	} // end updateInsuranceBG

	/*
	 * Method Name: updatePrescriptionBG Author: Gavin Micks Creation Date: January
	 * 15, 2024 Modified Date: January 15, 2024 Description: updates prescription in
	 * sql in the background
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed prescription
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void updatePrescriptionBG(String table, String column, Object obj, int ID) {
		service.submit(new Runnable() {
			public void run() {
				status = updatePrescription(table, column, obj, ID);
			}
		});
	} // end updatePrescriptionBG

	/*
	 * Method Name: updatePrescription Author: Gavin Micks Creation Date: January
	 * 15, 2024 Modified Date: January 15, 2024 Description: adds a patient in sql
	 *
	 * @Paramaters: Patient p: patient to add
	 * 
	 * @Return Value: none Data Type: void Dependencies: Patient Throws/Exceptions:
	 * none
	 */
	public void addPatientBG(Patient p) {
		service.submit(new Runnable() {
			public void run() {
				IDnum = addPatient(p);
				System.out.println("patient added: " + IDnum);
			}
		});
	} // end addPatientBG

	/*
	 * Method Name: addPrescriptionBG Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: adds a prescription to sql
	 * in background
	 *
	 * @Paramaters: Prescription p: prescription to add
	 * 
	 * @Return Value: prescription id Data Type: int Dependencies: Prescription
	 * Throws/Exceptions: none
	 */
	public int addPrescriptionBG(Prescription p) {
		service.submit(new Runnable() {
			public void run() {
				IDnum = addPrescription(p);
				p.setID(IDnum); // id from SQL
			}
		});
		return p.getID();
	} // end addPresctiptionBG

	/*
	 * Method Name: update Author: John Brown Creation Date: January 15, 2024
	 * Modified Date: January 15, 2024 Description: generic update method for sql
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed thing
	 * 
	 * @Return Value: true if successful, false else Data Type: boolean
	 * Dependencies: none Throws/Exceptions: Exception e
	 */
	public boolean update(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to
		try {
			// SQL statement to update row
			statement.executeUpdate("UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE ID = " + ID);
			return true; // return true if successful
		} catch (Exception e) {
			// Error Handling
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj + ", ID "
					+ ID + " in update in SQLHelper");
			return false; // return false if unsuccessful
		} // end try-catch
	} // end update

	/*
	 * Method Name: updateInsurance Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: updates an insurance plan
	 * in sql
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed insurance
	 * 
	 * @Return Value: true if successful, false else Data Type: boolean
	 * Dependencies: none Throws/Exceptions: Exception e
	 */
	public boolean updateInsurance(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to

		try {
			// SQL Statement to update insurance row
			statement.executeUpdate(
					"UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE insuranceID = " + ID);
			return true; // return true if successful
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj
					+ ", insuranceID " + ID + " in update in SQLHelper");
			return false; // return false if unsuccessful
		} // end try-catch
	} // end updateInsurance

	/*
	 * Method Name: updatePrescription Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: updates a prescription in
	 * sql
	 *
	 * @Paramaters: String table: table in sql, String column: column in sql, Object
	 * obj: object to change, int ID: id of changed prescription
	 * 
	 * @Return Value: true if successful, false else Data Type: boolean
	 * Dependencies: none Throws/Exceptions: Exception e
	 */
	public boolean updatePrescription(String table, String column, Object obj, int ID) {
		// https://www.geeksforgeeks.org/java-database-connectivity-with-mysql/ for
		// pulling from
		// https://www.geeksforgeeks.org/how-to-commit-a-query-in-jdbc/?ref=ml_lbp for
		// pushing to
		try {
			// SQL Statement to update insurance row
			statement.executeUpdate(
					"UPDATE " + table + " SET " + column + " = \"" + obj + "\" WHERE prescriptionID = " + ID);
			return true; // return true if successful
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for table " + table + ", column " + column + ", object " + obj
					+ ", prescriptionID " + ID + " in update in SQLHelper");
			return false; // return false if unsuccessful
		} // end try-catch
	} // end updatePrescription

	/*
	 * Method Name: addPatient Author: John Brown Creation Date: January 15, 2024
	 * Modified Date: January 15, 2024 Description: adds a patient to sql
	 *
	 * @Paramaters: Patient p
	 * 
	 * @Return Value: patient id Data Type: PrescriptionList Dependencies: Patient
	 * Throws/Exceptions: Exception e
	 */
	public int addPatient(Patient p) {
		String name = p.getName(); // name of patient
		int birthDay = p.getDateOfBirthDay(); // patient birthday
		int birthMonth = p.getDateOfBirthMonth(); // patient birth month
		int birthYear = p.getBirthYear(); // patient birth year
		String address = p.getAddress(); // patient address
		String phoneNumber = p.getPhoneNumber(); // patient phone number
		String email = p.getEmail(); // patient email
		String healthCard = p.getHealthCardNumber(); // patient health card
		String additionalNotes = p.getAdditionalNotes(); // patient notes
		String familyDoctorName = p.getFamilyDoctorName(); // patient doctor name
		String familyDoctorAddress = p.getFamilyDoctorAddress(); // patient doctor address
		String familyDoctorPhoneNumber = p.getFamilyDoctorNumber(); // patient doctor phone number
		String familyDoctorFax = p.getFamilyDoctor().getFax(); // patient doctor fax
		String gender = p.getGender(); // patient gender
		double weight = p.getWeight(); // patient weight
		int ID = p.getId(); // patient ID

		try {
			// SQL statement to add patient
			statement.executeUpdate("INSERT INTO PatientInfo values ( " + ID + " , \"" + name + "\" , " + birthDay
					+ " , " + birthMonth + " , " + birthYear + " , \"" + address + "\" , \"" + phoneNumber + "\" , \""
					+ email + "\" , \"" + healthCard + "\" , \"" + additionalNotes + "\" , \"" + familyDoctorName
					+ "\" , \"" + familyDoctorAddress + "\" , \"" + familyDoctorPhoneNumber + "\" , \""
					+ familyDoctorFax + "\" , \"" + gender + "\" , " + weight + " )");
			return ID; // ID from SQL
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in addPatient in SQLHelper");
			return -1; // if unsuccessful
		} // end try-catch
	}// end addPatient

	/*
	 * Method Name: getPatient Author: John Brown Creation Date: January 15, 2024
	 * Modified Date: January 15, 2024 Description: gets a patient from sql
	 *
	 * @Paramaters: int ID
	 * 
	 * @Return Value: found patient Data Type: Patient Dependencies: Patient
	 * Throws/Exceptions: Exception e
	 */
	public Patient getPatient(int ID) {

		try {
			// get patient at ID
			ResultSet resultSet = statement.executeQuery("SELECT * FROM manageRx.PatientInfo WHERE ID = " + ID);
			Patient temp = new Patient(); // patient read from SQL
			// fill in patient info with SQL data
			temp.setId(resultSet.getInt("ID"));
			temp.setName(resultSet.getString("name"));
			temp.setDateOfBirthDay(resultSet.getInt("birthDay"));
			temp.setDateOfBirthMonth(resultSet.getInt("birthMonth"));
			temp.setBirthYear(resultSet.getInt("birthYear"));
			temp.setAddress(resultSet.getString("address"));
			temp.setPhoneNumber(resultSet.getString("phoneNumber"));
			temp.setEmail(resultSet.getString("email"));
			temp.setHealthCardNumber(resultSet.getString("healthCard"));
			temp.setAdditionalNotes(resultSet.getString("addtionalNotes")); // typo in SQL column name...
			temp.setFamilyDoctorName(resultSet.getString("familyDoctorName"));
			temp.setFamilyDoctorAddress(resultSet.getString("familyDoctorAddress"));
			temp.setFamilyDoctorNumber(resultSet.getString("familyDoctorPhoneNumber"));
			temp.setGender(resultSet.getString("gender"));
			temp.setWeight(Double.parseDouble(resultSet.getString("weight")));
			temp.getFamilyDoctor().setFax(resultSet.getString("familyDoctorFax"));
			return temp; // return new patient
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for getPatient in SQLHelper");
			return null; // if unsuccessful
		} // end try-catch
	} // end getPatient

	/*
	 * Method Name: getAllPatients Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: gets all patients from sql
	 *
	 * @Paramaters: none
	 * 
	 * @Return Value: list of all patients Data Type: PrescriptionList Dependencies:
	 * PatientList, Patient Throws/Exceptions: Exception e
	 */
	public PatientList getAllPatients() {
		PatientList pList = new PatientList(); // patient list to be filled
		try {
			// SQL statement
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PatientInfo");

			while (resultSet.next()) {
				Patient temp = new Patient(); // patient to add to list
				// fill in all data from SQL
				temp.setId(resultSet.getInt("ID"));
				temp.setName(resultSet.getString("name"));
				temp.setDateOfBirthDay(resultSet.getInt("birthDay"));
				temp.setDateOfBirthMonth(resultSet.getInt("birthMonth"));
				temp.setBirthYear(resultSet.getInt("birthYear"));
				temp.setAddress(resultSet.getString("address"));
				temp.setPhoneNumber(resultSet.getString("phoneNumber"));
				temp.setEmail(resultSet.getString("email"));
				temp.setHealthCardNumber(resultSet.getString("healthCard"));
				temp.setAdditionalNotes(resultSet.getString("additionalNotes"));
				temp.getFamilyDoctor().setName(resultSet.getString("familyDoctorName"));
				temp.getFamilyDoctor().setAddress(resultSet.getString("familyDoctorAddress"));
				temp.setFamilyDoctorNumber(resultSet.getString("familyDoctorPhoneNumber"));
				temp.setGender(resultSet.getString("gender"));
				temp.setWeight(resultSet.getDouble("weight"));
				temp.getFamilyDoctor().setFax(resultSet.getString("familyDoctorFax"));
				pList.insert(temp);
			} // end while
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for getAllPatients in SQLHelper");
		} // end try-catch
		return pList;
	} // end getAllPatients

	/*
	 * Method Name: addPrescription Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: adds a prescription to sql
	 *
	 * @Paramaters: Prescription p: prescription to add
	 * 
	 * @Return Value: prescription id Data Type: int Dependencies: Prescription
	 * Throws/Exceptions: Exception e
	 */
	public int addPrescription(Prescription p) {

		int patientID = p.getPatientID(); // ID of patient in SQL
		int numRefills = p.getRefills(); // refills
		int quantity = p.getQuantity(); // quantity
		String dosage = p.getDosage(); // dosage
		String instructions = p.getInstructions(); // instructions
		String prescribedDuration = p.getDuration(); // duration
		String datePrescribed = p.getDate(); // date
		String drugName = p.getName(); // drug name
		String DIN = p.getDIN(); // DIN
		String form = p.getForm(); // form
		String docName = p.getDocName(); // doctor name
		String docAddress = p.getDocAddress(); // doctor address
		String docPhone = p.getDocPhone(); // doctor phone
		String docFax = p.getDocFax(); // doctor fax
		boolean current = p.getCurrent(); // is current?
		int curr; // current as int (0 or 1)
		// "boolean" in SQL
		if (current) {
			curr = 1;
		} else {
			curr = 0;
		}

		try {
			// SQL statement to get ID
			ResultSet resultSet = statement.executeQuery(
					"SELECT prescriptionID FROM PrescriptionInfo WHERE prescriptionID = (SELECT MAX(prescriptionID) FROM PrescriptionInfo)");
			resultSet.next();
			int ID = resultSet.getInt("prescriptionID") + 1; // ID from SQL
			// SQL statement to add prescription
			statement.executeUpdate("INSERT INTO PrescriptionInfo values ( " + patientID + " , \"" + numRefills
					+ "\" , " + quantity + " , \"" + dosage + "\" , \"" + instructions + "\" , \"" + prescribedDuration
					+ "\" , \"" + datePrescribed + "\" , \"" + drugName + "\" , \"" + DIN + "\" , \"" + form + "\" , "
					+ ID + " , " + curr + " , \"" + docName + "\" , \"" + docAddress + "\" , \"" + docPhone + "\" , \""
					+ docFax + "\")");
			return ID; // ID of prescription from SQL
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in addPrescription in SQLHelper");
			return -1;
		} // end try-catch
	} // end addPresctiption

	/*
	 * Method Name: getAllPrescriptions Author: John Brown Creation Date: January
	 * 15, 2024 Modified Date: January 15, 2024 Description: gets all prescriptions
	 * fromm sql
	 *
	 * @Paramaters: none
	 * 
	 * @Return Value: list of all prescriptions Data Type: PrescriptionList
	 * Dependencies: PrescriptionList, Prescription Throws/Exceptions: Exception e
	 */
	public PrescriptionList getAllPrescriptions() {
		PrescriptionList pList = new PrescriptionList(); // list of prescriptions to fill

		try {
			// SQL statement to get all prescriptions
			ResultSet resultSet = statement.executeQuery("SELECT * FROM PrescriptionInfo");
			// while there are prescriptions in SQL
			while (resultSet.next()) {
				Prescription temp = new Prescription(); // Prescription to add
				// fill prescription data
				temp.setPatientID(resultSet.getInt("patientID"));
				temp.setRefills(resultSet.getInt("numRefills"));
				temp.setQuantity(resultSet.getInt("quantity"));
				temp.setDosage(resultSet.getString("dosage"));
				temp.setInstructions(resultSet.getString("instructions"));
				temp.setDuration(resultSet.getString("prescribedDuration"));
				temp.setDate(resultSet.getString("datePrescribed"));
				temp.setBrandName(resultSet.getString("drugName"));
				temp.setDIN(resultSet.getString("DIN"));
				temp.setForm(resultSet.getString("form"));
				temp.setID(resultSet.getInt("prescriptionID"));
				temp.setCurrent(resultSet.getInt("current"));
				temp.setDocName(resultSet.getString("docPrescribedName"));
				temp.setDocPhone(resultSet.getString("docPrescribedPhone"));
				temp.setDocFax(resultSet.getString("docPrescribedFax"));
				temp.setDocAddress(resultSet.getString("docPrescribedAddress"));
				pList.insert(temp);
			} // end while
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for getAllPrescriptions in SQLHelper");
		} // end try-catch
		return pList;
	} // end getAllPrescriptions

	/*
	 * Method Name: getAllInsurance Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: gets all insurance from sql
	 *
	 * @Paramaters: none
	 * 
	 * @Return Value: linked list of insurance Data Type: LinkedList<Insurance>
	 * Dependencies: LinkedList, Insurance Throws/Exceptions: Exception e
	 */
	public LinkedList<Insurance> getAllInsurance() {
		LinkedList<Insurance> insuranceList = new LinkedList<Insurance>(); // list of insurance to fill
		try {
			// SQl statement to get all insurance
			ResultSet resultSet = statement.executeQuery("SELECT * FROM InsuranceInfo");
			// while theres more insurance entries
			while (resultSet.next()) {
				Insurance temp = new Insurance(); // insurance to add to list
				// get info from sql and fill temp
				temp.setPatientID(resultSet.getInt("patientID"));
				temp.setCompany(resultSet.getString("company"));
				temp.setNumber(resultSet.getInt("number"));
				temp.setID(resultSet.getInt("insuranceID"));
				insuranceList.add(temp); // add insurance to the list
			}
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " for getAllInsurance in SQLHelper");
		} // end try-catch
		return insuranceList;
	} // end getAllInsurance

	/*
	 * Method Name: addInsurance Author: John Brown Creation Date: January 15, 2024
	 * Modified Date: January 15, 2024 Description: adds an insurance plan to sql
	 *
	 * @Paramaters: String company: company added, int number: number added, int
	 * patientID: patient id for insurance plan
	 * 
	 * @Return Value: insurance id Data Type: int Dependencies: none
	 * Throws/Exceptions: Exception e
	 */
	public int addInsurance(String company, int number, int patientID) {
		try {
			// SQL statement to get ID
			ResultSet resultSet = statement.executeQuery(
					"SELECT insuranceID FROM InsuranceInfo WHERE insuranceID = (SELECT MAX(insuranceID) FROM InsuranceInfo)");
			resultSet.next();
			int ID = resultSet.getInt("insuranceID") + 1; // ID from SQL
			// add insurance to SQL
			statement.executeUpdate("INSERT INTO InsuranceInfo values ( " + patientID + " , \"" + company + "\" , "
					+ number + " , " + ID + " )");
			return ID; // ID in SQL
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in addInsurance in SQLHelper");
			return -1;
		} // end try-catch
	} // end addInsurance

	/*
	 * Method Name: removeInsurance Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: removes an insurance plan
	 * from sql
	 *
	 * @Paramaters: int ID: id of insurance to remove
	 * 
	 * @Return Value: none Data Type: void Dependencies: none Throws/Exceptions:
	 * none
	 */
	public void removeInsurance(int ID) {
		try {
			// remove insurance SQL Statement
			statement.executeUpdate("DELETE FROM InsuranceInfo WHERE insuranceID = " + ID);
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in addPrescription in SQLHelper");
		} // end try-catch
	} // end removeInsurance

	public DrugStockLinkedList getAllDrugStock() {
		DrugStockLinkedList stock = new DrugStockLinkedList(); // DrugStockLinkedList to fill
		try {
			// get all stock rows
			ResultSet resultSet = statement.executeQuery("SELECT * FROM DrugStock");
			while (resultSet.next()) {
				// create new DrugStock
				DrugStock drug = new DrugStock(resultSet.getString("DIN"), resultSet.getInt("quantity"),
						resultSet.getInt("threshold"), resultSet.getInt("ID"));
				stock.insert(drug, false); // add to the list
			} // end while
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in getAllDrugStock in SQLHelper");
		} // end try-catch
		return stock;
	} // end getAllDrugStock

	public int[] getAllContainerStock() {
		int[] containers = new int[3]; // array to store container size
		try {
			// SQL statement to get container stock
			ResultSet resultSet = statement.executeQuery("SELECT * FROM ContainerStock");
			resultSet.next();
			// read SQl data
			containers[0] = resultSet.getInt("numSmall");
			containers[1] = resultSet.getInt("numMed");
			containers[2] = resultSet.getInt("numLarge");
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in getAllContainerStock in SQLHelper");
		} // end try-catch
		return containers;
	} // end getAllContainerStock

	public void updateContainerStock(String column, int newNum) {
		try {
			// SQl statement to update container stock
			statement.executeUpdate("UPDATE ContainerStock SET " + column + " =  " + newNum);
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + "in updateContainerStock in SQLHelper");
		} // end try-catch
	} // end updateContainerStock

	public int addDrugStock(DrugStock drug) {
		try {
			// SQl statement to get ID
			ResultSet resultSet = statement
					.executeQuery("SELECT ID FROM DrugStock WHERE ID = (SELECT MAX(ID) FROM DrugStock)");
			resultSet.next();
			int ID = resultSet.getInt("ID") + 1; // ID from SQL
			// SQL statement to add DrugStock
			statement.executeUpdate("INSERT INTO DrugStock values ( " + ID + " , \"" + drug.getDrugDIN() + "\" , "
					+ drug.getNumInStock() + " , " + drug.getStockThreshold() + " )");
			return ID; // ID from SQL
		} catch (Exception e) {
			// Error handling
			logErrors.log(e.getMessage() + " in addDrugStock in SQLHelper");
			return -1;
		} // end try-catch
	} // end addDrugStock

	/*
	 * Method Name: getAllUsernames Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: gets all usernames from sql
	 *
	 * @Paramaters: none
	 * 
	 * @Return Value: string array of usernames Data Type: String[] Dependencies:
	 * LinkedList Throws/Exceptions: none
	 */
	public String[] getAllUsernames() {
		LinkedList<String> usernames = new LinkedList<String>(); // list of usernames

		try {
			// SQl statement get all usernames
			ResultSet resultSet = statement.executeQuery("SELECT * FROM LoginInfo");
			while (resultSet.next()) {
				usernames.add(resultSet.getString("username")); // add username to list
			} // end while
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " in getAllUsernames in SQLHelper");
		} // end try-catch

		// convert to array
		String[] users = new String[usernames.size()];
		for (int i = 0; i < users.length; i++) {
			users[i] = usernames.get(i);
		} // end for
		return users;
	} // end getAllUsernames

	/*
	 * Method Name: getAllPasswords Author: John Brown Creation Date: January 15,
	 * 2024 Modified Date: January 15, 2024 Description: gets all passwords from sql
	 *
	 * @Paramaters: none
	 * 
	 * @Return Value: string array of passwords Data Type: String[] Dependencies:
	 * LinkedList Throws/Exceptions: none
	 */
	public String[] getAllPasswords() {
		LinkedList<String> passwords = new LinkedList<String>(); // list of password hashes

		try {
			// get all pw hashes
			ResultSet resultSet = statement.executeQuery("SELECT * FROM LoginInfo");
			while (resultSet.next()) {
				passwords.add(resultSet.getString("password")); // add to list
			}
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in getAllPasswords in SQLHelper");
		} // end try-catch

		// convert to array
		String[] passes = new String[passwords.size()];
		for (int i = 0; i < passes.length; i++) {
			passes[i] = passwords.get(i);
		} // end for
		return passes;
	} // end getAllPasswords

	/*
	 * Method Name: addOrder Author: Gavin Micks Creation Date: January 18,
	 * 2024 Modified Date: January 19, 2024 Description: 
	 *
	 * @Paramaters: String DIN - DIN to add, int qty - quantity of drug, String size - size of container, String dosage - dosage, int containerQty - quantity of containers
	 * 
	 * @Return Value: int - ID Data Type: int Dependencies:
	 * SQL Throws/Exceptions: none
	 */
	public int addOrder(String DIN, int qty, String size, String dosage, int containerQty) {
		try {
			// SQl statement get ID
			ResultSet resultSet = statement
					.executeQuery("SELECT ID FROM OrderInfo WHERE ID = (SELECT MAX(ID) FROM OrderInfo)");
			resultSet.next();
			int ID = resultSet.getInt("ID") + 1; // SQL ID
			// SQL statement add order
			statement.executeUpdate(
					"INSERT INTO OrderInfo values ( " + ID + " , \"" + DIN + "\" , " + qty + " , \"" + size + "\" , \""
							+ dosage + "\" , \"" + containerQty + "\" , \"example@supplier.ca\" , " + 0 + ")"); // testme
			return ID; // SQL ID
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in Order in SQLHelper");
			return -1;
		} // end try-catch
	} // end addOrder

	/*
	 * Method Name: getAllOrders Author: Gavin Micks Creation Date: January 18,
	 * 2024 Modified Date: January 19, 2024 Description: 
	 *
	 * @Paramaters: n/A
	 * 
	 * @Return Value: all orders in SQL Data Type: String[][] Dependencies:
	 * SQL, ArrayList Throws/Exceptions: none
	 */
	public String[][] getAllOrders() {
		ArrayList<String[]> al = new ArrayList<>(); // list of orders
		try {
			// get all orders
			ResultSet resultSet = statement.executeQuery("SELECT * FROM OrderInfo");
			while (resultSet.next()) {
				// fill order info
				String[] entry = new String[7];
				entry[0] = Integer.toString(resultSet.getInt("ID"));
				entry[1] = resultSet.getString("DIN");
				entry[2] = Integer.toString(resultSet.getInt("drugQuantity"));
				entry[3] = resultSet.getString("size");
				entry[4] = resultSet.getString("dosage");
				entry[5] = Integer.toString(resultSet.getInt("containerQuantity"));
				entry[6] = Integer.toString(resultSet.getInt("delivered"));
				al.add(entry); // add order info to list
			} // end while
		} catch (Exception e) {
			// error handling
			logErrors.log(e.getMessage() + " in getAllContainerStock in SQLHelper");
		} // end try-catch

		// convert to array
		String[][] ret = new String[al.size()][7];
		for (int i = 0; i < al.size(); i++) {
			ret[i] = al.get(i);
		} // end for

		return ret;
	}// end getAllOrders
} // end SQLHelper