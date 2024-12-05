package it2c.nogra.rhuas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author
 */
public class Medicine {

    public void mTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n######################");
            System.out.println("#   MEDICINE PANEL   #");
            System.out.println("######################");
            System.out.println("1. ADD MEDICINE");
            System.out.println("2. VIEW MEDICINE");
            System.out.println("3. UPDATE MEDICINE");
            System.out.println("4. DELETE MEDICINE");
            System.out.println("5. EXIT");

            int act = getValidAction(sc, 1, 5);

            Medicine m = new Medicine();
            switch (act) {
                case 1:
                    m.addMedicine();
                    m.viewMedicine();
                    break;
                case 2:
                    m.viewMedicine();
                    break;
                case 3:
                    m.viewMedicine();
                    m.updateMedicine();
                    m.viewMedicine();
                    break;
                case 4:
                    m.viewMedicine();
                    m.deleteMedicine();
                    m.viewMedicine();
                    break;
                case 5:
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
            System.out.println("\n----------------------");
            System.out.println("Enter Selection:");
            if (sc.hasNextInt()) {
                action = sc.nextInt();
                sc.nextLine(); 
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

    public void addMedicine() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.println("\n----------------------");
        String mname = getValidMedicineName(sc, "Medicine Name:");
        
        System.out.println("\n----------------------");
        String mindi = getValidMedicineName(sc, "Medicine Indication:");
        
        System.out.println("\n----------------------");
        System.out.println("Expiry (YYYY-MM-DD):");
        String expiry = getValidExpiryDate(sc);
        
        System.out.println("\n----------------------");
        System.out.println("Stocks (0 or a positive integer):");
        int stock = getValidStock(sc);

        String sql = "INSERT INTO medicines (m_name, m_indication, m_expiry, m_stocks) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, mname, mindi, expiry, Integer.toString(stock));

        System.out.println("Medicine added successfully!");
    }

    private String getValidMedicineName(Scanner sc, String prompt) {
        String name;
        while (true) {
            System.out.println(prompt);
            name = sc.nextLine();
            if (name.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid input. Only letters and spaces are allowed. Please try again.");
            }
        }
        return name;
    }

    private String getValidExpiryDate(Scanner sc) {
        String expiry;
        while (true) {
            System.out.println("\n----------------------");
            expiry = sc.nextLine();
            try {
                LocalDate date = LocalDate.parse(expiry, DateTimeFormatter.ISO_LOCAL_DATE);
                if (!date.isBefore(LocalDate.now())) {
                    break; 
                } else {
                    System.out.println("Expiry date cannot be in the past. Please enter a valid date (YYYY-MM-DD):");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD):");
            }
        }
        return expiry;
    }

    private int getValidStock(Scanner sc) {
        int stock;
        while (true) {
            System.out.println("\n----------------------");
            if (sc.hasNextInt()) {
                stock = sc.nextInt();
                sc.nextLine(); 
                if (stock >= 0) {
                    break;
                } else {
                    System.out.println("Stock must be a non-negative integer. Please enter again:");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next(); 
            }
        }
        return stock;
    }

    public void viewMedicine() {
        String qry = "SELECT * FROM medicines";
        String[] hrds = {"ID", "Name", "Indication", "Expiry", "Stock"};
        String[] clms = {"med_id", "m_name", "m_indication", "m_expiry", "m_stocks"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }


    private void updateMedicine() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.println("\n----------------------");
        System.out.println("Enter Medicine ID:");
        int id = getValidMedicineId(sc, conf);

        System.out.println("\n----------------------");
        System.out.println("Enter the new stock (0 or a positive integer):");
        int stock = getValidStock(sc);

        String qry = "UPDATE medicines SET m_stocks = ? WHERE med_id = ?";
        conf.updateRecord(qry, Integer.toString(stock), Integer.toString(id));

        System.out.println("Medicine updated successfully!");
    }

    private void deleteMedicine() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.println("\n----------------------");
        System.out.println("Enter Medicine ID to delete:");
        int id = getValidMedicineId(sc, conf);

        String sqlDelete = "DELETE FROM medicines WHERE med_id = ?";
        conf.deleteRecord(sqlDelete, Integer.toString(id));

        System.out.println("Medicine deleted successfully!");
    }

    private int getValidMedicineId(Scanner sc, config conf) {
        int id;
        while (true) {
            System.out.println("\n----------------------");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT med_id FROM medicines WHERE med_id = ?", id) != 0) {
                    break;
                } else {
                    System.out.println("Selected ID doesn't exist. Please select a valid Medicine ID:");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next(); 
            }
        }
        return id;
    }
}
