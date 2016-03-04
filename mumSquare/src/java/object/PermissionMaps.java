/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 * @author CRE0260
 */
public class PermissionMaps {
    
    public static final HashMap<String,String[]> systemsPermissionMap=initializeSystemPermissionMaps();

    private static HashMap<String, String[]> initializeSystemPermissionMaps() {
         HashMap<String, String[]> hashMap=new HashMap<>();
         ResourceBundle resourceBundle=ResourceBundle.getBundle(StringConstants.SYSTEM_PERMISSION_PROPERTIES);
         Enumeration<String> systems=resourceBundle.getKeys();
         while(systems.hasMoreElements()){
             String next=systems.nextElement();
             hashMap.put(next, resourceBundle.getString(next).split(StringConstants.PROPERTIES_SPLIT_CHARACTERS));
         }
         return hashMap;
    }
    
    
}
