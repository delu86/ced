/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author CRE0260
 */
public class StringConstants {
    
    //resource db
    public final static String CED_DB="cedDB";
    public final static String EPV_DB="epvDB";
    public final static String IDAA_DB="idaaDB";
    
    public final static String ID_PARAMETER="id";
    public final static String JSON_FOLDER="/json/";
    public final static String JSON_EXTENSION=".json";
    public final static String DATABASE_JSON_KEY_NAME="database";
    public final static String QUERY_JSON_KEY_NAME="query";
    public final static String URL_DB_CONNECTION_JSON_KEY_NAME="url_connection";
    public final static String URL_DB_RESOURCE="resource_name";
    public final static String ADMITTED_USERS="admitted_users";
    public final static String PARAMETER_JSON_KEY_NAME="parameters";
    public final static String DRIVER_JSON_KEY_NAME="driver";
    public final static String CHART_VIEW_PAGE="chart.jsp";
    
    //Systems Facilities
    public final static String SYSTEM_REALE_PRODUZIONE="SIES";
    public final static String SYSTEM_REALE_SVILUPPO="SIEGE";
    public final static String SYSTEM_AMISSIMA_PRODUZIONE="ASDN";
    public final static String SYSTEM_AMISSIMA_SVILUPPO="ASSV";
    public final static String SYSTEM_CREDEM_PRODUZIONE="MVSA";
    public final static String SYSTEM_CREDEM_SVILUPPO="MVSB";
    //Systems Cedacri
    public final static String SY7="GSY7";
    public final static String SY2="BSY7";
    public final static String SY3="CSY3";
    public final static String SY5="ZSY5";
    public final static String SYA="ESYA";
    
    public final static String SYSTEM_PERMISSION_PROPERTIES="properties.systemsPermission";
    public final static String PROPERTIES_SPLIT_CHARACTERS=",";
    
    //Login error messages
    public static final String MESSAGE_ATTRIBUTE = "message";
    public static final Object MESSAGE_ERROR = "Errore: username o password errati";
    public static final Object MESSAGE_DOMAIN_ERROR = "Errore: dominio mail non autorizzato";
    public static final Object MESSAGE_ERROR_SERVER = "Errore: server momentaneamente irraggiungibile; riprovare pi� tardi";

    //Activation account  messages
    public static final String MESSAGE_OK = "message_ok";
    public static final Object MESSAGE_OK_TEXT =  "Attivazione andata a buon fine effettuare il login per accedere";
    public static final Object MESSAGE_ERROR_ACTIVATION = "Errore:attivazione non eseguita";
    //Pages
    public static final String INDEX = "login.jsp";

}
