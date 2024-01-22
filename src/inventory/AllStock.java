/**
***********************************************
 @Name: AllStock
 @Author           : Christina Wong
 @Creation Date    : December 12, 2023
 @Modified Date	   : January 20, 2024
   @Description    : This is the overall stock inventory management that allows you to search through and modify the inventory.
   
***********************************************
*/
package inventory;

import java.util.*;
import java.io.IOException;
import utilities.logErrors;
import utilities.SQLHelper;

public class AllStock {

	private int totalNum; // total stock
	private DrugStockLinkedList drugsList; // list of drugs in stock
	
	// total counts for stock
	private int numSmallContainers;
	private int numMediumContainers;
	private int numLargeContainers;
	private int numBags;
	
	// used to link with MySQL
	private SQLHelper helper = new SQLHelper();
	
	// scanner, mainly to trace and debug
	Scanner ui = new Scanner(System.in);
	
	public AllStock(int total, int small, int medium, int large, int bags) {
		this.drugsList = helper.getAllDrugStock();
		totalNum = total;
		numSmallContainers = small;
		numMediumContainers = medium;
		numLargeContainers = large;
		numBags = bags;
	} // end AllStock constructor
	
	public AllStock(DrugStockLinkedList drugList) {
		this.drugsList = drugList;
		totalNum = 0;
		numSmallContainers = 0;
		numMediumContainers = 0;
		numLargeContainers = 0;
		numBags = 0;
	} // end AllStock constructor
	
	public AllStock() {
		this.drugsList = helper.getAllDrugStock();
		totalNum = 0;
		numSmallContainers = 0;
		numMediumContainers = 0;
		numLargeContainers = 0;
		numBags = 0;
	} // end AllStock blank constructor

// not used
//	public void orderMore(String drug) {
//			
//	} // end orderMore
	
	public int getTotal() {
		return totalNum;
	} // end getTotal
	
	public void setTotal(int total) {
		totalNum = total;
	} // end setTotal
	
	public int getNumSmall() {
		return numSmallContainers;
	} // end getNumSmall
	
	public void setNumSmall(int small) {
		this.numSmallContainers = small;
	} // end setNumSmall
	
	public int getNumMedium() {
		return numMediumContainers;
	} // end getNumMedium
	
	public void setNumMedium(int medium) {
		this.numMediumContainers = medium;
	} // end setNumMedium
	
	public int getNumLarge() {
		return numLargeContainers;
	} // end getNumLarge
	
	public void setNumLarge(int large) {
		this.numLargeContainers = large;
	} // end setNumLarge
	
	public int getNumBags() {
		return numBags;
	} // end getNumBags
	
	public void setNumBags(int bags) {
		this.numBags = bags;
	} // end setNumBags
	
	/** Method Name: searchByDIN
	* @Author Christina Wong 
	* @Date December 15, 2023
	* @Modified December 16, 2023
	* @Description This searches the inventory for a certain drug, by DIN.
	* @Parameters String drugDIN, DIN of drug to find
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public void searchByDIN(String drugDIN) {
		boolean stockFound;
		stockFound = drugsList.checkStockDIN(drugDIN);
		if(stockFound) {
			drugSearch(drugDIN);			
		} // end if
		else {
			
		} // end else
	} // end searchByDIN
	
	/** Method Name: searchByName
	* @Author Christina Wong 
	* @Date December 15, 2023
	* @Modified December 16, 2023
	* @Description This searches the inventory for a certain drug, by name.
	* @Parameters String drugName, the name of the drug to find
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public void searchByName(String drugName) {
		String searchDIN = "";
		searchDIN = drugsList.checkStockName(drugName);		
		if(searchDIN.equals("")) {
		} // end if
		else {
			drugSearch(searchDIN);
		} // end else
	} // end searchByName
	
	/** Method Name: drugSearch
	* @Author Christina Wong 
	* @Date December 16, 2023
	* @Modified December 16, 2023
	* @Description This prints the inventory information for a specific drug.
	* @Parameters String printDrug, the DIN of the drug to print info for
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public void drugSearch(String printDrug) {
		drugsList.printDrugInfo(printDrug);		
	} // end drugSearch
	
	/** Method Name: shipmentArrival
	* @Author Christina Wong 
	* @Date December 16, 2023
	* @Modified December 16, 2023
	* @Description This updates a drug's stock information when a shipment arrives.
	* @Parameters int newStock, the quantity of the shipment; String arrivalDIN, the DIN of the drug arriving; String nameGen, the generic name of the drug; String nameBrand, the brand name of the drug (could be ""); String classDrug, class of the drug; int dosage, dosage of drug
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: IOException
    */
	public void shipmentArrival(int newStock, String arrivalDIN, String classDrug) throws IOException {
		updateStock(newStock, arrivalDIN, classDrug, "");
	} // end shipmentArrival	
	
	/** Method Name: isInteger
	* @Author Christina Wong 
	* @Date December 29, 2023
	* @Modified January 9, 2024
	* @Description This checks if input was an integer.
	* @Parameters String num, the user's input
	* @Returns boolean true if num is an integer, false if num is not an integer
	* Dependencies: Integer, utilities logErrors
	* Throws/Exceptions: NumberFormatException
    */
	public boolean isInteger(String num)
	{
		if (num == null)
		{
			return false;
		} // end if
		try
		{
			int checkInt = Integer.parseInt(num);
		} catch (NumberFormatException nfe)
		{
			// input was not an integer
			logErrors.log("NumberFormatException " + String.valueOf(nfe));
			return false;
		} // end try catch
		return true;
	} //end isInteger method
	
