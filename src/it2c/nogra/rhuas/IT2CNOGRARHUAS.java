package it2c.nogra.rhuas;

import java.util.Scanner;

public class IT2CNOGRARHUAS {
    
      private final config conf = new config();

    public void addPatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("First Name:");
        String fname = sc.next();
        System.out.println("Last Name:");
        String lname = sc.next();
        System.out.println("Email:");
        String email = sc.next();
        System.out.println("Appointment Date:");
        String adate = sc.next();
        String sql = "INSERT INTO patients (p_name, p_lname, p_email, p_appointmentdate) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, adate);
    }
     private void viewPatients() {
        String patientsQuery = "SELECT * FROM patients";
        String[] patientsHeaders = {"ID", "First Name", "Last Name", "Email", "Appointment Date"};
        String[] patientsColumns = {"p_id", "p_name", "p_lname", "p_email", "p_appointmentdate"};

        conf.viewRecords(patientsQuery, patientsHeaders, patientsColumns);
    }
     
     private void deletePatient() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Patient ID to delete:");
        int patientId = sc.nextInt();
        String sql = "DELETE FROM patients WHERE p_id = ?";
        conf.deleteRecord(sql, String.valueOf(patientId));
    }
     
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IT2CNOGRARHUAS demo = new IT2CNOGRARHUAS();
        String response;

        do {
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. EXIT");

            System.out.println("SELECT ACTION:");
            int action = sc.nextInt();

            switch(action) {
                case 1:               
                    demo.addPatients();
                    
                    break;
                case 2:
                    demo.viewPatients();
                    break;
               
                    
                case 4:
                    demo.deletePatient();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println("Continue? (yes/no):");
            response = sc.next();
        } while(response.equals("yes"));
        System.out.println("Thank You");
    }
}
