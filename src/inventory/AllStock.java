/**
***********************************************
 @Name: AllStock
 @Author           : Christina Wong
 @Creation Date    : December 12, 2023
 @Modified Date	   : January 10, 2024
   @Description    : 
   
***********************************************
*/
package inventory;

import java.util.*;
import java.io.IOException;
import utilities.logErrors;

public class AllStock {

	private int totalNum; // total stock
	private DrugStockLinkedList drugsList; // list of drugs in stock
	
	// total counts for stock
	private int numSmallContainers;
	private int numMediumContainers;
	private int numLargeContainers;
	private int numBags;
	Scanner ui = new Scanner(System.in); // delete when doing ui
	
	public AllStock(int small, int medium, int large, int bags) {
		// set totalNum
		this.drugsList = new DrugStockLinkedList();
		numSmallContainers = small;
		numMediumContainers = medium;
		numLargeContainers = large;
		numBags = bags;
	} // end AllStock constructor
	
	public AllStock() {
		this.drugsList = new DrugStockLinkedList();
		totalNum = 0;
		numSmallContainers = 0;
		numMediumContainers = 0;
		numLargeContainers = 0;
		numBags = 0;
	} // end AllStock blank constructor
	
	/** Method Name: orderMore
	* @Author Christina Wong 
	* @Date December 12, 2023
	* @Modified December 13, 2023
	* @Description This .
	* @Parameters  String drug, the drug to be ordered 
	* @Returns N/A
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	public void orderMore(String drug) {
		// hopefully this works with the OrderUI, not sure exactly how we were going to do this or if we need it
		// drugToOrder, containerToorder, dosage, nuofdrug, num of container, order drug or order container 				
				
	} // end orderMore
	
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
	
	// current UML for StockUI has an option to viewStock
	// adjust UI to have radio buttons or dropbox for user to choose how to search stock to view
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
			System.out.println("Drug is not found in inventory.");
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
			System.out.println("Drug is not found in inventory.");
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
		System.out.println("\nInventory Information:");
		drugsList.printDrugInfo(printDrug);		
	} // end drugSearch
	
	// needs to interact with stock ui
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
	* Dependencies: Integer
	* Throws/Exceptions: NumberFormatException, utilities logErrors
    */
	public static boolean isInteger(String num)
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
	* @Modified January 10, 2024
	* @Description When a shipment arrives, the current inventory is checked to see if the drug is in stock and either updates the stock information or adds a new stock of drug.
	* @Parameters int newStock, the quantity of the shipment; String arrivalDIN, the DIN of the drug arriving; String classDrug, class of the drug; String newThreshold, the drug threshold (is blank if the drug has been added to inventory previously)
	* @Returns void
	* Dependencies: DrugStock, Drug, isInteger
	* Throws/Exceptions: N/A
    */
	public void updateStock(int newStock, String arrivalDIN, String classDrug, String newThreshold) throws IOException{
		boolean isStocked = drugsList.checkStockDIN(arrivalDIN);
		boolean isValid = false;
		// if this is the inventory's first shipment of the drug
		// should not run if parameter threshold is ""
		if(isStocked == false) { 		
			// needs to be in StockUI
			// System.out.println("Enter threshold:");
			// will have to be revised to work with swing

			while(isValid == false) {
				// will need to be moved to StockUI and revised for input in the setThresholdNum JTextField:
				// newThreshold = setThresholdNum.getText().trim(); 

				if(isInteger(newThreshold)) {
					isValid = true;
					DrugStock newDrugStock = new DrugStock(arrivalDIN, 0, Integer.parseInt(newThreshold));	
					drugsList.insert(newDrugStock);
				} // end if
				else {
					// JOptionPane.showMessageDialog(frame, "Threshold must be an integer","ERROR", JOptionPane.WARNING_MESSAGE); // frame is the name of the frame	

					System.out.println("Enter threshold:");
					newThreshold = ui.nextLine();
				} // end else
			} // end while

		} // end if
		
		drugsList.newShipment(arrivalDIN, newStock);
		
	} // end updateStock
	
	// must be called in Patient in method addActivePrescription
	// should be called in whichever method in Prescription or Patient is called to fill refills
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
	* @Modified December 19, 2023
	* @Description This prints inventory usage information for a specific drug.
	* @Parameters  String DIN, DIN of drug to check the inventory for.
	* @Returns void
	* Dependencies: DrugStockLinkedList
	* Throws/Exceptions: N/A
    */
	public void viewUsage(String DIN) {
		drugsList.viewStockUsage(DIN);
	} // end viewUsage
	
	public void viewFullInventory() {
		drugsList.viewFullInventory();
	}
	
} // end AllStock