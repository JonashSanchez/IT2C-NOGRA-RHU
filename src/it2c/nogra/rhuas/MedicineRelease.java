package it2c.nogra.rhuas;

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
        String response;
        do {
            System.out.println("\n----------------------");
            System.out.println("MEDICINE RELEASE PANEL");
            System.out.println("1. ADD MEDRELEASE");
            System.out.println("2. VIEW MEDICINE RELEASE");
            System.out.println("3. UPDATE MEDICINE RELEASE");
            System.out.println("4. DELETE MEDICINE RELEASE");
            System.out.println("5. EXIT");

            int act = getValidAction(sc, 1, 5);

            MedicineRelease mr = new MedicineRelease();

            switch (act) {
                case 1:
                    mr.addMedRelease();
                    mr.viewMedRelease();
                    break;
                case 2:
                    mr.viewMedRelease();
                    break;
                case 3:
                    mr.viewMedRelease();
                    mr.updateMedRelease();
                    mr.viewMedRelease();
                    break;
                case 4:
                    mr.viewMedRelease();
                    mr.deleteMedRelease();
                    mr.viewMedRelease();
                    break;
                case 5:
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
            sc.next(); // clear the invalid input
        }
    }
    return action;
}


    private void addMedRelease() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        Patient cs = new Patient();
        cs.viewPatients();
        System.out.print("Enter the ID of the Patient: ");
        int pid = getValidPatientId(sc, conf);

        // Only display medicines that are in stock
        String msql = "SELECT med_id, m_name, m_stocks FROM medicines WHERE m_stocks > 0";
        System.out.println("Available Medicines:");
        System.out.printf("%-10s %-30s %-10s%n", "ID", "Medicine Name", "Stock");
        System.out.println("-------------------------------------------");
        conf.viewRecords(msql, new String[]{"ID", "Medicine Name", "Stock"}, new String[]{"med_id", "m_name", "m_stocks"});

        System.out.print("Enter the ID of the Medicine: ");
        int mid = getValidMedicineId(sc, conf);

        System.out.print("Enter quantity to release: ");
        int quantity = getValidQuantity(sc, conf, mid);

        sc.nextLine(); // consume the newline

        System.out.print("Enter the release date (YYYY-MM-DD): ");
        String releaseDate = getValidReleaseDate(sc);

        System.out.print("Enter the release status (medicine not released/medicine released/pending): ");
        String status = getValidStatus(sc);

        String medReleaseQry = "INSERT INTO medicinerelease (p_id, med_id, quantity, m_release, m_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(medReleaseQry, Integer.toString(pid), Integer.toString(mid), Integer.toString(quantity), releaseDate, status);

        // Update the stock in medicines table
        String updateStockQry = "UPDATE medicines SET m_stocks = m_stocks - ? WHERE med_id = ?";
        conf.updateRecord(updateStockQry, Integer.toString(quantity), Integer.toString(mid));

        System.out.println("Medicine release added successfully!");
    }

    private int getValidPatientId(Scanner sc, config conf) {
        int pid;
        String sql = "SELECT p_id FROM patients WHERE p_id = ?";
        while (true) {
            if (sc.hasNextInt()) {
                pid = sc.nextInt();
                if (conf.getSingleValue(sql, pid) != 0) {
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
                if (releaseDate.isAfter(LocalDate.now())) {
                    break;
                } else {
                    System.out.println("Release date must be in the future. Valid Format (YYYY-MM-DD):");
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
                System.out.println("Invalid status. Please enter 'medicine not released', 'medicine released', or 'pending':");
            }
        }
        return status;
    }

    private void viewMedRelease() {
        String qry = "SELECT * FROM medicinerelease";
        String[] hrds = {"Release ID", "Patient ID", "Medicine ID", "Quantity", "Release Date", "Status"};
        String[] clms = {"mr_id", "p_id", "med_id", "quantity", "m_release", "m_status"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    private void updateMedRelease() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter the ID of the medicine release record to update: ");
        int releaseId = getValidReleaseId(sc, conf);

        System.out.print("Enter new quantity: ");
        int quantity = getValidQuantity(sc, conf, releaseId);

        sc.nextLine(); // consume the newline

        System.out.print("Enter new release date (YYYY-MM-DD): ");
        String releaseDate = getValidReleaseDate(sc);

        System.out.print("Enter new status (medicine not released/medicine released/pending): ");
        String status = getValidStatus(sc);

        String updateQry = "UPDATE medicinerelease SET quantity = ?, m_release = ?, m_status = ? WHERE mr_id = ?";
        conf.updateRecord(updateQry, Integer.toString(quantity), releaseDate, status, Integer.toString(releaseId));

        System.out.println("Medicine release record updated successfully!");
    }

    private int getValidReleaseId(Scanner sc, config conf) {
        int releaseId;
        String sql = "SELECT mr_id FROM medicinerelease WHERE mr_id = ?";
        while (true) {
            if (sc.hasNextInt()) {
                releaseId = sc.nextInt();
                if (conf.getSingleValue(sql, releaseId) != 0) {
                    break;
                } else {
                    System.out.println("Record does not exist, select again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return releaseId;
    }

    private void deleteMedRelease() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter the ID of the medicine release record to delete: ");
        int releaseId = getValidReleaseId(sc, conf);

        String deleteQry = "DELETE FROM medicinerelease WHERE mr_id = ?";
        conf.deleteRecord(deleteQry, Integer.toString(releaseId));

        System.out.println("Medicine release record deleted successfully!");
    }
}
