/*
 * 
 */
package stats;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.DatabaseConnector;

/**
 *
 * @author CRE0260
 */
public class NotAvailableIntervalGenerator {
    
    
    private Connection connection;
    private PreparedStatement statement_insert;
    private PreparedStatement statement_select;
    private ResultSet resultSet;
    public final String selectString;
    public final String insertString;
   
    public NotAvailableIntervalGenerator(){
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DatabaseConnector.DB_RESOURCES);
        selectString=resourceBundle.getString("SELECT_FOR_INTERVAL");
        insertString=resourceBundle.getString("INSERT_INTERVAL");
        try {
            connection=DatabaseConnector.getConnection();
            statement_select=connection.prepareStatement(selectString);
            resultSet=statement_select.executeQuery();
            generateNotAvailableInterval();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NotAvailableIntervalGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void generateNotAvailableInterval(){
        String actualCodAtm="";
        String start,end,opecodeStart,opecodeEnd;
        int state=1;
        try {
            while(resultSet.next()){
                String codAtm=resultSet.getString("codAtm");
              if(actualCodAtm.equals(codAtm)){
                if(state==1){//atm attualmente disponibile
                    String available=resultSet.getString("disp");
                    if(!(available.equals("1")||available.equals("=")))
                    {
                        state=Integer.parseInt(available);
                        opecodeStart=resultSet.getString("opecode");
                        if(opecodeStart.equals("294")){
                            start=resultSet.getString("datetime");
                        }
                        else{//opecode!=294
                            String anomCod=resultSet.getString("anomCod");
                            if(anomCod.equals("54")){//se anomalia di tipo 54 (collegamento fra sportello e unità di controllo assente)
                                start=resultSet.getString("datetime");
                                end=  resultSet.getString("logdatim");
                                writeRecord(start,end,opecodeStart,opecodeStart,actualCodAtm,null);
                            }
                            else{//altro tipo di anomalia bloccante
                                start=resultSet.getString("datetime");
                            }
                        }
                    }
                }else{//atm attualmente indisponibile
                    
                }
              }
              else{//nuovo atm
                  
              }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NotAvailableIntervalGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeRecord(String start, String end, String opecodeStart, String opecodeEnd, String actualCodAtm, String anomCod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
