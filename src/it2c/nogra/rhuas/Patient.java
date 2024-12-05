package it2c.nogra.rhuas;

import java.util.Scanner;

public class Patient {
    public void pRHU() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n######################");
            System.out.println("#    PATIENT PANEL   #");
            System.out.println("######################");
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
            System.out.println("\n---------------------------");
            response = getValidYesNoResponse(sc, "Do you want to continue? (yes/no)");
            System.out.println("\n---------------------------");
        } while (response.equalsIgnoreCase("yes"));
    }

    private String getValidYesNoResponse(Scanner sc, String prompt) {
        String response;
        while (true) {
            System.out.println(prompt);
            response = sc.next();
            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("\n---------------------------");
                System.out.println("Invalid input, please enter 'yes' or 'no'.");
                System.out.println("\n---------------------------");
            }
        }
        return response;
    }

    public void addPatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String fname, lname, email, contact;

        System.out.println("\n----------------------");
        while (true) {
            System.out.println("First Name:");
            fname = sc.nextLine();
            if (fname.matches("[a-zA-Z ]+")) {
                break;
            }
            System.out.println("Invalid input. First name should only contain letters and spaces.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.println("Last Name:");
            lname = sc.nextLine();
            if (lname.matches("[a-zA-Z ]+")) {
                break;
            }
            System.out.println("Invalid input. Last name should only contain letters and spaces.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.println("Email:");
            email = sc.next();
            if (email.matches("\\w+@gmail\\.com")) {
                break;
            }
            System.out.println("Invalid input. Email should be in the format 'example@gmail.com'.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.println("Contact Number:");
            contact = sc.next();
            if (contact.matches("\\d{11}")) {
                break;
            }
            System.out.println("Invalid input. Contact number should be exactly 11 digits.");
        }

        String sql = "INSERT INTO patients (p_name, p_lname, p_email, p_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, contact);
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

        System.out.println("\n----------------------");
        int id = getValidPatientId(sc, conf);

        String ufname, ulname, uemail, ucontact;

        System.out.println("\n----------------------");
        while (true) {
            System.out.print("Enter the new first name: ");
            ufname = sc.nextLine();
            if (ufname.matches("[a-zA-Z ]+")) {
                break;
            }
            System.out.println("Invalid input. First name should only contain letters and spaces.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.print("Enter the new last name: ");
            ulname = sc.nextLine();
            if (ulname.matches("[a-zA-Z ]+")) {
                break;
            }
            System.out.println("Invalid input. Last name should only contain letters and spaces.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.print("Enter the new email: ");
            uemail = sc.next();
            if (uemail.matches("\\w+@gmail\\.com")) {
                break;
            }
            System.out.println("Invalid input. Email should be in the format 'example@gmail.com'.");
        }

        System.out.println("\n----------------------");
        while (true) {
            System.out.print("Enter the new contact number: ");
            ucontact = sc.next();
            if (ucontact.matches("\\d{11}")) {
                break;
            }
            System.out.println("Invalid input. Contact number should be exactly 11 digits.");
        }

        String qry = "UPDATE patients SET p_name = ?, p_lname = ?, p_email = ?, p_contact = ? WHERE p_id = ?";
        conf.updateRecord(qry, ufname, ulname, uemail, ucontact, id);
    }

    private int getValidPatientId(Scanner sc, config conf) {
        int id;
        while (true) {
            System.out.print("Enter Patient ID: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?", id) != 0) {
                    break;
                } else {
                    System.out.println("Selected ID doesn't exist.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
            }
        }
        return id;
    }

    private void deletePatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.println("\n----------------------");
        int id;
        while (true) {
            System.out.print("Enter Patient ID to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?", id) != 0) {
                    break;
                } else {
                    System.out.println("Selected ID doesn't exist.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
            }
        }

        String sqlDelete = "DELETE FROM patients WHERE p_id = ?";
        conf.deleteRecord(sqlDelete, id);
    }
}
