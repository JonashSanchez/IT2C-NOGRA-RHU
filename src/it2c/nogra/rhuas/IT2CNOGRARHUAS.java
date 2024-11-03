package it2c.nogra.rhuas;

import java.util.Scanner;

public class IT2CNOGRARHUAS {
    
    
      

//    public void addPatients() {
//        Scanner sc = new Scanner(System.in);
//        config conf = new config();
//        System.out.println("First Name:");
//        String fname = sc.next();
//        System.out.println("Last Name:");
//        String lname = sc.next();
//        System.out.println("Email:");
//        String email = sc.next();
//        System.out.println("Appointment Date:");
//        String adate = sc.next();
//        String sql = "INSERT INTO patients (p_name, p_lname, p_email, p_appointmentdate) VALUES (?, ?, ?, ?)";
//        conf.addRecord(sql, fname, lname, email, adate);
//    }
//     private void viewPatients() {
//        String patientsQuery = "SELECT * FROM patients";
//        String[] patientsHeaders = {"ID", "First Name", "Last Name", "Email", "Appointment Date"};
//        String[] patientsColumns = {"p_id", "p_name", "p_lname", "p_email", "p_appointmentdate"};
//        config conf = new config();
//        conf.viewRecords(patientsQuery, patientsHeaders, patientsColumns);
//    }
     
//     private void updatePatient(){
//         Scanner sc = new Scanner(System.in);
//             System.out.println("Enter Patient ID:");
//             int id = sc.nextInt();
//          
//             
//           System.out.println("Enter the new first name:");
//           String ufname = sc.next();
//           
//             System.out.println("Enter the new last name:");
//           String ulname = sc.next();
//           
//             System.out.println("Enter new last email:");
//           String uemail = sc.next();
//           
//             System.out.println("Enter new appointment date:");
//           String udate = sc.next();
//             
//           String qry = "UPDATE patients SET p_name = ?, p_lname = ?, p_email = ?, p_appointmentdate = ? WHERE p_id =?";
//           
//           config conf = new config();
//           conf.updateRecord(qry, ufname, ulname, uemail, udate, id);
//         
//     }
             
             
             
//    private void deletePatient(){
//         Scanner sc = new Scanner(System.in);
//             System.out.println("Enter Patient ID to delete:");
//             int id = sc.nextInt();
//             String sqlDelete = "DELETE FROM patients WHERE p_id = ?";
//           config conf = new config();
//           conf.deletePatient(sqlDelete, id);
//           
//    }       
    
    
     
     
    
     
    
    public static void main(String[] args) {
        
        Scanner sc= new Scanner(System.in);
        boolean exit = true;
        do{
        System.out.println("WELCOME TO RHU APPOINTMENT");
        System.out.println("");
        System.out.println("1. PATIENT");
        System.out.println("2. MEDICINE");
        System.out.println("3. RHU APPOINTMENT");
        System.out.println("4.MEDICINE RELEASE");
        System.out.println("5. REPORTS");
        System.out.println("6.EXIT");
        
        System.out.println("Enter Action:");
        int action = sc.nextInt();
        
        switch(action){
            case 1:
            Patient cs = new Patient();
            cs.pRHU();
            break;
            
            case 2:
                Medicine m = new Medicine();
                m.mTransaction();
                
                break;
             
            case 3:
                 Appointment a  = new Appointment();
                 a.aAppointment();
                
                break;
                
            case 4:
                    MedicineRelease mr = new MedicineRelease();
                    mr.mRelease();
                break;
            
            case 6:
                System.out.println("Exit Selected...type 'yes' to continue");
                String resp=sc.next();
                if(resp.equalsIgnoreCase("yes")){
                exit = false;
                }
                
            break;
        }
        
            System.out.println("Do you ");
      }while(exit);
        
        
     
        
        
        
        
//        Scanner sc = new Scanner(System.in);
//        IT2CNOGRARHUAS demo = new IT2CNOGRARHUAS();
//        String response;
//
//        do {
//            System.out.println("1. ADD");
//            System.out.println("2. VIEW");
//            System.out.println("3. UPDATE");
//            System.out.println("4. DELETE");
//            System.out.println("5. EXIT");
//
//            System.out.println("SELECT ACTION:");
//            int action = sc.nextInt();
//
//            switch(action) {
//                case 1:               
//                    demo.addPatients();
//                    
//                    break;
//                case 2:
//                    demo.viewPatients();
//                    break;
//                
//                case 3:
//                    demo.viewPatients();
//                    demo.updatePatient();
//                    break;
//               
//                    
//                case 4:
//                    demo.viewPatients();
//                    
//                    demo.deletePatient();
//                    demo.viewPatients();
//                    break;
//                case 5:
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//            System.out.println("Continue? (yes/no):");
//            response = sc.next();
//        } while(response.equals("yes"));
//        System.out.println("Thank You");
//    }
}
}
