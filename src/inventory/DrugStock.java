/**
***********************************************
 @Name: DrugStock
 @Author           : Christina Wong
 @Creation Date    : December 12, 2023
 @Modified Date	   : January 19, 2024
   @Description    : This stores a drug and its inventory information.  This information can be edited or retrieved.
   
***********************************************
*/
package inventory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*; // just for testing purposes, will need to be replaced with UI stuff later
import utilities.*;
import java.io.IOException;
public class DrugStock {
	private Drug drug; // drug
	private int numInStock; // current stock of drug
	private int stockThreshold; // when the drug's threshold is reached, alert is sent
	private String[][] stockChanges = new String[32][4]; // array of the past month (31 days) of stock changes
	private int ID; // drug ID in SQL
	private SQLHelper helper = new SQLHelper(); // used to link with MySQL
	Scanner ui = new Scanner(System.in);
	
	public DrugStock(String DIN, int inStock, int threshold, int ID) throws IOException {
		this.ID = ID;
		this.drug = new Drug();
		this.drug = drugFinder.getDrug(DIN);
		numInStock = inStock;
		stockThreshold = threshold;
		fillStockChanges();
	} // end DrugStock constructor with stock info
	
	public DrugStock(String DIN) throws IOException {
		this.ID = ID;
		this.drug = new Drug();
		this.drug = drugFinder.getDrug(DIN);
		numInStock = 0;
		stockThreshold = 0;
		fillStockChanges();
	} // end DrugStock constructor without stock info
	
	public Drug getDrug() {
		return drug;
	} // end getDrug
	
	public String getDrugName() {
		return drug.getDrugName();
	} // end getDrugName
	
	public void setDrugName(String drugName) {
		drug.setDrugName(drugName);
	} // end setDrugName
	
	public String getDrugDIN() {
		return drug.getDIN();
	} // end getDrugDIN
	
	public void setDrugDIN(String drugDIN) {
		drug.setDIN(drugDIN);
	} // end setDrugDIN
	
	public int getNumInStock() {
		return numInStock;
	} // end getNumInStock
	
	/** Method Name: removeFromStock
	* @Author Christina Wong 
	* @Date December 12, 2023
	* @Modified January 19, 2024
	* @Description This subtracts a filled prescription from the total stock of the drug.
	* @Parameters  int filled, the amount of of this drug removed from the stock to fill a prescription 
	* @Returns void
	* Dependencies: changeInStock, SQLHelper
	* Throws/Exceptions: N/A
    */
	public void removeFromStock(int filled) {
		this.numInStock -= filled;
		changeInStock("Prescription filled:", filled, numInStock);
		helper.update("DrugStock", "quantity", this.numInStock, ID);
		checkThreshold();		
	} // end removeFromStock
	
	/** Method Name: checkThreshold
	* @Author Christina Wong 
	* @Date January 17, 2024
	* @Modified January 18, 2024
	* @Description This checks drug stock amounts compared to their threshold levels.
	* @Parameters N/A
	* @Returns int 0 if the drug is above threshold, 1 if the drug is below threshold, 2 if the drug is exactly at the threshold
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	public int checkThreshold() {
		if(numInStock < stockThreshold) {
			return 1;
		} // end if
		else if(numInStock == stockThreshold) {
			return 2;
		} // end else if
		return 0;
	} // end checkThreshold
	
	/** Method Name: addToStock
	* @Author Christina Wong 
	* @Date December 12, 2023
	* @Modified January 19, 2024
	* @Description This adds a new shipment arrival to the total stock.
	* @Parameters  int added, the amount of this drug added to the current stock
	* @Returns void
	* Dependencies: changeInStock, SQLHelper
	* Throws/Exceptions: N/A
    */
	public void addToStock(int added) {
		this.numInStock += added;
		changeInStock("Shipment arrival:", added, numInStock);
		helper.update("DrugStock", "quantity", this.numInStock, ID);
		checkThreshold();
	} // end addToStock
	
	public int getStockThreshold() {
		return stockThreshold;
	} // end getStockThreshold
	
