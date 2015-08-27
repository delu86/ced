/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author CRE0260
 */
public class DatabaseConnector {
    
    public final static String DB_RESOURCES="parser.database";
    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCES);
        Class.forName(resourceBundle.getString("driver"));
        return 
               DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),
                                           resourceBundle.getString("password"));
    }
   
    
}
