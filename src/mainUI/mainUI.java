package mainUI;
import patientUI.*;
import stockUI.*;
import javax.swing.*;
import java.awt.*;


public class mainUI {

    private JButton openSettings = new JButton();
    private JButton openPatientManager = new JButton();
    private JButton openStock = new JButton();
    private JButton openOrder = new JButton();
    private loginUI login = new loginUI();
    private settingsUI settings = new settingsUI();
    private patientManagerUI patientManager = new patientManagerUI();
    private StockUI stock = new StockUI();
    private OrderUI order = new OrderUI();

    private void login(String username, String password) {
        // Method body goes here
    }

    private void updateSaveFile() {
        // Method body goes here
    }

    private DrugStock suggestAlt(AllStock allStock, String drugName) {
        // Method body goes here
    }

    private String findDrugInfo(String drugName) {
        // Method body goes here
    }
}
