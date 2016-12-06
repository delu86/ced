/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.AbstractATMParser;

/**
 *
 * @author CRE0260
 */
public class DatabaseConnector {
    
    public final static String DB_RESOURCES="database.properties";
    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        PropertyResourceBundle resourceBundle;
                try (FileInputStream fis = new FileInputStream(DB_RESOURCES)) {
                    resourceBundle=new PropertyResourceBundle(fis);
                    Class.forName(resourceBundle.getString("driver"));
                   return DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
                }
                catch (FileNotFoundException ex) {
                    
            Logger.getLogger(AbstractATMParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(AbstractATMParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
   
    
}
