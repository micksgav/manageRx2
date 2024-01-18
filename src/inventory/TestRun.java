
package inventory;

import java.io.IOException;
import java.util.*;

public class TestRun {

	public static void main(String[] args) throws IOException {
		AllStock stock = new AllStock(100, 1000, 10000, 400);
		Scanner ui = new Scanner(System.in);
		Drug drug1 = drugFinder.getDrug("00548359");

		

		System.out.println("Enter threshold:");
		String threshold = ui.nextLine(); 
		stock.updateStock(100, drug1.getDIN(), drug1.getDrugClass(), threshold);

		
		System.out.println("\nEnter threshold:");
		threshold = ui.nextLine();
		stock.updateStock(200, drug1.getDIN(), drug1.getDrugClass(), threshold);
		
//		Drug drug2 = drugFinder.getDrug("02248808");
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		stock.updateStock(50, drug2.getDIN(), drug2.getDrugClass(), threshold);
//		
//		Drug drug3 = drugFinder.getDrug("02532042");
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		stock.updateStock(500, drug3.getDIN(), drug3.getDrugClass(), threshold);
//		
//		System.out.println("\n\n\n");
//		stock.searchByName(drug1.getDrugName()); 
//		
//		
//		System.out.println("\n\n");
//		stock.viewUsage(drug1.getDIN());
//		
//		System.out.println("\n\n");
//		stock.viewUsage(drug2.getDIN());
//		
//		System.out.println("\n\n");
//		stock.viewUsage(drug3.getDIN());
		
		
		stock.viewFullInventory();

		
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		stock.updateStock(100, drug2.getDIN(), drug2.getDrugClass(), threshold);

//		System.out.println("\nstock 3 update");

//		System.out.println("checking interactions");
//		drug1.checkInteractions(drug2);

//		stock.searchByDIN(drug1.getDIN());


//		stock.searchByDIN(drug2.getDIN());
//		

//		
//		stock.viewUsage(drug2.getDIN());
//		
//		stock.viewUsage("00432894");

	}

}

