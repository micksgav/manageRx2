/**
***********************************************
 @Name: DrugStockLinkedList
 @Author           : Christina Wong
 @Creation Date    : December 13, 2023
 @Modified Date	   : January 20, 2024
   @Description    : This is a linked list of DrugStock nodes with methods to search through and modify the list and its nodes.
   
***********************************************
*/
package inventory;
import utilities.*;

public class DrugStockLinkedList {

	private static class Node {
		DrugStock drugStock;		
		Node next;
	} // end Node
	
	private Node head; // pointer to first node

	/** Method Name: find
	* @Author Kyle McKay
	* @Date Unknown
	* @Modified December 15, 2023
	* @Description This checks if a drug already exists in the inventory.
	* @Parameters  DrugStock searchDrug, the item that is being searched for
	* @Returns boolean true if found, false if not found
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public boolean find(DrugStock searchDrug) {
		Node runner; // pointer to traverse list
		runner = head;

		while (runner != null) {
			if (runner.drugStock.equals(searchDrug))
				return true;
			runner = runner.next;
		} // end while

		// if the drug is never found
		return false;
	} // end find()
	
	/** Method Name: checkStockDIN
	* @Author Christina Wong 
	* @Date December 15, 2023
	* @Modified December 15, 2023
	* @Description This searches the inventory by drug DIN.
	* @Parameters  String searchDIN, the DIN of the drug to search for 
	* @Returns boolean true if found, false if not found
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public boolean checkStockDIN(String searchDIN) {
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(searchDIN)) {
				return true;
			} // end if
			runner = runner.next;
		} // end while	
		return false;
	} // end checkStock
	
	/** Method Name: checkStockName
	* @Author Christina Wong 
	* @Date December 15, 2023
	* @Modified December 16, 2023
	* @Description This searches the inventory by drug name.
	* @Parameters  String searchName, the brand or generic name of the drug to search for 
	* @Returns String of the drug DIN if found, empty string if not found
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public String checkStockName(String searchName) {
		Node runner;
		runner = head;
		
		while(runner != null) {
			if(runner.drugStock.getDrugName().equals(searchName)) {
				return runner.drugStock.getDrugDIN();
			} // end if
			runner = runner.next;
		} // end while
		
		return "";
	} // end checkStockName
	
	/** Method Name: printDrugInfo
	* @Author Christina Wong 
	* @Date December 16, 2023
	* @Modified December 16, 2023
	* @Description This prints the information of a specific drug.
	* @Parameters  String DINString, DIN of drug being printed
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void printDrugInfo(String DINString) {
		Node runner = head;
		
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DINString)) {
				System.out.print("Drug: " + runner.drugStock.getDrugName());
				System.out.println();
				
				System.out.println("\nDIN: " + runner.drugStock.getDrugDIN());
				System.out.println("\nDrug Class: " + runner.drugStock.getDrug().getDrugClass());
				System.out.println("Current stock: " + runner.drugStock.getNumInStock());
				System.out.println("Current threshold: " + runner.drugStock.getStockThreshold());
				System.out.println("\nStock is " + (runner.drugStock.getNumInStock() - runner.drugStock.getStockThreshold()) + " away from threshold.");
				
			} // end if
			runner = runner.next;
		} // end while
	} // end printDrugInfo
	
	/** Method Name: insert
	* @Author Kyle McKay
	* @Date Unknown
	* @Modified January 19, 2023
	* @Description This adds a new drug to the stock, in sequential order based on DIN.
	* @Parameters  DrugStock insertDrugStock, drug added to linked list; boolean saveToSQL, true if the data is added to the SQL table, false if the data came from the table
	* @Returns void
	* Dependencies: DrugStock, SQLHelper addDrugStock
	* Throws/Exceptions: N/A
    */
	public void insert(DrugStock insertDrugStock, boolean saveToSQL) {
		SQLHelper helper = new SQLHelper();
		Node newNode; // a node to contain the new item.
		newNode = new Node();
		newNode.drugStock = insertDrugStock;

		if (head == null) {
			head = newNode;

		} // end if

		else if (Integer.parseInt(head.drugStock.getDrugDIN()) > Integer.parseInt(newNode.drugStock.getDrugDIN())) {
			newNode.next = head;
			head = newNode;
		} // end if

		else {
			Node runner; // A node for traversing the list.
			Node previous; // Always points to the node preceding runner.
			runner = head.next; // Start by looking at the SECOND position.
			previous = head;
			while (runner != null && Integer.parseInt(runner.drugStock.getDrugDIN()) < Integer.parseInt(insertDrugStock.getDrugDIN())) {
				previous = runner;
				runner = runner.next;
			} // end while
			newNode.next = runner; // Insert newNode after previous.
			previous.next = newNode;

		} // end else
				
		if(saveToSQL == true) {
			int ID = helper.addDrugStock(insertDrugStock);
			insertDrugStock.setID(ID);
		} // end if

		// printDINs(); // was used for testing purposes
	} // end insert()
	
