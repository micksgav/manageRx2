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

		System.out.println("Enter threshold:");
		String threshold = ui.nextLine();
		inventory.updateStock(100, drug1.getDIN(), drug1.getDrugClass(), threshold);

		
		Drug drug2 = drugFinder.getDrug("02248808");
		System.out.println("\nEnter threshold:");
		threshold = ui.nextLine();
		inventory.updateStock(50, drug2.getDIN(), drug2.getDrugClass(), threshold);
		
		Drug drug3 = drugFinder.getDrug("02532042");
		System.out.println("\nEnter threshold:");
		threshold = ui.nextLine();
		inventory.updateStock(500, drug3.getDIN(), drug3.getDrugClass(), threshold);

		stockUI stock = new stockUI(inventory);

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