	/** Method Name: updateStock
	* @Author Christina Wong 
	* @Date December 16, 2023
	* @Modified January 12, 2024
	* @Description When a shipment arrives, the current inventory is checked to see if the drug is in stock and either updates the stock information or adds a new stock of drug.
	* @Parameters int newStock, the quantity of the shipment; String arrivalDIN, the DIN of the drug arriving; String classDrug, class of the drug; String newThreshold, the drug threshold (is blank if the drug has been added to inventory previously)
	* @Returns void
	* Dependencies: DrugStockLinkedList, DrugStock, Drug, isInteger
	* Throws/Exceptions: IOException
    */
	public void updateStock(int newStock, String arrivalDIN, String classDrug, String newThreshold) throws IOException{
		boolean isStocked = drugsList.checkStockDIN(arrivalDIN); // if drug already exists in inventory
		boolean isValid = false; // if threshold value is valid
		
		// if this is the inventory's first shipment of the drug
		if(isStocked == false) { 		

			while(isValid == false) {
			
				if(isInteger(newThreshold)) {
					isValid = true;
					DrugStock newDrugStock = new DrugStock(arrivalDIN, 0, Integer.parseInt(newThreshold), 0);	
					drugsList.insert(newDrugStock, true);
				} // end if
				else {
					newThreshold = ui.nextLine();
				} // end else
			} // end while

		} // end if
		
		drugsList.newShipment(arrivalDIN, newStock);
		
	} // end updateStock
	
	/** Method Name: thresholdCheck
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 18, 2024
	* @Description This checks drug stock amounts compared to their threshold levels.
	* @Parameters String DIN, the drug to check the threshold of
	* @Returns int atThreshold, 0 if the drug is above threshold, 1 if the drug is below threshold, 2 if the drug is exactly at the threshold
	* Dependencies: DrugStockLinkedList, isInteger
	* Throws/Exceptions: N/A
    */
	public int thresholdCheck(String DIN) {
		if(isInteger(DIN) == false) {
			DIN = drugsList.getDINForName(DIN);
		} // end if
		int atThreshold = drugsList.checkThreshold(DIN);
		return atThreshold;
	} // end thresholdCheck
	
	/** Method Name: fillPrescription
	* @Author Christina Wong 
	* @Date December 26, 2023
	* @Modified December 26, 2023
	* @Description This updates a drug's stock information when a prescription is filled.
	* @Parameters  String DIN, DIN of drug for prescription being filled; int fillAmount, amount of drug prescribed
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public void fillPrescription(String DIN, int fillAmount) {
		drugsList.fillPrescription(DIN, fillAmount);
	} // end fillPrescription
	
	/** Method Name: viewUsage
	* @Author Christina Wong 
	* @Date December 19, 2023
	* @Modified January 17, 2024
	* @Description This prints inventory usage information for a specific drug.
	* @Parameters  String DIN, DIN of drug to check the inventory for.
	* @Returns boolean found, true if the drug is found, false if the drug is not found
	* Dependencies: DrugStockLinkedList, isInteger
	* Throws/Exceptions: N/A
    */
	public boolean viewUsage(String DIN) {
		if(isInteger(DIN) == false) {
			DIN = drugsList.getDINForName(DIN);
		} // end if
		boolean found = drugsList.viewStockUsage(DIN);
		return found;
	} // end viewUsage
	
	/** Method Name: viewFullInventory
	* @Author Christina Wong 
	* @Date January 12, 2024
	* @Modified January 12, 2024
	* @Description This prints current inventory stats for the full inventory.
	* @Parameters  N/A
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public String[][] viewFullInventory() {
		String[][] fullInventory = drugsList.viewFullInventory();
		return fullInventory;
	} // end viewFullInventory
	
	/** Method Name: viewDrugInventory
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 17, 2024
	* @Description This retrieves a drug's stock information.
	* @Parameters  String DIN, drug to return inventory information for.
	* @Returns String[][] stockInventory, array of stock inventory information
	* Dependencies: DrugStockLinkedList, isInteger
	* Throws/Exceptions: N/A
    */
	public String[][] viewDrugInventory(String DIN){
		if(isInteger(DIN) == false) {
			DIN = drugsList.getDINForName(DIN);
		} // end if
		String[][] stockInventory = drugsList.getStockUsage(DIN);
		return stockInventory;
	} // end viewDrugInventory
	
	/** Method Name: changeThreshold
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 17, 2024
	* @Description This changes the threshold of the specified drug.
	* @Parameters  String DIN, drug to change threshold of; int newThreshold, the drug's new threshold
	* @Returns void
	* Dependencies: DrugStockLinkedList, isInteger
	* Throws/Exceptions: N/A
    */
	public void changeThreshold(String DIN, int newThreshold) {
		if(isInteger(DIN) == false) {
			DIN = drugsList.getDINForName(DIN);
		} // end if
		drugsList.setNewThreshold(DIN, newThreshold);
	} // end changeThreshold
	
} // end AllStock