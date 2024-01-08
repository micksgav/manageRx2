
package inventory;

import java.io.IOException;

public class TestRun {

	public static void main(String[] args) throws IOException {
		AllStock stock = new AllStock(100, 1000, 10000, 400);
		Drug drug1 = drugFinder.getDrug("00548359");
		Drug drug2 = drugFinder.getDrug("02248808");

		stock.updateStock(100, drug1.getDIN(), drug1.getDrugClass());
		System.out.println("stock 1 update");
		stock.updateStock(200, drug1.getDIN(), drug1.getDrugClass());
		System.out.println("\nstock 2 update");

		stock.updateStock(100, drug2.getDIN(), drug2.getDrugClass());

		System.out.println("\nstock 3 update");

//		System.out.println("checking interactions");
//		drug1.checkInteractions(drug2);

//		stock.searchByDIN(drug1.getDIN());

		stock.searchByName(drug1.getDrugName()); // error from drug gen name

//		stock.searchByDIN(drug2.getDIN());
//		
//		stock.viewUsage(drug1.getDIN());
//		
//		stock.viewUsage(drug2.getDIN());
//		
//		stock.viewUsage("00432894");

	}

}

