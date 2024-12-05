package it2c.nogra.rhuas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Hazel Nogra
 */
public class MedicineRelease {

    public void mRelease() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n----------------------");
            System.out.println("MEDICINE RELEASE PANEL");
            System.out.println("1. ADD MEDRELEASE");
            System.out.println("2. VIEW MEDICINE RELEASE");
            System.out.println("3. UPDATE MEDICINE RELEASE");
            System.out.println("4. DELETE MEDICINE RELEASE");
            System.out.println("5. EXIT");

            int act = getValidAction(sc, 1, 5);

            switch (act) {
                case 1:
                    addMedRelease();
                    viewMedRelease();
                    break;
                case 2:
                    viewMedRelease();
                    break;
                case 3:
                    viewMedRelease();
                    updateMedRelease();
                    viewMedRelease();
                    break;
                case 4:
                    viewMedRelease();
                    deleteMedRelease();
                    viewMedRelease();
                    break;
                case 5:
                    return;
            }

            System.out.println("Do you want to continue? (yes/no)");
        } while (getYesNoResponse(sc));
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

  private void addMedRelease() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    
    System.out.println("Available Appointments:");
    String appointmentQuery = "SELECT a_id, p_id, a_date, a_purpose, a_status FROM appointment";
    String[] appointmentHeaders = {"Appointment ID", "Patient ID", "Date", "Purpose", "Status"};
    String[] appointmentColumns = {"a_id", "p_id", "a_date", "a_purpose", "a_status"};
    conf.viewRecords(appointmentQuery, appointmentHeaders, appointmentColumns);

    
    System.out.print("Enter the ID of the Appointment: ");
    int appointmentId = getValidAppointmentId(sc, conf);

    
    String medicineQuery = "SELECT med_id, m_name, m_stocks FROM medicines WHERE m_stocks > 0";
    System.out.println("Available Medicines:");
    System.out.printf("%-10s %-30s %-10s%n", "Medicine ID", "Medicine Name", "Stock");
    System.out.println("-------------------------------------------");
    conf.viewRecords(medicineQuery, new String[]{"Medicine ID", "Medicine Name", "Stock"}, new String[]{"med_id", "m_name", "m_stocks"});

    
    System.out.print("Enter the ID of the Medicine: ");
    int medicineId = getValidMedicineId(sc, conf);

    
    System.out.print("Enter quantity to release: ");
    int quantity = getValidQuantity(sc, conf, medicineId);

    sc.nextLine(); 

    
    System.out.print("Enter the release date (YYYY-MM-DD): ");
    String releaseDate = getValidReleaseDate(sc);

   
    System.out.print("Enter the release status (medicine not released/medicine released/pending): ");
    String status = getValidStatus(sc);

    
    String medReleaseQuery = "INSERT INTO medicinerelease (a_id, med_id, quantity, m_release, m_status) VALUES (?, ?, ?, ?, ?)";
    conf.addRecord(medReleaseQuery, Integer.toString(appointmentId), Integer.toString(medicineId), Integer.toString(quantity), releaseDate, status);

    
    String updateStockQuery = "UPDATE medicines SET m_stocks = m_stocks - ? WHERE med_id = ?";
    conf.updateRecord(updateStockQuery, Integer.toString(quantity), Integer.toString(medicineId));

    System.out.println("Medicine release added successfully!");
}


    private int getValidAppointmentId(Scanner sc, config conf) {
        int appointmentId;
        String sql = "SELECT a_id FROM appointment WHERE a_id = ?";
        while (true) {
            if (sc.hasNextInt()) {
                appointmentId = sc.nextInt();
                if (conf.getSingleValue(sql, appointmentId) != 0) {
                    break;
                } else {
                    System.out.println("Appointment does not exist, please select again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return appointmentId;
    }

    private int getValidMedicineId(Scanner sc, config conf) {
        int mid;
        String sql = "SELECT med_id FROM medicines WHERE med_id = ? AND m_stocks > 0";
        while (true) {
            if (sc.hasNextInt()) {
                mid = sc.nextInt();
                if (conf.getSingleValue(sql, mid) != 0) {
                    break;
                } else {
                    System.out.println("Medicine does not exist or is out of stock, select again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return mid;
    }

    private int getValidQuantity(Scanner sc, config conf, int mid) {
        int quantity;
        String sql = "SELECT m_stocks FROM medicines WHERE med_id = ?";
        int stock = (int) conf.getSingleValue(sql, mid);
        while (true) {
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                if (quantity > 0 && quantity <= stock) {
                    break;
                } else {
                    System.out.println("Invalid quantity, please enter a number between 1 and " + stock + ".");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return quantity;
    }

    private String getValidReleaseDate(Scanner sc) {
        String date;
        while (true) {
            date = sc.nextLine();
            try {
                LocalDate releaseDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                if (!releaseDate.isBefore(LocalDate.now())) {
                    break;
                } else {
                    System.out.println("Release date must be today or in the future. Valid Format (YYYY-MM-DD):");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date (YYYY-MM-DD):");
            }
        }
        return date;
    }

    private String getValidStatus(Scanner sc) {
        String status;
        while (true) {
            status = sc.nextLine().trim().toLowerCase();
            if (status.equals("medicine not released") || status.equals("medicine released") || status.equals("pending")) {
                break;
            } else {
                System.out.println(" Please enter 'medicine not released', 'medicine released', or 'pending':");
            }
        }
        return status;
    }

    private void viewMedRelease() {
        String qry = "SELECT * FROM medicinerelease";
        String[] hrds = {"Release ID", "Appointment ID", "Medicine ID", "Quantity", "Release Date", "Status"};
        String[] clms = {"mr_id", "a_id", "med_id", "quantity", "m_release", "m_status"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

   private void updateMedRelease() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    System.out.print("Enter the ID of the medicine release record to update: ");
    int releaseId = getValidReleaseId(sc, conf);
    

    
    String status = getValidStatus(sc);

    
    String updateQry = "UPDATE medicinerelease SET m_status = ? WHERE mr_id = ?";
    conf.updateRecord(updateQry, status, Integer.toString(releaseId));

    System.out.println("Medicine release status updated successfully!");
}



    private int getValidReleaseId(Scanner sc, config conf) {
        int id;
        String sql = "SELECT mr_id FROM medicinerelease WHERE mr_id = ?";
        while (true) {
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (conf.getSingleValue(sql, id) != 0) {
                    break;
                } else {
                    System.out.println("Record not found. Please enter a valid ID.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return id;
    }

    private boolean getYesNoResponse(Scanner sc) {
        String response;
        while (true) {
            response = sc.next().trim().toLowerCase();
            if (response.equals("yes") || response.equals("no")) {
                return response.equals("yes");
            } else {
                System.out.println("Invalid response. Please enter 'yes' or 'no':");
            }
        }
    }

    private void deleteMedRelease() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter the ID of the medicine release record to delete: ");
        int releaseId = getValidReleaseId(sc, conf);

        String deleteQuery = "DELETE FROM medicinerelease WHERE mr_id = ?";
        conf.deleteRecord(deleteQuery, Integer.toString(releaseId));

        System.out.println("Medicine release record deleted successfully!");
    }
}
