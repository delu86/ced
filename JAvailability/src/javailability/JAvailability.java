/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package javailability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author CRE0260
 */
public class JAvailability {

    
    public static int matrix [][]=new int[144][31];
    public static final String SELECT="SELECT substr(SMF30ISS, 9 , 2) as giorno, substr(SMF30ISS, 12 , 2) as ora, \n" +
                                      "substr(SMF30ISS, 15 , 1) as minute_slot,round(SUM(EXECTM)/600 , 0)\n" +
                                      "from CR00515.EPV30_23_INTRVL a where SMF30JBN=? and "
                                    + "substr(SMF30ISS, 1 , 7)=? \n" +
                                      "group by substr(SMF30ISS, 9 , 2),substr(SMF30ISS, 12 , 2),"
                                    + "substr(SMF30ISS, 15 , 1)  order  by 1 , 2 , 3;";
    public static final String SELECT_WITH_SYSTEM_FILTER="SELECT substr(SMF30ISS, 9 , 2) as giorno, substr(SMF30ISS, 12 , 2) as ora, \n" +
                                      "substr(SMF30ISS, 15 , 1) as minute_slot,round(SUM(EXECTM) , 0)\n" +
                                      "from CR00515.EPV30_23_INTRVL a where SMF30JBN=? and "
                                    + "substr(SMF30ISS, 1 , 7)=? and system=?\n" +
                                      "group by substr(SMF30ISS, 9 , 2),substr(SMF30ISS, 12 , 2),"
                                    + "substr(SMF30ISS, 15 , 1)  order  by 1 , 2 , 3;";
    
    public static void writeCSV(String applid,String date,String system){
        PropertyResourceBundle res;
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            File csv=new File(applid+"_"+date+".txt");
            res= new PropertyResourceBundle(fis);
            Class.forName(res.getString("driver"));
            connection=DriverManager.getConnection(res.getString("url"));
            if(system==null){
                ps=connection.prepareStatement(SELECT);
                ps.setString(1, applid);
                ps.setString(2, date);}
            else{
                ps=connection.prepareStatement(SELECT_WITH_SYSTEM_FILTER);
                ps.setString(1, applid);
                ps.setString(2, date);
                ps.setString(3, system);
            }
            rs=ps.executeQuery();
            while(rs.next()){
                matrix[rs.getInt(2)*6+rs.getInt(3)][rs.getInt(1)-1]=rs.getInt(4);
            }
            String line = null;
            try (FileWriter writer = new FileWriter(csv)) {
                for(int i=0;i<144;i++){
                    //int ora=i/6;
                    //int minuto=i%6;
                    //System.out.print(ora+":"+minuto+" ");
                    for(int k=0;k<31;k++){
                        //System.out.print(matrix[i][k]+";");
                        writer.write(String.valueOf(matrix[i][k])+";");
                    }
                    writer.write("\n");
                }}
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String listFile=args[0];
        String dateFilter=args[1];
        String systemFilter=null;
        if(args.length==3)
            systemFilter=args[2];
        try (BufferedReader br = new BufferedReader(new FileReader(listFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                writeCSV(line, dateFilter, systemFilter);
    }
    }   catch (FileNotFoundException ex) {
            Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JAvailability.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
