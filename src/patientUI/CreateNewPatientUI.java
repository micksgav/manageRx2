package patientUI;

import java.util.*;

import PatientManagement.*;

public class CreateNewPatientUI {
    private String name = "";
    private int age = 0;
    private String address = "";
    private String dateOfBirth = "";
    private LinkedList<Prescription> activePrescriptions = new LinkedList<>();
    private LinkedList<Prescription> pastPrescriptions = new LinkedList<>();
    private int phoneNumber = 0;
    private String email = "";
    private LinkedList<Integer> cardNum = new LinkedList<>();
    private LinkedList<Integer> cardExp = new LinkedList<>();
    private LinkedList<String> allergiesAndDietary = new LinkedList<>();
    private LinkedList<String> medicalConditions = new LinkedList<>();
    private LinkedList<String> lifestyleHabits = new LinkedList<>();
    private FamilyDoctor familyDoctor = new FamilyDoctor();
    private LinkedList<Insurance> insuranceInformation = new LinkedList<>();
    private int numInsurancePlans = 0;
    //private Patient patient = new Patient();

//    private Patient addPatient(String name, int age, String address, String dateOfBirth, LinkedList<Prescription> activePrescriptions, LinkedList<Prescription> pastPrescriptions, int phoneNumber, String email, LinkedList<Integer> cardNum, LinkedList<Integer> cardExp, LinkedList<String> allergiesAndDietary, LinkedList<String> medicalConditions, LinkedList<String> lifestyleHabits, FamilyDoctor familyDoctor, LinkedList<Insurance> insuranceInformation, int numInsurancePlans) {
//        //fill here
//    }
}
