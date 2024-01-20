package mainUI;
import java.io.IOException;

import inventory.*;
public class mainMainTemp {


	public static void main(String[] args) throws IOException {
		loginUI login = new loginUI();
		login.setVisible(true);

		
//		mainUI main = new mainUI();
//		main.setVisible(true);
//		
//		orderUI order = new orderUI();
//		order.setVisible(true);
//			
		
		AllStock inventory = new AllStock();
		Drug drug1 = drugFinder.getDrug("00548359");

		inventory.updateStock(100, drug1.getDIN(), drug1.getDrugClass(), "500");
		
		Drug drug2 = drugFinder.getDrug("02248808");
		inventory.updateStock(50, drug2.getDIN(), drug2.getDrugClass(), "60");
		
		inventory.updateStock(400, drug1.getDIN(), drug1.getDrugClass(), "");
		
		Drug drug3 = drugFinder.getDrug("02532042");
		inventory.updateStock(500, drug3.getDIN(), drug3.getDrugClass(), "30");
		
//		stockUI stock = new stockUI(inventory);
//		stock.setVisible(true);

//		Drug drug2 = drugFinder.getDrug("02248808");
//		System.out.println("\nEnter threshold:");
//		inventory.updateStock(50, drug2.getDIN(), drug2.getDrugClass(), "20");
//		

//		Drug drug3 = drugFinder.getDrug("02532042");
//		System.out.println("\nEnter threshold:");
//		inventory.updateStock(500, drug3.getDIN(), drug3.getDrugClass(), "30");

		//stockUI stock = new stockUI(inventory);
//		DrugStockUI viewStock = new DrugStockUI(inventory, drug1.getDIN());

	}

}
