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
public class Patient {
    public void pRHU(){
        
        Scanner sc=new Scanner(System.in);
        String response;
        do{
        System.out.println("\n----------------------");
        System.out.println("PATIENT PANEL");
        System.out.println("1.ADD PATIENT");
        System.out.println("2.VIEW PATIENT");
        System.out.println("3.UPDATE PATIENT");
        System.out.println("4.DELETE PATIENT");
        System.out.println("5,EXIT");
        
        System.out.println("Enter Selection");
        int act = sc.nextInt();
        Patient cs= new Patient();
        switch(act){
            
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
        System.out.println("Do you want to continue?(yes/no)");
        response = sc.next();
        
        
        
    }while(response.equalsIgnoreCase("yes"));
        
   }
    
    public void addPatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("First Name:");
        String fname = sc.next();
        System.out.println("Last Name:");
        String lname = sc.next();
        System.out.println("Email:");
        String email = sc.next();
        System.out.println("Contact Number:");
        String adate = sc.next();
        String sql = "INSERT INTO patients (p_name, p_lname, p_email, p_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, adate);
    }
    public void viewPatients() {
        String patientsQuery = "SELECT * FROM patients";
        String[] patientsHeaders = {"ID", "First Name", "Last Name", "Email", "Contact Number"};
        String[] patientsColumns = {"p_id", "p_name", "p_lname", "p_email", "p_contact"};
        config conf = new config();
        conf.viewRecords(patientsQuery, patientsHeaders, patientsColumns);
    }
    
         private void updatePatient(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
             System.out.println("Enter Patient ID:");
             int id = sc.nextInt();
             
          while(conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.println("Select Patient ID Again");
              id=sc.nextInt();
              
          }
          
             
           System.out.println("Enter the new first name:");
           String ufname = sc.next();
           
             System.out.println("Enter the new last name:");
           String ulname = sc.next();
           
             System.out.println("Enter new last email:");
           String uemail = sc.next();
           
             System.out.println("Enter new contact number:");
           String udate = sc.next();
             
           String qry = "UPDATE patients SET p_name = ?, p_lname = ?, p_email = ?, p_contact = ? WHERE p_id =?";
           
           
           conf.updateRecord(qry, ufname, ulname, uemail, udate, id);
         
     }
             private void deletePatient(){
         Scanner sc = new Scanner(System.in);
          config conf = new config();
             System.out.println("Enter Patient ID to delete:");
             int id = sc.nextInt();
             
               while(conf.getSingleValue("SELECT p_id FROM patients WHERE p_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.println("Select Patient ID Again");
              id=sc.nextInt();
              
          }
             String sqlDelete = "DELETE FROM patients WHERE p_id = ?";
          
           conf.deleteRecord(sqlDelete, id);
           
    }      
    
}
