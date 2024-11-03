/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it2c.nogra.rhuas;

import java.util.Scanner;

/**
 *
 * @author Hazel Nogra
 */
public class MedicineRelease {
    
    public void mRelease(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n----------------------");
            System.out.println("MEDICINE RELEASE PANEL");
            System.out.println("1.ADD MEDRELEASE");
            System.out.println("2.VIEW MEDICINE RELEASE");
            System.out.println("3.UPDATE MEDICINE RELEASE");
            System.out.println("4.DELETE MEDICINE RELEASE");
            System.out.println("5.EXIT");
        
            System.out.println("Enter Selection");
            int act = sc.nextInt();
            MedicineRelease mr = new MedicineRelease();
        
            switch(act) {
                case 1:
                    addMedRelease();
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
                    break;
                case 5:
                    break;
            }
            System.out.println("Do you want to continue?(yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }
    
    private void addMedRelease(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        Patient cs = new Patient();
        cs.viewPatients();
        System.out.print("Enter the ID of the Patient: ");
        int pid = sc.nextInt();
        
        String psql = "SELECT p_id FROM patients WHERE p_id = ? ";
        while (conf.getSingleValue(psql, pid) == 0) {
            System.out.println("Patient does not exist, select again.");
            pid = sc.nextInt();
        }

        Medicine m = new Medicine();
        m.viewMedicine();
        
        System.out.print("Enter the ID of the Medicine: ");
        int mid = sc.nextInt();
        
        String msql = "SELECT med_id FROM medicines WHERE med_id = ? ";
        while (conf.getSingleValue(msql, mid) == 0) {
            System.out.println("Medicine does not exist, select again.");
            mid = sc.nextInt();
        }
        
        System.out.print("Enter quantity to release: ");
        int quantity = sc.nextInt();
        
        sc.nextLine();
        
        System.out.print("Enter the release date (YYYY-MM-DD): ");
        String releaseDate = sc.nextLine();
        
        System.out.print("Enter the release status: ");
        String status = sc.nextLine();
        
        String medReleaseQry = "INSERT INTO medicinerelease (p_id, med_id, quantity, m_release, m_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(medReleaseQry, Integer.toString(pid), Integer.toString(mid), Integer.toString(quantity), releaseDate, status);
        
        System.out.println("Medicine release added successfully!");
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
        int releaseId = sc.nextInt();
        
        String sql = "SELECT mr_id FROM medicinerelease WHERE mr_id = ?";
        while (conf.getSingleValue(sql, releaseId) == 0) {
            System.out.println("Record does not exist, select again.");
            releaseId = sc.nextInt();
        }
        
        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        
        sc.nextLine();
        
        System.out.print("Enter new release date (YYYY-MM-DD): ");
        String releaseDate = sc.nextLine();
        
        System.out.print("Enter new status: ");
        String status = sc.nextLine();
        
        String updateQry = "UPDATE medicinerelease SET quantity = ?, m_release = ?, m_status = ? WHERE mr_id = ?";
        conf.updateRecord(updateQry, Integer.toString(quantity), releaseDate, status, Integer.toString(releaseId));
        
        System.out.println("Medicine release record updated successfully!");
    }
    
    private void deleteMedRelease() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter the ID of the medicine release record to delete: ");
        int releaseId = sc.nextInt();
        
        String sql = "SELECT mr_id FROM medicinerelease WHERE mr_id = ?";
        while (conf.getSingleValue(sql, releaseId) == 0) {
            System.out.println("Record does not exist, select again.");
            releaseId = sc.nextInt();
        }
        
        String deleteQry = "DELETE FROM medicinerelease WHERE mr_id = ?";
        conf.deleteRecord(deleteQry, Integer.toString(releaseId));
        
        System.out.println("Medicine release record deleted successfully!");
    }
}
