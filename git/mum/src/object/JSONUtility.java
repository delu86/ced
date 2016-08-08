/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 *
 * @author CRE0260
 */
public class JSONUtility {

    
    
    public static JsonObject getJsonObject(String fullPath) throws FileNotFoundException{
        JsonReader reader=Json.createReader(new FileReader(fullPath));
        return (JsonObject) reader.read();
    }}