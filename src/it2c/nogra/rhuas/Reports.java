package it2c.nogra.rhuas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reports {

    
    public void showReports() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n----------------------");
            System.out.println("REPORTS PANEL");
            System.out.println("1. PATIENT REPORT");
            System.out.println("2. GENERAL REPORT");
            System.out.println("3. EXIT");

            int action = getValidAction(sc, 1, 3);

            Reports r = new Reports();

            switch (action) {
                case 1:
                    r.generatePatientReport(sc); 
                    break;
                case 2:
                    r.generateGeneralReport();
                    break;
                case 3:
                    break;
            }
            System.out.println("Do you want to continue? (yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }

    private int getValidAction(Scanner sc, int min, int max) {
        int action;
        while (true) {
            System.out.println("Enter Selection:");
            if (sc.hasNextInt()) {
                action = sc.nextInt();
                if (action >= min && action <= max) {
                    break;
                } else {
                    System.out.println("Invalid selection, please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next(); 
            }
        }
        return action;
    }

    
    private void generatePatientReport(Scanner sc) {
       
        String qry = "SELECT p_id, p_name, p_lname, p_email, p_contact FROM patients";
        String[] headers = {"Patient ID", "First Name", "Last Name", "Email", "Contact Number"};
        String[] columns = {"p_id", "p_name", "p_lname", "p_email", "p_contact"};
        config conf = new config();
        
        
        conf.viewRecords(qry, headers, columns);

       
        int patientId = getValidPatientId(sc, conf);

        
        showSelectedPatientReport(patientId);
    }

   
    private int getValidPatientId(Scanner sc, config conf) {
        int pid;
        String sql = "SELECT COUNT(*) FROM patients WHERE p_id = ?";
        while (true) {
            System.out.print("Enter the Patient ID to view their report: ");
            if (sc.hasNextInt()) {
                pid = sc.nextInt();
                if (conf.getSingleValue(sql, pid) > 0) {
                    break;
                } else {
                    System.out.println("Patient does not exist, select again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next(); 
            }
        }
        return pid;
    }

    
    private void showSelectedPatientReport(int patientId) {
        String qry = "SELECT patients.p_id, patients.p_name, patients.p_lname, patients.p_email, patients.p_contact, "
                + "appointment.a_id, appointment.a_date, appointment.a_purpose, appointment.a_status, "
                + "medicinerelease.mr_id, medicinerelease.med_id, medicinerelease.quantity, medicinerelease.m_release, medicinerelease.m_status "
                + "FROM patients "
                + "LEFT JOIN appointment ON patients.p_id = appointment.p_id "
                + "LEFT JOIN medicinerelease ON patients.p_id = medicinerelease.p_id "
                + "WHERE patients.p_id = " + patientId;
        
        String[] headers = {
                "Patient ID", "First Name", "Last Name", "Email", "Contact Number",
                "Appointment ID", "Appointment Date", "Purpose", "Status",
                "Release ID", "Medicine ID", "Quantity", "Release Date", "Medicine Status"
        };
        String[] columns = {
                "p_id", "p_name", "p_lname", "p_email", "p_contact",
                "a_id", "a_date", "a_purpose", "a_status",
                "mr_id", "med_id", "quantity", "m_release", "m_status"
        };
        
        config conf = new config();
        conf.viewRecords(qry, headers, columns);
    }

    
    private void generateGeneralReport() {
    String qry = "SELECT patients.p_id, patients.p_name, patients.p_lname, patients.p_email, patients.p_contact, "
            + "GROUP_CONCAT(DISTINCT appointment.a_id) as appointment_ids, GROUP_CONCAT(DISTINCT appointment.a_date) as appointment_dates, "
            + "GROUP_CONCAT(DISTINCT appointment.a_purpose) as appointment_purposes, GROUP_CONCAT(DISTINCT appointment.a_status) as appointment_statuses, "
            + "GROUP_CONCAT(DISTINCT medicinerelease.mr_id) as release_ids, GROUP_CONCAT(DISTINCT medicinerelease.med_id) as medicine_ids, "
            + "GROUP_CONCAT(DISTINCT medicinerelease.quantity) as quantities, GROUP_CONCAT(DISTINCT medicinerelease.m_release) as release_dates, "
            + "GROUP_CONCAT(DISTINCT medicinerelease.m_status) as medicine_statuses "
            + "FROM patients "
            + "LEFT JOIN appointment ON patients.p_id = appointment.p_id "
            + "LEFT JOIN medicinerelease ON patients.p_id = medicinerelease.p_id "
            + "GROUP BY patients.p_id, patients.p_name, patients.p_lname, patients.p_email, patients.p_contact";

    String[] headers = {
            "Patient ID", "First Name", "Last Name", "Email", "Contact Number",
            "Appointment IDs", "Appointment Dates", "Appointment Purposes", "Appointment Statuses",
            "Release IDs", "Medicine IDs", "Quantities", "Release Dates", "Medicine Statuses"
    };
    String[] columns = {
            "p_id", "p_name", "p_lname", "p_email", "p_contact",
            "appointment_ids", "appointment_dates", "appointment_purposes", "appointment_statuses",
            "release_ids", "medicine_ids", "quantities", "release_dates", "medicine_statuses"
    };

    config conf = new config();
    conf.viewRecords(qry, headers, columns);
}

}
