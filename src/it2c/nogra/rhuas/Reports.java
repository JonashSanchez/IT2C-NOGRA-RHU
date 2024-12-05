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
            System.out.println("\n---------------------------");
            response = getValidYesNoResponse(sc, "Do you want to continue? (yes/no)");
            System.out.println("\n---------------------------");
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

    
    private void generatePatientReport(Scanner sc) {
       
        String qry = "SELECT p_id, p_name, p_lname FROM patients";
        String[] headers = {"Patient ID", "First Name", "Last Name"};
        String[] columns = {"p_id", "p_name", "p_lname"};
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
    config conf = new config();

   
    String patientQuery = "SELECT p_id, p_name, p_lname, p_email, p_contact FROM patients WHERE p_id = ?";
    try (Connection conn = config.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(patientQuery)) {
        pstmt.setInt(1, patientId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("\n====================== PATIENT INFORMATION ======================");
                System.out.printf("Patient ID : %d%n", rs.getInt("p_id"));
                System.out.printf("First Name : %s%n", rs.getString("p_name"));
                System.out.printf("Last Name  : %s%n", rs.getString("p_lname"));
                System.out.printf("Email      : %s%n", rs.getString("p_email"));
                System.out.printf("Contact No : %s%n", rs.getString("p_contact"));
                System.out.println("===============================================================");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error fetching patient information: " + e.getMessage());
    }

    
    String appointmentQuery = "SELECT a_id, a_date, a_purpose, a_status FROM appointment WHERE p_id = ?";
    String[] appointmentHeaders = {"Appointment ID", "Appointment Date", "Purpose", "Status"};
    String[] appointmentColumns = {"a_id", "a_date", "a_purpose", "a_status"};

    System.out.println("\n====================== APPOINTMENT DETAILS ======================");
    conf.viewRecords(appointmentQuery.replace("?", String.valueOf(patientId)), appointmentHeaders, appointmentColumns);

   
     String medicineQuery = "SELECT mr_id, med_id, quantity, m_release, m_status FROM medicinerelease WHERE a_id IN (SELECT a_id FROM appointment WHERE p_id = ?)";
    String[] medicineHeaders = {"Release ID", "Medicine ID", "Quantity", "Release Date", "Medicine Status"};
    String[] medicineColumns = {"mr_id", "med_id", "quantity", "m_release", "m_status"};

    System.out.println("\n====================== MEDICINE RELEASE DETAILS ======================");
    conf.viewRecords(medicineQuery.replace("?", String.valueOf(patientId)), medicineHeaders, medicineColumns);
}



    
  private void generateGeneralReport() {
    String qry = "SELECT p_id, p_name, p_lname FROM patients";
    String[] headers = {"Patient ID", "First Name", "Last Name"};
    String[] columns = {"p_id", "p_name", "p_lname"};
    
    config conf = new config();
    
    System.out.println("\n====================== GENERAL REPORT ======================");
    conf.viewRecords(qry, headers, columns);
    System.out.println("===========================================================");
}

}
 