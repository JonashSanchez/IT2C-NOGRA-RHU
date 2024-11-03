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
public class Appointment {
    
     public void aAppointment(){
        
          Scanner sc=new Scanner(System.in);
        String response;
        do{
        System.out.println("\n----------------------");
        System.out.println("APPOINTMENT PANEL");
        System.out.println("1.ADD APPOINTMENT");
        System.out.println("2.VIEW APPOINTMENT");
        System.out.println("3.UPDATE APPOINTMENT");
        System.out.println("4.DELETE APPOINTMENT");
        System.out.println("5,EXIT");
        
        System.out.println("Enter Selection");
        int act = sc.nextInt();
        Appointment a  = new Appointment();
        switch(act){
            
            case 1:
                addAppointment();
                 viewAppointment();
                
                
           
               
                break;
            case 2:
                viewAppointment();
               
             
                
                
                break;
            case 3:
              
               
              
                break;
                
            case 4:  
              
                break;
                
            case 5:
                
                break;
                
            
        }
        System.out.println("Do you want to continue?(yes/no)");
        response = sc.next();
        
        
        
    }while(response.equalsIgnoreCase("yes"));
        
   }
    private void addAppointment(){
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        
        Patient cs= new Patient();
        cs.viewPatients();
        System.out.print("Enter the ID of the  Patient");
        int pid = sc.nextInt();
        
        String sql = "SELECT p_id FROM patients WHERE p_id = ? ";
        while(conf.getSingleValue(sql, pid) == 0){
            System.out.println("Patient does not Exist, Select Again");
            pid = sc.nextInt();
        }
        
       System.out.print("Enter appointment date (YYYY-MM-DD): ");
    sc.nextLine();  
    String date = sc.nextLine();
    
    
    System.out.print("Enter appointment purpose: ");
    String purpose = sc.nextLine();
    
    
    System.out.print("Enter appointment status: ");
    String status = sc.nextLine();
    
    
    String appointmentqry = "INSERT INTO appointment (p_id, a_date, a_purpose, a_status) VALUES (?, ?, ?, ?)";
    conf.addRecord(appointmentqry, Integer.toString(pid), date, purpose, status);
    
    System.out.println("Appointment added successfully!");
        
        
        
        
        
    }
    
   public void viewAppointment() {
    String qry = "SELECT a_id, p_name, p_lname, p_email, p_contact, a_date, a_purpose, a_status FROM appointment "
            + "LEFT JOIN patients ON patients.p_id = appointment.p_id";
    String[] hrds = {"ID", "First Name", "Last Name", "Email", "Contact Number", "Date", "Purpose", "Status"};
    String[] clms = {"a_id", "p_name", "p_lname", "p_email", "p_contact", "a_date", "a_purpose", "a_status"};
    config conf = new config();
    conf.viewRecords(qry, hrds, clms);
}

     
     
}
