package it2c.nogra.rhuas;

import java.util.Scanner;

public class IT2CNOGRARHUAS {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        do {
            System.out.println("\n###########################");
            System.out.println("# WELCOME TO RHU APPOINTMENT #");
            System.out.println("###########################");
            System.out.println("1. PATIENT");
            System.out.println("2. MEDICINE");
            System.out.println("3. RHU APPOINTMENT");
            System.out.println("4. MEDICINE RELEASE");
            System.out.println("5. REPORTS");
            System.out.println("6. EXIT");
            System.out.println("\n---------------------------");

            int action = 0;
            while (true) {
                System.out.println("Enter Action (1-6):");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 6) {
                        break;
                    }
                }
                sc.nextLine();
                System.out.println("Invalid selection. Please enter a number between 1 and 6.");
                System.out.println("\n---------------------------");
            }

            switch (action) {
                case 1:
                    Patient cs = new Patient();
                    cs.pRHU();
                    break;

                case 2:
                    Medicine m = new Medicine();
                    m.mTransaction();
                    break;

                case 3:
                    Appointment a = new Appointment();
                    a.aAppointment();
                    break;

                case 4:
                    MedicineRelease mr = new MedicineRelease();
                    mr.mRelease();
                    break;

                case 5:
                    Reports reports = new Reports();
                    reports.showReports();
                    break;

                case 6:
                    System.out.println("\n---------------------------");
                    System.out.println("Exit Selected...type 'yes' to continue");
                    String resp = sc.next();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    System.out.println("\n---------------------------");
                    break;
            }
        } while (exit);
    }
}