	/** Method Name: fillPrescription
	* @Author Christina Wong 
	* @Date December 26, 2023
	* @Modified December 27, 2023
	* @Description This adjusts stock amounts when a prescription is filled.
	* @Parameters  String DIN, DIN of drug for prescription being filled; int amount, quantity of drug prescribed
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void fillPrescription(String DIN, int amount) {
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				runner.drugStock.removeFromStock(amount);
				break;
			} // end if
			runner = runner.next;
		} // end while
	} // end fillPrescription
	
	/** Method Name: newShipment
	* @Author Christina Wong 
	* @Date December 22, 2023
	* @Modified December 24, 2023
	* @Description This adjusts stock amounts when a new shipment arrives.
	* @Parameters  String arrivalDIN, DIN of drug that arrived; int newStock, amount of stock that arrives
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void newShipment(String arrivalDIN, int newStock) {
		// if inventory already has some of the drug in stock
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(arrivalDIN)) {
				runner.drugStock.addToStock(newStock);
				break;
			} // end if
			runner = runner.next;
		} // end while
	} // end newShipment
	
	/** Method Name: printDINs
	* @Author Christina Wong 
	* @Date December 18, 2023
	* @Modified December 19, 2023
	* @Description This prints the DINs of all drugs currently in stock, was used in testing code.
	* @Parameters  N/A
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void printDINs() {
		Node runner;
		runner = head;
		while(runner != null) {
			runner = runner.next;
		} // end while
	} // end printDINS
	
	/** Method Name: viewStockUsage
	* @Author Christina Wong 
	* @Date December 19, 2023
	* @Modified January 17, 2023
	* @Description This checks the current inventory for a drug and, if found, prints inventory usage information for a specific drug.
	* @Parameters  String DIN, DIN of drug to view information on
	* @Returns boolean found, true if the drug is found in the inventory, false if it is not found
	* Dependencies: DrugStock
	* Throws/Exceptions: error message, if DIN is not found
    */
	public boolean viewStockUsage(String DIN) {
		Node runner;
		runner = head;
		boolean found = false;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				runner.drugStock.viewUsage();
				found = true;
				return true;
			} // end if
			runner = runner.next;
		} // end while
		if(found == false) {
			return false;
		} // end if
		
		return found;
	} // end viewStockUsage
	
	/** Method Name: getStockUsage
	* @Author Christina Wong 
	* @Date January 16, 2024
	* @Modified January 17, 2024
	* @Description This retrieves the array of stock usage information of a specified drug.
	* @Parameters  String DIN, DIN of drug to retrieve information on
	* @Returns String[][] stockUsage, the drug's stock usage
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public String[][] getStockUsage(String DIN){
		Node runner;
		runner = head;
		String[][] stockUsage = null;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				stockUsage = runner.drugStock.getStockChanges();
			} // end if
			runner = runner.next;
		} // end while
		return stockUsage;
	} // end getStockUsage
	
	/** Method Name: getDINForName
	* @Author Christina Wong 
	* @Date January 16, 2024
	* @Modified January 17, 2024
	* @Description This takes the name of a drug and returns its DIN if it exists in the inventory.
	* @Parameters  String name, the name of the drug to return the corresponding DIN of
	* @Returns String drugDIN, the corresponding DIN
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public String getDINForName(String name) {
		String drugDIN = "";
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugName().toLowerCase().equals(name.toLowerCase())) {
				drugDIN = runner.drugStock.getDrugDIN();
			} // end if
			runner = runner.next;
		} // end while
		return drugDIN;
	} // end getDINForName
	
	/** Method Name: setNewThreshold
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 17, 2024
	* @Description This changes the threshold of the specified drug.
	* @Parameters  String DIN, drug to change threshold of; int newThreshold, the drug's new threshold
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void setNewThreshold(String DIN, int newThreshold) {
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				runner.drugStock.setStockThreshold(newThreshold);
			} // end if
			runner = runner.next;
		} // end while
	} // end setNewThreshold
	
	/** Method Name: viewFullInventory
	* @Author Christina Wong 
	* @Date January 12, 2024
	* @Modified January 12, 2024
	* @Description This prints current inventory stats for every drug in the inventory.
	* @Parameters  N/A
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public String[][] viewFullInventory() {
		int nodeCount = countNodes();
		String[][] allInventory = new String[nodeCount][3];
		int lineCount = 0;
		Node runner;
		runner = head;
		while(runner != null) {
			allInventory[lineCount][0] = runner.drugStock.getDrugName();
			if(runner.drugStock.getDrugName().length() < 8) {
			} // end if
			allInventory[lineCount][1] = runner.drugStock.getDrugDIN();
			allInventory[lineCount][2] = String.valueOf(runner.drugStock.getNumInStock());	
			lineCount++;
			runner = runner.next;
		} // end while
		
		return allInventory;
	} // end viewFullInventory
	
	/** Method Name: checkThreshold
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 18, 2024
	* @Description This checks drug stock amounts compared to their threshold levels.
	* @Parameters String DIN, the drug to check the threshold of
	* @Returns int atThreshold, 0 if the drug is above threshold, 1 if the drug is below threshold, 2 if the drug is exactly at the threshold
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public int checkThreshold(String DIN) {
		int atThreshold = 0;
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				atThreshold = runner.drugStock.checkThreshold();
			} // end if
			runner = runner.next;
		} // end while
		return atThreshold;
	} // end checkThreshold
	
	/** Method Name: countNodes
	* @Author Christina Wong 
	* @Date January 15, 2024
	* @Modified January 15, 2024
	* @Description This counts the number of nodes in the list.
	* @Parameters N/A
	* @Returns int count, the number of nodes in the list
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	public int countNodes() {
		int count = 0;;
		Node runner;
		runner = head;
		while(runner != null) {
			count++;
			runner = runner.next;
		} // end while

		return count;
	} // end countNodes
	

	/** Method Name: getElements
	* @Author Kyle McKay
	* @Date Unknown
	* @Modified December 15, 2023
	* @Description This retrieves the drug stock of every node in the list.
	* @Parameters  N/A
	* @Returns DrugStock[], an array of all the drug stocks in th elist
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
    public DrugStock[] getElements() {
        int count;          // For counting elements in the list.
        Node runner;        // For traversing the list.
        DrugStock[] drugs;  // An array to hold the list elements.

        count = 0;
        runner = head;
        while (runner != null) {
            count++;
            runner = runner.next;
        } // end while

        drugs = new DrugStock[count];
        runner = head;
        count = 0;
        while (runner != null) {
            drugs[count] = runner.drugStock;
            count++;
            runner = runner.next;
        } // end while

        // Return the array that has been filled with the list elements.

        return drugs;

    } // end getElements()
	
} // end LinkedList