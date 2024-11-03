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
public class Medicine {
    
    public void mTransaction(){
        
          Scanner sc=new Scanner(System.in);
        String response;
        do{
        System.out.println("\n----------------------");
        System.out.println("PATIENT PANEL");
        System.out.println("1.ADD MEDICINE");
        System.out.println("2.VIEW MEDICINE");
        System.out.println("3.UPDATE MEDICINE");
        System.out.println("4.DELETE MEDICINE");
        System.out.println("5,EXIT");
        
        System.out.println("Enter Selection");
        int act = sc.nextInt();
        Medicine m = new Medicine();
        switch(act){
            
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
        System.out.println("Do you want to continue?(yes/no)");
        response = sc.next();
        
        
        
    }while(response.equalsIgnoreCase("yes"));
        
   }
    
    public void addMedicine(){
         Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Medicine Name:");
        String mname = sc.next();
        System.out.println("Medicine Indication:");
        String mindi = sc.next();
        System.out.println("Expiry:");
        String expiry = sc.next();
        System.out.println("Stocks:");
        String stock = sc.next();
        
       
        String sql = "INSERT INTO medicines (m_name, m_indication, m_expiry, m_stocks) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, mname, mindi, expiry, stock);
        
    }
      public void viewMedicine() {
        String qry = "SELECT * FROM medicines";
        String[] hrds = {"ID", " Name", "Indication", "Expiry", "Stock"};
        String[] clms = {"med_id", "m_name", "m_indication", "m_expiry", "m_stocks"};
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }
      
            private void updateMedicine(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();
             System.out.println("Enter Medicine ID:");
             int id = sc.nextInt();
             
          while(conf.getSingleValue("SELECT med_id FROM medicines WHERE med_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.println("Select Medicine ID Again");
              id=sc.nextInt();
              
          }
          
             
           System.out.println("Enter the new stock:");
           String stock = sc.next();
           
            
            
             
           String qry = "UPDATE medicines SET m_stocks = ? WHERE med_id =?";
           
           
           conf.updateRecord(qry, stock , id);
         
     }
              private void deleteMedicine(){
         Scanner sc = new Scanner(System.in);
          config conf = new config();
             System.out.println("Enter Medicine ID to delete:");
             int id = sc.nextInt();
             
               while(conf.getSingleValue("SELECT med_id FROM medicines WHERE med_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.println("Select Medicine  ID Again");
              id=sc.nextInt();
              
          }
             String sqlDelete = "DELETE FROM medicines WHERE med_id = ?";
          
           conf.deleteRecord(sqlDelete, id);
           
    }      
        
        
        
        
     }
    
    
    
    
    

