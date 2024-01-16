/**
***********************************************
 @Name: DrugStockLinkedList
 @Author           : Christina Wong
 @Creation Date    : December 13, 2023
 @Modified Date	   : January 9, 2024
   @Description    : 
   
***********************************************
*/
package inventory;

// there are extra print statements for testing that can probably be deleted later

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
		System.out.println("searching for DIN " + searchDIN);
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(searchDIN)) {
				System.out.println("found");
				return true;
			} // end if
			runner = runner.next;
		} // end while	
		System.out.println("not found");
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
	* @Modified December 18, 2023
	* @Description This adds a new drug to the stock, in sequential order based on DIN.
	* @Parameters  DrugStock insertDrugStock, drug added to linked list
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void insert(DrugStock insertDrugStock) {
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
		
		System.out.println("new node inserted");
		printDINs(); // mainly for testing purposes - may not need to keep in code?

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
		System.out.println("adding new shipment");
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(arrivalDIN)) {
				runner.drugStock.addToStock(newStock);
				System.out.println("shipment added");
				break;
			} // end if
			runner = runner.next;
		} // end while
	} // end newShipment
	
	/** Method Name: printDINs
	* @Author Christina Wong 
	* @Date December 18, 2023
	* @Modified December 19, 2023
	* @Description This prints the DINs of all drugs currently in stock.
	* @Parameters  N/A
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: N/A
    */
	public void printDINs() {
		Node runner;
		runner = head;
		while(runner != null) {
			System.out.println(runner.drugStock.getDrugDIN());
			runner = runner.next;
		} // end while
	} // end printDINS
	
	/** Method Name: viewStockUsage
	* @Author Christina Wong 
	* @Date December 19, 2023
	* @Modified December 22, 2023
	* @Description This checks the current inventory for a drug and, if found, prints inventory usage information for a specific drug.
	* @Parameters  String DIN, DIN of drug to view information on
	* @Returns void
	* Dependencies: DrugStock
	* Throws/Exceptions: error message, if DIN is not found
    */
	public void viewStockUsage(String DIN) {
		System.out.println("Viewing stock usage");
		Node runner;
		runner = head;
		boolean found = false;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				runner.drugStock.viewUsage();
				found = true;
				break;
			} // end if
			runner = runner.next;
		} // end while
		if(found == false) {
			// keep this statement, could be a DialogueBox or something else in GUI?
			System.out.println("Drug not found in inventory");
		} // end if
	} // end viewStockUsage
	
	public String getDINForName(String name) {
		String drugDIN = "";
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugName().toLowerCase().equals(name.toLowerCase())) {
				drugDIN = runner.drugStock.getDrugDIN();
			}
			runner = runner.next;
		}
		return drugDIN;
	}
	
	public void setNewThreshold(String DIN, int newThreshold) {
		Node runner;
		runner = head;
		while(runner != null) {
			if(runner.drugStock.getDrugDIN().equals(DIN)) {
				runner.drugStock.setStockThreshold(newThreshold);
				System.out.println("new threshold for " + runner.drugStock.getDrugName() + " (" + runner.drugStock.getDrugDIN() + ")");
			}
			runner = runner.next;
		}
	}
	
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
		//allInventory[0][0] = "Drug Name:\t\tDIN:\t\tCurrent Stock:";
		int lineCount = 0;
		Node runner;
		runner = head;
		System.out.println("INVENTORY:");
		System.out.println("Drug Name:\t\tDIN:\t\tCurrent Stock:");
		while(runner != null) {
			allInventory[lineCount][0] = runner.drugStock.getDrugName();
			System.out.print(runner.drugStock.getDrugName() + "\t\t");
			if(runner.drugStock.getDrugName().length() < 8) {
				System.out.print("\t");
			}
			allInventory[lineCount][1] = runner.drugStock.getDrugDIN();
			allInventory[lineCount][2] = String.valueOf(runner.drugStock.getNumInStock());
			System.out.print(runner.drugStock.getDrugDIN() + "\t");
			System.out.print(runner.drugStock.getNumInStock());
			System.out.println();			
			
			lineCount++;
			runner = runner.next;
		} // end while
		
		return allInventory;
	} // end viewFullInventory
	
	
	public int countNodes() {
		int count = 0;;
		Node runner;
		runner = head;
		while(runner != null) {
			count++;
			runner = runner.next;
		}
		
		
		return count;
	}
	
	// not sure if we will need this
    public DrugStock[] getElements() {

        int count;          // For counting elements in the list.
        Node runner;        // For traversing the list.
        DrugStock[] drugs;  // An array to hold the list elements.

        // First, go through the list and count the number
        // of elements that it contains.

        count = 0;
        runner = head;
        while (runner != null) {
            count++;
            runner = runner.next;
        } // end while

        // Create an array just large enough to hold all the
        // list elements.  Go through the list again and
        // fill the array with elements from the list.

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