package it2c.nogra.rhuas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Hazel Nogra
 */
public class Appointment {

    public void aAppointment() {
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n----------------------");
            System.out.println("APPOINTMENT PANEL");
            System.out.println("1. ADD APPOINTMENT");
            System.out.println("2. VIEW APPOINTMENT");
            System.out.println("3. UPDATE APPOINTMENT");
            System.out.println("4. DELETE APPOINTMENT");
            System.out.println("5. EXIT");
            System.out.println("\n----------------------");

            int act = getValidAction(sc, 1, 5);

            Appointment a = new Appointment();

            switch (act) {
                case 1:
                    a.addAppointment();
                    a.viewAppointment();
                    break;
                case 2:
                    a.viewAppointment();
                    break;
                case 3:
                    a.viewAppointment();
                    a.updateAppointment();
                    a.viewAppointment();
                    break;
                case 4:
                    a.viewAppointment();
                    a.deleteAppointment();
                    a.viewAppointment();
                    break;
                case 5:
                    break;
            }
            System.out.println("\n----------------------");
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

    private void addAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        Patient cs = new Patient();
        cs.viewPatients();
        System.out.print("Enter the ID of the Patient: ");
        int pid = getValidPatientId(sc, conf);

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = getValidAppointmentDate(sc);

        System.out.println("Available Services: ");
        String[] services = {"Consultation", "Check-up", "Vaccination", "Screening"};
        for (int i = 0; i < services.length; i++) {
            System.out.println((i + 1) + ". " + services[i]);
        }
        System.out.print("Enter appointment purpose (choose a number): ");
        String purpose = getValidPurpose(sc, services);

        System.out.print("Enter appointment status (pending/done): ");
        String status = getValidStatus(sc);

        String appointmentqry = "INSERT INTO appointment (p_id, a_date, a_purpose, a_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(appointmentqry, Integer.toString(pid), date, purpose, status);

        System.out.println("Appointment added successfully!");
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

    private String getValidAppointmentDate(Scanner sc) {
        String date;
        while (true) {
            date = sc.nextLine();
            try {
                LocalDate appointmentDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                if (appointmentDate.isAfter(LocalDate.now())) {
                    break; // Valid date
                } else {
                    System.out.println("Appointment date must be in the future. Please enter a valid date (YYYY-MM-DD):");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date (YYYY-MM-DD):");
            }
        }
        return date;
    }

    private String getValidPurpose(Scanner sc, String[] services) {
        int choice;
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= services.length) {
                    break; // Valid choice
                } else {
                    System.out.println("Invalid choice. Please choose a number from the list:");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return services[choice - 1];
    }

    private String getValidStatus(Scanner sc) {
        String status;
        while (true) {
            status = sc.nextLine().trim().toLowerCase();
            if (status.equals("pending") || status.equals("done")) {
                break; 
            } else {
                System.out.println("Please enter either 'pending' or 'done':");
            }
        }
        return status;
    }

    public void viewAppointment() {
        String qry = "SELECT a_id, p_name, p_lname, p_email, p_contact, a_date, a_purpose, a_status FROM appointment "
                + "LEFT JOIN patients ON patients.p_id = appointment.p_id";
        String[] hrds = {"ID", "First Name", "Last Name", "Email", "Contact Number", "Date", "Purpose", "Status"};
        String[] clms = {"a_id", "p_name", "p_lname", "p_email", "p_contact", "a_date", "a_purpose", "a_status"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    private void updateAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter the ID of the appointment record to update: ");
        int appointmentId = getValidAppointmentId(sc, conf);

        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String date = getValidAppointmentDate(sc);

        System.out.println("Available Services: ");
        String[] services = {"Consultation", "Check-up", "Vaccination", "Screening"};
        for (int i = 0; i < services.length; i++) {
            System.out.println((i + 1) + ". " + services[i]);
        }
        System.out.print("Enter new appointment purpose (choose a number): ");
        String purpose = getValidPurpose(sc, services);

        System.out.print("Enter new appointment status (pending/done): ");
        String status = getValidStatus(sc);

        String updateQry = "UPDATE appointment SET a_date = ?, a_purpose = ?, a_status = ? WHERE a_id = ?";
        conf.updateRecord(updateQry, date, purpose, status, Integer.toString(appointmentId));

        System.out.println("Appointment record updated successfully!");
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
                    System.out.println("Record does not exist, select again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
        return appointmentId;
    }

    private void deleteAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter the ID of the appointment record to delete: ");
        int appointmentId = getValidAppointmentId(sc, conf);

        String deleteQry = "DELETE FROM appointment WHERE a_id = ?";
        conf.deleteRecord(deleteQry, Integer.toString(appointmentId));

        System.out.println("Appointment record deleted successfully!");
    }
}
