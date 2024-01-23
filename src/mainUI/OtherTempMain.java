package mainUI;
import java.io.IOException;
import java.util.Scanner;
import inventory.*;
public class OtherTempMain {

	public static void main(String[] args) throws IOException {
		Scanner ui = new Scanner(System.in);

//		mainUI main = new mainUI();
//		main.setVisible(true);
		//mainUI main = new mainUI();
		//main.setVisible(true);

		// loginUI login = new loginUI();
		// login.setVisible(true);

		AllStock inventory = new AllStock();
		Drug drug1 = drugFinder.getDrug("00548359");
		inventory.updateStock(100, drug1.getDIN(), drug1.getDrugClass(), "500");
		
//		Drug drug2 = drugFinder.getDrug("02248808");
//		inventory.updateStock(80, drug2.getDIN(), drug2.getDrugClass(), "40");
		
		inventory.updateStock(400, drug1.getDIN(), drug1.getDrugClass(), "");
//		
//		Drug drug3 = drugFinder.getDrug("02532042");
//		inventory.updateStock(100, drug3.getDIN(), drug3.getDrugClass(), "30");
		
//		Drug drug4 = drugFinder.getDrug("00013285");	
//		inventory.updateStock(299, drug4.getDIN(), drug4.getDrugClass(), "199");
		//inventory.updateStock(100, "00548459", drug1.getDrugClass(), "50");


//		stockUI stock = new stockUI("ManageRx", inventory);
//		
//		 inventoryUI newInventory = new inventoryUI(inventory);

		//newInventory.setVisible(true);
		
//		stock.setVisible(true);
		
		
		
		
		
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		inventory.updateStock(200, drug1.getDIN(), drug1.getDrugClass(), threshold);
//		
//		Drug drug2 = drugFinder.getDrug("02248808");
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		inventory.updateStock(50, drug2.getDIN(), drug2.getDrugClass(), threshold);
//		
//		Drug drug3 = drugFinder.getDrug("02532042");
//		System.out.println("\nEnter threshold:");
//		threshold = ui.nextLine();
//		inventory.updateStock(500, drug3.getDIN(), drug3.getDrugClass(), threshold);
		
		
		
//		orderUI order = new orderUI();
//		order.setVisible(true);

	}

}