	/** Method Name: setStockThreshold
	* @Author Christina Wong 
	* @Date January 15, 2024
	* @Modified January 19, 2024
	* @Description This changes the threshold of this drug stock.
	* @Parameters  int threshold, the new stock threshold
	* @Returns void
	* Dependencies: SQLHelper
	* Throws/Exceptions: N/A
    */
	public void setStockThreshold(int threshold) {
		this.stockThreshold = threshold;
		helper.update("DrugStock", "threshold", threshold, ID);
	} // end setStockThreshold
	
	public void setID(int newID) {
		this.ID = newID;
	} // end setID
	
	public String[][] getStockChanges(){
		return stockChanges;
	} // end getStockChanges
	
	/** Method Name: fillStockChanges
	* @Author Christina Wong 
	* @Date December 23, 2023
	* @Modified December 23, 2023
	* @Description This sets every stockChanges element to an empty string.
	* @Parameters  N/A 
	* @Returns void
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	public void fillStockChanges() {
		stockChanges[0][0] = this.getDrugName();
		stockChanges[0][1] = this.getDrugDIN();
		for(int row = 1; row < stockChanges.length; row++) {
			for(int col = 0; col < stockChanges[row].length; col++) {
				stockChanges[row][col] = "";
			} // end for
		} // end for
	} // end fillStockChanges
	
	/** Method Name: changeInStock
	* @Author Christina Wong 
	* @Date December 18, 2023
	* @Modified January 17, 2024
	* @Description This adds a stock change to the array containing the past month (31 days) of changes.
	* @Parameters  String change, the type of stock change (prescription filled or shipment arrival); int amount, the amount of stock added or removed
	* @Returns void
	* Dependencies: DateTimeFormatter, LocalDateTime
	* Throws/Exceptions: N/A
    */
	public void changeInStock(String change, int amount, int curTotal) {
		int date = -1;
		for(int i = 1; i < stockChanges.length; i++) {
			if(stockChanges[i][0] == null) {
				date = i;
				break;
			} // end if
		} // end for
        //https://www.javatpoint.com/java-get-current-date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String changeDate = dtf.format(now);
        
		if(date == -1) {
			for(int row = 1; row < stockChanges.length - 1; row++) {
				for(int col = 0; col < stockChanges[row].length; col++) {
					stockChanges[row][col] = stockChanges[row + 1][col];
				} // end for
			} // end for

			// update date
			stockChanges[stockChanges.length - 1][0] = changeDate.substring(0, 10);
			stockChanges[stockChanges.length - 1][1] = change; 
			stockChanges[stockChanges.length - 1][2] = String.valueOf(amount);
			stockChanges[stockChanges.length - 1][3] = String.valueOf(curTotal);
			
		} // end if
		else {
			stockChanges[date][0] = changeDate;
			stockChanges[date][1] = change;
			stockChanges[date][2] = String.valueOf(amount);
			stockChanges[date][3] = String.valueOf(curTotal);
		} // end else
	} // end changeInStock
	
	// will show record of the last month (last 31 days) of usage, this will have to be adjusted for ui
	/** Method Name: viewUsage
	* @Author Christina Wong 
	* @Date December 18, 2023
	* @Modified December 20, 2023
	* @Description This prints the usage of this drug's stock over the past month, including shipment arrivals and prescription dispensing.
	* @Parameters  N/A
	* @Returns void
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	public void viewUsage() {
// used in testing		
//		System.out.println("\nDATE:\t\tINVENTORY CHANGE:\t\tAMOUNT:\t\tCURRENT STOCK:");
		for (int row = 1; row < stockChanges.length; row++) {
			// if the row has information to print
			if(stockChanges[row][0].length() != 0) {
//				System.out.print(stockChanges[row][0] + ":\t");
//				System.out.print(stockChanges[row][1] + "\t\t");
//				System.out.print(stockChanges[row][2] + "\t\t");
//				System.out.print(stockChanges[row][3]);
//				System.out.println();
			} // end if

		} // end for

	} // end viewUsage
} // end DrugStock