/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import object.JSONUtility;
import object.StringConstants;

/**
 *
 * @author CRE0260
 */
public class JsonQueryResolver extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ResultSet rs;
        PreparedStatement ps;
        response.setContentType("application/json;");
        try  {
            PrintWriter out = response.getWriter();
            //get the full path of the json file
            JsonObject json= JSONUtility.getJsonObject(getServletContext().getRealPath(
                                    StringConstants.JSON_FOLDER
                                   +request.getParameter(StringConstants.ID_PARAMETER)
                                   +StringConstants.JSON_EXTENSION));
            JsonObject dbObject = json.getJsonObject(StringConstants.DATABASE_JSON_KEY_NAME);
            JsonArray parameters = dbObject.getJsonArray(StringConstants.PARAMETER_JSON_KEY_NAME);
            JsonArray admittedUsers = json.getJsonArray(StringConstants.ADMITTED_USERS);
            
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
            DataSource datasource=(DataSource) envContext.lookup(
                        dbObject.get(StringConstants.URL_DB_RESOURCE).toString().replace("\"", ""));
            ps=
                        datasource.getConnection().prepareStatement(dbObject.get(StringConstants.QUERY_JSON_KEY_NAME).toString().replace("\"", ""));
            int indexParam=1;
            if(parameters!=null){
            Iterator iterator=parameters.iterator();
            while(iterator.hasNext()){
                    String parameter=request.getParameter(iterator.next().toString().replace("\"", ""));
                    if(parameter.matches(StringConstants.INTEGER_PATTERN_STRING)&&
                       !parameter.equals(StringConstants.JESNUMBER_PARAMETER))
                       ps.setInt(indexParam++,Integer.parseInt(parameter));
                    else
                       ps.setString(indexParam++,parameter);
                }}
             rs=ps.executeQuery();
            JsonArrayBuilder builderDataArray= Json.createArrayBuilder();
            JsonArrayBuilder builderDataLabelsArray= Json.createArrayBuilder();
            ResultSetMetaData rsMetaData= rs.getMetaData();
            int columnCount=  rsMetaData.getColumnCount(); 
            for(int i=1;i<=rsMetaData.getColumnCount();i++){
                   builderDataLabelsArray.add(rsMetaData.getColumnLabel(i));
               }
            while(rs.next()){
                   JsonArrayBuilder builderData= Json.createArrayBuilder();
                   for(int i=1;i<=columnCount;i++){
                       builderData.add(rs.getString(i));
                   }
                   builderDataArray.add(builderData.build());
               }
            JsonObjectBuilder buildJSON=fromObjectToBuilder(json);
            //add data to final json
            buildJSON.add("data", builderDataArray.build());
            buildJSON.add("dataLabel", builderDataLabelsArray.build());
            //write the json with on the response
            JsonWriter jsonWriter = Json.createWriter(out);
            jsonWriter.write(buildJSON.build());
            }
        catch (NamingException ex) {
                Logger.getLogger(JsonQueryResolver.class.getName()).log(Level.SEVERE, null, ex);
            } 
        catch (SQLException ex) {
                Logger.getLogger(JsonQueryResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
    } 
            
        
    
    
    public static JsonObjectBuilder fromObjectToBuilder(JsonObject obj) {
        JsonObjectBuilder builder=Json.createObjectBuilder();
        for(Map.Entry<String, JsonValue> entry :obj.entrySet()){
        builder.add(entry.getKey(),entry.getValue());
    }
        return builder;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}