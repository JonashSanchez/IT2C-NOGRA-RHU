package it2c.nogra.rhuas;

import java.util.Scanner;

/**
 *
 * @author
 */
public class Patient {
    public void pRHU() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n----------------------");
            System.out.println("PATIENT PANEL");
            System.out.println("1. ADD PATIENT");
            System.out.println("2. VIEW PATIENT");
            System.out.println("3. UPDATE PATIENT");
            System.out.println("4. DELETE PATIENT");
            System.out.println("5. EXIT");

            int act = 0;
            while (true) {
                System.out.println("Enter Selection (1-5):");
                if (sc.hasNextInt()) {
                    act = sc.nextInt();
                    if (act >= 1 && act <= 5) {
                        break;
                    }
                }
                sc.nextLine(); 
                System.out.println("Invalid selection. Please enter a number between 1 and 5.");
            }

            Patient cs = new Patient();
            switch (act) {
                case 1:
                    cs.addPatients();
                    cs.viewPatients();
                    break;
                case 2:
                    cs.viewPatients();
                    break;
                case 3:
                    cs.viewPatients();
                    cs.updatePatient();
                    cs.viewPatients();
                    break;
                case 4:
                    cs.viewPatients();
                    cs.deletePatient();
                    cs.viewPatients();
                    break;
                case 5:
                    break;
            }

            System.out.println("Do you want to continue? (yes/no)");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));
    }

    public void addPatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String fname, lname, email, adate;
        while (true) {
            System.out.println("First Name:");
            fname = sc.next();
            if (fname.matches("[a-zA-Z]+")) {
                break;
            }
            System.out.println("Invalid input. First name should only contain letters.");
        }

        while (true) {
            System.out.println("Last Name:");
            lname = sc.next();
            if (lname.matches("[a-zA-Z]+")) {
                break;
            }
            System.out.println("Invalid input. Last name should only contain letters.");
        }

        while (true) {
            System.out.println("Email:");
            email = sc.next();
            if (email.matches("\\w+@gmail\\.com")) {
                break;
            }
            System.out.println("Invalid input. Email should be in the format 'example@gmail.com'.");
        }

        while (true) {
            System.out.println("Contact Number:");
            adate = sc.next();
            if (adate.matches("\\d+")) {
                break;
            }
            System.out.println("Invalid input. Contact number should only contain numbers.");
        }

        String sql = "INSERT INTO patients (p_name, p_lname, p_email, p_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, adate);
    }

    public void viewPatients() {
        String patientsQuery = "SELECT * FROM patients";
        String[] patientsHeaders = { "ID", "First Name", "Last Name", "Email", "Contact Number" };
        String[] patientsColumns = { "p_id", "p_name", "p_lname", "p_email", "p_contact" };
        config conf = new config();
        conf.viewRecords(patientsQuery, patientsHeaders, patientsColumns);
    }

    private void updatePatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter Patient ID:");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.println("Select Patient ID Again:");
            id = sc.nextInt();
        }

        String ufname, ulname, uemail, udate;
        while (true) {
            System.out.println("Enter the new first name:");
            ufname = sc.next();
            if (ufname.matches("[a-zA-Z]+")) {
                break;
            }
            System.out.println("Invalid input. First name should only contain letters.");
        }

        while (true) {
            System.out.println("Enter the new last name:");
            ulname = sc.next();
            if (ulname.matches("[a-zA-Z]+")) {
                break;
            }
            System.out.println("Invalid input. Last name should only contain letters.");
        }

        while (true) {
            System.out.println("Enter the new email:");
            uemail = sc.next();
            if (uemail.matches("\\w+@gmail\\.com")) {
                break;
            }
            System.out.println("Invalid input. Email should be in the format 'example@gmail.com'.");
        }

        while (true) {
            System.out.println("Enter the new contact number:");
            udate = sc.next();
            if (udate.matches("\\d+")) {
                break;
            }
            System.out.println("Invalid input. Contact number should only contain numbers.");
        }

        String qry = "UPDATE patients SET p_name = ?, p_lname = ?, p_email = ?, p_contact = ? WHERE p_id =?";
        conf.updateRecord(qry, ufname, ulname, uemail, udate, id);
    }

    private void deletePatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter Patient ID to delete:");
        int id = sc.nextInt();

        while (conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?", id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.println("Select Patient ID Again:");
            id = sc.nextInt();
        }

        String sqlDelete = "DELETE FROM patients WHERE p_id = ?";
        conf.deleteRecord(sqlDelete, id);
    }
}
