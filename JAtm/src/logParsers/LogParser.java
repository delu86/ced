/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CRE0260
 */
public class LogParser {
    
    protected  Connection connection=null;
    protected PreparedStatement statement =null;
    protected final String DB_RESOURCE_PATH="logParsers.database";
   
    protected   final String LOG_FOLDER_PATH="C:\\Users\\cre0260\\Desktop\\ATM\\";
    
    /**
     *formato dei datetime
     */
    protected   final String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    
        //crea connessione con il database
    protected void connectDB() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCE_PATH); 
        Class.forName(resourceBundle.getString("driver"));
        connection=DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }
    //disconnessione dal database
    protected void disconnectDB(){
        if(statement!=null) try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
